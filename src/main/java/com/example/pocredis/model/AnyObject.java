package com.example.pocredis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Any;

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
	
	//	public AnyObject att(AnyObject obj) {
//
//	}
	
}
