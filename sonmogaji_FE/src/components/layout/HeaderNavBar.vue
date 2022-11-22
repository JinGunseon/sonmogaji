<template>
  <b-navbar class="px-5">
    <b-navbar-brand href="/">
      <img src="@/assets/logos/logo.png" width="200px" alt="sonmogaji" />
    </b-navbar-brand>
    <b-navbar-nav class="w-100"></b-navbar-nav>
    <b-navbar-nav class="ml-auto">
      <b-nav-item-dropdown v-if="isLoggedIn">
        <template #button-content>
          <b-icon icon="person-circle" font-scale="2"></b-icon>
        </template>
        <b-dropdown-item @click="logout">로그아웃</b-dropdown-item>
        <b-dropdown-item @click="myPage">마이페이지</b-dropdown-item>
      </b-nav-item-dropdown>
      <b-nav-item-dropdown v-else>
        <template #button-content>
          <b-icon icon="list" font-scale="2"></b-icon>
        </template>
        <b-dropdown-item @click="login">로그인</b-dropdown-item>
      </b-nav-item-dropdown>
    </b-navbar-nav>
  </b-navbar>
</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "HeaderNavBar",
  computed: {
    ...mapState(userStore, ["isLoggedIn", "account"]),
  },
  methods: {
    ...mapActions(userStore, ["logout"]),
    login() {
      this.$router.push({ name: "login" });
    },
    myPage() {
      this.$router.push({ name: "myPage" });
    },
  },
};
</script>
