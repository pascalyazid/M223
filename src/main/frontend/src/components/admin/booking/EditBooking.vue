<template>
  <div class="adduser-box">
    <h2>Edit Booking</h2>
    <form>
      <div class="user-box">
        <input type="date" id="date" required :value="booking.date">
      </div>
      <div class="user-box" >
        <span for="halfday">Full day</span><br>
        <input type="radio" id="fullDay" name="duration" style="margin: 0" checked>
        <br><br>
        <span for="halfday">Half day</span><br>
        <input type="radio" id="halfday" name="duration" style="margin: 0">
      </div>
      <br><br><br>
      <div class="user-box">
        <span for="halfday">Accepted</span><br>
        <input type="checkbox" id="accepted" name="accepted" style="margin: 0">
      </div>
      <a @click="editBooking()">
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
  name: 'EditBooking',
  data() {
    return {
      booking: {}
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
    const res = await axios.get('/api/v1/bookings/' +  this.$route.params.id, config)
        .catch(function (error) {
          window.location.href = "/404";
        });

    const data = await res.data;
    this.booking = data;
    if(data.isFullDay == "false") {
      document.getElementById("halfday").checked = true;
    }
    if(data.accepted == "true") {
      document.getElementById("accepted").checked = true;
    }
  },
  methods : {
    editBooking: async function  () {
      try {
        const response = await axios({
          method: 'put',
          url: "/api/v1/bookings/" +this.$route.params.id + "/update",
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem("user"),
          },
          data: {
            "date": document.getElementById("date").value,
            "isFullDay": document.getElementById("fullDay").checked,
            "accepted": document.getElementById("accepted").checked
          }
        });

        if (!response.status == 200) {
          throw new Error('Request failed');
        } else {
          window.location.href = "/booking";
        }
      } catch (error) {
        console.error(error);
      }
    }
  }

};

</script>

<style>


.adduser-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0,0,0,.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0,0,0,.6);
  border-radius: 10px;
}

.adduser-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.adduser-box .user-box {
  position: relative;
}

.adduser-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}
.adduser-box .user-box label {
  position: absolute;
  top:0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.adduser-box .user-box input:focus ~ label,
.adduser-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.adduser-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
  letter-spacing: 4px
}

.adduser-box a:hover {
  background: #03e9f4;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03e9f4,
  0 0 25px #03e9f4,
  0 0 50px #03e9f4,
  0 0 100px #03e9f4;
}

.adduser-box a span {
  position: absolute;
  display: block;
}

.adduser-box a span:nth-child(1) {
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03e9f4);
  animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%,100% {
    left: 100%;
  }
}

.adduser-box a span:nth-child(2) {
  top: -100%;
  right: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(180deg, transparent, #03e9f4);
  animation: btn-anim2 1s linear infinite;
  animation-delay: .25s
}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%,100% {
    top: 100%;
  }
}

.adduser-box a span:nth-child(3) {
  bottom: 0;
  right: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(270deg, transparent, #03e9f4);
  animation: btn-anim3 1s linear infinite;
  animation-delay: .5s
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%,100% {
    right: 100%;
  }
}

.adduser-box a span:nth-child(4) {
  bottom: -100%;
  left: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(360deg, transparent, #03e9f4);
  animation: btn-anim4 1s linear infinite;
  animation-delay: .75s
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%,100% {
    bottom: 100%;
  }
}
</style>
