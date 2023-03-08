const express = require('express');
const cors = require("cors");
const fs = require('fs');
const path = require('path');
const sessions = require('express-session');
var cookieParser = require('cookie-parser');
//const database = require("./database/connection")
const readDB = require('./database/read')
const insertDB = require('./database/create')
const updateDB = require('./database/update')
const deleteDB = require('./database/delete')
const app = express();
const http = require('http');
const { Server } = require("socket.io");
const server = http.createServer(app);
const multer = require('multer');
const { Socket } = require('dgram');
const upload = multer({ dest: 'uploads/' });
const CryptoJS = require('crypto-js');
const nodemailer = require('nodemailer');


const io = new Server(server);


const PORT = 7073
const SOCKET_PORT = 7074

app.use(cookieParser());
app.use(function (req, res, next) {
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
    user: { isAuth: false }

}));

//Middleware necessari per parsejar el body i colocar-ho dintre del req.body.
app.use(express.json());


//Configuració del mòdul de cors. 
//Per cada petició es crida aquest middleware (app.use)
//rep dos parametres el mòdul cors. El primer és l'origen, i el segon és una funció
//de callback que ens permet controlar si acceptem la petició o no.
app.use(cors({
    credentials: true,
    origin: function (origin, callback) {
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
    if (session.user && session.user.isAuth) {
        var ret = {
            text: "Sessió Activa => amb usuari " + session.user.username
        };
    }

    console.log(ret)
    res.send(JSON.stringify(ret));

});

app.post('/upload', upload.single('image'), (req, res) => {
    const filePath = req.file.path;
    const originalName = req.file.originalname;


    fs.readFile(filePath, (err, data) => {
        if (err) throw err;

        const contentType = req.file.mimetype;
        const fileExt = contentType.split('/')[1];

        const newFileName = `${originalName.split('.')[0]}.${fileExt}`;

        const newPath = `${__dirname}/uploads/${newFileName}`;
        fs.unlink(filePath, (err => { }));
        if (fs.existsSync(newPath)) {
            console.log("EXISTS");
        }


        else {
            fs.writeFile(newPath, data, { encoding: 'binary' }, (err) => {
                if (err) throw err;

                console.log(`File ${newFileName} uploaded successfully!`);

                res.json({ success: true });
            });
        }

    });
});


app.post('/uploadCropped', upload.single('croppedImage'), (req, res) => {
    const filePath = req.file.path;
    const originalName = req.file.originalname;


    fs.readFile(filePath, (err, data) => {
        if (err) throw err;

        const contentType = req.file.mimetype;
        const fileExt = contentType.split('/')[1];

        const newFileName = `${originalName.split('.')[0]}.${fileExt}`;
        console.log(filePath);
        const newPath = `${__dirname}/uploads/cropped_${newFileName}`;
        fs.unlink(filePath, (err => { }));
        if (fs.existsSync(newPath)) {
            const newPath = `${__dirname}/uploads/cropped_${newFileName}`;
        }


        else {
            fs.writeFile(newPath, data, { encoding: 'binary' }, (err) => {
                if (err) throw err;

                console.log(`File ${newFileName} uploaded successfully!`);

                res.json({ success: true });
            });
        }

    });
});


app.listen(PORT, () => {
    console.log("Server Running [" + PORT + "]");
});



app.post("/sendEmail", (req, res) => {
    console.log(req.body.values);
    let dataN = fs.readFileSync('./copia.json', 'utf8');
    let obj = JSON.parse(dataN);
    for(const el of obj.admins){
        if(el.username === req.body.values[0])
        {
            console.log("IGUALES")
            res.send("NO")
            res.end();
            return;
        }
    }
     var transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'baldhead.oficial@gmail.com',
            pass: 'zawzvjjxswcjrfxs'
        }
    });

    let html2 = fs.readFileSync('../../../emailTemplate.html', 'utf8');

    let mailOptions = {
        from: 'baldhead.oficial@gmail.com', 
        to: req.body.values[0], 
        subject: 'REGISTRADO',
        html: html2
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            return console.log(error);
        }
        console.log('Message sent: %s', info.messageId);
        let passwd = CryptoJS.SHA256(req.body.values[1]).toString();
        let newObj = {
            "username": req.body.values[0],
            "password": passwd,
            "isAuth": true,
            "rol": "user"
          };
          
        // push the new object to the `admins` array
        obj.admins.push(newObj);

        fs.writeFile("./copia.json", JSON.stringify(obj), (err) => {
            console.log(err);
            if(!err)
                res.json({ success: true });

        });
    });
});


