const { Stats } = require("./connection");
const readDB = require('./read')

const updateStats = function (newStats, callback) {
    readDB.getStatsSelected(newStats.nombreTipo, function (existe) {
        if (!existe) {
            callback("No existe el " + newStats.nombreTipo)
        } else {
            Stats.updateOne({ nombreTipo: newStats.nombreTipo }, newStats, (err, updatedStats) => {
                if (err) {
                    console.log(err);
                } else {
                    console.log("Estadisticas actualizadas");
                    readDB.getStatsSelected(newStats.nombreTipo, function (result) {
                        callback(result);
                    })
                }
            });
        }
    })
};

module.exports = {
  updateStats,
};
