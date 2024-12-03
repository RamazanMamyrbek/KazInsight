package ru.ramazanmamyrbek.kazinsightmonolith.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"favoritePlaces", "tours"})
@EqualsAndHashCode(callSuper = false, exclude = {"favoritePlaces", "tours","roles"})
public class User extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "place_id"}))
    private List<Place> favoritePlaces = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<Tour> tours = new ArrayList<>();
}
