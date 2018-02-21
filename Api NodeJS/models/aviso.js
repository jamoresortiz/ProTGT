const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const avisoSchema = new Schema ({
    localizacion: String, //latitud y longitud Ej.: 3.9495876795,-4.87659826900
    user: {type: Schema.ObjectId, ref: 'User'},
    fecha_aviso: {type: Date, default: Date.now()},
    estado: Number
});

module.exports = mongoose.model('Aviso', avisoSchema);