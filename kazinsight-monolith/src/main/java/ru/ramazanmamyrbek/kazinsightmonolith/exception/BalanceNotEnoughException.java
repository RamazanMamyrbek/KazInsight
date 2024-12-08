package ru.ramazanmamyrbek.kazinsightmonolith.exception;

import lombok.Getter;

@Getter
public class BalanceNotEnoughException extends RuntimeException{
    private final Long tourId;
    public BalanceNotEnoughException(String message, Long tourId) {
        super(message);
        this.tourId = tourId;
    }
}
