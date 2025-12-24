package com.app.user;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Orderitem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	    private String pname;
	    private String category;
	    private double pprice;
	    private int quantity;
	    
	    @ManyToOne
	    @JoinColumn(name ="userid")
	    User userid;

		private String photofilename;
		@Lob
		@Column(name = "photo", columnDefinition="LONGBLOB")
		private byte [] photo;
		public Orderitem() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Orderitem(Long id, String pname, String category, double pprice, int quantity, User userid,
				String photofilename, byte[] photo) {
			super();
			this.id = id;
			this.pname = pname;
			this.category = category;
			this.pprice = pprice;
			this.quantity = quantity;
			this.userid = userid;
			this.photofilename = photofilename;
			this.photo = photo;
		}
		@Override
		public String toString() {
			return "Orderitem [id=" + id + ", pname=" + pname + ", category=" + category + ", pprice=" + pprice
					+ ", quantity=" + quantity + ", userid=" + userid + ", photofilename=" + photofilename + ", photo="
					+ Arrays.toString(photo) + "]";
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPname() {
			return pname;
		}
		public void setPname(String pname) {
			this.pname = pname;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public double getPprice() {
			return pprice;
		}
		public void setPprice(double pprice) {
			this.pprice = pprice;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public User getUserid() {
			return userid;
		}
		public void setUserid(User userid) {
			this.userid = userid;
		}
		public String getPhotofilename() {
			return photofilename;
		}
		public void setPhotofilename(String photofilename) {
			this.photofilename = photofilename;
		}
		public byte[] getPhoto() {
			return photo;
		}
		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}
		
		
		
	 
	
	
	
	

}
