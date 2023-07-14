<template>
  <div class="">
    <h2>Add Booking</h2>
    <form>
      <div class="">
        <input type="date" id="date" :value="date" required>
      </div>
      sel
      <div class="" >

        <select name="bookingTime" id="bookingTime">
          <option value="MORNING">Morning</option>
          <option value="AFTERNOON">Afternoon</option>
          <option value="DAY">Whole Day</option>
        </select>

      </div>

      <a @click="addBooking()">
        <span></span>
        Submit
      </a>
    </form>
  </div>
</template>
<script>

import axios from "axios";
let config = {
  headers: {
    Authorization: localStorage.getItem("user"),
  }
}
export default {
  name: 'AddBookingPasge',
  data() {
    return {
      date: new Date().toISOString().split('T')[0]
    };
  },
  mounted() {
    if (localStorage.getItem("user") == null) {
      window.location.href = "/login";
    }
  },
  methods: {
    addBooking: async function  () {
      console.log(document.getElementById("date").value)
      try {
        const response = await axios({
          method: 'post',
          url: "/api/v1/bookings/add",
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem("user")
          },
          data: {
            "bookingDate": document.getElementById("date").value,
            "bookingTime": document.getElementById("bookingTime").value
          }
        });

        if (!response.status == 200) {
          throw new Error('Request failed');
        } else {
          if ('referrer' in document) {
            window.location = document.referrer;
          } else {
            window.history.back();
          }
        }
      } catch (error) {
        console.error(error);
      }
    }
  }
};

</script>

<style>


</style>
