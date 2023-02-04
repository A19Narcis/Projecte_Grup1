const mongoose = require("mongoose")

const { Partida } = require('./connection');
const { Stats } = require('./connection');

const readDB = require('./read')


const addNewZombie = function (statsZombie, callback) {
    if (statsZombie.nombreTipo == "ZombieEsqueleto") {
        const zombieEsq = new Stats(statsZombie)
        zombieEsq.save(function (err) {
            if (err){
                callback("Zombie esqueleto ya existe...");
            } else {
                callback("¡Zombie esqueleto insertado!");
            }
        })
    } else if (statsZombie[2] == 2) {
        
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
    //Afegir 5 docuemnts per Axe, War, Shield, Zombie, Boss els que venen ja fets amb el joc

    const statsAxe = new Stats({
        nombreTipo: "Axe",
        velocidad: 3,
        fuerza: 2,
        vida: 2,
        armadura: 0
    })
    statsAxe.save(function (err) {
        if (err) return console.log("Ya existen las stats de AXE");
        console.log("¡Stats de AXE insertadas!");
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
