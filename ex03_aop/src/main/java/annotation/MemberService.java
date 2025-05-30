package annotation;

import org.springframework.stereotype.Component;

@Component
public class MemberService {
	
	public void regist(Member member) {
		System.out.println("MemberService.regist(Member) 메서드 호출");
	}
	
	public boolean update(String memberid, UpdateInfo info) {
		System.out.println("MemberService.Update(String,UpdateInfo) 메서드 호출");
		return true;
	}
	
	public boolean delete(String memberid,String name, UpdateInfo info) {
		System.out.println("MemberService.delete(String,String,UpdateInfo) 메서드 호출");
		return false;
	}
}
