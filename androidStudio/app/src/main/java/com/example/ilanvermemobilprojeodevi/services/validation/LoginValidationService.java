package com.example.ilanvermemobilprojeodevi.services.validation;

import com.example.ilanvermemobilprojeodevi.db.user.Customer;

public class LoginValidationService {


    public boolean validate(String username, String password) throws Exception {
        if (validateInputLenght(username, "Username") &&
                validateInputLenght(password, "password")) {
            return true;
        }
        return false;
    }

    public boolean validateInputLenght(String input, String inputName) throws Exception {

        if (input.length() == 0)
            throw new Exception("You have to fill " + inputName);
        return true;

    }
}
