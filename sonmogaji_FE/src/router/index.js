import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "@/views/HomeView.vue";
import UserView from "@/views/UserView.vue";

//import sessionMain from '../views/session/SessionMain.vue'
import sessionMain1 from "../components/session/sessionmain1/SessionMain1.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    children: [
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/MetamaskLogin.vue"),
      },
      {
        path: "myPage",
        name: "myPage",
        component: () => import("@/components/user/MyPage.vue"),
      },
    ],
  },
  {
    path: "/auth",
    name: "auth",
    component: () => import("@/views/AuthView.vue"),
    children: [
      {
        path: "upload",
        name: "upload",
        component: () => import("@/components/auth/ImageUpload.vue"),
      },
    ],
  },
  {
    path: "/createRoom",
    name: "createRoom",
    component: () => import("../views/session/createRoom.vue"),
    redirect: (to) => {
      return { path: "/createRoom/createNickname" };
    },
    children: [
      {
        path: "createNickname",
        name: "createNickname",
        component: () => import("../components/session/sessioncreate/CreateNickname.vue"),
      },
    ],
  },
  {
    path: "/beforeEnter",
    name: "beforeEnter",
    component: () => import("../views/session/BeforeEnter.vue"),
    redirect: (to) => {
      return { path: "/beforeEnter/roomCode" };
    },
    children: [
      {
        path: "roomCode",
        name: "roomCode",
        component: () => import("../components/session/beforeEnter/RoomCode.vue"),
      },

      {
        path: "enterNickname",
        name: "enterNickname",
        component: () => import("../components/session/beforeEnter/EnterNickname.vue"),
      },
      {
        path: "chooseRoll",
        name: "chooseRoll",
        component: () => import("../components/session/beforeEnter/ChooseRoll.vue"),
      },
    ],
  },
  {
    path: "/session",
    name: "session",
    component: () => import("../views/session/SessionMain.vue"),
    redirect: (to) => {
      return { path: "/session/sessionLobby" };
    },
    children: [
      {
        path: "sessionLobby",
        name: "sessionLobby",
        component: () => import("../components/session/sessionlobby/SessionLobby.vue"),
      },
      {
        path: "sessionMain1",
        name: "sessionMain1",
        component: () => import("../components/session/sessionmain1/SessionMain1.vue"),
        children: [
          {
            path: "main1Slide1",
            name: "main1Slide1",
            component: () => import("../components/session/sessionmain1/Main1Slide1.vue"),
          },
          {
            path: "main1Slide2",
            name: "main1Slide2",
            component: () => import("../components/session/sessionmain1/Main1Slide2.vue"),
          },
          {
            path: "main1Slide3",
            name: "main1Slide3",
            component: () => import("../components/session/sessionmain1/Main1Slide3.vue"),
          },
        ],
      },
      {
        path: "sessionMain2",
        name: "sessionMain2",
        component: () => import("../components/session/sessionmain2/SessionMain2.vue"),
      },
      {
        path: "sessionMain3",
        name: "sessionMain3",
        component: () => import("../components/session/sessionmain3/SessionMain3.vue"),
      },
      {
        path: "sessionMain4",
        name: "sessionMain4",
        component: () => import("../components/session/sessionmain4/SessionMain4.vue"),
      },
      {
        path: "sessionMain5",
        name: "sessionMain5",
        component: () => import("../components/session/sessionmain5/SessionMain5.vue"),
      },
      {
        path: "sessionMain6",
        name: "sessionMain6",
        component: () => import("../components/session/sessionmain6/SessionMain6.vue"),
      },
      {
        path: "sessionMain7",
        name: "sessionMain7",
        component: () => import("../components/session/sessionmain7/SessionMain7.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
