package com.example.ilanvermemobilprojeodevi.services;

import com.example.ilanvermemobilprojeodevi.db.advert.Advert;
import com.example.ilanvermemobilprojeodevi.db.user.Customer;

public class ValidationService {
    public boolean validateSingUpProcess(Customer customer) throws Exception {
        return new SingupValidationService().validate(customer);
    }

    public boolean validateLoginProcess(String username, String password) throws Exception {
        return new LoginValidationService().validate(username, password);
    }

    public boolean validateProfilUpdateProcess(Customer customer) throws Exception {
        return new ProfilUpdateValidationService().validate(customer);
    }

    public boolean validateAdvertCreationInputs(Advert advert) throws Exception {
        return new AdvertValidationService().validate(advert);
    }


}
