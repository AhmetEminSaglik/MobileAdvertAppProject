const { validationResult } = require('express-validator');


class Middleware {
    handlerValidationError(req, res, next) {
        const error = validationResult(req)
        if (!error.isEmpty()) {
            return res.json(error.array()[0]);
        }
        next()
    }

    // authValidationError(req: Request, res: Response, next: NextFunction) {
    //     const token = req.header('bigelir-token');
    //     if (!token) return res.status(401).send({ result: 'Access Denied!: No token provided' });

    //     try {
    //         const decodedToken = jwt.verify(token, secretKey as string);
    //         // req.userData = decodedToken;
    //         next();
    //     } catch (error) {
    //         res.status(400).send({ result: "Oturum süreniz doldu lütfen tekrar giriş yapınız!" });
    //     }

    // }
}


module.exports = new Middleware()