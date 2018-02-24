const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const avisoSchema = new Schema ({
    rutas: [{type: Schema.ObjectId, ref: 'Ruta'}],
    user: {type: Schema.ObjectId, ref: 'User'},
    estado: Number
});

module.exports = mongoose.model('Aviso', avisoSchema);