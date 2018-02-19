const express = require('express');
const addressController = require('../controllers/address');
const auth = require('../middlewares/auth');
let router = express.Router();

router.post('/addaddress', addressController.addAddress);

module.exports = router;