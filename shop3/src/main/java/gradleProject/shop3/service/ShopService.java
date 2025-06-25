package gradleProject.shop3.service;

import gradleProject.shop3.domain.Item;
import gradleProject.shop3.domain.Sale;
import gradleProject.shop3.domain.SaleItem;
import gradleProject.shop3.repository.ItemRepository;
import gradleProject.shop3.repository.SaleItemRepository;
import gradleProject.shop3.repository.SaleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service 
public class ShopService {

	@Autowired  
	private ItemRepository itemRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private SaleItemRepository saleItemRepository;

	@Value("C:/Users/user/spring_study/shop3/src/main/resources/static/")
	private String resouce_dir;

	public List<Item> itemList() {
		return itemRepository.findAll();
	}
	
	public Item getItem(Integer id) {
		return itemRepository.findById(id).get();
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		// item.getPicture() : 업로드 된 파일이 존재. 파일의 내용 저장
		if(item.getPicture() !=null && !item.getPicture().isEmpty()) {
			// 업로드 폴더 지정
//			String path = request.getServletContext().getRealPath("/")+"img/";
			String path = resouce_dir + "img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		int maxid = itemRepository.findmaxId(); // db에서 id의 최대 값 조회
		item.setId(maxid +1);
		itemRepository.save(item);
	}

	// 파일 업로드하기
	private void uploadFileCreate(MultipartFile picture, String path) {
		String orgFile = picture.getOriginalFilename(); // 원본 파일의 이름
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs(); // 폴더가 없으면 생성
		}
		try {
			// picture : 파일의 내용
			// transferTo : picture 의 내용을 new File(path+orgFile)의 위치로 저장
			picture.transferTo(new File(path+orgFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
//			String path = request.getServletContext().getRealPath("/")+"img/";
			String path = resouce_dir + "img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemRepository.save(item);
	}

	public void itemDelete(Integer id) {
		itemRepository.deleteById(id);
	}

//	public Sale checkEnd(User loginUser, Cart cart) {
//		int maxsaleid = saleDao.getMaxSaleId();
//		Sale sale = new Sale();
//		sale.setSaleid(maxsaleid + 1);
//		sale.setUser(loginUser);
//		sale.setUserid(loginUser.getUserid());
//
//		saleDao.insert(sale);
//		int seq = 0;// 주문상품 번호
//		for (ItemSet is : cart.getItemSetList()) {
//			SaleItem saleItem = new SaleItem(sale.getSaleid(), ++seq, is);
//			sale.getItemList().add(saleItem);
//			saleItemDao.insert(saleItem);
//		}
//
//		return sale;
//	}
//
public List<Sale> saleList(String userid) {
	// 오류 수정: JpaRepository 표준 메소드인 findByUserid로 변경
	List<Sale> list = saleRepository.findByUserid(userid);

	for (Sale s : list) {
		// 오류 수정: SaleItemRepository의 findBySaleid를 사용하도록 변경
		List<SaleItem> saleItemList = saleItemRepository.findBySaleid(s.getSaleid());

		for (SaleItem si : saleItemList) {
			// 오류 수정: 비표준 select 메소드 대신 findById를 사용하고, 결과가 없을 경우를 대비해 orElse(null) 처리
			Item item = itemRepository.findById(si.getItemid()).orElse(null);
			si.setItem(item);
		}
		s.setItemList(saleItemList);
	}
	return list;
}
//
//	public void exchangeCreate() {
//		Document doc = null;
//		List<List<String>> trlist = new ArrayList<List<String>>();
//		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
//		String exdate = null;
//
//		try {
//			doc = Jsoup.connect(url).get();
//			Elements trs = doc.select("tr");
//			exdate = doc.select("p.table-unit").html();
//
//			for (Element tr : trs) {
//				List<String> tdlist = new ArrayList<String>();
//				Elements tds = tr.select("td");
//				for (Element td : tds) {
//					tdlist.add(td.html());
//				}
//				if (tdlist.size() > 0) {
//					if (tdlist.get(0).equals("USD") || tdlist.get(0).equals("CNH") ||
//						tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("EUR")) {
//							trlist.add(tdlist);
//						}
//				}
//			}
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//
//		for (List<String> tds :  trlist) {
//			Exchange ex = new Exchange(
//					0, tds.get(0), tds.get(1),
//					Float.parseFloat(tds.get(2).replace(",", "")),
//					Float.parseFloat(tds.get(3).replace(",", "")),
//					Float.parseFloat(tds.get(4).replace(",", "")),
//					exdate.trim());
//			exDao.insert(ex);
//		}
//
//	}

}

























