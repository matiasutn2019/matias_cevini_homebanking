const { createApp } = Vue

createApp({
  data() {
    return {
      response: [],
      clients: [],
      firstName: '',
      lastName: '',
      email: '',
      nameModal: '',
      lastNameModal: '',
      emailModal: '',
      url: ''
    }
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      axios
        .get('/api/clients')
        .then(res => {
          this.response = res
          this.clients = this.response.data
        })
    },

    addClient() {
      if (this.firstName == '' || this.lastName == '' || this.email == '') {
        alert('Ingrese los valores correspondientes');
      } else {
        axios
          .post('/api/clients', {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email
          })
          .then(this.loadData())
      }
    },

    deleteClient(id) {
      axios
        .delete('/api/clients/' + id)
        .then(this.loadData())
    },

    patch() {
      axios
        .patch(this.url, {
          name: this.nameModal,
          lastName: this.lastNameModal,
          email: this.emailModal
        })
        .then(this.loadData())
    },

    put() {
      axios
        .put(this.url, {
          name: this.nameModal,
          lastName: this.lastNameModal,
          email: this.emailModal
        })
        .then(this.loadData())
    },

    loadClientModal(client) {
      this.nameModal = client.firstName;
      this.lastNameModal = client.lastName;
      this.emailModal = client.email;
      this.loadURL(client)
    },

    loadURL(client) {
      this.url = client._links.client.href;
    },

    signout() {
      axios
        .post('/api/logout')
        .then(response =>
          window.location.href = "/web/index.html"
        )
    }
  }
}).mount('#app')