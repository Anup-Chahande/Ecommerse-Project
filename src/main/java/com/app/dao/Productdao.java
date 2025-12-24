package com.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.user.Product;

@Repository
public class Productdao {

    @Autowired
    SessionFactory sf;

    public void addproducts(Product p) {
        Session s = sf.openSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(p);
            t.commit();
        } finally {
            s.close(); 
        }
    }

    public List<Product> getallproducts() {
        Session s = sf.openSession();
        try {
            Criteria c = s.createCriteria(Product.class);
            return c.list();
        } finally {
            s.close(); 
        }
    }
    
    public void updateproducts(Product p) {
        Session s = sf.openSession();
        Transaction t = s.beginTransaction();
		s.merge(p);
		t.commit();	       
		s.close();
          
    } 
         
    public Product getbyid(long id) {
        Session s = sf.openSession();
        try {

        	return s.get(Product.class, id);
        } finally {
            s.close(); 
        }
    }
    
    	
    	
    
    
    
}
