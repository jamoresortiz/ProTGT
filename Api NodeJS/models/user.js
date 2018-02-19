const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const bcrypt = require('bcrypt-nodejs');
const crypto = require('crypto');

const userSchema = new Schema({
    nombre: String,
    apellidos: String,
    pais: String,
    telefono: String,
    direccion: {type: Schema.ObjectId, ref: 'Address'}
});

module.exports = mongoose.model('User', userSchema);