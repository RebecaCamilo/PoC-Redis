package com.example.pocredis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ANY_OBJECTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnyObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 150)
	private String description;
	
	public AnyObject(final String description) {
		this.description = description;
	}

}
