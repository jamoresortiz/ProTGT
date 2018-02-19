const service = require('../services');

module.exports.isAuth = (req, res, next) => {

    if (!req.headers.authorization) {
        return res.status(403).jsonp({error: 403,
            mensaje: 'No tiene autorización para acceder a ese recurso'
        });
    }

    let token = req.headers.authorization.split(" ")[1];

    service.decodeToken(token)
        .then(response => {
            req.user = response;
            next();
        })
        .catch(errorResponse => {
            res.status(errorResponse.status).jsonp({
                error: errorResponse.status,
                mensaje: errorResponse.mensaje
            });
        });

};