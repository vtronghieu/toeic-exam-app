package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.ChangePasswordDto;
import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void createUser(UserDto userDto);

    List<UserDto> getUsers(int page, int size);

    UserDto getUserById(UUID id);

    UserDto getUserByUsername(String username);

    void changePassword(UUID id, ChangePasswordDto changePasswordDto);

    void updateUserById(UUID id, UserDto userDto);

    void deleteUserById(UUID id);

    List<User> findAll();

    Optional<User> findById(UUID id);

    Optional<User> getByUsernameAndPassword(String username, String password);

    boolean existsUserById(UUID userId);

    User save(User user);
}
