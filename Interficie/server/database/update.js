const { AxeStats } = require('./connection');
const { WarStats } = require('./connection');
const { ShieldStats } = require('./connection');
const { ZombieStats } = require('./connection');
const { BossStats } = require('./connection');


const updateStats = function(tipo, newStats, callback){
    if (tipo == 1) {
        AxeStats.updateOne({}, newStats, (err, updatedStats) => {
            if (err) {
                console.log(err);
            } else {
                console.log("Axe Player has been updated");
                callback(updatedStats)
            }
        })
    } else if (tipo == 2) {
        WarStats.updateOne({}, newStats, (err, updatedStats) => {
            if (err) {
                console.log(err);
            } else {
                console.log("Warhammer Player has been updated");
                callback(updatedStats)
            }
        })
    } else if (tipo == 3) {
        ShieldStats.updateOne({}, newStats, (err, updatedStats) => {
            if (err) {
                console.log(err);
            } else {
                console.log("Shield Player has been updated");
                callback(updatedStats)
            }
        })
    } else if (tipo == 4) {
        ZombieStats.updateOne({}, newStats, (err, updatedStats) => {
            if (err) {
                console.log(err);
            } else {
                console.log("Zombie Player has been updated");
                callback(updatedStats)
            }
        })
    } else if (tipo == 5) {
        BossStats.updateOne({}, newStats, (err, updatedStats) => {
            if (err) {
                console.log(err);
            } else {
                console.log("Zombie BOSS has been updated");
                callback(updatedStats)
            }
        })
    }
}


module.exports = {
    updateStats
}