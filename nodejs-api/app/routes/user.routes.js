const express = require('express');
const Middleware = require('../middleware/index.js');
const UserValidator = require("../validation/user.validate")
const router = express.Router()
const UserController = require("../controllers/user.controller.js");

// Create a new User
// router.post("/", UserValidator.checkCreateUser(), Middleware.handlerValidationError, UserController.create);
router.post("/", UserController.create);
router.post("/login", UserController.login);
router.get("/", UserController.findAll);//tamam
router.get("/find/:id", UserController.findUserById);// hata
router.put("/:id", UserController.update); //tamam
router.delete("/:id", UserController.delete); //tamam

module.exports = router