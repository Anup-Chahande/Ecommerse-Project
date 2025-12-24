package com.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.user.Orderitem;
import com.app.user.User;

@Repository
public class Orderitemdao {

	@Autowired
	SessionFactory sf;

	public void saveorder(Orderitem order) {

		Session s = sf.openSession();
		s.save(order);

		Transaction t = s.beginTransaction();
		t.commit();
		s.close();

	}
	
	
	 
	
	

}
