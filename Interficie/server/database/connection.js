const mongoose = require("mongoose")
const autoIncrement = require("mongoose-auto-increment")

autoIncrement.initialize(mongoose.connection)

const URL = 'mongodb://a19nargomcar2:paco1234@labs.inspedralbes.cat:7010/?tls=false&authMechanism=DEFAULT&authSource=DAMA_Grup1'
//const URL = 'mongodb://localhost:27017/DAMA_Grup1'
const options = {
    dbName: 'DAMA_Grup1',
};


mongoose.set('strictQuery', false);

main().catch(err => console.log(err));

const statsPersonatges = new mongoose.Schema({
    nombreTipo: {
        type: String,
        required: true,
        unique: true
    },
    cantidadMinuto: {
        type: Number,
    },
    velocidad: {
        type: Number,
        default: 1
    },
    fuerza: {
        type: Number,
        default: 1
    },
    vida: {
        type: Number,
        default: 1
    },
    armadura: {
        type: Number
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
        default: 0
    }
});



partidaSchema.plugin(autoIncrement.plugin, { model: 'Partida', field: 'idP'})
const Partida = mongoose.model('Partida', partidaSchema);

const Stats = mongoose.model('Stats', statsPersonatges);


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
    Stats
}
