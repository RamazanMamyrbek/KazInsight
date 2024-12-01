package ru.ramazanmamyrbek.kazinsightmonolith.entity.enums;

import lombok.Getter;

@Getter
public enum PlaceType {
    INDOOR("Indoor"), OUTDOOR("Outdoor"), NATURE("Nature");
    final String name;
    PlaceType(String name) {
        this.name = name;
    }

}
