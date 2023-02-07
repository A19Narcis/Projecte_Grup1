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
        dialog_1: false,
        dialog_2: false,
        dialog_3: false,
        dialog_zomb1: false,
        dialog_zomb2: false,
        dialog_zomb3: false,
        num_zomb: 0,
        num_boss: 0,
        fuerza_zomb: 0,
        vel_zomb: 0,
        zomb: 0,
        show: 0,
        show2: 0,
        show_dial: false,
        vida_zombie: 0,
        vida_zombie_boss: 0,
        fuerza_zomb_boss: 0,
        vel_zomb_boss: 0,
        type_char: "",
        type_zombie: "",
        userAuth: '',
        axe_vel: 0,
        axe_fuerza: 0,
        axe_vida: 0,
        axe_arm: 0,
        warm_vel: 0,
        warm_fuerza: 0,
        warm_vida: 0,
        warm_arm: 0,
        shield_vel: 0,
        shield_fuerza: 0,
        shield_vida: 0,
        shield_arm: 0,
        new_cantidad: 0,
        new_velocidad: 0,
        new_vida: 0,
        new_fuerza: 0,
        new_type: 0,
        newReady: 0,
        typeIf: 0
    },
    methods: {
        getAuth: function () {
            console.log("Entro")
            console.log(this.username)
            console.log(this.pass)
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
            fetch("http://admin.alumnes.inspedralbes.cat:7073/getStats/",
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
                    console.log(data)
                    //axe
                    this.axe_vel = data[0].velocidad;
                    this.axe_fuerza = data[0].fuerza;
                    this.axe_vida = data[0].vida;
                    this.axe_arm = data[0].armadura;
                    //warm
                    this.warm_vel = data[1].velocidad;
                    this.warm_fuerza = data[1].fuerza;
                    this.warm_vida = data[1].vida;
                    this.warm_arm = data[1].armadura;
                    //shield
                    this.shield_vel = data[2].velocidad;
                    this.shield_fuerza = data[2].fuerza;
                    console.log(this.shield_fuerza);

                    this.shield_vida = data[2].vida;
                    this.shield_arm = data[2].armadura;

                    //zombie
                    this.num_zomb = data[3].cantidadMinuto;
                    this.fuerza_zomb = data[3].fuerza;
                    this.vida_zombie = data[3].vida;
                    this.vel_zomb = data[3].velocidad;
                    //boss
                    this.num_boss = data[4].cantidadMinuto;
                    this.fuerza_zomb_boss = data[4].fuerza;
                    this.vida_zombie_boss = data[4].vida;
                    this.vel_zomb_boss = data[4].velocidad;
                    if (data[5] != null) {
                        this.new_cantidad = data[5].cantidadMinuto;
                        this.new_fuerza = data[5].fuerza;
                        this.new_vida = data[5].vida;
                        this.new_velocidad = data[5].velocidad;                        
                        if(data[5].nombreTipo == "ZombieCaballero")
                            this.typeIf = 2;
                        if(data[5].nombreTipo == "ZombieEsqueleto")
                            this.typeIf = 1;
                        this.newReady = 1;
                    }
                    console.log(this.typeIf);


                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        updateStats: function (type, vel, fuerza, vida, armadura, new_type) {
            console.log(type);
            console.log(vel);
            console.log(fuerza);
            console.log(vida);
            console.log(armadura);
            console.log(new_type);
            if (type == 6) {
                console.log("ENNNNN");
                this.info.values.push(type);
                this.info.values.push(vel);
                this.info.values.push(fuerza);
                this.info.values.push(vida);
                this.info.values.push(new_type);
            }
            else if (type == 4 || type == 5) {
                this.info.values.push(type);
                this.info.values.push(vel);
                this.info.values.push(fuerza);
                this.info.values.push(vida);

            } else {
                console.log("else")
                this.info.values.push(type);
                this.info.values.push(vel);
                this.info.values.push(fuerza);
                this.info.values.push(vida);
                this.info.values.push(armadura);
            }
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

        addNewZombie: function (type, cantidad, fuerza, vida, velocidad) {
            console.log("add" + this.info.values)
            this.info.values.push(type);
            this.info.values.push(cantidad);
            this.info.values.push(fuerza);
            this.info.values.push(vida);
            this.info.values.push(velocidad);
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
                    this.newReady = 1;
                    console.log(this.newReady);
                    this.info.values = this.info.values.slice(0, 2);
                }
            ).catch(
                (error) => {
                    this.info.values = this.info.values.slice(0, 2);
                    console.log(error);
                }
            );

        },
        check: function (type) {
            console.log("fumo skeletons" + type);
            this.new_type = type;
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

