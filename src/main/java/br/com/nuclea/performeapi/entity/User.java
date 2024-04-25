package br.com.nuclea.performeapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String cpfCnpj;
	private String phone;
	private String email;
	@Transient
	private Boolean isAccreditor;
	@JsonIgnore
	private String password;
	@ManyToMany
	private List<Role> roles;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SITUATION_ID", nullable = false)
	private Situation situation;
	
	public void setPassword(String password) {
		this.password = passwordEncoder(). encode(password);
	}
	
	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
