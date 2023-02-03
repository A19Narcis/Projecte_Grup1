const express = require('express');
const cors = require("cors");
const fs = require('fs');
const path = require('path');
const sessions = require('express-session');
var cookieParser = require('cookie-parser');
const database = require("./database/connection")
const readDB = require('./database/read')
const insertDB = require('./database/create')
const updateDB = require('./database/update')
const app = express();

const PORT = 3000;

app.use(cookieParser());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");
    res.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
    next();
});
app.use(sessions({
    secret: "sadsadsadasd",
    resave: false,
    saveUninitialized: false,
    cookie: { maxAge: 100000 },
    user:{ isAuth: false}
    
}));

//Middleware necessari per parsejar el body i colocar-ho dintre del req.body.
app.use(express.json());


//Configuració del mòdul de cors. 
//Per cada petició es crida aquest middleware (app.use)
//rep dos parametres el mòdul cors. El primer és l'origen, i el segon és una funció
//de callback que ens permet controlar si acceptem la petició o no.
app.use(cors({ 
    credentials: true,
    origin: function(origin, callback){
        return callback(null, true)
    }
}));

//Ruta a /auth amb dos parametres que s'envien per "param"
app.post("/authPost", async (req, res) => {
    console.log("body ==============> ", req.body);
    console.log("session ==============>  ", req.session);
    console.log("headers ==============>  ", req.headers);

    let username = req.body.values[0];
    let passwd = req.body.values[1];
  
    ret = await checkUserFromJson(username, passwd);

    req.session.user = ret;

    console.log(req.session);

    res.send(JSON.stringify(ret));
      
});

//Ruta a /statusPost amb dos parametres que s'envien per "param"
app.post("/statusPost", async (req, res) => {
    console.log("body ==============> ", req.body);
    console.log("session ==============>  ", req.session);
    console.log("headers ==============>  ", req.headers);
    let session = req.session;

    var ret = {
        text: "No hi ha cap sessió activa"
    };  
    //console.log(session);

    console.log(session);
    if(session.user && session.user.isAuth){
        var ret = {
            text: "Sessió Activa => amb usuari "+session.user.username
        };
    }

    console.log(ret)
    res.send(JSON.stringify(ret));
      
});

//Ruta a /logOutPost amb dos parametres que s'envien per "param"

app.listen (PORT, ()=>{
    console.log("Server Running ["+PORT+"]");
});

function checkUserFromJson(username, passwd){
    let ret = {
        isAuth: false
    };
    console.log("username => "+username);
    console.log("password => "+passwd);
    var prom = new Promise((resolve, reject) => {
        //llegim l'array de admins
         fs.readFile(path.join(__dirname, "admins.json"), (err, data) =>{
             if(err){
                 console.log(err);
             }
             var dades = JSON.parse(data);
             console.log(data);
             //fins es una funció d'arrays en javascript.
             user = dades.admins.find(user => {      
                 if(user.username == username && user.password == passwd){
                     return true;
                 }
             });
             if(user){
                 console.log("user found");
                 ret.isAuth = user.isAuth;
                 ret.username = user.username;
             }
             console.log(ret);
             resolve(ret);
        }); 
    });
    return prom;
}

app.post("/getSession", async (req, res) => {
    console.log("body ==============> ", req.body);
    console.log("session ==============>  ", req.session);
    console.log("headers ==============>  ", req.headers);
    let session = req.session;

    var ret = {
        text: "No hi ha cap sessió activa"
    };  
    //console.log(session);

    console.log(session);
    if(session.user && session.user.isAuth){
        var ret = {
            isAuth: session.user.isAuth,
            username: session.user
        };
    }

    console.log(ret)
    res.send(JSON.stringify(ret));
      
});



/* =========== FUNCIONS RELACIONADES MONGODB =========== */

//Insertat los primeros datos del juego (Stats Jugadores, Zombies)
app.post("/", (req, res) => {
    insertDB.insertStats(function () {
        res.send({ success : true })
    });
})

//Ver las estadisticas de todos los personajes
app.post("/getStats", (req, res) => {
    readDB.getStats(function (dades) {
        res.send(dades)
    })
})

//Actualiza las stats de un tipo de personaje segun el tipo -> req.body.values[0]
app.post("/updateStats", (req, res) => {
    const tipo = req.body.values[0];
    let newStats = {}
    if (tipo <= 3) {
        newStats = {
            velocidad: req.body.values[1],
            fuerza: req.body.values[2],
            vida: req.body.values[3],
            armadura: req.body.values[4]
        }
    } else {
        newStats = {
            cantidad: req.body.values[1],
            fuerza: req.body.values[2],
            vida: req.body.values[3],
            velocidad: req.body.values[4]
        }
    }
    
    updateDB.updateStats(tipo, newStats, function () {
        res.send(newStats)
    })
})


//Insertar una nueva partida cuando acaba en la APP
app.post("/newPartida", (req, res) => {
    const partida = {
        jugadores: [
            {
                username: "Narcis",
                tipo: "Axe",
                kills: 12
            },
            {
                username: "Sergi",
                tipo: "Warhammer",
                kills: 32
            },
            {
                username: "Teo",
                tipo: "Shield",
                kills: 28
            },
        ],
        tiempo: "32:11"
    }
    insertDB.insertPartida(partida, function () {
        res.send({ success: true })
    })
})


//Obtener todas las partidas que hay registradas
app.post("/getPartidas", (req, res) => {
    readDB.getPartidas(function (partidas) {
        res.send(partidas)
    })
})


//Obtener las stats del tipo de personaje que nos indican
app.get("/getStatsPlayer/:nombreTipo", (req, res) => {
    var tipo = req.params.nombreTipo;
    readDB.getStatsSelected(tipo, function (dades) {
        res.send(dades)
    })    
})