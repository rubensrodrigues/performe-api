package br.com.nuclea.performeapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "SITUATION")
@Data
@NoArgsConstructor 
@AllArgsConstructor
public class Situation {

	@Id
	private Long id;
	private String description;
}