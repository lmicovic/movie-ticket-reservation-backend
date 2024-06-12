package application.configuration.jwt_security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/*
 * This is Custom User class that is used for Authentication and Authorization
 */
@Entity(name = "user_info")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	@JsonProperty(access = Access.WRITE_ONLY) // Can use Password in HTTP Request but HTTP Response will not have password.
	private String password;

	@Column(name = "roles")
	private String roles;		// Example: "ROLE_USER,ROLE_ADMIN"

	public UserInfo() {

	}

	public UserInfo(String firstname, String lastname, String email, String password, String roles) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.roles = roles;
		
	}

	public UserInfo(Long id, String firstname, String lastname, String email, String password, String roles) {

		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.roles = roles;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", roles=" + roles + "]";
	}

}