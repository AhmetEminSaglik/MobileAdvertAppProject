package com.example.ilanvermemobilprojeodevi.services.validation;

import com.example.ilanvermemobilprojeodevi.db.advert.Advert;

public class AdvertValidationService {

    public boolean validate(Advert advert) throws Exception {
        if (validateInputLenght(advert.getTitle(), "Title") &&
                validateInputLenght(advert.getDescription(), "Description") &&
                validateInputLenght(advert.getPrice(), "Price")) {
            return true;
        }
        return false;
    }

    public boolean validateInputLenght(String input, String inputName) throws Exception {
        if (input.length() != 0)
            return true;
        throw new Exception(inputName + " can not be empty ");

    }
}
