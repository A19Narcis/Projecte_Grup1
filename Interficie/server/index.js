const express = require('express');
const cors = require("cors");
const fs = require('fs');
const path = require('path');
const sessions = require('express-session');
var cookieParser = require('cookie-parser');
const app = express();

const PORT = 7073;

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
    cookie: { maxAge: 60000 },
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
        console.log(origin);
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