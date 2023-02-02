const mongoose = require("mongoose")
const autoIncrement = require("mongoose-auto-increment")

autoIncrement.initialize(mongoose.connection)

const URL = 'mongodb://a19nargomcar2:paco1234@labs.inspedralbes.cat:7010/?tls=false&authMechanism=DEFAULT&authSource=DAMA_Grup1'
//const URL = 'mongodb://localhost:27017/projecte'
const options = {
    dbName: 'DAMA_Grup1'
};

mongoose.set('strictQuery', true);

main().catch(err => console.log(err));

const statsAxeSchema = new mongoose.Schema({
    velocidad: {
        type: Number,
        default: 50
    },
    fuerza: {
        type: Number,
        default: 2
    },
    vida: {
        type: Number,
        default: 3
    },
    armadura: {
        type: Number,
        default: 0
    }
})

const statsShiSchema = new mongoose.Schema({
    velocidad: {
        type: Number,
        default: 50
    },
    fuerza: {
        type: Number,
        default: 1
    },
    vida: {
        type: Number,
        default: 3
    },
    armadura: {
        type: Number,
        default: 3
    }
})

const statsWarSchema = new mongoose.Schema({
    velocidad: {
        type: Number,
        default: 50
    },
    fuerza: {
        type: Number,
        default: 2
    },
    vida: {
        type: Number,
        default: 3
    },
    armadura: {
        type: Number,
        default: 0
    }
})

const statsZombieScema = new mongoose.Schema({
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

const statsZombieBossScema = new mongoose.Schema({
    cantidadMinuto: {
        type: Number,
        default: 1
    },
    fuerza: {
        type: Number,
        default: 2
    },
    vida: {
        type: Number,
        default: 2
    },
    velocidad: {
        type: Number,
        default: 15
    }
})



const jugadorSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    tipo: {
        type: String,
        required: true
    },
    kills: {
        type: Number,
        default: 0
    }
})

const partidaSchema = new mongoose.Schema({
    idP: {
        type: Number,
        unique: true,
        required: true,
        autoIncrement: true
    },
    jugadores: [jugadorSchema],
    tiempo: {
        type: String,
        required: true
    },
    puntos: {
        type: Number,

    }
});

partidaSchema.plugin(autoIncrement.plugin, { model: 'Partida', field: 'idP'})
const Partida = mongoose.model('Partida', partidaSchema);

const AxeStats = mongoose.model('AxeStats', statsAxeSchema);
const WarStats = mongoose.model('WarStats', statsWarSchema);
const ShieldStats = mongoose.model('ShieldStats', statsShiSchema);

const ZombieStats = mongoose.model('ZombieStats', statsZombieScema);
const BossStats = mongoose.model('BossStats', statsZombieBossScema);

async function main() {
    try{
        await mongoose.connect(URL, options);
        console.log('Connexió establerta al MongoDB!');

        //Close connection
        /*mongoose.connection.close(function(){
            console.log('Connexió amb la BD tancada');
            process.exit(0);
        });*/

    } catch (error) {
        handleError(error);
    }
}


module.exports = { 
    Partida,
    AxeStats,
    WarStats,
    ShieldStats,
    ZombieStats,
    BossStats
}
