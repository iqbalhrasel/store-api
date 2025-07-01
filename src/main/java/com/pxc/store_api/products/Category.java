package com.pxc.store_api.products;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Byte id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "category")
	private Set<Product> products = new HashSet<>();

	public Category(String name) {
		this.name = name;
	}
	
	public Category(byte id) {
		this.id = id;
	}


}
