var vue_app = new Vue({
    el: "#app",
    vuetify: new Vuetify(),
    data: {
        username: '',
        pass: '',
        postData: null,
        info: { values: [] },
        showPassword: false,
        auth: false,
        ready: 0,
        arrStats: [],
        arrLen: 0,
        dialog_1: false,
        values: [],
        id: 0,
        image: null,
        dialog_2: false,
        iter: 0,
        nombre: '',
        new_fuerza: 0,
        new_velocidad: 0,
        new_vida: 0,
        new_cantidad: 0,
        new_puntos: 0,
        done: 0,
        color: '',
        canvas: null,
        canvas2: null,
        arrNom: [],
        arrFuerza: [],
        arrVida: [],
        arrArmadura: [],
        arrVelocidad: [],
        zomArrNom: [],
        zomArrFuerza: [],
        zomArrVida: [],
        zomArrArmadura: [],
        zomArrVelocidad: [],
        zomPuntos: [],
        canvas3: null,
        medianaArr: [],
        url: '',

        
    },
    mounted(){
        this.getStats();
        this.getPartidas();
    },

    methods: {
        createCanv: function() {
            this.canvas = document.getElementById('canvas')

            var ctx = this.canvas.getContext('2d');
            //hay que llamar al get stats como sea, usaba dos funciones.
            this.canvas.width = 800;
            this.canvas.height = 600;
            var chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: this.arrNom,
                    datasets: [{
                        label: 'Fuerza',
                        data: this.arrFuerza,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    },{
                        label: 'Vida',
                        data: this.arrVida,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    },{
                        label: 'Velocidad',
                        data: this.arrVelocidad,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    },{
                        label: 'Armadura',
                        data: this.arrArmadura,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            id: 'y-axis-1',
                            type: 'linear',
                            position: 'left',
                            ticks: {min: 0, max:10}
                        }]
                    }
                }
            });


            this.canvas2 = document.getElementById('canvas2')
            var ctx2 = this.canvas2.getContext('2d');
            //hay que llamar al get stats como sea, usaba dos funciones.
            this.canvas2.width = 800;
            this.canvas2.height = 600;
            var chart2 = new Chart(ctx2, {
                type: 'bar',
                data: {
                    labels: this.zomArrNom,
                    datasets: [{
                        label: 'Puntos',
                        data: this.zomPuntos,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                    },{
                        label: 'Vida',
                        data: this.zomArrVida,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                    },{
                        label: 'Velocidad',
                        data: this.zomArrVelocidad,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                    },{
                        label: 'Fuerza',
                        data: this.zomArrFuerza,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                    }]
                },
                options: {  
                    scales: {
                        yAxes: [{
                            type: 'linear',
                            position: 'left',
                            ticks: {min: 0, max:10}
                        }]                    }
                }
            });

            this.canvas3 = document.getElementById('canvas3')
            var ctx3 = this.canvas3.getContext('2d');
            //hay que llamar al get stats como sea, usaba dos funciones.
            this.canvas3.width = 800;
            this.canvas3.height = 600;
            var arrNoms = ['0-250', '251-500', '501-750', '751-1000', '>1000'];
            var chart3 = new Chart(ctx3, {
                type: 'bar',
                data: {
                    labels: arrNoms,
                    datasets: [{
                        label: 'MEDIANA TIEMPO',
                        data: this.medianaArr,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                    }]
                },
                options: {  
                    scales: {
                        yAxes: [{
                            type: 'linear',
                            position: 'left',
                            ticks: {min: 0, max:this.arrPartidas.length}
                        }]                    }
                }
            }); 
        },
        createElements: function (){
            var count = 0;
            this.arrNom = [];
            this.arrFuerza = [];
            this.arrVida = [];
            this.arrArmadura = [];
            this.arrVelocidad = [];
            this.zomArrNom = [];
            this.zomArrFuerza = [];
            this.zomArrVida = [];
            this.zomArrArmadura = [];
            this.zomArrVelocidad = [];  
            this.zomPuntos = [];
            this.arrStats.forEach( el =>{
                if(count < 3){
                    this.arrNom.push(el.nombreTipo);
                    this.arrArmadura.push(el.armadura);
                    this.arrVelocidad.push(el.velocidad);
                    this.arrFuerza.push(el.fuerza);
                    this.arrVida.push(el.vida);
                }else{
                    this.zomArrNom.push(el.nombreTipo);
                    this.zomPuntos.push(el.puntos);
                    this.zomArrVelocidad.push(el.velocidad);
                    this.zomArrFuerza.push(el.fuerza);
                    this.zomArrVida.push(el.vida);
                }                
                count++;
            });
            this.createCanv();
        },

        createElements2: function(){
            var one = 0; //0-250
            var two = 0; //251-500
            var three = 0; //501-750
            var four = 0; //751-1000
            var five = 0; //>1000
            this.arrPartidas.forEach(el =>{
                if(el.puntos <= 250 && el.puntos >= 0)
                    one++;
                if(el.puntos <= 500 && el.puntos >= 251)
                    two++;
                if(el.puntos <= 750 && el.puntos >= 501)
                    three++;
                if(el.puntos <= 1000 && el.puntos >= 751)
                    four++;
                if(el.puntos >= 1001)
                    five++;
            });
            this.medianaArr.push(one);
            this.medianaArr.push(two);
            this.medianaArr.push(three);
            this.medianaArr.push(four);
            this.medianaArr.push(five);
            this.createCanv();
        },
        getAuth: function () {
            this.info.values.push(this.username);
            this.info.values.push(this.pass);
            if (this.username == '' || this.pass == '') {
                this.username = ''
                this.pass = ''
                return;
            }
            fetch("http://admin.alumnes.inspedralbes.cat:7073/authPost/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    body: JSON.stringify(this.info),
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.postData = data;
                    if (data.isAuth == true) {
                        this.auth = data.isAuth;
                        this.userAuth = data.username;
                        console.log("isAuth peti" + data.isAuth);
                        console.log("userPeticion" + data.username);
                        console.log("mine-" + this.username);

                        setTimeout(() => {
                            this.ready = 1;
                        }, 4000);
                    }
                    else {
                        console.log("entro aqui")
                        this.info.values = [];
                        this.username = '';
                        this.pass = '';
                    }
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },
         uploadImage: function() {
            const fileInput = document.getElementById('file-input');
            const file = fileInput.files[0];
            this.url = 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/' + file.name;
            console.log(file);
            const formData = new FormData();
            formData.append('image', file);

            fetch('http://admin.alumnes.inspedralbes.cat:7073/upload', {
              method: 'POST',
              body: formData
            })
            .then(response => {
              if (response.ok) {
                console.log('Image uploaded successfully!');
                this.dialog_2 = true;
                
              } else {
                console.error('Failed to upload image!');
              }
            })
            .catch(error => {
              console.error('Error uploading image:', error);
            });
          },
        
        getSession: function () {
            console.log("SSSS" + this.info.values[0])
            fetch("http://admin.alumnes.inspedralbes.cat:7073/getSession/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.postData = data;
                    this.auth = data.isAuth;
                    this.userAuth = data.username;
                    this.username = this.userAuth;
                    console.log("sesion user" + this.userAuth);
                    console.log("session mine" + this.username);
                    console.log("sesion auth" + this.auth);

                    if (this.auth == true && this.userAuth == this.username) {
                        this.ready = 1;
                        this.info.values.push(this.username);
                        this.info.values.push(this.username);
                    }
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },
        status: function () {

            fetch("http://admin.alumnes.inspedralbes.cat:7073/statusPost/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.postData = data;
                    console.log(this.postData.isAuth);
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        getStats: function () {
            fetch("http://admin.alumnes.inspedralbes.cat:7073/getStats2/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.arrStats = data;
                    this.arrLen = data.length;
                    this.createElements();
                    console.log(this.arrStats);
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        getPartidas: function(){
            fetch("http://admin.alumnes.inspedralbes.cat:7073/getPartidas2/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.arrPartidas = data;
                    this.createElements2();
                    console.log(this.arrPartidas);
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        updateStats: function (id, vel, fuerza, vida, armadura, nombreTipo, puntos) {
            console.log(id);
            console.log(vel);
            console.log(fuerza);
            console.log(vida);
            console.log(armadura);
            this.info.values.push(id);
            this.info.values.push(vel);
            this.info.values.push(fuerza);
            this.info.values.push(vida);
            this.info.values.push(armadura);
            this.info.values.push(nombreTipo);
            this.info.values.push(puntos);
            fetch("http://admin.alumnes.inspedralbes.cat:7073/updateStats/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    body: JSON.stringify(this.info),
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.info.values = this.info.values.slice(0, 2);
                    this.getStats();
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            ); 
        },

        deleteZom: function (nombreTipo, url) {
            this.info.values.push(nombreTipo);
            var urlSegments = url.split("/");
            urlSegments = urlSegments.slice(-2);
            urlSegments = urlSegments.join("/")
            var del = '../server/'+ urlSegments;
            this.info.values.push(del);
            
            fetch("http://admin.alumnes.inspedralbes.cat:7073/delete/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    body: JSON.stringify(this.info),
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    this.info.values = this.info.values.slice(0, 2);
                    console.log("POST DELETE: ", this.info.values);
                    this.getStats();
                }
            ).catch(
                (error) => {
                    this.info.values = this.info.values.slice(0, 2);
                    this.getStats();
                    console.log(error);
                }
            ); 
        },

        addNewZombie: function (velocidad, fuerza, vida, cantidad, puntos) {
            var nombreTipo = 'zombie_new';
            nombreTipo = nombreTipo + (this.arrLen +1);
            console.log(nombreTipo);
            console.log(velocidad);
            console.log(fuerza);
            console.log(vida);
            console.log(cantidad);
            this.info.values.push(nombreTipo);
            this.info.values.push(cantidad);
            this.info.values.push(velocidad);
            this.info.values.push(fuerza);
            this.info.values.push(vida);
            this.info.values.push(this.url);
            this.info.values.push(puntos);
            fetch("http://admin.alumnes.inspedralbes.cat:7073/addNewZombie/",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                    credentials: "include",
                    body: JSON.stringify(this.info),
                    mode: "cors",
                    cache: "default"
                }
            ).then(
                (response) => {
                    return (response.json());
                }
            ).then(
                (data) => {
                    console.log(data);
                    this.getStats();
                    this.info.values = this.info.values.slice(0, 2);
                }
            ).catch(
                (error) => {
                    this.info.values = this.info.values.slice(0, 2);
                    console.log(error);
                }
            );

        },
    },
    created() {
        const myText = new SplitType('#title')
        const myText2 = new SplitType('#subt')
        console.log(myText);

        myText2.chars.forEach(element => {
            if (element.className == 'char') {
                element.className = "char2"
            }
        });
        gsap.to('.char', {
            y: 0,
            stagger: 0.4,
            delay: 0.2,
            duration: .1
        });
        gsap.to('.char2', {
            y: 0,
            stagger: 0.4,
            delay: 0.2,
            duration: .1
        });
        setTimeout(() => {
            window.scroll({
                top: window.innerHeight + 200,
                behavior: 'smooth'
            });
        }, 3900)

        
    }
});

