const { Partida } = require('./connection');
const { AxeStats } = require('./connection');
const { WarStats } = require('./connection');
const { ShieldStats } = require('./connection');
const { ZombieStats } = require('./connection');
const { BossStats } = require('./connection');


const insertPartida = function (partida, callback) {
    const newPartida = new Partida(partida);
    newPartida.save(function (err) {
        if (err) return console.log(err);
        console.log("¡Nueva partida insertada!");
        callback();
    })
}



const insertStats = function(callback) {
    AxeStats.find().then((result) => {
        if (result.length == 0) {
            const axeStats = new AxeStats();
            axeStats.save(function (err) {
                if (err) return console.log(err);
            })
        } 
    })

    WarStats.find().then((result) => {
        if (result.length == 0) {
            const warStats = new WarStats();
            warStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    ShieldStats.find().then((result) => {
        if (result.length == 0) {
            const shiStats = new ShieldStats();
            shiStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    ZombieStats.find().then((result) => {
        if (result.length == 0) {
            const zombieStats = new ZombieStats();
            zombieStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    BossStats.find().then((result) => {
        if (result.length == 0) {
            const bossStats = new BossStats();
            bossStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })
    
    callback();
}

module.exports = {
    insertPartida,
    insertStats
};
