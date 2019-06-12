package com.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plane")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Captain")
    private String captain;

    @Column(name = "Engine")
    private String engine;

    @Column(name = "Series")
    private String series;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;


}
