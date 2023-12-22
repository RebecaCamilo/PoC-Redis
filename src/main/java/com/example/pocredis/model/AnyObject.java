package com.example.pocredis.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "ANY_OBJECTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnyObject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String description;

	@Column
	private int quantity;
	
	public AnyObject(final String description, final int quantity) {
		this.description = description;
		this.quantity = quantity;
	}

}
