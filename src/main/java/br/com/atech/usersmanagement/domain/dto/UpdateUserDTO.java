package br.com.atech.usersmanagement.domain.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private Long id;
    private String name;
    private String userName;
}
