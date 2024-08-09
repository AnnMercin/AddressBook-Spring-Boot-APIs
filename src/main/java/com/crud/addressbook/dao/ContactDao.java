package com.crud.addressbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crud.addressbook.model.Contact;

import jakarta.transaction.Transactional;


@Repository
public interface ContactDao extends JpaRepository<Contact, Integer> {

	List<Contact> findByUserId(Integer userId);

	 boolean existsByPhoneAndUserId(String phone, Integer userId);

	 boolean existsByEmailAndUserId(String email, Integer userId);
	 
	 @Query("SELECT COUNT(c) > 0 FROM Contact c WHERE c.email = :email AND c.id <> :id AND c.userId = :userId")
	 boolean existsByEmailAndNotId(String email,Integer id, Integer userId);

	 @Query("SELECT COUNT(c) > 0 FROM Contact c WHERE c.phone = :phone AND c.id <> :id AND c.userId = :userId")
	 boolean existsByPhoneAndNotId(String phone,Integer id, Integer userId);
	  
	 @Modifying	  
	 @Transactional
	 @Query("update Contact u set u.name = :name, u.email = :email, u.phone = :phone where u.id = :id")
	 	int updateEmailAndPhone(String name, String email, String phone, int id);
}
