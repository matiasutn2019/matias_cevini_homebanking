const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            client: [],
            accounts: [],
            accountTypes: [],
            accountTypeSelected: ''
        }
    },

    created() {
        this.loadData();
        this.loadAccounts();
        this.loadAccountTypes();
    },

    methods: {
        loadData() {
            axios
                .get('/api/clients/current')
                .then(res => {
                    this.response = res;
                    this.client = this.response.data
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })

        },

        loadAccounts() {
            axios
                .get('/api/clients/current/accounts')
                .then(res => {
                    this.accounts = res.data
                    this.sortAccounts();
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        loadAccountTypes() {
            axios
                .get('/api/clients/current/accounts/types')
                .then(res => {
                    this.accountTypes = res.data
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
                .post('/api/clients/current/accounts', 'accountType=' + this.accountTypeSelected)
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
        },

        deleteAccount(number) {
            axios
                .patch('/api/clients/current/accounts', 'number=' + number)
                .then(res => {
                    if (res.status === 204) {
                        swal('', 'Account deleted!', "success")
                            .then((ok) => {
                                if (ok) {
                                    window.location.reload()
                                }
                            })
                    }
                })
                .catch(error => {
                    swal('Code: ' + error.response.data.code, error.response.data.message, "error")
                })
        }
    }
}).mount('#app')