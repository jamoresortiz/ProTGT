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

// POST Iniciar sesión
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
                        direccion: user.direccion
                    });
                }
            });

    });

};

// GET Mostrar direcciones de un usuario
module.exports.showAdressesOfUser = (req, res) => {
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


          User.populate(user, {path: "direccion", select: '_id provincia localidad calle numero piso bloque puerta'}, (err, user) => {


              if (err) return res.status(500).jsonp({
                  error: 500,
                  mensaje: `${err.message}`
              });
              res.status(200).jsonp(user.direccion);
          });
      });
};

// PUT Editar datos usuario
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
              res.status(200).jsonp(result);
          });
      });
};

// PUT Editar dirección usuario
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

            Address
                .findById({_id: req.body.id_direccion}, (err, address) => {
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
                        return res.status(200).jsonp(address);
                        });

                    });
        });
};

// PUT Añadir una dirección a un usuario
module.exports.addAddressToUser = (req, res) => {
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

            console.log(user.email);

                    let address = new Address({
                        provincia: req.body.provincia,
                        localidad: req.body.localidad,
                        calle: req.body.calle,
                        numero: req.body.numero,
                        piso: req.body.piso,
                        bloque: req.body.bloque,
                        puerta: req.body.puerta
                    });

                    address.save((err, address) => {
                        if (err) return res.status(500).jsonp({
                            error: 500,
                            mensaje: `${err.message}`
                        });
                        User.update(user, {$push: {direccion: address}}, (err) => {
                            if (err) return res.status(500).jsonp({
                                error: 500,
                                mensaje: `${err.message}`
                            });
                            res.status(200).jsonp(address);
                        });
                    });
        });
};

// POST Comprobar email o móvil
module.exports.verifyEmailTelephone = (req, res) => {
    if (req.body.email){
        User
            .findOne({email: req.body.email}, (err, user) => {
                if (err) return res.status(500).jsonp({
                    error: 500,
                    mensaje: `${err.message}`
                });
                if (user) return res.status(400).jsonp({
                    error: 400,
                    mensaje: 'Usuario ya registrado con ese email'
                });
                if (!user) return res.status(200).jsonp({
                    mensaje: 'Email correcto'
                });
            });
    }

    if (req.body.telefono){
        User
            .findOne({telefono: req.body.telefono}, (err, user) => {
                if (err) return res.status(500).jsonp({
                    error: 500,
                    mensaje: `${err.message}`
                });
                if (user) return res.status(400).jsonp({
                    error: 400,
                    mensaje: 'Usuario ya registrado con ese telefono'
                });
                if (!user) return res.status(200).jsonp({
                    mensaje: 'Teléfono correcto'
                });
            });
    }
};

// DELETE Eliminar una dirección de un usuario
module.exports.deleteAddressOfUser = (req, res) => {

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

            Address.findById({_id: req.body.id_direccion}, (err, address) => {
                if (err) return res.status(500).jsonp({
                    error: 500,
                    mensaje: `Error de servidor`
                });
                if (!address) return res.status(404).jsonp({
                    error: 404,
                    mensaje: `No se encuentra la dirección`
                });

                address.remove((err) => {
                   if (err) return res.status(500).jsonp({
                      error: 500,
                      mensaje: `Error de servidor`
                   });

                   res.sendStatus(204);
                });

            });
    });
};