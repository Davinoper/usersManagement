package br.com.atech.usersmanagement.handler;

import br.com.atech.usersmanagement.constant.ErrorMessage;
import br.com.atech.usersmanagement.exeception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Object> emailAlreadyUsedException(EmailAlreadyUsedException exception) {
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.EMAIL_ALREADY_USED, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Object> invalidNameException(InvalidNameException exception){
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.INVALID_USER_NAME, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPasswordException(InvalidPasswordException exception){
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.INVALID_USER_PASSWORD, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserNameException.class)
    public ResponseEntity<Object> invalidUserNameException(InvalidUserNameException exception){
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.INVALID_USER_NICKNAME, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyUsedException.class)
    public ResponseEntity<Object> userNameAlreadyUsedException(UserNameAlreadyUsedException exception){
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.USERNAME_ALREADY_USED, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDontExistsException.class)
    public ResponseEntity<Object> userDontExistsException(UserDontExistsException exception){
        return new ResponseEntity(new ExceptionDetails(ErrorMessage.USER_DONT_EXISTS, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }


}
