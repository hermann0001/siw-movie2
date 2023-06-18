package it.uniroma3.siw.model;

import it.uniroma3.siw.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer mark;

    private User writer;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
