package ru.ramazanmamyrbek.kazinsightmonolith.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@Getter
@Setter
@ToString(exclude = {"favoriteUsers"})
@EqualsAndHashCode(callSuper = false, exclude = {"favoriteUsers", "images", "creator"})
public class Place extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany(mappedBy = "favoritePlaces")
    private List<User> favoriteUsers = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<Image> images;

}