function checkUserFromJson(username, passwd) {
    let ret = {
        isAuth: false
    };
    console.log("username => " + username);
    console.log("password => " + passwd);
    passwd = CryptoJS.SHA256(passwd).toString();
    console.log("after hash => " + passwd);
    var prom = new Promise((resolve, reject) => {
        //llegim l'array de admins
        fs.readFile(path.join(__dirname, "copia.json"), (err, data) => {
            if (err) {
                console.log(err);
            }
            var dades = JSON.parse(data);
            console.log(data);
            //fins es una funció d'arrays en javascript.
            user = dades.admins.find(user => {
                console.log("USER" + user.username);
                if (user.username == username && user.password == passwd) {
                    return true;
                }
            });
            if (user) {
                console.log("user found");
                ret.isAuth = user.isAuth;
                ret.username = user.username;
                ret.rol = user.rol;
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
    if (session.user && session.user.isAuth) {
        var ret = {
            isAuth: session.user.isAuth,
            username: session.user.username,
            rol: session.user.rol
        };
    }

    console.log("RET GET " + JSON.stringify(ret))
    res.send(JSON.stringify(ret));

});


/* =========== SOCKETS MULTIJUGADOR =========== */

var players = []
var puntosPartida = 0

//Controlar les connexions i desconnexions
io.on('connection', (socketJugador) => {

    if (players.length == 0) {
        puntosPartida = 0;
    }

    console.log("Jugador conectado");
    socketJugador.emit('socketID', { id: socketJugador.id });
    socketJugador.emit('getPlayers', players);
    socketJugador.on("newStatsPlayer", function (x, y, tipo, direccion, vidas, kills, username) {
        players.push(new player(socketJugador.id, x, y, tipo, direccion, vidas, kills, username));
        socketJugador.broadcast.emit('new_player', { id: socketJugador.id, xPl: x, yPl: y, tipoPl: tipo, direPl: direccion, vidasPl: vidas, killsPl: kills, usernamePl: username });
    });

    socketJugador.on('coorJugador', function(x, y, direccion, vidas, kills, username, ataque) {
        for (let i = 0; i < players.length; i++) {
            if (players[i].id == socketJugador.id) {
                players[i].x = x;
                players[i].y = y;
                players[i].direccion = direccion
                players[i].vidas = vidas
                players[i].username = username
                players[i].kills = kills
                players[i].ataque = ataque
                socketJugador.broadcast.emit('coorNuevas', players[i])
            }
        }    
    })

    socketJugador.on('updatePuntos', function (puntosKill) {
        puntosPartida = puntosPartida + puntosKill;
        socketJugador.broadcast.emit('newPuntosPartida', puntosPartida)
    })

    socketJugador.on('zombieInfo', function (deadEnemies, enemies) {
        socketJugador.broadcast.emit('newZombieInfo', deadEnemies, enemies)
    })


    socketJugador.on('disconnect', function () {
        console.log("Jugador desconectado");
        socketJugador.broadcast.emit('player_disc', { id: socketJugador.id });
        for (let i = 0; i < players.length; i++) {
            if (players[i].id == socketJugador.id) {
                players.splice(i, 1);
            }

        }
    });
});

function player(id, x, y, tipo, direccion, vidas, kills, username) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.tipo = tipo,
        this.direccion = direccion,
        this.vidas = vidas
    this.kills = kills,
        this.username = username
}

server.listen(SOCKET_PORT, () => {
    console.log("Sockets Running [" + SOCKET_PORT + "]");
});

/* =========== FUNCIONS RELACIONADES MONGODB =========== */

//Insertat los primeros datos del juego (Stats Jugadores, Zombies)
app.post("/", (req, res) => {
    insertDB.insertStats(function () {
        res.send({ success: true })
    });
})

//Ver las estadisticas de todos los personajes
app.get("/getStats", (req, res) => {
    readDB.getStats(function (dades) {
        res.send(dades)
    })
})

app.post("/getStats2", (req, res) => {
    readDB.getStats(function (dades) {
        res.send(dades)
    })
})

//Actualiza las stats de un tipo de personaje segun el tipo -> req.body.values[0]
app.post("/updateStats", (req, res) => {
    console.log(req.body.values);

    if (!isNaN(req.body.values[0]))
        return;
    var type = req.body.values[7];

    let newStats = {}
    if (req.body.values[2] <= 2) {
        newStats = {
            nombreTipo: type,
            velocidad: req.body.values[3],
            fuerza: req.body.values[4],
            vida: req.body.values[5],
            armadura: req.body.values[6],

        }
    } else {
        newStats = {
            nombreTipo: type,
            cantidadMinuto: req.body.values[6],
            velocidad: req.body.values[3],
            fuerza: req.body.values[4],
            vida: req.body.values[5],
            puntos: req.body.values[8],
            mapa: req.body.values[9]
        }
    }
    updateDB.updateStats(newStats, function (updatedStats) {
        res.send(updatedStats)
    })
})



//Insertar una nueva partida cuando acaba en la APP
app.post("/newPartida", (req, res) => {
    const partida = {
        jugadores: req.body.jugadores,
        tiempo: req.body.tiempo,
        puntos: req.body.puntos,
        mapa: req.body.mapa
    }
    insertDB.insertPartida(partida, function () {
        res.send({ success: true })
    })
})


//Obtener todas las partidas que hay registradas
app.get("/getPartidas", (req, res) => {
    readDB.getPartidas(function (partidas) {
        res.send(partidas)
    })
})
app.post("/getPartidas2", (req, res) => {
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


//Delete
app.post("/delete", (req, res) => {
    console.log(req.body.values[2]);
    var deletedType = req.body.values[2];
    fs.unlink(req.body.values[3], (err => { }));
    fs.unlink(req.body.values[4], (err => { }));

    deleteDB.removeStatsFrom(deletedType, (callback) => {
        res.send(callback)
    })
})


//Insert new Zombie
app.post("/addNewZombie", (req, res) => {
    console.log(req.body.values);
    var type = '';
    if (!isNaN(req.body.values[0]))
        return;

    var statsNewZombie = {
        nombreTipo: req.body.values[2],
        cantidadMinuto: req.body.values[3],
        velocidad: req.body.values[4],
        fuerza: req.body.values[5],
        vida: req.body.values[6],
        url: req.body.values[7],
        puntos: req.body.values[8],
        mapa: req.body.values[9]
    }

    insertDB.addNewZombie(statsNewZombie, (callback) => {
        console.log("Nuevo enemigo añadido: " + statsNewZombie.nombreTipo)
        res.send({ success: true })
    })
})
