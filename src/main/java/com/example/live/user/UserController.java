package com.example.live.user;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  } 

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userRepository.findById(id).get();
  }
@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user)
{
    User savedUser = userRepository.save(user);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId()).toUri();

    return ResponseEntity.created(location).build();
}

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    User existingUser = userRepository.findById(id).get();
    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    return userRepository.save(existingUser);
  }
  @PatchMapping("/{id}")
  public ResponseEntity<User> updateUserPartially(
  @PathVariable Long id,
 @RequestBody User userDetails) {
       Optional<User> existingUser= userRepository.findById(id);
        //.orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ id));

       existingUser.get().setEmail(userDetails.getEmail());
      // existingUser.get().setName(userDetails.getName());
     User updatedUser = userRepository.save(existingUser.get());
     ResponseEntity<User> re=new ResponseEntity<>(updatedUser,HttpStatus.OK);
      return re;
 }
 
 
 

  @DeleteMapping("/{id}")
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  public String deleteUser(@PathVariable Long id) {
   // try {
      userRepository.findById(id).get();
      userRepository.deleteById(id);
     // return "User deleted successfully";
    //} catch (Exception e) {
      //return "User not found";
	return null;
    }
  }
  

