const { Partida } = require('./connection');
const { AxeStats } = require('./connection');
const { WarStats } = require('./connection');
const { ShieldStats } = require('./connection');
const { ZombieStats } = require('./connection');
const { BossStats } = require('./connection');


//Select de les Stats dels Zombies / Charactes
const getStats = async (callback) => {
    let fullStats = []
    const resAxe = await AxeStats.find({});
    fullStats.push(resAxe)
    const resWar = await WarStats.find({});
    fullStats.push(resWar)
    const resShi= await ShieldStats.find({});
    fullStats.push(resShi)
    const resZombie = await ZombieStats.find({});
    fullStats.push(resZombie)
    const resBoss = await BossStats.find({});
    fullStats.push(resBoss)

     callback(fullStats)
}

//Select de les partides
const getPartidas = function(callback) {
    Partida.find({}, (error, partidas) => {
        if (error) {
            return res.status(500).json({ error });
        }
        callback(partidas)
    })
}

module.exports = {
    getPartidas,
    getStats
};