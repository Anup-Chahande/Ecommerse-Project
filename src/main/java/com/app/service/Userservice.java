package com.app.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.Userdao;
import com.app.user.User;

@Service
public class Userservice {

	@Autowired
	Userdao d;

	public void adduser(User user) {

		d.adduser(user);

	}

	public List<User> getalluser() {

		return d.getalluser();
	}

	public void update(User user) {

		d.update(user);

	}
	
	public User getbyid(long id) {

		          

		return d.getbyid(id);
	}

}
