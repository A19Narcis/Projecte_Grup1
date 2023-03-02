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
        tiempoArr: [],
        url: '',
        arrBonus: [],
        map1: 0,
        map2: 0,
        rol: '',
        infoDialog: false


    },
    mounted() {
        this.getStats();
        this.getPartidas();
    },

    methods: {
        createCanv: function () {
            var canvas = document.getElementById('canvas')
            //hay que llamar al get stats como sea, usaba dos funciones.
            canvas.width = 800;
            canvas.height = 600;
            var chart = new Chart(canvas, {
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

                    }, {
                        label: 'Vida',
                        data: this.arrVida,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    }, {
                        label: 'Velocidad',
                        data: this.arrVelocidad,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-axis-1'

                    }, {
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
                            ticks: { min: 0, max: 10 }
                        }]
                    }
                }
            });


            var canvas2 = document.getElementById('canvas2')
            //hay que llamar al get stats como sea, usaba dos funciones.
            canvas2.width = 800;
            canvas2.height = 600;
            var chart2 = new Chart(canvas2, {
                type: 'bar',
                data: {
                    labels: this.zomArrNom,
                    datasets: [{
                        label: 'Puntos',
                        data: this.zomPuntos,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                    }, {
                        label: 'Vida',
                        data: this.zomArrVida,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                    }, {
                        label: 'Velocidad',
                        data: this.zomArrVelocidad,
                        backgroundColor: 'rgba(54, 262, 135, 0.2)',
                        borderColor: 'rgba(54, 262, 135, 1)',
                        borderWidth: 1,
                    }, {
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
                            ticks: { min: 0, max: 10 }
                        }]
                    }
                }
            });

            var canvas3 = document.getElementById('canvas3')
            //hay que llamar al get stats como sea, usaba dos funciones.
            canvas3.width = 800;
            canvas3.height = 600;
            var arrNoms = ['0-1000', '1000-2000', '2000-3000', '3000-4000', '>5000'];
            var chart3 = new Chart(canvas3, {
                type: 'bar',
                data: {
                    labels: arrNoms,
                    datasets: [{
                        label: 'MEDIANA PUNTOS',
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
                            ticks: { min: 0, max: this.arrPartidas.length }
                        }]
                    }
                }
            });


            var canvas4 = document.getElementById('canvas4')
            //hay que llamar al get stats como sea, usaba dos funciones.
            canvas4.width = 800;
            canvas4.height = 600;
            var arrNoms = ['0-5', '5-10', '10-15', '15-20', '>20'];
            console.log(this.tiempoArr);
            var chart4 = new Chart(canvas4, {
                type: 'bar',
                data: {
                    labels: arrNoms,
                    datasets: [{
                        label: 'MEDIANA TIEMPO',
                        data: this.tiempoArr,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                    }]
                    
                },
                options: {
                    scales: {
                        yAxes: [{
                            type: 'linear',
                            position: 'left',
                            ticks: { min: 0, max: this.arrPartidas.length }
                        }]
                    }
                }
            });

        },
        createElements: function () {
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
            this.arrStats.forEach(el => {
                if (count < 3) {
                    this.arrNom.push(el.nombreTipo);
                    this.arrArmadura.push(el.armadura);
                    this.arrVelocidad.push(el.velocidad);
                    this.arrFuerza.push(el.fuerza);
                    this.arrVida.push(el.vida);
                } else {
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

        createElements2: function () {
            var one = 0; //0-250
            var two = 0; //251-500
            var three = 0; //501-750
            var four = 0; //751-1000
            var five = 0; //>1000
            var one1 = 0; //0-5
            var two2 = 0; //5-10
            var three3 = 0; //10-15
            var four4 = 0; //15-20
            var five5 = 0; //>20
            this.arrPartidas.forEach(el => {
                var splitted = el.tiempo.split(':');
                console.log(splitted[0]);
                if (splitted[0] <= 5 && splitted[0] >= 0)
                    one1++;
                if (splitted[0] <= 10 && splitted[0] >= 6)
                    two2++;
                if (splitted[0] <= 15 && splitted[0] >= 11)
                    three3++;
                if (splitted[0] <= 20 && splitted[0] >= 16)
                    four4++;
                if (splitted[0] > 20)
                    five5++;

                if (el.puntos <= 1000 && el.puntos >= 0)
                    one++;
                if (el.puntos <= 2000 && el.puntos >= 1001)
                    two++;
                if (el.puntos <= 3000 && el.puntos >= 2001)
                    three++;
                if (el.puntos <= 4000 && el.puntos >= 3001)
                    four++;
                if (el.puntos >= 4001)
                    five++;

            });
            this.medianaArr.push(one);
            this.medianaArr.push(two);
            this.medianaArr.push(three);
            this.medianaArr.push(four);
            this.medianaArr.push(five);

            this.tiempoArr.push(one1);
            this.tiempoArr.push(two2);
            this.tiempoArr.push(three3);
            this.tiempoArr.push(four4);
            this.tiempoArr.push(five5);
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
            fetch("http://192.168.2.113:7073/authPost/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/authPost/",
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
                        this.rol = data.rol;
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
        uploadImage: function () {
            const fileInput = document.getElementById('file-input');
            const file = fileInput.files[0];
            this.url = 'http://192.168.2.113:5500/Projecte_Grup1/Interficie/server/uploads/' + file.name;
            //this.url = 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/' + file.name;
            console.log(file);
            const formData = new FormData();
            formData.append('image', file);

            fetch('http://192.168.2.113:7073/upload', {
            //fetch('http://admin.alumnes.inspedralbes.cat:7073/upload', {
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
            fetch("http://192.168.2.113:7073/getSession/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/getSession/",
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
                    this.rol = data.rol;
                    console.log("sesion user" + this.userAuth);
                    console.log("session mine" + this.username);
                    console.log("sesion auth" + this.auth);
                    console.log("rol" + data.rol);

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

            fetch("http://192.168.2.113:7073/statusPost/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/statusPost/",
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
            fetch("http://192.168.2.113:7073/getStats2/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/getStats2/",
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
                    this.arrBonus = [{
                        'nomBonus': 'damage',
                        'img': 'http://127.0.0.1:5500/Projecte_Grup1/Interficie/server/uploads/damageBonus.png',
                        //'img': 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/damageBonus.png',
                        'descr': 'Te da un punto más de daño durante 10 segundos'
                    }, {
                        'nomBonus': 'live',
                        'img': 'http://127.0.0.1:5500/Projecte_Grup1/Interficie/server/uploads/liveBonus.png',
                        //'img': 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/liveBonus.png',
                        'descr': 'Te da una vida más'
                    }, {
                        'nomBonus': 'points',
                        'img': 'http://127.0.0.1:5500/Projecte_Grup1/Interficie/server/uploads/pointsBonus.png',
                        //'img': 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/pointsBonus.png',
                        'descr': 'Te da un x2 en cada kill que hagas durante 10 segundos'
                    }, {
                        'nomBonus': 'velocity',
                        'img': 'http://127.0.0.1:5500/Projecte_Grup1/Interficie/server/uploads/velocityBonus.png',
                        //'img': 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/velocityBonus.png',
                        'descr': 'Te sube un punto de velocidad durante 10 segundos'
                    }, {
                        'nomBonus': 'shield',
                        'img': 'http://127.0.0.1:5500/Projecte_Grup1/Interficie/server/uploads/shieldBonus.png',
                        //'img': 'http://admin.alumnes.inspedralbes.cat/Projecte_Grup1/Interficie/server/uploads/shieldBonus.png',
                        'descr': 'Te da una armadura más'
                    }];
                    console.log(this.arrStats);
                    console.log(this.arrBonus);
                    this.createElements();
                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        getPartidas: function () {
            fetch("http://192.168.2.113:7073/getPartidas2/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/getPartidas2/",
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

                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        updateStats: function (id, vel, fuerza, vida, armadura, nombreTipo, puntos, check1, check2) {
            console.log(check1, check2);
            var maps = [];
            if (check1 != false) { maps.push(1); console.log("ES FALSE") }
            else
                maps.push(0);
            if (check2 != false) { maps.push(2); console.log("ES FALSE") }
            else
                maps.push(0);
            console.log(maps);

            /* console.log(id);
            console.log(vel);
            console.log(fuerza);
            console.log(vida);
            console.log(armadura); */
            this.info.values.push(id);
            this.info.values.push(vel);
            this.info.values.push(fuerza);
            this.info.values.push(vida);
            this.info.values.push(armadura);
            this.info.values.push(nombreTipo);
            this.info.values.push(puntos);
            this.info.values.push(maps);
            fetch("http://192.168.2.113:7073/updateStats/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/updateStats/",
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
            var del = '../server/' + urlSegments;
            this.info.values.push(del);

            fetch("http://192.168.2.113:7073/delete/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/delete/",
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
            var maps = [];
            if (this.map1 != null)
                maps.push(parseInt(this.map1));
            else
                maps.push(0);
            if (this.map2 != null)
                maps.push(parseInt(this.map2));
            else
                maps.push(0);
            console.log(maps);
            var nombreTipo = 'zombie_new';
            nombreTipo = nombreTipo + (this.arrLen + 1);
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
            this.info.values.push(maps);
            fetch("http://192.168.2.113:7073/addNewZombie/",
            //fetch("http://admin.alumnes.inspedralbes.cat:7073/addNewZombie/",
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

