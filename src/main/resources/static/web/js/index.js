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
                        window.location.href = "/web/accounts.html";
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
            }
        }
    }
}).mount('#app')