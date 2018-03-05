const express = require('express');
const userController = require('../controllers/user');
const auth = require('../middlewares/auth');
let router = express.Router();


router.post('/register', userController.signUp);
router.post('/addcontacto', userController.addContactoDeConfianza);
router.post('/login', userController.signIn);
router.post('/register/verify', userController.verifyEmailTelephone);
router.get('/user', auth.isAuth, userController.showUser);
router.get('/showaddresses', auth.isAuth, userController.showAdressesOfUser);
router.get('/showcontactos', auth.isAuth, userController.showContactosofUser);
router.get('/alluser/shownumberphones', auth.isAuth, userController.showAllTelephone);
router.put('/edit',auth.isAuth, userController.editUser);
router.put('/edit/address',auth.isAuth, userController.editAddressOfUser);
router.put('/addaddress',auth.isAuth, userController.addAddressToUser);
router.delete('/deleteaddress', auth.isAuth, userController.deleteAddressOfUser);

module.exports = router;
