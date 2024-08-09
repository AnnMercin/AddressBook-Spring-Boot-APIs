package com.crud.addressbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crud.addressbook.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User ,Integer >{

	User findByPhone(String phone);
	
	boolean existsByPhone(String phone);
	
	boolean existsByEmail(String email);
	
	  @Modifying
	   @Transactional
	   @Query("update User u set u.name = :name, u.email = :email, u.phone = :phone where u.id = :id")
	    int updateEmailAndPhone(String name, String email, String phone, int id);


}
