package ru.ramazanmamyrbek.kazinsightmonolith.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tours")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false, exclude = {"images", "participants"})
public class Tour extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @ManyToMany(mappedBy = "favouriteTours")
    private List<User> favouriteUsers;

    @ManyToMany
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tour_id"})
    )
    private List<User> participants = new ArrayList<>();

    public String getNormalDate() {
        return "%d.%d.%d".formatted(startDate.getDayOfMonth(), startDate.getMonthValue(), startDate.getYear());
    }

    public String getTime() {
        return "%d:%d".formatted(startDate.getHour(), startDate.getMinute());
    }

    public int getNormalPrice() {
        return price.intValue();
    }

}
