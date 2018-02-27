const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const bcrypt = require('bcrypt-nodejs');
const crypto = require('crypto');

const userSchema = new Schema({
    nombre: String,
    apellidos: String,
    email: {type: String, unique: true, lowercase: true},
    password: String,
    pais: String,
    telefono: {type: String, unique: true},
    direccion: [{type: Schema.ObjectId, ref: 'Address'}],
    contactos_confianza: [{type: Schema.ObjectId, ref:'ContactosConfianza'}]
});

userSchema.pre('save', function (next) {

    let user = this;

    if (!user.isModified('password')) return next();

    bcrypt.genSalt(10, (err, salt) => {

        if (err) return next();

        bcrypt.hash(user.password, salt, null, (err, hash) => {

            if (err) return next();

            user.password = hash;
            next();

        });

    });


});

module.exports = mongoose.model('User', userSchema);