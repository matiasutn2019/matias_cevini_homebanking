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
                        if (response.status === 200) {
                            window.location.href = "/web/accounts.html";
                        }
                    })
                    .catch(error => {
                        if (error.response.data.exception === 'No value present') {
                            alert('Code: ' + error.response.status + '\nUsername not found');
                            this.emailLogin = ''
                        } else if (error.response.data.exception === 'Bad credentials') {
                            alert('Code: ' + error.response.status + '\nPassword incorrect');
                            this.passwordLogin = ''
                        } else if (error.response.data.exception === 'Maximum sessions of 1 for this principal exceeded') {
                            alert('Code: ' + error.response.status + '\nYou are already logged in');
                        }
                    })
            }
        },
        register() {
            if (this.firstName == '' || this.lastName == '' || this.email == '' || this.password == '') {
                alert('Ingrese los valores correspondientes');
            } else {
                axios
                    .post('/api/clients', "firstName=" + this.firstName + "&lastName="
                        + this.lastName + "&email=" + this.email + "&password=" + this.password,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        this.emailLogin = this.email;
                        this.passwordLogin = this.password;
                        this.login()
                    })
                    .catch(error => {
                        alert('Code: ' + error.response.status + '\n' + error.response.data);
                    })
            }
        }
    }
}).mount('#app')