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
    },

    methods: {
        signout() {
            axios
                .post('/api/logout')
                .then(response =>
                    window.location.href = "/web/index.html"
                )
        },

        loadData() {
            axios
                .get('/api/clients/current')
                .then(res => {
                    this.response = res;
                    this.client = this.response.data;
                    this.accounts = this.client.accountsDTO;
                })
        },

        doTransaction() {
            if (this.amount == 0 || isNaN(this.amount) || this.description == '' || this.accountOrigin == '' || this.accountDestination == '') {
                alert('Please enter the corresponding values');
            } else if (window.confirm("Are you sure to carry out the transaction?")) {
                axios
                    .post('/api/transactions',
                        'amount=' + this.amount +
                        '&description=' + this.description +
                        '&accountOriginNumber=' + this.accountOrigin +
                        '&accountDestinationNumber=' + this.accountDestination,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        if (response.status === 200) {
                            alert('Successfully!!!')
                            window.location.reload()
                        }
                    })
            } else {
                window.location.href = "/web/accounts.html"
            }
        },

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
                alert('The amount is too high. Please select a different amount.');
                this.amount = 0;
            } else if (isNaN(this.amount)) {
                alert('The amount is not a number. Please select a different amount.');
                this.amount = 0;
            }
        },
    }
}).mount('#app')