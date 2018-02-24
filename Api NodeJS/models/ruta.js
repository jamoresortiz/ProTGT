const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const rutaSchema = new Schema ({
    localizacion: String, //latitud y longitud Ej.: 3.9495876795,-4.87659826900
    fecha_envio_loc: {type: Date, default: Date.now()}
});

module.exports = mongoose.model('Ruta', rutaSchema);