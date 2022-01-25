const express = require('express');
const Middleware = require('../middleware/index.js');
const AdvertValidator = require("../validation/advert.validate")
const router = express.Router()
const AdvertController = require("../controllers/advert.controller.js");

// Create a new Advert
router.post("/", AdvertValidator.checkCreateAdvert(), Middleware.handlerValidationError, AdvertController.create);
router.get("/", AdvertController.findAll);//tamam
router.get("/user/:id",AdvertController.findAllAdvertWhichBelongsToUserId);//calisiyor
router.get("/:id", AdvertController.findAdvertById);//calisiyor
router.put("/:id", AdvertController.update);//tamam
router.delete("/:id", AdvertController.delete);//tamam

module.exports = router