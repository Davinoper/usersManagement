

package br.com.atech.usersmanagement.utils;

import br.com.atech.usersmanagement.domain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    public static boolean compareEncryptedPassword(String password, String encryptedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password,encryptedPassword);
    }

    public static User hideUserPassword(User user){
        user.setPassword("***************");
        return user;
    }
}
