package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.Productdao;
import com.app.user.Product;

@Service
public class Productservice {

	@Autowired
	Productdao pd;

	public void addproducts(Product p) {

		pd.addproducts(p);

	}
	
	public List<Product> getallproducts() {
	
	
	return pd.getallproducts();
	
	}

}
