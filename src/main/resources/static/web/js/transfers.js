const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            accountOrigin: '',
            accountDestination: '',
            amount: '',
            description: '',
            accounts: [],
            picked: '',
            validAccounts: [],
            accountOriginBalance: '',
            client: []
        }
    },

    created() {
        this.loadData();
        this.loadAccounts();
    },

    methods: {
        signout() {
            axios
                .post('/api/logout')
                .then(response =>
                    window.location.href = "/web/index.html"
                )
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        loadData() {
            axios
                .get('/api/clients/current')
                .then(res => {
                    this.response = res;
                    this.client = this.response.data;
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
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        doTransaction() {
            if (isNaN(this.amount) || this.description == ''
                || this.accountOrigin == '' || this.accountDestination == '') {
                swal('', 'Type the corresponding values', "warning");
            } else {
                swal({
                    title: "",
                    text: "Are you sure to transfer?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: false,
                })
                    .then((willTransfer) => {
                        if (willTransfer) {
                            axios
                                .post('/api/clients/current/transactions',
                                    'amount=' + this.amount +
                                    '&description=' + this.description +
                                    '&accountOriginNumber=' + this.accountOrigin +
                                    '&accountDestinationNumber=' + this.accountDestination)
                                .then(response => {
                                    if (response.status === 200) {
                                        swal('', 'Transfer done!', "success")
                                            .then((ok) => {
                                                if (ok) {
                                                    window.location.reload()
                                                }
                                            })
                                    }
                                })
                                .catch(error => {
                                    swal('Code: ' + error.response.status, error.response.data, "error");
                                })
                        } else {
                            swal('', "Transfer rejected", "success")
                                .then((ok) => {
                                    if (ok) {
                                        window.location.href = "/web/accounts.html"
                                    }
                                })

                        }
                    })
            }
        }

    },

    computed: {
        filterAccounts() {
            this.validAccounts = this.accounts.filter(x => x.number != this.accountOrigin)
            this.accountOriginBalance = this.accounts
                .filter(x => x.number == this.accountOrigin)
                .map(x => x.balance)
        },

        validateAmount() {
            if (parseInt(this.amount) > parseInt(this.accountOriginBalance)) {
                swal('', 'The amount is too high. Please select a different amount', "error");
                this.amount = '';
            } else if (isNaN(this.amount)) {
                swal('', 'The amount is not a number. Please select a different amount', "error");
                this.amount = '';
            } else if (parseInt(this.amount) <= 0) {
                swal('', 'The amount can not be zero', "error");
                this.amount = '';
            }
        },
    }
}).mount('#app')