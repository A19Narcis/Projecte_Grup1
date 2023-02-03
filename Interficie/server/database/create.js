const mongoose = require("mongoose")

const { Partida } = require('./connection');
const { AxeStats } = require('./connection');
const { WarStats } = require('./connection');
const { ShieldStats } = require('./connection');
const { ZombieStats } = require('./connection');
const { BossStats } = require('./connection');

const readDB = require('./read')


const addNewZombie = function (tipo, statsZombie, callback) {
    console.log(statsZombie);
    if (tipo == 1) {
        const statsZombieEsqueletoSchema = new mongoose.Schema({
            cantidadMinuto: {
                type: Number,
                default: 15
            },
            fuerza: {
                type: Number,
                default: 1
            },
            vida: {
                type: Number,
                default: 1
            },
            velocidad: {
                type: Number,
                default: 50
            }
        })
        const ZombieEsqueleto = mongoose.model('ZombieEsqueleto', statsZombieEsqueletoSchema);
        const zombieEsq = new ZombieEsqueleto(statsZombie)
        zombieEsq.save(function (err) {
            if (err) return console.log(err);
            console.log("¡Zombie esqueleto insertado!");
            //readDB.getStats()
            callback();
        })
    } else if (statsZombie[2] == 2) {
        const statsZombieCaballero = new mongoose.Schema({
            cantidadMinuto: {
                type: Number,
                default: 15
            },
            fuerza: {
                type: Number,
                default: 1
            },
            vida: {
                type: Number,
                default: 1
            },
            velocidad: {
                type: Number,
                default: 20
            }
        })
    }
}


const insertPartida = function (partida, callback) {
    const newPartida = new Partida(partida);
    newPartida.save(function (err) {
        if (err) return console.log(err);
        console.log("¡Nueva partida insertada!");
        callback();
    })
}

const insertStats = function (callback) {
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
    insertStats,
    addNewZombie
};
