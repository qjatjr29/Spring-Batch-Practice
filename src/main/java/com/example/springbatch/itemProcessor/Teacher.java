package com.example.springbatch.itemProcessor;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
@Entity
public class Teacher {

  @Id
  private Long id;

  private String name;

  @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL,orphanRemoval = true)
  private List<Student> students;

}
