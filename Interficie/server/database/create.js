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
        callback();
    })
}



const insertStats = function(callback) {
    AxeStats.find().then((result) => {
        if (result.length == 0) {
            console.log("Introduciendo stats Axe player...");
            const axeStats = new AxeStats();
            axeStats.save(function (err) {
                if (err) return console.log(err);
            })
        } 
    })

    WarStats.find().then((result) => {
        if (result.length == 0) {
            console.log("Introduciendo stats Warhammer player...");
            const warStats = new WarStats();
            warStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    ShieldStats.find().then((result) => {
        if (result.length == 0) {
            console.log("Introduciendo stats Shield player...");
            const shiStats = new ShieldStats();
            shiStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    ZombieStats.find().then((result) => {
        if (result.length == 0) {
            console.log("Introduciendo stats Zombie enemy...");
            const zombieStats = new ZombieStats();
            zombieStats.save(function (err) {
                if (err) return console.log(err);
            })
        }
    })

    BossStats.find().then((result) => {
        if (result.length == 0) {
            console.log("Introduciendo stats Zombie Boss enemy...");
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
