package com.pxc.store_api.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pxc.store_api.products.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();
	
//	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
//	private Profile profile;
	
	@ManyToMany
	@JoinTable(
			name = "wishlist", 
			joinColumns= @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="product_id")
	)
	private Set<Product> wishlist = new HashSet<>();

	public void addAddresses(Address address) {
		addresses.add(address);
		address.setUser(this);
	}
	
	public void removeAddresses(Address address) {
		addresses.remove(address);
		address.setUser(null);
	}
	
	public void addToWishlist(Product product) {
		wishlist.add(product);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				'}';
	}
}
