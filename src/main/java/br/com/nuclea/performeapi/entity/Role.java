package br.com.nuclea.performeapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	private Long id;
	private String name;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="USER",
             joinColumns={@JoinColumn(name="USER_ID")},
             inverseJoinColumns={@JoinColumn(name="ROLES_ID")})
	private List<User> users;

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Role(String name){
		this.name = name;
	}
}
