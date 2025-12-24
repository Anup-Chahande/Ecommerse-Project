package com.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.Orderitemdao;
import com.app.service.Productservice;
import com.app.user.Orderitem;
import com.app.user.Product;
import com.app.user.User;

@Controller
public class Productcontroller {

	@Autowired
	Productservice ps;
	
	
	@Autowired
	Orderitemdao od;
	

	@GetMapping("/viewform")
	public String viewproductform() {

		return "formproducts";

	}

	@PostMapping("/addproduct")
	public String addproducts(@RequestParam("pname") String pname, @RequestParam("category") String category,
			@RequestParam("pprice") double price, @RequestParam("photo") MultipartFile photo) throws IOException {

		Product p = new Product();
		p.setPname(pname);
		p.setCategory(category);
		p.setPprice(price);

		if (!photo.isEmpty()) {
			p.setPhotofilename(photo.getOriginalFilename());
			p.setPhoto(photo.getBytes());
		}

		ps.addproducts(p);
	    return "redirect:/getallproducts";

	}
	
	
	
	@GetMapping("/getallproducts")
	public String getallproducts(Model model) {
	    try {
	        List<Product> products = ps.getallproducts();

	        for (Product p : products) {
	            if (p.getPhoto() != null) {
	                String base64Photo = Base64.getEncoder().encodeToString(p.getPhoto());
	                p.setPhotofilename(base64Photo);
	            }
	        }

	        model.addAttribute("products", products);
	        return "all";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "all"; // must return something
	    }
	}

            
	 @GetMapping("/category/{category}")
	 public String getByCategory(@PathVariable String category, Model model) {

	     List<Product> products = ps.getallproducts();
	     List<Product> filtered = new ArrayList<>();

	     for (Product p : products) {
	         if (p.getCategory().equalsIgnoreCase(category)) {
	             // Convert image bytes to Base64
	             if (p.getPhoto() != null) {
	                 String base64Photo = Base64.getEncoder().encodeToString(p.getPhoto());
	                 p.setPhotofilename(base64Photo);
	             }
	             filtered.add(p);
	         }
	     }

	     model.addAttribute("products", filtered);
	     model.addAttribute("categoryName", category); // Pass category for heading

	     return "category"; // single template for all categories
	 }

	 
	 @GetMapping("/search")
	 public String search(@RequestParam String keyword, Model model) {

	     List<Product> list = ps.getallproducts();   // get all
	     List<Product> result = new ArrayList<>();   // empty list

	     // filter manually
	     for (Product p : list) {
	         if (p.getPname().toLowerCase().contains(keyword.toLowerCase()) ||
	             p.getCategory().toLowerCase().contains(keyword.toLowerCase())) {

	             // convert image
	             if (p.getPhoto() != null) {
	                 String base64 = Base64.getEncoder().encodeToString(p.getPhoto());
	                 p.setPhotofilename(base64);
	             }

	             result.add(p);
	         }
	     }

	     model.addAttribute("products", result);
	     model.addAttribute("categoryName", "Search Results");

	     return "category";  
	 }
	 
	 
	 
	 @PostMapping("/addtocart")
	 @ResponseBody
	 public String addToCart(@RequestBody Product p, HttpSession session) {

	     List<Product> cart = (List<Product>) session.getAttribute("cart");
	     if (cart == null) cart = new ArrayList<>();

	     boolean found = false;

	     for (Product prod : cart) {
	         if (prod.getPid() == p.getPid()) { // compare by product id
	             prod.setQuantity(prod.getQuantity() + 1); // increment quantity
	             found = true;
	             break;
	         }
	     }

	     if (!found) {
	         p.setQuantity(1); // first time adding
	         cart.add(p);
	     }

	   

	     session.setAttribute("cart", cart);

	     return String.valueOf(cart.size());
	 }



 
	 
	 @GetMapping("/cart")
	 public String viewcart(HttpSession session,Model model) {
		 
		 List<Product> cart = (List<Product>) session.getAttribute("cart");
		 if (cart == null) {
		        cart = new ArrayList<>();
		    }
		 
		 

		    // If images exist, convert to Base64 so they can be shown
		    for (Product p : cart) {
		        if (p.getPhoto() != null) {
		            String base64Photo = Base64.getEncoder().encodeToString(p.getPhoto());
		            p.setPhotofilename(base64Photo);
		        }
		    
		    }
		    double total=0;
		     for (Product product : cart) {
		         total += product.getPprice() * product.getQuantity(); // ✅ IMPORTANT
			
			}
			    model.addAttribute("total", total);

		    model.addAttribute("cartProducts", cart);
		    return "mycart";  // Thymeleaf template: cart.html
		}
		 
	  
		 
	 
	 @PostMapping("/buyenow")
	 public String buynow(HttpSession session) {
		 
		    User user = (User) session.getAttribute("loggedUser");
		 List<Product> cart = (List<Product>) session.getAttribute("cart");
               for (Product product : cart) {
				Orderitem oi = new Orderitem();
			    Product fullProduct = ps.getbyid(product.getPid()); // fetch full product from DB

				oi.setPname(product.getPname());
				oi.setCategory(product.getCategory());
				oi.setPprice(product.getPprice());
				oi.setQuantity(product.getQuantity());
		        oi.setPhoto(fullProduct.getPhoto());
		        oi.setPhotofilename("product_" + product.getPid() + ".jpg");
		        oi.setUserid(user);
		               
		        od.saveorder(oi);
		        
		        if (user.getOl() == null) user.setOl(new ArrayList<>());
		        user.getOl().add(oi);
            	   
			}
		  
               session.removeAttribute("cart");
		        session.removeAttribute("total");
		 
        	   return "redirect:/userorders";

		 
	 }
	 
	 @GetMapping("/userorders")
	 public String getuseranditsorder(HttpSession session, Model model) {
	     User user = (User) session.getAttribute("loggedUser");
	     List<Orderitem> orders = user.getOl();

	     for (Orderitem o : orders) {
	         if (o.getPhoto() != null) {
	             String base64Photo = Base64.getEncoder().encodeToString(o.getPhoto());
	             o.setPhotofilename(base64Photo);
	         }
	     }

	     model.addAttribute("orders", orders);
	     return "orders";
	 }
			    
			    


			 
			 
			 
		 }
		 
		 
		 
		 
		 
		 
		
		 
		
		
	


	

	 
	
	

	
	
	

	
	
	

