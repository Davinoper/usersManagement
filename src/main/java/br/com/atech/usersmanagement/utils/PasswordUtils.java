

package br.com.atech.usersmanagement.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    public static boolean compareEncryptedPassword(String password, String encryptedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(password,encryptedPassword)){
            return true;
        }
        return false;
    }
}
