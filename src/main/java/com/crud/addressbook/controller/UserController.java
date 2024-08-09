package com.crud.addressbook.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crud.addressbook.model.User;
import com.crud.addressbook.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("user")
public class UserController {
	
	@Autowired 
	UserService userService;
	
	@PostMapping("/new")
	public ResponseEntity<?>  createUser(@Valid @RequestBody User user) {
		String responseString = userService.userVailation(user.getEmail(), user.getPhone());
		if(responseString.equals("Created")) {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
		}else {
			 Map<String, String> errors = new HashMap<>();
			 errors.put("error", responseString);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
		}
	}
	@GetMapping("login")
	public Integer verifyUser(@RequestParam String phone ,@RequestParam String password) {
		
		return  userService.verifyUser(phone,password);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> verifyUser( @PathVariable Integer id) {  
			Optional<User> user =	userService.getUser(id);
			 if (Optional.empty().equals(user)) {
				 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record Not Found");
			  }else {
				 return ResponseEntity.status(HttpStatus.OK).body(user);
			  }
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<?>  editUser(@Valid @RequestBody User user,@PathVariable Integer id  ) {
		String responseString = userService.byIdUserVailation(user,id);
		if(responseString.equals("updated")) {
			return ResponseEntity.status(HttpStatus.CREATED).body(responseString);
		}else {
			 Map<String, String> errors = new HashMap<>();
			 errors.put("error", responseString);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
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
