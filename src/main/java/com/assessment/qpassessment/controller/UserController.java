package com.assessment.qpassessment.controller;

import com.assessment.qpassessment.entity.User;
import com.assessment.qpassessment.model.UserDetails;
import com.assessment.qpassessment.model.UserResponse;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/")
    public ResponseEntity<UserResponse<List<User>>> getAllUsers(@RequestHeader(name = "USER_ROLE") UserRole role) {
        UserResponse<List<User>> resp = null;
        try {
            resp = userService.getAllUsers(role);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new UserResponse<List<User>>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PostMapping(path = "/")
    public ResponseEntity<UserResponse<User>> addUser(@RequestBody UserDetails userDetails,
                                                      @RequestHeader(name = "USER_ROLE") UserRole role) {
        UserResponse<User> resp = null;
        try {
            resp = userService.addAUser(role, userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new UserResponse<User>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "/")
    public ResponseEntity<UserResponse<User>> updateUser(@RequestBody UserDetails userDetails,
                                                      @RequestHeader(name = "USER_ROLE") UserRole role) {
        UserResponse<User> resp = null;
        try {
            resp = userService.updateAUser(role, userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new UserResponse<User>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserResponse<User>> deleteUser(@PathVariable(name = "id", required = true) Integer id,
                                                         @RequestHeader(name = "USER_ROLE") UserRole role) {
        UserResponse<User> resp = null;
        try {
            resp = userService.deleteAUser(role, id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new UserResponse<User>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
