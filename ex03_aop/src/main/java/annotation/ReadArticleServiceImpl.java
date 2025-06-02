package annotation;

import org.springframework.stereotype.Component;

@Component("readArticleService")
public class ReadArticleServiceImpl implements ReadArticleService{
	@Override
	public Article getArticleAndReadCnt(int id) throws Exception {
		System.out.println("getArticleAndReadCnt(" + id +")");
		if(id == 0) {
			throw new Exception("id는 0이 안됨");
		}
		return new Article(id);
	}
}