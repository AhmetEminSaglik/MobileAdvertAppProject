const { body, param, query } = require("express-validator")

class AdvertValidator {
    checkCreateAdvert() {
        return [
            body('title')
                .notEmpty()
                .withMessage('Title boş olamaz!'),
            body('description')
                .notEmpty()
                .withMessage('Açıklama boş olamaz!'),
            body('price')
                .notEmpty()
                .withMessage('Fiyat boş olamaz!'),
            body('userId')
                .notEmpty()
                .withMessage('User id boş olamaz!'),
            body('image')
                .notEmpty()
                .withMessage('image boş olamaz!'),
        ]
    }


}

module.exports = new AdvertValidator()