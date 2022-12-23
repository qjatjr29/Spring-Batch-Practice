package com.example.springbatch.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserRequest {

  @Getter
  @RequiredArgsConstructor
  public static class SignUp {
    private String name;
    private Integer age;

    public SignUp(String name, Integer age) {
      this.name = name;
      this.age = age;
    }
  }

}
