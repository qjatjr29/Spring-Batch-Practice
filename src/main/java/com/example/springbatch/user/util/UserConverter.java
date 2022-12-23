package com.example.springbatch.user.util;

import com.example.springbatch.user.dto.UserResponse;
import com.example.springbatch.user.dto.UserResponse.Summary;
import com.example.springbatch.user.entity.User;

public class UserConverter {

  public static UserResponse.Summary toUserSummary(User user) {
    return new UserResponse.Summary(user.getName(), user.getIsDeleted());
  }

}
