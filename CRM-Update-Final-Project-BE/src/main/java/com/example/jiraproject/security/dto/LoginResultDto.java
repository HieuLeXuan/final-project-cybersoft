package com.example.jiraproject.security.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roleCodes;
    private String accessToken;
    private String refreshToken;
}