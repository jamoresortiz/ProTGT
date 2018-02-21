const express = require('express');
const userController = require('../controllers/user');
const auth = require('../middlewares/auth');
let router = express.Router();


router.post('/register', userController.signUp);
router.post('/login', userController.signIn);
router.put('/edit',auth.isAuth, userController.editUser);
router.put('/edit/address',auth.isAuth, userController.editAddressOfUser);

module.exports = router;
