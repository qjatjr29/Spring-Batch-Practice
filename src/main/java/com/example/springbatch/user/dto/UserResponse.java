package com.example.springbatch.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserResponse {

  @Getter
  @RequiredArgsConstructor
  public static class Summary {
    private String name;
    private Boolean isDeleted;

    public Summary(String name, Boolean isDeleted) {
      this.name = name;
      this.isDeleted = isDeleted;
    }
  }

}
