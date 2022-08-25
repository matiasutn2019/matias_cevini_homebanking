const { createApp } = Vue

createApp({
  data() {
    return {
      response: [],
      client: {}
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
          console.log(this.client)
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
        .then(res => {
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
    },
    createAccount() {
      axios
        .post('/api/clients/current/accounts')
        .then(res => {
          window.location.reload();
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