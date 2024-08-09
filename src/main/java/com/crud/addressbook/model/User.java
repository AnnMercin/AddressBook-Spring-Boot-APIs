package com.crud.addressbook.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@NotNull(message = "Name is Mandatory")
	@NotBlank(message = "Name is Blank")
	public String name;
	@NotNull(message = "Password is Mandatory")
	@NotBlank(message = "Pasword is Blank")
	@Size(min = 5, max = 20, message = "password must be between 5 and 20 characters")
	public String password;
	@NotNull(message = "Email is Mandatory")
	@NotBlank(message = "Email is Blank")
	@Email(message = "Should be Email")
	public String email;
	@Size(min = 10, max = 10, message = "Mobile Number must be  10 characters")
	@NotNull(message = "Phone Number is Mandatory")
	@NotBlank(message = "Phone Number is Blank")
	public String phone;
	
	@ManyToMany
	List<Contact> contacts;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
