package com.example.springbatch.user.service;

import com.example.springbatch.user.dto.UserRequest.SignUp;
import com.example.springbatch.user.dto.UserResponse;
import com.example.springbatch.user.dto.UserResponse.Summary;
import com.example.springbatch.user.entity.User;
import com.example.springbatch.user.repository.UserRepository;
import com.example.springbatch.user.util.UserConverter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void save(SignUp signUp) {

    User user = new User(signUp.getName(), signUp.getAge());

    userRepository.save(user);

  }

  public List<Summary> findAll() {

    List<User> users = userRepository.findAll();

    List<Summary> userSummary = users.stream().map(UserConverter::toUserSummary)
        .collect(Collectors.toList());

    return userSummary;
  }

  @Transactional
  public void softDelete(Long userId) {
    userRepository.deleteById(userId);
  }

  @Transactional
  public void deleteStep(LocalDateTime now){

    List<User> userByIsDeleted = userRepository.findDeleteUserList();

    List<User> deleteUsers = userByIsDeleted.stream()
        .filter(user -> gapDate(user.getUpdatedAt(), now))
        .collect(Collectors.toList());

    for (User deleteUser : deleteUsers) {
      userRepository.hardDelete(deleteUser.getId());
    }

  }

  private boolean gapDate(LocalDateTime updateDate, LocalDateTime now) {
    Duration between = Duration.between(updateDate, now);
    if(between.getSeconds() > 60) return true;
    return false;
  }
}
