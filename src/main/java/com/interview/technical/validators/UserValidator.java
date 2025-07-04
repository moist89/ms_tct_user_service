package com.interview.technical.validators;

import com.interview.technical.enums.EMessages;
import com.interview.technical.exceptions.GeneralException;
import com.interview.technical.exceptions.InvalidPasswordException;
import com.interview.technical.models.User;
import com.interview.technical.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidator {
    private final Pattern passwordPattern;
    private final UserJpaRepository userJpaRepository;

    public UserValidator(@Value("${regex.password}") String regexPassword,
                         UserJpaRepository userJpaRepository) {
        this.passwordPattern = Pattern.compile(regexPassword);
        this.userJpaRepository = userJpaRepository;
    }

    public void validatePassword(String password) {
        if (!passwordPattern.matcher(password).matches()) {
            throw new InvalidPasswordException(
                    EMessages.MSG_ERROR_PASSWORD_BAD_FORMAT.getValue()
            );
        }
    }
    public void verificarDuplicidad(String name, String email) {
        boolean emailExists = userJpaRepository.existsByEmailIgnoreCase(email);
        boolean nameExists = userJpaRepository.existsByNameIgnoreCase(name);
        if (nameExists) {
            throw new GeneralException(EMessages.MSG_ERROR_NAME_REPEATED.getValue());
        }
        if(emailExists){
            throw new GeneralException(EMessages.MSG_ERROR_EMAIL_REPEATED.getValue());
        }
    }

}
