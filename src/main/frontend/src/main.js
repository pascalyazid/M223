import { createApp } from 'vue';
import VueAxios from 'vue-axios';
import App from '@/App';
import axios from 'axios';
import mitt from 'mitt';
import {createRouter, createWebHistory} from "vue-router";
import AddUserPage from "@/components/admin/user/AddUserPage.vue";
import EditUser from "@/components/EditUser.vue";
import PageNotFound from "@/components/PageNotFound.vue";
import AdminUser from "@/components/admin/user/AdminUser.vue";
import AdminBooking from "@/components/admin/booking/AdminBooking.vue";
import AddBookingPage from "@/components/AddBookingPage.vue";
import Login from "@/components/login.vue";
import EditBooking from "@/components/admin/booking/EditBooking.vue";
import mainPage from "@/components/mainPage.vue";
import Register from "@/components/register.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: "/", name: "MainPage", component: mainPage },
        {path: "/login", name: "LoginPage", component: Login },
        {path: "/register", name: "RegisterPage", component: Register },
        {path: "/booking", name: "AdminBooking", component: AdminBooking},
        {path: "/users", name: "AdminUserPage", component: AdminUser},
        {path: "/addUser", name: "AddUserPage", component: AddUserPage},
        {path: "/addBooking", name: "AddBookingPage", component: AddBookingPage},
        {path: "/editBooking/:id", name: "EditBookingPage", component: EditBooking},
        {path: "/editUser/:id", name: "EditUserPage", component: EditUser},
        { path: "/:catchAll(.*)", name: "PageNotFoundPage", component: PageNotFound }
    ]
})

const emitter = mitt();
const app = createApp(App);
app.config.globalProperties.emitter = emitter;
app.use(VueAxios, axios);
app.use(router);
app.mount('#app');
