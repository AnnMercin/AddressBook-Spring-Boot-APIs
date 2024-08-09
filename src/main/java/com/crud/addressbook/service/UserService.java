package com.crud.addressbook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.addressbook.dao.UserDao;
import com.crud.addressbook.model.User;


@Service
public class UserService {

	@Autowired
	UserDao userDeo;
	
	public User createUser(User user) {
		
		return userDeo.save(user);
	}

	public Integer verifyUser(String Phone, String password) {
	if (userDeo.existsByPhone(Phone) == false) {
		return 404;
	}
	User user =  userDeo.findByPhone(Phone);
	if (user.getPassword().equals(password) ) {
		return user.id;
	}else {
		return 404;
	}
	}
	public String userVailation(String email,String phone) {
		
		if(userDeo.existsByEmail(email) == true){
			return "Already Present Email";
			
		}else if (userDeo.existsByPhone(phone)== true) {
			return "Already Present Phone";
		}
		else {
			return "Created";	
		}
		
	}

	public Optional<User> getUser(Integer id) {
		return userDeo.findById(id);
	}

	public String byIdUserVailation(User user, Integer id) {
		user.setId(id);
		if (userDeo.existsByEmail(user.email)== true && userDeo.existsByPhone(user.phone)== true ) {
			createUser(user);
			return "updated";
		}else if (userDeo.existsByEmail(user.email)== true || userDeo.existsByPhone(user.phone)== true) {
			 userDeo.updateEmailAndPhone(user.getName(), user.getEmail(), user.getPhone(), user.getId());
			 return "updated";
		} 
		return userVailation(user.email, user.phone);
	}
}
