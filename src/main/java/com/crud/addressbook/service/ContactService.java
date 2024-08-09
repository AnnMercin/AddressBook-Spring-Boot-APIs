package com.crud.addressbook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crud.addressbook.dao.ContactDao;
import com.crud.addressbook.model.Contact;

import jakarta.validation.Valid;


@Service
public class ContactService {
	
	@Autowired
	ContactDao contactDao;

	public List<Contact> getContacts(Integer user_id) {
		return contactDao.findByUserId(user_id);
	}

	public String createContact(@Valid Contact contact) {
		if((contactDao.existsByEmailAndUserId(contact.getEmail(),contact.getUserId()) == false)
				&& (contactDao.existsByPhoneAndUserId(contact.getPhone(),contact.getUserId()) == false) ) {
		contactDao.save(contact);
				return "created";
		}else if (contactDao.existsByEmailAndUserId(contact.getEmail(),contact.getUserId())==true) {
			return "This Email id exists";
		}else if (contactDao.existsByPhoneAndUserId(contact.getPhone(),contact.getUserId()) == true )  {
			return "This Phone id exists";
		}else {
			return "Failed";
		}
	}

	public Optional<Contact> getContact( Integer id) {
		return contactDao.findById(id);
	}

	public String updateContact(@Valid Contact contact) {
	     Optional<Contact> temContact =	contactDao.findById(contact.getId());
	     Contact tem = 	temContact.get();
		if((contactDao.existsByEmailAndUserId(contact.getEmail(),contact.getUserId()) == false)
				&& (contactDao.existsByPhoneAndUserId(contact.getPhone(),contact.getUserId()) == false) ) {
		contactDao.save(contact);
				return "Updated";
		}else if (tem.getId() == contact.getId()) {
			 if (contactDao.existsByEmailAndNotId(contact.getEmail(), contact.getId(), contact.getUserId())) {
		            return "This Email ID already exists for another contact";
		        }
			 if (contactDao.existsByPhoneAndNotId(contact.getPhone(), contact.getId(), contact.getUserId())) {
			        return "This Phone number already exists for another contact";
			        }
			contactDao.updateEmailAndPhone(contact.getName(), contact.getEmail(), contact.getPhone(), contact.getId());
			return "Updated";
		}
		else if (contactDao.existsByEmailAndUserId(contact.getEmail(),contact.getUserId())==true) {
			return "This Email id exists";
		}else if (contactDao.existsByPhoneAndUserId(contact.getPhone(),contact.getUserId()) == true )  {
			return "This Phone id exists";
		}else {
			return "Failed";
		}
	}
	public String deleteContact(Integer id) {
		
		contactDao.deleteById(id);
		
		return "Record is Deleted";
	}

}
