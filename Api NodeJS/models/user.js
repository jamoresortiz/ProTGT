const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const bcrypt = require('bcrypt-nodejs');
const crypto = require('crypto');

const userSchema = new Schema({
    nombre: String,
    apellidos: String,
    email: String,
    password: String,
    pais: String,
    telefono: String,
    direccion: {type: Schema.ObjectId, ref: 'Address'}
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