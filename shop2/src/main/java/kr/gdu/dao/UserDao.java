package kr.gdu.dao;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.UserMapper;
import kr.gdu.logic.User;



@Repository
public class UserDao {

	@Autowired //SqlSessionTemplate주입 (DBConfig에서 @bean으로 등록 돼 있음)
	private SqlSessionTemplate template;
	
	private Map<String,Object> param = new HashMap<>();
	private final Class<UserMapper> cls = UserMapper.class;
	
	public void insert(User user) {
		
		template.getMapper(cls).insert(user);
	}

	/*public String selectUser(User user) {
		param.clear();
		return template.getMapper(cls).selectUser(user);
		
	}*/
	
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		return template.selectOne("kr.gdu.dao.mapper.UserMapper.select",param);
		
	}

	public void update(User user) {
		 template.getMapper(cls).update(user);
		
	}

	public void delete(String userid) {
		template.getMapper(cls).delete(userid);
		
	}

	public void changepw(User loginUser) {
		template.getMapper(cls).changePw(loginUser);
		
	}

	public String search(User user) {
		param.clear();
		String col = "userid"; //아이디검색
		if(user.getUserid()!=null) {
			col = "password";//비밀번호검색
		}
		param.clear();
		param.put("col", col);
		param.put("userid", user.getUserid());
		param.put("email", user.getEmail());
		param.put("phoneno", user.getPhoneno());
		return template.getMapper(cls).search(param);
	}

	public List<User> list() {
		return template.getMapper(cls).list();
	}

	  public List<User> list(String[] idchks) {
		  param.clear();
		  param.put("userids", idchks);
		return template.getMapper(cls).select(param);
	}

	
	
	
}
