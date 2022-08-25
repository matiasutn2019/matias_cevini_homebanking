const { createApp } = Vue

createApp({
    data() {
        return {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            emailLogin: '',
            passwordLogin: ''
        }
    },

    created() {

    },

    methods: {
        login() {
            if (this.emailLogin == '' || this.passwordLogin == '') {
                alert('Ingrese los valores correspondientes');
            } else {
                axios
                    .post('/api/login', 'email=' + this.emailLogin + '&password=' + this.passwordLogin,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        console.log('signed in!!!')
                        window.location.href = "/web/accounts.html"
                    }
                    )
                    .catch(function (error) {
                        if (error.response) {
                            console.log(error.response.data);
                            console.log(error.response.status);
                            console.log(error.response.headers);
                        } else if (error.request) {
                            console.log(error.request);
                        } else {
                            console.log('Error', error.message);
                        }
                        console.log(error.config);
                    });
            }
        },
        register() {
            axios
                .post('/api/clients', "firstName=" + this.firstName + "&lastName="
                    + this.lastName + "&email=" + this.email + "&password=" + this.password,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('registered')
                    this.emailLogin = this.email
                    this.passwordLogin = this.password
                    this.login()
                })
                .catch(error => {
                    if (error.response) {
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                    } else if (error.request) {
                        console.log(error.request);
                    } else {
                        console.log('Error', error.message);
                    }
                    console.log(error.config);
                })
        }
    }
}).mount('#app')