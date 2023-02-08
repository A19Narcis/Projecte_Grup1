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
        done: 0,
        color: ''
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
                    this.arrStats = data;
                    this.arrLen = data.length;
                    console.log(this.arrStats);


                }
            ).catch(
                (error) => {
                    console.log(error);
                }
            );
        },

        updateStats: function (id, vel, fuerza, vida, armadura, nombreTipo) {
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

        addNewZombie: function (velocidad, fuerza, vida, cantidad) {
            var nombreTipo = 'ZombieEsqueleto';
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
        check: function (type) {
            console.log("fumo skeletons" + type);
            this.new_type = type;
        },
/*         handleDrop(event) {
            event.preventDefault();
            let file = event.dataTransfer.files[0];
            let reader = new FileReader();
            reader.addEventListener('load', () => {
              this.image = reader.result;
              this.dialog_2 = true;
              console.log(this.dialog_2);
            });
            reader.readAsDataURL(file);
          },
          handleDragOver(event) {
            event.preventDefault();
            event.target.classList.add('drop-zone-active');
          },
          handleDragLeave(event) {
            event.target.classList.remove('drop-zone-active');
          } */

          openFileDialog() {
            this.$refs.fileInput.click();
            this.dialog_2 = true; 
          },
          addImage(event) {
            this.image = event.target.files[0];
            this.dialog_2 = true;
            // Do something with the file, such as previewing it or uploading it to a server
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

