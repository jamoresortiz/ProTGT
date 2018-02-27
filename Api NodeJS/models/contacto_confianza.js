const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const contactosSchema = new Schema ({
    telefono: String,
    nombre: String
});

module.exports = mongoose.model('ContactosConfianza', contactosSchema);