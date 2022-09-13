const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            number: '',
            cvv: '',
            amount: '',
            description: '',
        }
    },

    created() {
        this.loadData();
    },

    methods: {
        loadData() {
            axios
                .get('/api/clients/current')
                .then(res => {
                    this.response = res;
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })

        },

        pay() {
            if (this.number == '' || this.cvv == '' || this.amount == '' || this.description == '') {
                swal('', 'Type the corresponding values', "warning");
            } else {
                axios
                    .post('/api/clients/current/payments',
                        {
                            number: this.number,
                            cvv: this.cvv,
                            amount: this.amount,
                            description: this.description
                        },
                        {
                            headers: { 'content-type': 'application/json' }
                        }
                    )
                    .then(response => {
                        if (response.status === 200) {
                            window.location.reload();
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        swal('Code: ' + error.response.data.code, error.response.data.message, 'error');
                    })
            }

        },

        signout() {
            axios
                .post('/api/logout')
                .then(res => {
                    console.log('signed out!!!')
                    window.location.href = "/web/index.html"
                })

        },

    }
}).mount('#app')