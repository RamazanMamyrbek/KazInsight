package ru.ramazanmamyrbek.kazinsightmonolith.entity.enums;

import lombok.Getter;

@Getter
public enum PlaceType {
    INDOOR("Indoor"), OUTDOOR("Outdoor");
    final String name;
    PlaceType(String name) {
        this.name = name;
    }

}
