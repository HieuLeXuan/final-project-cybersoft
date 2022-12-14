package com.example.riraproject.user.dto;

import com.example.riraproject.common.validation.annotation.FieldNotNull;
import com.example.riraproject.common.validation.annotation.UUIDConstraint;
import com.example.riraproject.common.validation.group.SaveInfo;
import com.example.riraproject.common.validation.group.UpdateInfo;
import com.example.riraproject.user.model.User;
import com.example.riraproject.user.validation.annotation.UniqueUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@UniqueUser(groups = {SaveInfo.class, UpdateInfo.class})
public class UserDto {

    @UUIDConstraint(groups = UpdateInfo.class)
    private UUID id;

    @Size(min = 5, max = 25, message = "{user.username.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{user.username.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String username;

    @Size(min = 5, max = 25, message = "{user.password.size}", groups = {SaveInfo.class}) //UPDATE DON'T CHANGE PASSWORD
    @NotBlank(message = "{user.password.not-blank}", groups = {SaveInfo.class})
    private String password;

    @Size(max = 25, message = "{user.firstName.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{user.firstName.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String firstName;

    @Size(max = 25, message = "{user.lastName.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{user.lastName.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String lastName;

    @FieldNotNull(message = "{user.gender.not-null}", groups = {SaveInfo.class, UpdateInfo.class})
    private User.Gender gender;

    @Size(max = 200, message = "{user.avatar.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String avatar;

    @Size(min = 5, max = 25, message = "{user.email.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{user.email.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String email;

    @Size(min = 5, max = 200, message = "{user.facebookUrl.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String facebookUrl;

    @Size(min = 5, max = 200, message = "{user.occupation.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String occupation;

    @Size(min = 5, max = 200, message = "{user.department.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String department;

    @Size(min = 5, max = 200, message = "{user.hobbies.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String hobbies;

    @FieldNotNull(message = "{user.accountStatus.not-null}", groups = {SaveInfo.class, UpdateInfo.class})
    private User.AccountStatus accountStatus;
}
