package com.example.live.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.live.user.dto.UserUpdateRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserController<JSONObject> {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
		if(id<=0) {
			Map<String, String> error=new HashMap<>();
			error.put("errorKey", "INVALID_PARAM");
			error.put("errorMessage", "User id is not valid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		Optional<User> user = userRepository.findById(id);
		// return new ResponseEntity(user,HttpStatus.OK);
		if (!user.isPresent()) {
			Map<String, String> error=new HashMap<>();
			error.put("errorKey", "USER_NOT_EXITS");
			error.put("errorMessage", "User does not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return ResponseEntity.status(HttpStatus.OK).body(user.get());
	}
	 @PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user) {
		
	//	return userRepository.save(user);
		String firstName = user.getFirstName();
        if (firstName == null || !Pattern.matches("^[A-Za-z ]+$", firstName)) {
            return ResponseEntity.badRequest().body("Invalid name. It should contain only letters and spaces.");
        }
        return ResponseEntity.ok("User " + firstName + " created successfully!");
    }
	
	 @PostMapping("/createAll")
	    public String createUsers(@RequestBody List<User> users) {
	        userRepository.saveAll(users);  // Save a list of users
	        return "Users created successfully!";
	    }
	
	 @PutMapping("/batch-update")
	    @ResponseStatus(HttpStatus.OK)
	    public List<User> updateMultipleUsers(@RequestBody List<UserUpdateRequest> userUpdateRequests) {
		 for (UserUpdateRequest request : userUpdateRequests) {
	            User user = userRepository.findById(request.getId())
	                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getId()));

	            // Update user fields if provided in the request
	            if (request.getFirstName() != null) {
	                user.setFirstName(request.getFirstName());
	            }
	            if (request.getLastName() != null) {
	                user.setLastName(request.getLastName());
	            }
	           if (request.getEmail() != null) {
	               user.setEmail(request.getEmail());
	            }
	           if (request.getStatus() != null) {
		               user.setStatus(request.getStatus());
		            }

	            userRepository.save(user);  // Save the updated user
	        }
	        return userRepository.findAll(); // Return the updated list of users (or return something else if necessary)
	    }
		 
		 @PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
		 Optional<User> existingUser = userRepository.findById(id);
		    
		    // If the user doesn't exist, throw an exception
		    if (!existingUser.isPresent()) {
		   
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); 
		    }

		    // Proceed with updating the user
		    User user1 = existingUser.get();
		user1.setFirstName(user.getFirstName());
		
		user1.setLastName(user.getLastName());
		user1.setEmail(user.getEmail());
		String firstName = user.getFirstName();
        if (firstName == null || !Pattern.matches("^[A-Za-z ]+$", firstName)) {
            return ResponseEntity.badRequest().body("Invalid name. It should contain only letters and spaces.");
        }
        return ResponseEntity.ok("User " + firstName + " created successfully!");
	
	
}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Object> patchUser(@PathVariable Long id, @RequestBody User user) {
		User existingUser = userRepository.findById(id).get();
		if (user.getFirstName() != null)
			existingUser.setFirstName(user.getFirstName());
		if (user.getEmail() != null)
			existingUser.setEmail(user.getEmail());
		//return userRepository.save(existingUser);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		try {
			userRepository.findById(id).get();
			userRepository.deleteById(id);
			return "User deleted successfully";
		} catch (Exception e) {
			return "User not found";
		}
	}

}
