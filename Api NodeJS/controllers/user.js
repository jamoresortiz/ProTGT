const mongoose = require('mongoose');
const bcrypt = require('bcrypt-nodejs');
const service = require('../services');
const User = require('../models/user');
const Address = require('../models/address');

// POST Registrar usuario
module.exports.signUp = (req, res) => {

    Address
        .findOne({_id: req.body.address_id}, (err, address) => {
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

        user
            .save((err, user) => {
            if (err)
                return res.status(500).jsonp({
                    error: 500,
                    mensaje: `${err.message}`
                });

            return res.status(201).jsonp({
                mensaje: 'Registro correcto',
                token: service.createToken(user),
                nombre: user.nombre,
                apellidos: user.apellidos,
                email: user.email,
                pais: user.pais,
                telefono: user.telefono,
                direccion: user.direccion
            });

        });

    });

};

//POST Iniciar sesión
module.exports.signIn = (req, res) => {

    User
        .findOne({email: req.body.email})
        .select('_id password nombre apellidos email pais telefono domicilio')
        .exec((err, user) => {

            if (err) return res.status(401).jsonp({error: 401, mensaje: 'Fallo de Allow Origin'});
            if (!user) return res.status(404).jsonp({error: 404, mensaje: 'No existe el usuario'});

            bcrypt.compare(req.body.password, user.password, (err, result) => {
                if (err) return res.status(401).jsonp({error: 401, mensaje: 'Error dentro de bcrypt'});
                if (result == false)
                    return res.status(401).jsonp({error: 401, mensaje: 'Error en la autenticación'});
                else {
                    req.user = user;
                    res.status(200).jsonp({
                        mensaje: 'Login correcto',
                        token: service.createToken(user),
                        nombre: user.nombre,
                        apellidos: user.apellidos,
                        email: user.email,
                        pais: user.pais,
                        telefono: user.telefono,
                        domicilio: user.domicilio
                    });
                }
            });


    });

};

module.exports.editUser = (req, res) => {
  User
      .findOne({_id: req.user}, (err, user) => {
          if (err) return res.status(401).jsonp({
              error: 401,
              mensaje: `Error de autentificación`
          });

          if (!user) return res.status(404).jsonp({
              error: 404,
              mensaje: `No se encuentra el usuario`
          });

          User.findById({_id: user._id}, (err, user) => {
              if (err) return res.status(500).jsonp({
                  error: 500,
                  mensaje: `Error de servidor`
              });

              if (req.body.nombre)
                user.nombre = req.body.nombre;
              if (req.body.apellidos)
                user.apellidos = req.body.apellidos;
              if (req.body.email)
                  user.email = req.body.email;
              if (req.body.password)
                user.password = req.body.password;
              if (req.body.pais)
                user.pais = req.body.pais;
              if (req.body.telefono)
                user.telefono = req.user.telefono;

              user.save(function (err, result) {
                  if (err) return res.status(500).jsonp({
                      error: 500,
                      mensaje: `${err.message}`
                  });
                  res.status(201).jsonp(result);
              });
          });
      });
};

module.exports.editAddressOfUser = (req, res) => {

    User
        .findOne({_id: req.user}, (err, user) => {
            if (err) return res.status(401).jsonp({
                error: 401,
                mensaje: `Error de autentificación`
            });

            if (!user) return res.status(404).jsonp({
                error: 404,
                mensaje: `No se encuentra el usuario`
            });

            User
                .findById({_id: user._id}, (err, user) => {
                    if (err) return res.status(500).jsonp({
                        error: 500,
                        mensaje: `Error de servidor`
                    });

                    Address
                        .findById({_id: user.direccion}, (err, address) => {
                            if (err) return res.status(500).jsonp({
                                error: 500,
                                mensaje: `Error de servidor`
                            });

                            if (req.body.provincia)
                                address.provincia = req.body.provincia;
                            if (req.body.localidad)
                                address.localidad = req.body.localidad;
                            if (req.body.calle)
                                address.calle = req.body.calle;
                            if (req.body.numero)
                                address.numero = req.body.numero;
                            if (req.body.piso)
                                address.piso = req.body.piso;
                            if (req.body.bloque)
                                address.bloque = req.body.bloque;
                            if (req.body.puerta)
                                address.puerta = req.body.puerta;

                            address.save((err, address) => {
                                if (err) return res.status(500).jsonp({
                                        error: 500,
                                        mensaje: `${err.message}`
                                });

                                return res.status(201).jsonp(address);
                            });

                        });
            });
        });
};