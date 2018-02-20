const mongoose = require('mongoose');
const bcrypt = require('bcrypt-nodejs');
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
            email: req.body.email,
            password: req.body.password,
            pais: req.body.pais,
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
                mensaje: 'Registro correcto',
                token: service.createToken(user),
                nombre: result.nombre,
                apellidos: result.apellidos,
                email: result.email,
                pais: result.pais,
                telefono: result.telefono,
                direccion: result.direccion
            });

        });

    });

};

//POST Iniciar sesión

module.exports.signIn = (req, res) => {

    User.findOne({email: req.body.email}).select('_id nombre apellidos email pais domicilio +password').exec((err, user) => {

        if (err) return res.status(401).jsonp({error: 401, mensaje: 'Fallo de Allow Origin'});
        if (!user) return res.status(404).jsonp({error: 404, mensaje: 'No existe el usuario'});

        bcrypt.compare(req.body.password, user.password, (err, result) => {
            if (err) return res.status(401).jsonp({error: 401, mensaje: 'Error en la autenticación'});
            if (result == false)
                return res.status(401).jsonp({error: 401, mensaje: 'Error en la autenticación'});
            else {
                req.user = user;
                res.status(200).jsonp({
                    mensaje: 'Login correcto',
                    token: service.createToken(user),
                    nombre: user.nombre,
                    apellidos: user.apellidos
                });
            }
        });


    });

};