package br.com.atech.usersmanagement.domain.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String email;
    private String password;
    private String name;
    private String userName;
}
