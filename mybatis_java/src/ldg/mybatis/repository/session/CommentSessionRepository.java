package ldg.mybatis.repository.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import ldg.mybatis.model.Comment;

public class CommentSessionRepository {
	private final String namespace = "ldg.mybatis.repository.mapper.CommentMapper";
	private SqlSessionFactory getSqlSessionFactory() { // SqlSessionFactory == Connection 같은 의미
		String resource = "mybatis-config.xml";
		// InputStream 파일 안에 있는 내용을 불러오기 위해서 사용
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		}catch(IOException e) {
			throw new IllegalArgumentException(e);
		}
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	public Comment selectCommentByPrimaryKey(Long commentNo) {
		// DBMS와 연결 ( Connection )
		SqlSession sqlSession = getSqlSessionFactory().openSession(); // SqlSessionFactory안에 있는 openSession
		try {
			return (Comment)sqlSession.selectOne(namespace+".selectCommentByPrimaryKey", commentNo);
		}finally {
			sqlSession.close();
		}
	}
	public List<Comment> selectCommentByCondition(Map<String, Object> condition) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			return sqlSession.selectList(namespace+".selectCommentByCondition", condition);
		}finally {
			sqlSession.close();
		}
	}
}
