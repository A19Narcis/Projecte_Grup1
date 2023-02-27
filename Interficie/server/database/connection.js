const mongoose = require("mongoose")
const autoIncrement = require("mongoose-auto-increment")
const { Partida, Stats } = require("./schemas")

autoIncrement.initialize(mongoose.connection)
const URL = 'mongodb://a19nargomcar2:paco1234@labs.inspedralbes.cat:7010/?tls=false&authMechanism=DEFAULT&authSource=DAMA_Grup1'
//const URL = 'mongodb://localhost:27017/DAMA_Grup1'
const options = {
    dbName: 'DAMA_Grup1',
    connectTimeoutMS: 10000
};

main().catch(err => console.log(err));


async function main() {
    try{
        await mongoose.connect(URL, options).then(
            () => {
                console.log('Connexió establerta al MongoDB!');
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
        handleError(error);
    }
}


module.exports = { 
    Partida,
    Stats
}

