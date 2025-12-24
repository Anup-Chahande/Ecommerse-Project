package com.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.user.Product;
import com.app.user.User;

@Repository
public class Userdao {
	@Autowired
	SessionFactory sf;

	public void adduser(User user) {

		Session s = sf.openSession();
		s.save(user);
		Transaction t = s.beginTransaction();
		t.commit();

	}

	public void update(User user) {
		Session s = sf.openSession();

		 try {
				Transaction t = s.beginTransaction();
				s.merge(user);
				t.commit();	        } finally {
	            s.close(); 
	        }
	
	}

	public List<User> getalluser() {

		Session s = sf.openSession();
		
		Criteria c = s.createCriteria(User.class);
		List<User> ls = c.list();

		return ls;
	}
	
	
	

	public User getbyid(long id) {

		Session s = sf.openSession();

		  
				User us = s.get(User.class, id);
	            s.close(); 
	        
		  
			return us;

		
		
		
	}

}
