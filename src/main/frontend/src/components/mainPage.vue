<template>
  <div>
    <table>
      <thead>
      <tr>

        <th>Date</th>
        <th>Duration</th>
        <th>Status</th>
        <th>Delete</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="booking in bookings" :key="booking.id" :id="booking[0]" v-bind:class = "(booking.accepted)?'accepted':'notAccepted'">

        <td >
          {{ booking[1] }}
        </td>
        <td v-if="booking[2] == 2">
          Full day
        </td>
        <td v-if="booking[2] == 1">
          Afternoon
        </td>
        <td v-if="booking[2] == 0">
          Morning
        </td>
        <td v-if="booking.accepted">
          Accepted
        </td>
        <td v-else>
         Pending
        </td>
        <td><button class="delete-button"  @click="deleteBooking(booking[0])">Delete</button></td>
      </tr>
      </tbody>
    </table>
  </div>
  <button class="edit-button" onclick="window.location.href = '/addBooking'" >Add Booking</button>
  <button class="edit-button"  @click="editAccount">Edit Account</button>
  <button v-if="isAdmin" class="edit-button"  onclick="window.location.href='/users'">User-List</button>
  <button v-if="isAdmin" class="edit-button"  onclick="window.location.href='/booking'">Booking-List</button>
  <br><br>
</template>
<script>

import axios from "axios";
let config = {
  headers: {
    Authorization: localStorage.getItem("user"),
  }
}
export default {
  name: 'MainPage',
  data() {
    return {
      bookings: [],
      isAdmin: false
    };
  },
  async mounted() {
    let data = ' ';
    if (localStorage.getItem("user") == null) {
      window.location.href = "/login";
    }
    await axios.get('/api/v1/bookings/list/user' , config)
        .then((async function (res) {
          data = await res.data;
        }))
        .catch(function (error) {
          if(error.status == 401) {
            window.location.href = "/login";
          }
        })




    //const data = await res.data;
    this.bookings = data;
  },
  methods: {
    deleteBooking: async function  (id) {
      document.getElementById(id).style.display = "none";
      try {
        const response = await axios.delete('/api/v1/bookings/delete/' + id, config);

        if (!response.status == 200) {
          throw new Error('Request failed');
        }
      } catch (error) {
        console.error(error);
      }
    },
    editAccount: async function(){
      const response = await axios.get("/api/v1/users/id", config);
      console.log(response)
      window.location.href = '/editUser/' + response.data;
    }
  }

};

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.accepted {
  background-color: rgba(66, 185, 131, 0.5);
}

.accepted:hover {
  background-color: #42b983 !important;
}

.notAccepted {
  background-color: rgba(248, 81, 81, 0.5);
}

.notAccepted:hover {
  background-color: #fa7c73;
}

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
