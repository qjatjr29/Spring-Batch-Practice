package com.example.springbatch.user.repository;

import com.example.springbatch.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM user AS u WHERE u.is_deleted = true", nativeQuery = true)
  List<User> findDeleteUserList();

  @Modifying
  @Query(value = "DELETE FROM user AS u WHERE u.is_deleted = true AND u.id = :userId", nativeQuery = true)
  void hardDelete(@Param("userId") Long userId);
}

