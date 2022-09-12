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
            percentage: '',
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
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        getLoans() {
            axios
                .get('/api/loans')
                .then(res =>
                    this.loans = res.data
                )
                .catch(error => {
                    swal('Code: ' + error.response.status, error.response.data, "error");
                })
        },

        apply() {
            if (this.amountSelected == ''
                || this.loanSelected == '' || this.accountDestination == '') {
                swal('', 'Type the corresponding values', "warning",);
            } else {
                swal({
                    title: "",
                    text: "Are you sure to apply?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: false,
                })
                    .then((willApply) => {
                        if (willApply) {
                            axios
                                .post('/api/loans',
                                    {
                                        id: this.loanId[0],
                                        amount: this.amountSelected,
                                        payments: this.paymentSelected,
                                        accountNumber: this.accountDestination,
                                        percentage: this.percentage
                                    },
                                    {
                                        headers: { 'content-type': 'application/json' }
                                    }
                                )
                                .then(res => {
                                    if (res.status === 201) {
                                        swal('', 'Loan granted!', "success")
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
                            swal('', "Load rejected", "success")
                                .then((ok) => {
                                    if (ok) {
                                        window.location.reload()
                                    }
                                })
                        }
                    });
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
            if (this.paymentSelected != '') {
                this.paymentAmount = (parseInt(this.amountSelected) + ((parseInt(this.amountSelected) / 100 * parseInt(this.percentage))))
                    / parseInt(this.paymentSelected)
            } else {
                this.paymentAmount = 0
            }
            this.loanId = this.loans
                .filter(x => x.name == this.loanSelected)
                .map(x => x.id);
        },

        validateAmount() {
            if (parseInt(this.amountSelected) > parseInt(this.maxAmount)) {
                swal('', 'The amount is too high. Please select a different amount', "error");
                this.amountSelected = '';
            } else if (isNaN(this.amountSelected)) {
                swal('', 'The amount is not a number. Please select a different amount', "error");
                this.amountSelected = '';
            } else if (parseInt(this.amountSelected) <= 0) {
                swal('', 'The amount can not be zero', "error");
                this.amountSelected = '';
            }
        },

        getPercentage() {
            this.percentage = this.loans.filter(loan => loan.name == this.loanSelected).map(loan => loan.percentage)[0]
            console.log('percentage '+this.percentage)
            console.log('payment '+this.paymentAmount)
        }
    }

}).mount('#app')