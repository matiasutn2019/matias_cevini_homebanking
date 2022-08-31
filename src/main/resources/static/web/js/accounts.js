const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            client: [],
            accounts: [],
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
                    this.client = this.response.data
                    this.accounts = this.client.accountsDTO
                    this.sortAccounts();
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })

        },

        signout() {
            axios
                .post('/api/logout')
                .then(res => {
                    console.log('signed out!!!')
                    window.location.href = "/web/index.html"
                })

        },

        createAccount() {
            axios
                .post('/api/clients/current/accounts')
                .then(res => {
                    window.location.reload();
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })

        },

        sortAccounts() {
            this.accounts.sort((a, b) => {
                if (a.id == b.id) {
                    return 0;
                }
                if (a.id > b.id) {
                    return -1;
                }
                return 1;
            });
        }
    }
}).mount('#app')