const { createApp } = Vue

createApp({
    data() {
        return {
            cardType: '',
            cardColor: '',
        }
    },

    created() {

    },

    methods: {
        create() {
            if (this.cardType == '' || this.cardColor == '') {
                alert('Please enter the corresponding values');
            } else {
                axios
                    .post('/api/clients/current/cards',
                        'cardType=' + this.cardType + '&cardColor=' + this.cardColor,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(res => {
                        if (res.status === 201) {
                            window.location.href = "/web/cards.html"
                        }
                    })
                    .catch(error => {
                        if (error.response) {
                            alert('Code: ' + error.response.status + '\n' + error.response.data);
                        } 
                    })
            }
        },

        signout() {
            axios
                .post('/api/logout')
                .then(response => {
                    window.location.href = "/web/index.html"
                })
        }
    }
}).mount('#app')