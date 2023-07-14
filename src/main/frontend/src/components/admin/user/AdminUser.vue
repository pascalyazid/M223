<template>
  <div>
    <table>

    </table>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>First name</th>
        <th>Last name</th>
        <th>E-mail</th>
        <th>Role</th>
        <th>Edit</th>
        <th>Delete</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id" :id="user.id">
        <td >
          {{ user.id }}
        </td>
        <td >
          {{ user.firstName }}
        </td>
        <td >
          {{ user.lastName }}
        </td>
        <td >
          {{ user.email }}
        </td>
        <td >
          {{ user.role }}
        </td>
        <td><button class="edit-button"  @click="editUser(user.id)">Edit</button></td>
        <td><button class="delete-button"  @click="deleteUser(user.id)">Delete</button></td>

      </tr>
      </tbody>
    </table>
  </div>
  <button onclick="window.location.href = '/addUser'" >Add User</button>
</template>
<script>

import axios from "axios";

let config = {
  headers: {
    Authorization: localStorage.getItem("user"),
  }
}
export default {
  name: 'AdminUser',
  data() {
    return {
      users: []
    };
  },
  async mounted() {
    if (localStorage.getItem("user") == null) {
      window.location.href = "/login";
    }
    axios.get("/api/v1/users/isAdmin", config).then((res) => {
      if (!res.data){
        window.location.href = "/";
      }
    });
    await axios.get('/api/v1/users/list', config)
        .then((res) => {
          const data =  res.data;
          this.users = data;
      }
    );
  },
  methods: {
    deleteUser: async function  (id) {
      document.getElementById(id).style.display = "none ";


      try {
        const response = (await axios.delete('/api/v1/users/' + id + "/delete", config));

        if (!response.status == 200) {
          throw new Error('Request failed');
        }
      } catch (error) {
        console.error(error);
      }
    },
    editUser: async function  (id) {
      window.location.href = "/editUser/" + id
    }
  }

};

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}
th {
  background-color: #f2f2f2;
}
tr:hover {
  background-color: #f5f5f5;
}
.delete-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 6px 12px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 2px 2px;
  cursor: pointer;
}

.edit-button {
  background-color: #3639f4;
  color: white;
  border: none;
  padding: 6px 12px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 2px 2px;
  cursor: pointer;
}
</style>
