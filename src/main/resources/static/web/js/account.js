const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            account: [],
            id: 0,
            transactions: []
        }
    },

    created() {
        this.loadId();
    },

    methods: {
        loadId() {
            const urlParams = new URLSearchParams(window.location.search);
            this.id = urlParams.get("id");
            this.loadData(this.id);
        },

        loadData(id) {
            axios
                .get('/api/accounts/' + id)
                .then(res => {
                    this.response = res
                    this.account = this.response.data
                    this.transactions = this.account.transactionsDTO
                    this.sortTransactions()
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        signout() {
            axios
                .post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.href = "/web/index.html"
                })
        },

        sortTransactions() {
            this.transactions.sort((a, b) => {
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