const mongoose = require("mongoose")

const { Partida } = require('./connection');
const { Stats } = require('./connection');

const readDB = require('./read')


const addNewZombie = function (statsZombie, callback) {
    const statsNew = new Stats(statsZombie)
    statsNew.save(function (err) {
        if(err){
            callback(statsZombie.nombreTipo + " ya existe...")
        } else {
            callback()
        }
    })
}

const insertPartida = function (partida, callback) {
    const newPartida = new Partida(partida);
    newPartida.save(function (err) {
        if (err) return console.log(err);
        console.log("¡Nueva partida insertada!");
        callback();
    })
}

module.exports = {
    insertPartida,
    addNewZombie
};