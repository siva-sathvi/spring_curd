package com.example.live.user;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id;
  private String status;
 // @Column(name = "name")
 // private String name;
 
  @Column(name = "first_name")
  @Pattern(regexp = "^[A-Za-z ]+$", message = "First name cannot contain numbers or special characters")
  private String firstName;

  @Column(name = "last_name")
  @Pattern(regexp = "^[A-Za-z ]+$", message = "First name cannot contain numbers or special characters")
  private String lastName;


  @Column(name = "email")
  private String email;

  //getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

 // public String getName() {
  //  return name;
 // }

  
 public String getFirstName() {
   return firstName;
 }

  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }

  public String getLastName() {
      return lastName;
  }

  public void setLastName(String lastName) {
      this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public String getStatus() {
      return status;
  }

  public void setStatus(String status) {
      this.status = status;
  }
}
