const mongoose = require('mongoose');
const service = require('../services');
const Address = require('../models/address');

// POST Registrar dirección
module.exports.addAddress = (req, res) => {

    let address = new Address({
        provincia: req.body.provincia,
        localidad: req.body.localidad,
        calle: req.body.calle,
        numero: req.body.numero,
        piso: req.body.piso,
        bloque: req.body.bloque,
        puerta: req.body.puerta
    });

    address.save((err, result) => {
        if (err) return res.status(500).jsonp({
                   error: 500,
                   mensaje: `${err.message}`
        });

        return res.status(201).jsonp(result);
    });

};