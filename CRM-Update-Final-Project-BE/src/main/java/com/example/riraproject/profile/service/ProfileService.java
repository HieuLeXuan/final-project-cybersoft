package com.example.riraproject.profile.service;

import com.example.riraproject.common.util.MessageUtil;
import com.example.riraproject.profile.dto.ChangePasswordForm;
import com.example.riraproject.role.model.Role;
import com.example.riraproject.security.dto.LoginResultDto;
import com.example.riraproject.security.util.JwtUtil;
import com.example.riraproject.user.dto.UserDto;
import com.example.riraproject.user.model.User;
import com.example.riraproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

public interface ProfileService {
    LoginResultDto updateProfile(UserDto dto);
    void changePassword(ChangePasswordForm form);
}
@Service
@Transactional
@RequiredArgsConstructor
class ProfileServiceImpl implements ProfileService {
    private final UserService userService;
    private final ModelMapper mapper;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResultDto updateProfile(UserDto dto) { //USER CAN ONLY UPDATE THEIR OWN PROFILE
        if (!jwtUtil.getAuthenticatedUsername().equals(dto.getUsername())) { // if this is not their profile
            throw new ValidationException(MessageUtil.getMessage(messageSource, "user.invalid"));
        }
        User user = userService.findUserById(dto.getId());
        //When user update their profile, they can't change their username & password & avatar
        String username = user.getUsername(); // store username
        String password = user.getPassword();
        mapper.map(dto, user);
        user.setUsername(username); // return back username & password
        user.setPassword(password);
        UserDto userDto = mapper.map(user, UserDto.class);
        return LoginResultDto.builder()
                .userData(userDto)
                .roleCodes(user.getRoles().stream().map(Role::getCode).toList())
                .build();
    }

    @Override
    public void changePassword(ChangePasswordForm form) {
        User user = userService.findUserById(form.getUserId());
        if (!jwtUtil.getAuthenticatedUsername().equals(user.getUsername())) {
            throw new ValidationException(MessageUtil.getMessage(messageSource, "user.invalid"));
        }
        if (passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        } else {
            throw new ValidationException(MessageUtil.getMessage(messageSource, "user.password.incorrect"));
        }
    }
}
