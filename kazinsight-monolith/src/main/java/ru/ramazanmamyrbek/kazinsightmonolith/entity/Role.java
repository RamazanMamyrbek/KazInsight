package ru.ramazanmamyrbek.kazinsightmonolith.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.RoleName;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
