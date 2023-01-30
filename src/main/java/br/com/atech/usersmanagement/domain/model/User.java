package br.com.atech.usersmanagement.domain.model;

import br.com.atech.usersmanagement.domain.model.business.UserInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserInterface {
    private String email;
    private String password;
    private String name;
    private String userName;

    @Override
    public boolean validPassword() {
        if(this.password.length() < 6){
            return false;
        }
        return true;
    }
}
