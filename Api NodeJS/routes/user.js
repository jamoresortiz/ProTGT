const express = require('express');
const userController = require('../controllers/user');
const auth = require('../middlewares/auth');
let router = express.Router();


router.post('/register', userController.signUp);

module.exports = router;
