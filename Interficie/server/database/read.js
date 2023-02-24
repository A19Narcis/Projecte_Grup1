const { Partida } = require('./connection');
const { Stats } = require('./connection');
const { Mapas } = require('./connection');


//Select stats nomes 1 tipus
const getStatsSelected = async (tipo, callback) => {
    const statsOne = await Stats.findOne({ nombreTipo: tipo })
    callback(statsOne)
}



//Select de les Stats dels Zombies / Charactes
const getStats = async (callback) => {
    const fullStats = await Stats.find({});
    console.log("Cogiendo datos de los personajes i de los zombies...");
    callback(fullStats)
}

//Select de les partides
const getPartidas = function (callback) {
    Partida.find({}, (error, partidas) => {
        if (error) {
            return res.status(500).json({ error });
        }
        callback(partidas)
    })
}

module.exports = {
    getPartidas,
    getStats,
    getStatsSelected
};