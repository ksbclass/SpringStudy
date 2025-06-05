package controller;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.User;
import service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

    private final DataSource dataSource;

	@Autowired
	private UserService service;

    UserController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	@GetMapping("*") // GET 방식 모든 요청시 호출
	public ModelAndView form() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User());
		return mav; // url과 연동 된 뷰를 호출
	}
	
	@PostMapping("join")
	//BindingResult은 입력값 검증 대상 변수의 다음에 와야함
	public ModelAndView userAdd(@Valid User user,BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			//추가 오류 메시지 등록. global error로 추가하기
			bresult.reject("error.input.user");
			bresult.reject("error.input.check");
			return mav;
		}
		//정상적으로 입력된 경우
		try {			
			service.userInsert(user);
		} catch(DataIntegrityViolationException e) { //키값 중복된 경우
			e.printStackTrace();
			bresult.reject("error.duplicate.user");
			return mav;
		} catch(Exception e) {
			e.printStackTrace();
			return mav;
		}
		mav.setViewName("redirect:login");
		return mav;
	}
	
	/*
	 1.userid 맞는 User를 db에서 조회하기
	 2.비밀번호 검증
	 	일치:session.setAttribute("loginUser",dbUser) => 로그인 정보
	 	불일치 : 비밀번호를 확인하세요. 출력(error.login.password)
	 3.비밀번호 일치하는 경우 mypage로 페이지 이동 => 404 오류 발생(임시)
	 */
	@PostMapping("login")
	public ModelAndView login(User user,BindingResult bresult, HttpSession session) {
		
		//session : 세션
		if(user.getUserid().trim().length() < 3 || user.getUserid().trim().length() > 10) {
			// rejectValue : @Valid 어노테이션에서 등록 방식으로 처리
			// messages.properties 파일에 error.required.userid로 메시지 등록
			bresult.rejectValue("userid", "error.required");
		}
		
		if(user.getPassword().trim().length() < 3 || user.getPassword().trim().length() > 10) {
			bresult.rejectValue("password", "error.required");
		}
		
		ModelAndView mav = new ModelAndView();
		
		if(bresult.hasErrors()) {//등록된 오류 존재?
			//global error 등록
			bresult.reject("error.input.check");
			return mav;
		}
		
		User dbUser = service.selectUser(user.getUserid());
		if(dbUser == null) { // 아이디 없음
			bresult.reject("error.login.id");
			return mav;
		}
		
		if(user.getPassword().equals(dbUser.getPassword())) { // 일치
			session.setAttribute("loginUser", dbUser);
			mav.setViewName("redirect:mypage?userid=" +user.getUserid());
		}else {
			bresult.reject("error.login.password");
			return mav;
		}
		return mav;
	}
	
	/*
	 * AOP 설정 필요 : UserLoginAspect 클래스의 userIdCheck 메서드로 구현
	 * 	1. 로그인여부 검증
	 * 	   로그아웃상태인 경우 로그인후 거래메세지 출력.login 페이지로 이동
	 * 	2. 본인 거래 여부 검증
	 * 	   admin이 아니면서 다른 사용자 정보는 출력 불가
	 */
	@RequestMapping("mypage")
	public ModelAndView idCheckMypage(String userid, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUser(userid);
		mav.addObject("user",user);
		return mav;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	//로그인 상태,본인정보만 조회 검증 =>AOP 클래스
	@GetMapping({"update","delete"}) 
	public ModelAndView idcheckUser(String userid , HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUser(userid);
		mav.addObject("user",user);
		return mav;
	}
	
}
