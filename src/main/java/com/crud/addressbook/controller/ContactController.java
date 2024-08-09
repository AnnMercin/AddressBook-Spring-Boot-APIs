package com.crud.addressbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crud.addressbook.model.Contact;
import com.crud.addressbook.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("contacts")
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Contact>>getContacts(@RequestParam Integer user_id) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.getContacts(user_id));
	}
	@PostMapping("/new")
	public  ResponseEntity<?> createContact (@Valid @RequestBody Contact contact) {
		String res = contactService.createContact(contact);
		if (res.equals("created")) {
			 return ResponseEntity.status(HttpStatus.OK).body(res);
		 }
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(res);
	}
	@PutMapping("/edit/{id}")
	public  ResponseEntity<?> editContact (@Valid @RequestBody Contact contact,@PathVariable Integer id){
		
		Optional<Contact> getcontact =contactService.getContact(id);
	  if (Optional.empty().equals(getcontact)){
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record Not Found");
	  }else {
		  contact.setId(id);
		  String res =  contactService.updateContact(contact); 
		  return ResponseEntity.status(HttpStatus.OK).body(res);
	  }
	}
	@GetMapping("/{id}")
	public  ResponseEntity<?> getContact (@PathVariable Integer id){
		Optional<Contact> contact =contactService.getContact(id);
		 if (Optional.empty().equals(contact)) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record Not Found");
		  }else {
			 return ResponseEntity.status(HttpStatus.OK).body(contact);
		  }
	}
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteContact (@PathVariable Integer id){
		Optional<Contact> contact =contactService.getContact(id);
		 if (Optional.empty().equals(contact)) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record Not Found");
		  }else {
			  String response = contactService.deleteContact(id);
			  return ResponseEntity.status(HttpStatus.OK).body(response);
		  }
	}
	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
	    Map<String, String> errors = new HashMap<>();
	
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	
	    return errors;
	}
}
