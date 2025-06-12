package kr.gdu.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gdu.dao.UserDao;
import kr.gdu.logic.User;

@Service
public class UserService {

	@Autowired
	UserDao userdao;
	
	public void userInsert(User user) {
		userdao.insert(user);
	}
	
	public User selectUser(String userid) {
		return userdao.selectOne(userid);
	}

	public void userUpdate(User user) {
		userdao.update(user);
	}

	public void userDelete(String userid) {
		userdao.delete(userid);
		
	}

	public void updatePassword(String chgpass, String userid) {
		userdao.updatePassword(chgpass,userid);
	}

	public String getSearch(User user) {
		return userdao.search(user);
	}

	public List<User> list() {
		return userdao.list();
	}

	
}
