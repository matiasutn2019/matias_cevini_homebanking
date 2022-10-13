const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            clients: [],
            firstName: '',
            lastName: '',
            email: '',
            nameModal: '',
            lastNameModal: '',
            emailModal: '',
            url: '',
            password: '',
        }
    },

    created() {
        this.loadData();
    },

    methods: {
        loadData() {
            axios
                .get('/api/admin/clients')
                .then(res => {
                    this.response = res
                    this.clients = this.response.data
                    console.log(this.response)
                })
        },

        addClient() {
            if (this.firstName == '' || this.lastName == '' || this.email == '' || this.password == '') {
                alert('Ingrese los valores correspondientes');
            } else {
                axios
                    .post('/api/admin/clients', {
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                        password: this.password
                    })
                    .then(this.loadData())
            }
        },

        deleteClient(id) {
            axios
                .delete('/api/admin/clients/' + id)
                .then(res => {
                    if (res.statusCode === 204) {
                        this.loadData()
                    } else {
                        console.log(res)
                    }
                })
                .catch(error => {
                    swal('Code: ' + error.response.data.code, error.response.data.message, 'error');
                })
        },

        patch() {
            axios
                .patch(this.url, {
                    name: this.nameModal,
                    lastName: this.lastNameModal,
                    email: this.emailModal
                })
                .then(this.loadData())
        },

        loadClientModal(client) {
            this.nameModal = client.firstName;
            this.lastNameModal = client.lastName;
            this.emailModal = client.email;
            this.loadURL(client)
        },

        loadURL(client) {
            this.url = client._links.client.href;
        },

        signout() {
            axios
                .post('/api/logout')
                .then(response =>
                    window.location.href = "/web/index.html"
                )
        }
    }
}).mount('#app')