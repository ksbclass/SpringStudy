package kr.gdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import kr.gdu.sitemesh.SiteMeshFilter;

@ServletComponentScan // 하위 패키지 모두가 Scan 대상
@SpringBootApplication 
@EnableAspectJAutoProxy
public class Shop2Application {

	public static void main(String[] args) {
		SpringApplication.run(Shop2Application.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<SiteMeshFilter> siteMeshFilter(){
		FilterRegistrationBean<SiteMeshFilter> filter =
		new FilterRegistrationBean<SiteMeshFilter>();
		
		filter.setFilter(new SiteMeshFilter());
		return filter;
	}
}
