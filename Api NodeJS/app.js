const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const logger =  require('morgan');
const mongoose = require('mongoose');
const auth = require('./middlewares/auth');
const config = require('./config');
const cors = require('cors');


mongoose.connect(config.MONGODB_URI,
    { useMongoClient: true }).then(
    ()=> {
        const User = require('./models/user');
        const Address = require('./models/address');

        User.find((err, result) =>{
            if (!(result && result.length)) {
                let user1 = new User({
                    nombre: "Jorge",
                    apellidos: "Amores Ortiz",
                    pais: "España",
                    telefono: "625946146",
                    email: "jamoresortiz@gmail.com",
                    password: "12345",
                    direccion: mongoose.Types.ObjectId('5a8c3a036b471523182c54ae')
                });

                user1.save((err, result) => {
                    if (err) return console.log(`Error de inserción ${err.message}`);
                    console.log(`insertado: ${result}`);
                });
            } else {
                console.log('Ya hay datos');
            }
        });

        Address.find((err, result) => {
            if (!(result && result.length)) {
                let address = new Address({
                    provincia: "Sevilla",
                    localidad: "Algaba (La)",
                    calle: "Manuel Velázquez Bazán",
                    numero: 14
                });

                address.save((err, result) => {
                    if (err) return console.log(`Error de insercion ${err.message}`);
                console.log(`insertado: ${result}`);
                });
            } else {
            console.log('Ya hay datos');
            }
        });
    },
    (err) => {
        console.error(`Error de conexión: ${err.mensaje}`);
    }
);

mongoose.Promise = global.Promise;


const user = require('./routes/user');
const address = require('./routes/address');

let app = express();

app.use(logger('dev'));
app.use(cors());

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser())

app.use('/protgt/api/v1/auth', user);
app.use('/protgt/api/v1/address', address);

module.exports = app;
