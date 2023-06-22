package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @NotNull
    private byte[] data;
}
