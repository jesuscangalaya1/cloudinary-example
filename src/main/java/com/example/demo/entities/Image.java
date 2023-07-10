package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private int capacity;

    @Column(name = "duracion")
    @DateTimeFormat(pattern = "HH:mm")
    private String duration;


    private String name;
    private String imagenUrl;
    private String imagenId;


    public Image(String name, String imagenUrl, String imagenId) {
        this.name = name;
        this.imagenUrl = imagenUrl;
        this.imagenId = imagenId;
    }


}
