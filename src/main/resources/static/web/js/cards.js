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
    },
    filteredArray(cards) {
      this.creditCards = cards.filter(c => c.type == 'CREDIT');
      this.debitCards = cards.filter(c => c.type == 'DEBIT');
    },

    signout() {
      axios
        .post('/api/logout')
        .then(response => {
          window.location.href = "/web/index.html";
        })
    }
  }
}).mount('#app')
