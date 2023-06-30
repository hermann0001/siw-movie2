package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@NotNull
	private String name;
	@NotBlank
	private String surname;
	@Column(nullable = false, unique = true)
	private String email;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Review> reviews;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public @NotNull String getName() {
		return name;
	}
	
	public void setName(@NotNull String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else return email.equals(other.email);
		return true;
	}
}