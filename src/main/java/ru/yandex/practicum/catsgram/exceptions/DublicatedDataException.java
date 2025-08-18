package ru.yandex.practicum.catsgram.exceptions;

public class DublicatedDataException extends RuntimeException {
  public DublicatedDataException(String message) {
    super(message);
  }
}
