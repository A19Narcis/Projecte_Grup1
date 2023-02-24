const mongoose = require("mongoose")
const autoIncrement = require("mongoose-auto-increment")

autoIncrement.initialize(mongoose.connection)

mongoose.set('strictQuery', false);

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
    },
    url:{
        type: String,
        required: true
    },
    puntos:{
        type: Number
    },
    mapa: {
        type: [Number],
        sparse: true
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

const mapaSchema = new mongoose.Schema({
    nombre: {
        type: String,
        required: true
    }
});

partidaSchema.plugin(autoIncrement.plugin, { model: 'Partida', field: 'idP'})

const Partida = mongoose.model('Partida', partidaSchema);
const Stats = mongoose.model('Stats', statsPersonatges);
const Mapas = mongoose.model('Mapas', mapaSchema);


module.exports = { 
    Partida,
    Stats,
    Mapas
}
