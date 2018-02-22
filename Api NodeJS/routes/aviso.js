const express = require('express');
const avisoController = require('../controllers/aviso');
const auth = require('../middlewares/auth');
let router = express.Router();

router.post('/addaviso', avisoController.addAviso);
router.get('/', avisoController.showAllAvisos);
router.get('/sinresolver', avisoController.showAllAvisosSinResolver);
router.get('/:id_aviso', avisoController.showOneAviso);
router.put('/:id_aviso/changestatus', avisoController.changeStatusAviso);
router.put('/:id_aviso/sendlocation', avisoController.sendLocation);


module.exports = router;