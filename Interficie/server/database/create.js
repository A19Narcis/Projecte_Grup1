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

const insertStats = function (callback) {
    //Afegir 5 docuemnts per Axe, War, Shield, Zombie, Boss els que venen ja fets amb el joc

    const statsCrossbow = new Stats({
        nombreTipo: "Crossbow",
        velocidad: 3,
        fuerza: 1,
        vida: 2,
        armadura: 0
    })
    statsCrossbow.save(function (err) {
        if (err) return console.log("Ya existen las stats de CROSSBOW");
        console.log("¡Stats de CROSSBOW insertadas!");
    })

    const statsWar = new Stats({
        nombreTipo: "Warhammer",
        velocidad: 3,
        fuerza: 2,
        vida: 2,
        armadura: 0
    })
    statsWar.save(function (err) {
        if (err) return console.log("Ya existen las stats de WARHAMMER");
        console.log("¡Stats de WARHAMMER insertadas!");
    })

    const statsShield = new Stats({
        nombreTipo: "Shield",
        velocidad: 3,
        fuerza: 1,
        vida: 3,
        armadura: 2
    })
    statsShield.save(function (err) {
        if (err) return console.log("Ya existen las stats de SHIELD");
        console.log("¡Stats de SHIELD insertadas!");
    })

    const statsZombie = new Stats({
        nombreTipo: "Zombie",
        cantidadMinuto: 10,
        velocidad: 3,
        fuerza: 2,
        vida: 2
    })
    statsZombie.save(function (err) {
        if (err) return console.log("Ya existen las stats de ZOMBIE");
        console.log("¡Stats de ZOMBIE insertadas!");
    })

    const statsBoss = new Stats({
        nombreTipo: "Boss",
        cantidadMinuto: 1,
        velocidad: 3,
        fuerza: 2,
        vida: 2
    })
    statsBoss.save(function (err) {
        if (err) return console.log("Ya existen las stats de BOSS");
        console.log("¡Stats de BOSS insertadas!");
    })

    callback();
}

module.exports = {
    insertPartida,
    insertStats,
    addNewZombie
};
