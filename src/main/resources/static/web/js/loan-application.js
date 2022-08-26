const { createApp } = Vue

createApp({
    data() {
        return {
            accounts: [],
            loans: [],
            loanSelected: '',
            maxAmount: 0,
            payments: [],
            paymentSelected: '',
            accountDestination: '',
            amountSelected: '',
            paymentAmount: '',
            loanId: '',
        }
    },

    created() {
        this.getAccounts();
        this.getLoans();
    },

    methods: {
        signout() {
            axios
                .post('/api/logout')
                .then(response =>
                    window.location.href = "/web/index.html"
                )
        },

        getAccounts() {
            axios
                .get('/api/clients/current/accounts')
                .then(res =>
                    this.accounts = res.data
                )
        },

        getLoans() {
            axios
                .get('/api/loans')
                .then(res =>
                    this.loans = res.data
                )
        },

        apply() {
            if (isNaN(this.amountSelected) || this.amountSelected == ''
                || this.loanSelected == '' || this.accountDestination == ''
                || parseInt(this.amountSelected) > parseInt(this.maxAmount)) {
                alert('Please enter the corresponding values');
            } else if (window.confirm("Are you sure to apply?")) {
                axios
                    .post('/api/loans',
                        {
                            id: this.loanId[0],
                            amount: this.amountSelected,
                            payments: this.paymentSelected,
                            accountNumber: this.accountDestination
                        },
                        {
                            headers: { 'content-type': 'application/json' }
                        }
                    )
                    .then(res => {
                        if (res.status === 201) {
                            alert('Successfully!!!');
                            window.location.reload();
                        }
                    })
            }
        }
    },

    computed: {
        filterLoans() {
            this.maxAmount = this.loans
                .filter(x => x.name == this.loanSelected)
                .map(x => x.maxAmount);
            this.payments = this.loans
                .filter(x => x.name == this.loanSelected)
                .map(x => x.payments);
            this.paymentAmount = (parseInt(this.amountSelected) + parseInt(this.amountSelected) * 0.2)
                / parseInt(this.paymentSelected)
            this.loanId = this.loans
                .filter(x => x.name == this.loanSelected)
                .map(x => x.id);
        },

        validateAmount() {
            if (parseInt(this.amountSelected) > parseInt(this.maxAmount)) {
                alert('The amount is too high. Please select a different amount.');
                this.amountSelected = 0;
            } else if (isNaN(this.amountSelected)) {
                alert('The amount is not a number. Please select a different amount.');
                this.amountSelected = 0;
            }
        }
    }

}).mount('#app')