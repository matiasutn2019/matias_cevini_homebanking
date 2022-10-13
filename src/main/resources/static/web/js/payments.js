const { createApp } = Vue

createApp({
    data() {
        return {
            number: '',
            holder: '',
            cvv: '',
            amount: '',
            accountDestination: '',
            description: '',
        }
    },

    created() {

    },

    methods: {
        pay() {
            if (this.number == '' || this.holder == '' || this.cvv == ''
                || this.amount == '' || this.description == '' || this.accountDestination == '') {
                swal('', 'Type the corresponding values', "warning");
            } else {
                axios
                    .post('/api/payments',
                        {
                            cardNumber: this.number,
                            cardHolder: this.holder,
                            cardCvv: this.cvv,
                            amountPayment: this.amount,
                            accountDestination: this.accountDestination,
                            description: this.description
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

        }
    }
}).mount('#app')