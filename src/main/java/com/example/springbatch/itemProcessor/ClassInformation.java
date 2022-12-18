package com.example.springbatch.itemProcessor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ClassInformation {

  private String teacherName;
  private Integer studentSize;

  public ClassInformation(String teacherName, Integer studentSize) {
    this.teacherName = teacherName;
    this.studentSize = studentSize;
  }
}
