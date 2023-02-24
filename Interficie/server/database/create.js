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

const insertStats = async function (callback) {
    const statsPromises = [];
  
    const statsCrossbow = new Stats({
      nombreTipo: "Crossbow",
      velocidad: 3,
      fuerza: 1,
      vida: 2,
      armadura: 0,
      url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/crossbow.png",

    });

    const validateStatsCrossbow = await Stats.findOne({ nombreTipo: statsCrossbow.nombreTipo })
    if (validateStatsCrossbow !== null) {
        console.log("Stats de CROSSBOW ya existen");
    } else {
        statsPromises.push(statsCrossbow.save());
        console.log("Stats de CROSSBOW insertadas");
    }


    
    const statsWar = new Stats({
      nombreTipo: "Warhammer",
      velocidad: 3,
      fuerza: 2,
      vida: 2,
      armadura: 0,
      url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/warhammer.png"
    });
  
    const validateStatsWar = await Stats.findOne({ nombreTipo: statsWar.nombreTipo })
    if (validateStatsWar !== null) {
        console.log("Stats de WARHAMMER ya existen");
    } else {
        statsPromises.push(statsWar.save());
        console.log("Stats de WARHAMMER insertadas");
    }
    
  
    const statsShield = new Stats({
      nombreTipo: "Shield",
      velocidad: 3,
      fuerza: 1,
      vida: 3,
      armadura: 2,
      url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/shield.png"
    });

    const validateStatsShield = await Stats.findOne({ nombreTipo: statsShield.nombreTipo })
    if (validateStatsShield !== null) {
        console.log("Stats de SHIELD ya existen");
    } else {
        statsPromises.push(statsShield.save());
        console.log("Stats de SHIELD insertadas");
    }
  
    
  
    const statsZombie = new Stats({
      nombreTipo: "Zombie",
      cantidadMinuto: 10,
      velocidad: 3,
      fuerza: 2,
      vida: 2,
      url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/zombie.png",
      puntos: 1
    });
  
    const validateStatsZombie = await Stats.findOne({ nombreTipo: statsZombie.nombreTipo })
    if (validateStatsZombie !== null) {
        console.log("Stats de ZOMBIE ya existen");
    } else {
        statsPromises.push(statsZombie.save());
        console.log("Stats de ZOMBIE insertadas");
    }
    
  
    const statsBoss = new Stats({
      nombreTipo: "Boss",
      cantidadMinuto: 1,
      velocidad: 3,
      fuerza: 2,
      vida: 2,
      url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/boss.png",
      puntos: 2
    });
  
    const validateStatsBoss = await Stats.findOne({ nombreTipo: statsBoss.nombreTipo })
    if (validateStatsBoss !== null) {
        console.log("Stats de BOSS ya existen");
    } else {
        statsPromises.push(statsBoss.save());
        console.log("Stats de BOSS insertadas");
    }
    
  
    Promise.all(statsPromises)
      .then(() => {
        console.log("Todas las stats han sido guardadas correctamente.");
        callback();
      })
      .catch((err) => {
        console.error("Error al guardar las estadísticas: ", err);
        callback(err);
      });
};

module.exports = {
    insertPartida,
    insertStats,
    addNewZombie
};
