package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.user.ChangePasswordDto;
import com.tip.dg4.toeic_exam.dto.user.UserDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.UserMapper;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.models.UserInfo;
import com.tip.dg4.toeic_exam.models.enums.UserRole;
import com.tip.dg4.toeic_exam.repositories.UserRepository;
import com.tip.dg4.toeic_exam.services.UserInfoService;
import com.tip.dg4.toeic_exam.services.UserService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Lazy private final UserInfoService userInfoService;

    /**
     * Creates a new user.
     *
     * @param userDto the user data transfer object
     * @throws ConflictException   if a user with the given username already exists
     * @throws BadRequestException if the user role is undefined
     * @throws TExamException      if an unexpected error occurs while creating the user
     */
    @Override
    public void createUser(UserDto userDto) {
        try {
            if (userRepository.existsByUsername(userDto.getUsername())) {
                throw new ConflictException(ExceptionConstant.USER_E002);
            }
            if (Objects.equals(UserRole.UNDEFINED, UserRole.getRole(userDto.getRole()))) {
                throw new BadRequestException(ExceptionConstant.USER_E003);
            }
            User user = userMapper.convertDtoToModel(userDto);

            user.setUserInfo(userInfoService.setUserInfo(userDto.getUserInfo()));
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a list of users, paginated.
     *
     * @param page the page number
     * @param size the number of users per page
     * @return a list of users
     * @throws TExamException if an unexpected error occurs while getting the users
     */
    @Override
    public List<UserDto> getUsers(int page, int size) {
        try {
            long totalElements = userRepository.count();
            if (totalElements == 0) return Collections.emptyList();

            int correctPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int correctSize = TExamUtil.getCorrectSize(size, totalElements);

            return userRepository.findAll(PageRequest.of(correctPage, correctSize))
                    .map(userMapper::convertModelToDto)
                    .getContent();
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets the user by ID.
     *
     * @param id the user ID
     * @return a user DTO, or `null` if the user cannot be found
     * @throws TExamException if an unexpected error occurs while getting the user
     */
    @Override
    public UserDto getUserById(UUID id) {
        try {
            return userRepository.findById(id).map(userMapper::convertModelToDto)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets the user by username.
     *
     * @param username the user's username
     * @return a user DTO, or `null` if the user cannot be found
     * @throws TExamException if an unexpected error occurs while getting the user
     */
    @Override
    public UserDto getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .map(userMapper::convertModelToDto)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Changes the password of the user with the given ID.
     *
     * @param id                the user ID
     * @param changePasswordDto the change password DTO
     * @throws NotFoundException   if the user cannot be found
     * @throws BadRequestException if the old password is incorrect or the new password and confirm new password do not match
     * @throws TExamException      if an unexpected error occurs while changing the password
     */
    @Override
    public void changePassword(UUID id, ChangePasswordDto changePasswordDto) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));
            if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
                throw new BadRequestException(ExceptionConstant.USER_E008);
            }
            if (!Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmNewPassword())) {
                throw new BadRequestException(ExceptionConstant.USER_E009);
            }

            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Updates the user with the given ID.
     *
     * @param id      the user ID
     * @param userDto the user DTO
     * @throws NotFoundException   if the user cannot be found
     * @throws BadRequestException if the user role is undefined
     * @throws TExamException      if an unexpected error occurs while updating the user
     */
    @Override
    public void updateUserById(UUID id, UserDto userDto) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));
            UserRole userRole = UserRole.getRole(userDto.getRole());
            if (Objects.equals(UserRole.UNDEFINED, userRole)) {
                throw new BadRequestException(ExceptionConstant.USER_E003);
            }

            UserInfo userInfo = userInfoService.setUserInfo(userDto.getUserInfo(), user.getUserInfo());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(userRole);
            user.setActive(userDto.isActive());
            user.setUserInfo(userInfo);

            userRepository.save(user);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Deletes the user with the given ID.
     *
     * @param id the user ID
     * @throws NotFoundException if the user cannot be found
     * @throws TExamException    if an unexpected error occurs while deleting the user
     */
    @Override
    public void deleteUserById(UUID id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new NotFoundException(ExceptionConstant.USER_E001);
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Finds the user by ID.
     *
     * @param id the user ID
     * @return an optional containing the user, or empty if the user cannot be found
     */
    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    /**
     * Finds all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Gets the user with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return an optional containing the user, or empty if the user cannot be found or the password is incorrect
     */
    @Override
    public Optional<User> getByUsernameAndPassword(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        boolean validUser = userOptional.isPresent() &&
                passwordEncoder.matches(password, userOptional.get().getPassword());

        return validUser ? userOptional : Optional.empty();
    }

    /**
     * Checks if a user with the given ID exists.
     *
     * @param userId the user ID
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean existsUserById(UUID userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Saves the given user.
     *
     * @param user the user to save
     * @return the saved user
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
