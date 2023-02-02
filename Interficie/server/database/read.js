const { Partida } = require('./connection');
const { AxeStats } = require('./connection');
const { WarStats } = require('./connection');
const { ShieldStats } = require('./connection');
const { ZombieStats } = require('./connection');
const { BossStats } = require('./connection');


//Select stats nomes 1 tipus
const getStatsSelected = (tipo, callback) => {
    console.log(tipo);
    if (tipo == 0) {
        const resAxe = AxeStats.find({});
        console.log("Stats Axe...");
        callback(resAxe)
    } else if (tipo == 1) {
        const resWar = WarStats.find({});
        console.log("Stats Warhammer...");
        callback(resWar)
    } else if (tipo == 2) {
        const resShi = ShieldStats.find({});
        console.log("Stats Shield...");
        callback(resShi)
    } else if (tipo == 3) {
        const resZombie = ZombieStats.find({});
        console.log("Stats Zombie...");
        callback(resZombie)
    } else if (tipo == 4) {
        const resBoss = BossStats.find({});
        console.log("Stats Boss...");
        callback(resBoss)
    }
}



//Select de les Stats dels Zombies / Charactes
const getStats = async (callback) => {
    let fullStats = []
    const resAxe = await AxeStats.find({});
    fullStats.push(resAxe)
    const resWar = await WarStats.find({});
    fullStats.push(resWar)
    const resShi = await ShieldStats.find({});
    fullStats.push(resShi)
    const resZombie = await ZombieStats.find({});
    fullStats.push(resZombie)
    const resBoss = await BossStats.find({});
    fullStats.push(resBoss)

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