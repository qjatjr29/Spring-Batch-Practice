package com.example.springbatch.quartz.common;

public class ApiResponse {

  private Boolean isSuccess;
  private String message;

  public ApiResponse(Boolean isSuccess, String message) {
    this.isSuccess = isSuccess;
    this.message = message;
  }
}
