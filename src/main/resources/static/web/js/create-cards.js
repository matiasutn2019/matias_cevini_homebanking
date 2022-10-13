const { createApp } = Vue

createApp({
    data() {
        return {
            response: [],
            cards: [],
            creditCardsColor: [],
            debitCardsColor: [],
            cardType: '',
            cardColor: '',
            cardColors: [],
            validCardColors: [],
        }
    },

    created() {
        this.loadData(),
            this.loadType(),
            this.cardColors = ['SILVER', 'GOLD', 'PLATINUM']
    },

    methods: {
        loadData() {
            axios
                .get('/api/clients/current/cards')
                .then(res => {
                    this.response = res
                    this.cards = this.response.data
                    this.filteredArray(this.cards)
                    this.validateColors()
                })
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        filteredArray(cards) {
            this.creditCardsColor = cards.filter(c => c.type == 'CREDIT').map(c => c.color)
            this.debitCardsColor = cards.filter(c => c.type == 'DEBIT').map(c => c.color)
        },

        create() {
            if (this.cardType == '' || this.cardColor == '') {
                swal('', 'Type the corresponding values', "warning",);
            } else {
                axios
                    .post('/api/clients/current/cards',
                        'cardType=' + this.cardType + '&cardColor=' + this.cardColor)
                    .then(res => {
                        if (res.status === 201) {
                            window.location.href = "/web/cards.html"
                        }
                    })
                    .catch(error => {
                        swal('Code: ' + error.response.status, error.response.data, "error");
                    })
            }
        },

        loadType() {
            const urlParams = new URLSearchParams(window.location.search);
            this.cardType = urlParams.get("type");
        },

        signout() {
            axios
                .post('/api/logout')
                .then(response => {
                    window.location.href = "/web/index.html"
                })
        },

        validateColors() {
            if (this.cardType == 'CREDIT') {
                this.validCardColors =
                    this.cardColors.filter(c => !this.creditCardsColor.includes(c))

            } else if (this.cardType == 'DEBIT') {
                this.validCardColors =
                    this.cardColors.filter(c => !this.debitCardsColor.includes(c))
            }
        }
    }
}).mount('#app')