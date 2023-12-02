package com.assessment.qpassessment.service;

import com.assessment.qpassessment.entity.User;
import com.assessment.qpassessment.model.UserDetails;
import com.assessment.qpassessment.model.UserResponse;
import com.assessment.qpassessment.model.UserRole;

import java.util.List;

public interface UserService {
    public UserResponse<List<User>> getAllUsers(Integer userId);

    public UserResponse<User> addAUser(Integer userId, UserDetails userDetails);

    public UserResponse<User> updateAUser(Integer userId, UserDetails userDetails);

    public UserResponse<User> deleteAUser(Integer userId, Integer id);

    public UserResponse<User> getUser(Integer userId);
}
