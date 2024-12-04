package ru.ramazanmamyrbek.kazinsightmonolith.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@ToString(exclude = {"tour", "place"})
@EqualsAndHashCode(callSuper = false, exclude = {"tour", "place"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private String name;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
}
