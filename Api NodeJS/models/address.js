const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const addressSchema = new Schema ({
    provincia: String,
    localidad: String,
    calle: String,
    numero: Number,
    piso: String,
    bloque: String,
    puerta: String
});

module.exports = mongoose.model('Address', addressSchema);