package com.example.cms_backend.Validators;

import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.Entities.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserValidator {

    private UserValidator() {

    }

    public static void validateUser(User user){
        String fullName = user.getName();
        String[]  nameParts = fullName.trim().split("\\s+");
        if(nameParts.length != 3){
            throw new UserNotValidException(ErrorMessages.FULL_NAME_IS_REQUIRED.getMessage());
        }

        String Tz_Phone_Regex = "^(\\+255|0)(6[2789]|7[1345678]|9[1-9])[0-9]{7}$";
        String userPhoneNumber = user.getPhone().replaceAll("\\s+", "");
        if (!Pattern.matches(Tz_Phone_Regex, userPhoneNumber)){
            throw new UserNotValidException(ErrorMessages.INVALID_PHONE_NUMBER.getMessage());
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!Pattern.matches(emailRegex, user.getEmail())) {
            throw new UserNotValidException(ErrorMessages.INVALID_EMAIL.getMessage());
        }

//        if (user.getBirthDate() == null) {
//            throw new UserNotValidException(ErrorMessages.EMAIL_REQUIRED.getMessage());
//        }
        if (user.getBirthDate() != null) {
            int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
            if (age < 10) {
                throw new UserNotValidException(ErrorMessages.BELOW_AGE_LIMIT.getMessage());
            }
        }





    }
}
