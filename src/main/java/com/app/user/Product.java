package com.app.user;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long pid;
	String pname;
	String category;
	double pprice;
	
    private String photofilename;
	@Lob
	@Column(name = "photo", columnDefinition="LONGBLOB")
	private byte [] photo;

	public Product() {
		super();
	}

	public Product(long pid, String pname, String category, double pprice, String photofilename, byte[] photo) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.category = category;
		this.pprice = pprice;
		this.photofilename = photofilename;
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", category=" + category + ", pprice=" + pprice
				+ ", photofilename=" + photofilename + ", photo=" + Arrays.toString(photo) + "]";
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
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
