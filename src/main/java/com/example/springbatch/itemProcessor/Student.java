package com.example.springbatch.itemProcessor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
@Entity
public class Student {

  @Id
  private Long id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Teacher teacher;

}
