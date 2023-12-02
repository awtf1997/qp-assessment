package com.assessment.qpassessment.service.impl;

import com.assessment.qpassessment.entity.User;
import com.assessment.qpassessment.model.UserDetails;
import com.assessment.qpassessment.model.UserResponse;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse<List<User>> getAllUsers(Integer userId) {
        return null;
    }

    @Override
    public UserResponse<User> addAUser(Integer userId, UserDetails userDetails) {
        return null;
    }

    @Override
    public UserResponse<User> updateAUser(Integer userId, UserDetails userDetails) {
        return null;
    }

    @Override
    public UserResponse<User> deleteAUser(Integer userId, Integer id) {
        return null;
    }

    @Override
    public UserResponse<User> getUser(Integer userId) {
        return null;
    }
}
