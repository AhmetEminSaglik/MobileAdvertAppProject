package com.example.ilanvermemobilprojeodevi.services.validation;

import com.example.ilanvermemobilprojeodevi.db.user.Customer;

public class ProfilUpdateValidationService {
    int minInputLength = 5;

    public boolean validate(Customer customer) throws Exception {
        if (validateInputLenght(customer.getFullName(), "Full Name") &&
                validateInputLenght(customer.getPassword(), "Password") &&
                validateInputLenght(customer.getPhoneNo(), "Phone No") &&
                validateInputLenght(customer.geteMail(), "Email")

        ) {
            return true;
        }
        return false;
    }

    public boolean validateInputLenght(String input, String inputName) throws Exception {

        if (input.length() >= minInputLength)
            return true;
        throw new Exception(inputName + " value length should be bigger than " + minInputLength);

    }
}
