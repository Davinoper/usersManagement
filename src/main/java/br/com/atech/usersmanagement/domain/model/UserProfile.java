package br.com.atech.usersmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private String email;
    private String userName;
    private String name;
}
