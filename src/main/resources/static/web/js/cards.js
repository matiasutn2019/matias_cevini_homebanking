const { createApp } = Vue

createApp({
  data() {
    return {
      response: [],
      client: {},
      creditCards: [],
      debitCards: [],
    }
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      axios
        .get('/api/clients/current')
        .then(res => {
          this.response = res
          this.client = this.response.data
          this.filteredArray(this.client.cards);
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
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
          console.log('signed out!!!')
          window.location.href = "/web/index.html"
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        })
    }
  }
}).mount('#app')
