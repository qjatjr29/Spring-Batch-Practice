package com.example.springbatch.user.controller;

import com.example.springbatch.user.dto.UserRequest;
import com.example.springbatch.user.dto.UserResponse;
import com.example.springbatch.user.dto.UserResponse.Summary;
import com.example.springbatch.user.service.UserService;
import java.net.URI;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/")
  public ResponseEntity<?> save(@RequestBody UserRequest.SignUp signUp) {
    userService.save(signUp);
    return ResponseEntity.created(URI.create("/")).build();
  }

  @GetMapping("/")
  public ResponseEntity<List<UserResponse.Summary>> findAll() {
    List<Summary> users = userService.findAll();
    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> delete(@PathVariable Long userId) {
    userService.softDelete(userId);
    return ResponseEntity.noContent().build();
  }

}
