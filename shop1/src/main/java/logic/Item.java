package logic;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data // getter,setter,toString...
public class Item {
	
	private int id;
	private String name;
	private int price;
	private String description;
	private String pictureUrl;
	private MultipartFile picture;
}
