package ru.ramazanmamyrbek.kazinsightmonolith.exception;

public class FileNotSupportedException extends RuntimeException{
    public FileNotSupportedException(String message) {
        super(message);
    }
}
