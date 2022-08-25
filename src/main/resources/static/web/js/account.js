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
        .get('http://localhost:8080/api/accounts/' + id)
        .then(res => {
          this.response = res
          this.account = this.response.data
          this.transactions = this.account.transactionsDTO
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