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
          this.response = res;
          this.client = this.response.data;
        })
        
    },
    
    signout() {
      axios
        .post('/api/logout')
        .then(res => {
          console.log('signed out!!!')
          window.location.href = "/web/index.html"
        })
        
    },

    createAccount() {
      axios
        .post('/api/clients/current/accounts')
        .then(res => {
          window.location.reload();
        })
        
    }
  }
}).mount('#app')