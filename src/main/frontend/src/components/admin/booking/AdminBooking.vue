<template>
  <div>
    <table>

    </table>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>User</th>
        <th>Date</th>
        <th>Duration</th>
        <th>Status</th>
        <th>Edit</th>
        <th>Delete</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="booking in bookings" :key="booking.id" :id="booking.id" v-bind:class = "(booking.accepted)?'accepted':'notAccepted'">
        <td >
          {{ booking.id }}
        </td>
        <td >
          {{ booking.userName }}
        </td>
        <td >
          {{ booking.date }}
        </td>
        <td v-if="booking.isFullDay">
          Full day
        </td>
        <td v-else>
          Half day
        </td>
        <td v-if="booking.accepted">
          Accepted
        </td>
        <td v-else>
          Not accepted
        </td>
        <td><button class="edit-button"  @click="editBooking(booking.id)">Edit</button></td>
        <td><button class="delete-button"  @click="deleteBooking(booking.id)">Delete</button></td>

      </tr>
      </tbody>
    </table>
  </div>
  <button onclick="window.location.href = '/addBooking'" >Add Booking</button>
</template>
<script>

import axios from "axios";
let config = {
  headers: {
    Authorization: localStorage.getItem("user"),
  }
}
export default {
  name: 'AdminBooking',
  data() {
    return {
      bookings: []
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
    const res = await axios.get('/api/v1/bookings/list', config);
    const data = await res.data;
    this.bookings = data;
  },
  methods: {
    deleteBooking: async function  (id) {
      document.getElementById(id).style.display = "none ";
      try {
        const response = await axios.delete('/api/v1/bookings/' + id + "/delete", config);

        if (!response.status == 200) {
          throw new Error('Request failed');
        }
      } catch (error) {
        console.error(error);
      }
    },
    editBooking: async function  (id) {
      window.location.href = "/editBooking/" + id
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
