const mongoose = require("mongoose")
const autoIncrement = require("mongoose-auto-increment")
const { Partida, Stats } = require("./schemas")


const user = "a19nargomcar2"
const passwd = "paco1234"
const host = "labs.inspedralbes.cat"
const puerto = 7010
const database = "DAMA_Grup1"

autoIncrement.initialize(mongoose.connection)
const URL = 'mongodb://' + user + ':' + passwd + '@' + host + ':' + puerto + '/?tls=false&authMechanism=DEFAULT&authSource=' + database + ''
//const URL = 'mongodb://127.0.0.1:27017/DAMA_Grup1'
const options = {
    dbName: 'DAMA_Grup1',
    connectTimeoutMS: 10000
};

main().catch(err => console.log(err));


async function main() {
    try {
        await mongoose.connect(URL, options).then(
            async () => {
                console.log('Connexió establerta al MongoDB!');

                /* AÑADIR LOS PJs QU VIENEN POR DEFECTO Y NO SE PUEDEN BORRAR DESDE LA WEB */
                const statsCrossbow = new Stats({
                    nombreTipo: "Crossbow",
                    velocidad: 5,
                    fuerza: 2,
                    vida: 2,
                    armadura: 0,
                    //url: "http://192.168.236.187:5500/Projecte_Grup1/Interficie/server/uploads/crossbow.png"
                    url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/crossbow.png",
                });

                const validateStatsCrossbow = await Stats.findOne({ nombreTipo: statsCrossbow.nombreTipo })
                if (validateStatsCrossbow == null) {
                    await statsCrossbow.save();
                    console.log("Stats de CROSSBOW insertadas");
                }

                const statsWar = new Stats({
                    nombreTipo: "Warhammer",
                    velocidad: 3,
                    fuerza: 3,
                    vida: 3,
                    armadura: 0,
                    //url: "http://192.168.236.187:5500/Projecte_Grup1/Interficie/server/uploads/warhammer.png"
                    url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/warhammer.png"
                });

                const validateStatsWar = await Stats.findOne({ nombreTipo: statsWar.nombreTipo })
                if (validateStatsWar == null) {
                    await statsWar.save();
                    console.log("Stats de WARHAMMER insertadas");
                }

                const statsShield = new Stats({
                    nombreTipo: "Shield",
                    velocidad: 3,
                    fuerza: 1,
                    vida: 5,
                    armadura: 3,
                    //url: "http://192.168.236.187:5500/Projecte_Grup1/Interficie/server/uploads/shield.png"
                    url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/shield.png"
                });

                const validateStatsShield = await Stats.findOne({ nombreTipo: statsShield.nombreTipo })
                if (validateStatsShield == null) {
                    await statsShield.save();
                    console.log("Stats de SHIELD insertadas");
                }

                const statsZombie = new Stats({
                    nombreTipo: "Zombie",
                    cantidadMinuto: 20,
                    velocidad: 1,
                    fuerza: 1,
                    vida: 1,
                    //url: "http://192.168.236.187:5500/Projecte_Grup1/Interficie/server/uploads/zombie.png",
                    url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/zombie.png",
                    puntos: 1,
                    mapa: [1, 2]
                });

                const validateStatsZombie = await Stats.findOne({ nombreTipo: statsZombie.nombreTipo })
                if (validateStatsZombie == null) {
                    await statsZombie.save();
                    console.log("Stats de ZOMBIE insertadas");
                }


                const statsBoss = new Stats({
                    nombreTipo: "Boss",
                    cantidadMinuto: 4,
                    velocidad: 2,
                    fuerza: 2,
                    vida: 2,
                    //url: "http://192.168.236.187:5500/Projecte_Grup1/Interficie/server/uploads/boss.png",
                    url: "http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/boss.png",
                    puntos: 5,
                    mapa: [1, 2]
                });

                const validateStatsBoss = await Stats.findOne({ nombreTipo: statsBoss.nombreTipo })
                if (validateStatsBoss == null) {
                    await statsBoss.save();
                    console.log("Stats de BOSS insertadas");
                }
            },
            err => {
                console.log('No es pot connectar al MongoDB...');
            }
        );

        //Close connection
        /*mongoose.connection.close(function(){
            console.log('Connexió amb la BD tancada');
            process.exit(0);
        });*/

    } catch (error) {
        console.log(error);
    }
}


module.exports = {
    Partida,
    Stats
}