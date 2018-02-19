const mongoose = require('mongoose');
const service = require('../services');
const User = require('../models/user');
const Address = require('../models/address');

// POST Registrar usuario
module.exports.signUp = (req, res) => {

    Address.findOne({_id: req.body.address_id}, (err, address) => {
        if (err) return res.status(404).jsonp({
            error: 404,
            mensaje: 'No existe la dirección'});

        let user = new User({
            nombre: req.body.nombre,
            apellidos: req.body.apellidos,
            telefono: req.body.telefono,
            direccion: mongoose.Types.ObjectId(address._id)
        });

        user.save((err, result) => {
            if (err)
                return res.status(500).jsonp({
                    error: 500,
                    mensaje: `${err.message}`
                });

            return res.status(201).jsonp({
                token: service.createToken(user),
                nombre: result.nombre,
                apellidos: result.apellidos,
                telefono: result.telefono,
                direccion: result.direccion
            });

        });

    });

};