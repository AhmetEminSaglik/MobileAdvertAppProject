const { body, param, query } = require("express-validator")

class UserValidator {
    checkCreateUser() {
        return [
            body('username')
                .notEmpty()
                .withMessage('Username bos olamaz!'),
            body('fullName')
                .notEmpty()
                .withMessage('Açıklama boş olamaz!'),
            body('password')
                .notEmpty()
                .withMessage('Fiyat boş olamaz!'),
            body('phone')
                .notEmpty()
                .withMessage('User id boş olamaz!'),
            body('mail')
                .notEmpty()
                .withMessage('image boş olamaz!'),
        ]
    }


}

module.exports = new UserValidator()