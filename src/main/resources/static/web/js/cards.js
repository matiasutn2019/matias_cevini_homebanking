const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            response2: [],
            client: [],
            creditCards: [],
            debitCards: [],
            cards: [],
            cardNumber: '',
            picked: false,
        }
    },

    created() {
        this.loadData();
        this.loadCards();
    },

    methods: {
        loadData() {
            axios
                .get('/api/clients/current')
                .then(res => {
                    this.response = res
                    this.client = this.response.data
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        loadCards() {
            axios
                .get('/api/clients/current/cards')
                .then(res => {
                    this.response2 = res
                    this.cards = this.response2.data
                    this.filteredArray(this.cards)
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        filteredArray(cards) {
            this.creditCards = cards.filter(c => c.type == 'CREDIT');
            this.debitCards = cards.filter(c => c.type == 'DEBIT');
        },

        signout() {
            axios
                .post('/api/logout')
                .then(response => {
                    window.location.href = "/web/index.html"
                })
        },

        deleteCard() {

            axios
                .patch('/api/clients/current/cards', 'number=' + this.cardNumber)
                .then(res => {
                    if (res.status === 204) {
                        swal('', 'Card deleted!', "success")
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

        },

        deleteButton() {
            this.picked = true;
        }
    }
}).mount('#app')