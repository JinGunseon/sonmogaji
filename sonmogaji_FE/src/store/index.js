import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import apiStore from "@/store/module/apiStore";
import socketStore from "@/store/module/socketStore";
import userStore from "@/store/module/userStore";

Vue.use(Vuex);
const store = new Vuex.Store({
  modules: {
    apiStore,
    socketStore,
    userStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
