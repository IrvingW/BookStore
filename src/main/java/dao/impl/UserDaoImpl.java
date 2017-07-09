package dao.impl;

import java.util.List;

import model.Profile;
import model.User;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import dao.UserDao;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	
	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Integer save(User user) {
		return (Integer) getHibernateTemplate().save(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	public void update(User user) {
		getHibernateTemplate().merge(user);
	}

	public User getUserById(int id) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find(
				"from User as u where u.id=?", id);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}
	
	public User getUserByName(String name){
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find(
				"from User as u where u.user_name=?",name);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}

	public List<User> getAllUsers() {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate()
				.find("from User");
		return users;
	}
	
	public String getIntroByName(String name){
		Query query = Query.query(Criteria.where("name").is(name));
		Profile profile = mongoTemplate.findOne(query, Profile.class, "profile");
		if(profile != null)
			return profile.getIntroduce();
		else
			return "";
	}
	
	public void saveProfile(String name, String introduce){
		Query query = Query.query(Criteria.where("name").is(name));
		Profile profile = mongoTemplate.findOne(query, Profile.class, "profile");
		if(profile != null)
			mongoTemplate.remove(query, Profile.class);
		
		profile = new Profile();
		profile.setName(name);
		profile.setIntroduce(introduce);
		
		mongoTemplate.save(profile, "profile");
	}

}
