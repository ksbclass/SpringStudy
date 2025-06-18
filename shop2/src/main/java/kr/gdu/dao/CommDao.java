package kr.gdu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.CommentMapper;
import kr.gdu.logic.Comment;

@Repository
public class CommDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<String, Object>();
	private Class<CommentMapper> cls = CommentMapper.class;
	
	
	
	public List<Comment> commentList(int num) {
		return template.getMapper(cls).commentList(num);
	}



	public int comMaxSeq(int num) {		
		return template.getMapper(cls).comMaxSeq(num);
	}



	public void comInsert(Comment comm) {
		template.getMapper(cls).comInsert(comm);
		
	}



	public Comment commSelectOne(int num, int seq) {
		param.clear();
		param.put("num", num);
		param.put("seq", seq);
		
		return template.getMapper(cls).commSelectOne(param);
	}



	public void commDel(int num, int seq) {
		
		template.getMapper(cls).commdel(num,seq);
	}

}
