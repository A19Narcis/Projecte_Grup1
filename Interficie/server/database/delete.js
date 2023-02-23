const mongoose = require('mongoose');

const { Stats } = require('./connection');

const removeStatsFrom = async function(tipo, callback) {
/*     await Stats.deleteOne({ nombreTipo: tipo }, (err) => {
        if (err) {
            return handleError(err);
        } else {
            callback('El "${tipo}" ha sido eliminado')
        }
    }) */

    const deleteResult = Stats.deleteOne({ nombreTipo: tipo});
    console.log(tipo);
    deleteResult.exec().then(result => callback('El ' + tipo + ' ha sido eliminado')).catch(error => console.error(error));
}

module.exports = {
    removeStatsFrom
}