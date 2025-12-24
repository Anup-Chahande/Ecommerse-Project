package com.app.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String Name;
	String number;
	String email;
	String password;
	
	@OneToMany(mappedBy = "userid",fetch = FetchType.EAGER )
	List<Orderitem>ol;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(long id, String name, String number, String email, String password, List<Orderitem> ol) {
		super();
		this.id = id;
		Name = name;
		this.number = number;
		this.email = email;
		this.password = password;
		this.ol = ol;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Name=" + Name + ", number=" + number + ", email=" + email + ", password="
				+ password + ", ol=" + ol + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Orderitem> getOl() {
		return ol;
	}

	public void setOl(List<Orderitem> ol) {
		this.ol = ol;
	}
	
	
	
	
	
	
	

}
