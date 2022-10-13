const { createApp } = Vue

createApp({
    data() {
        return {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            emailLogin: '',
            passwordLogin: '',
            admin: ''
        }
    },

    created() {

    },

    methods: {
        login() {
            if (this.emailLogin == '' || this.passwordLogin == '' || this.passwordLogin.length < 6) {
                swal('', 'Type the corresponding values', "warning");
            } else {
                axios
                    .post('/api/login', 'email=' + this.emailLogin + '&password=' + this.passwordLogin)
                    .then(response => {
                        if (response.status === 200) {
                            if (this.admin) {
                                window.location.href = "/web/manager.html"
                            } else {
                                window.location.href = "/web/accounts.html";
                            }
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        if (error.response.data.exception === 'No value present') {
                            swal('Code: ' + error.response.status, 'Username not found', "error");
                            this.emailLogin = ''
                            this.passwordLogin = ''
                        } else if (error.response.data.exception === 'Bad credentials') {
                            swal('Code: ' + error.response.status, 'Password incorrect', "error");
                            this.passwordLogin = ''
                        } else if (error.response.data.exception === 'Maximum sessions of 1 for this principal exceeded') {
                            swal('Code: ' + error.response.status, 'You are already logged in', "error");
                        }
                        swal('Code: ' + error.response.data.code, error.response.data.message, "error");

                    })
            }
        },
        register() {
            if (this.firstName == '' || this.lastName == '' || this.email == ''
                || this.password == '' || this.password.length < 6) {
                swal('', 'Type the corresponding values', "warning");
            } else {
                axios
                    .post('/api/clients', "firstName=" + this.firstName + "&lastName="
                        + this.lastName + "&email=" + this.email + "&password=" + this.password)
                    .then(response => {
                        if (response.status === 201) {
                            this.emailLogin = this.email;
                            this.passwordLogin = this.password;
                            this.login()
                        }
                    })
                    .catch(error => {
                        swal('Code: ' + error.response.data.code, error.response.data.message, 'error');
                    })
            }
        }
    }
}).mount('#app')