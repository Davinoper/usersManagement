package br.com.atech.usersmanagement.domain.dto;

import lombok.Data;

@Data
public class ChangeUserPasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
