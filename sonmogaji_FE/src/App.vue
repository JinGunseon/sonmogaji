<template>
  <div id="app">
    <!-- <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
      <router-link to="/beforeEnter">memorandum join</router-link>
    <router-link to="/beforeEnter">memorandum create</router-link>
    
    </nav>
    <router-view/> -->
    <HeaderNavBar id="header" />
    <RouterView id="section" />
    <FooterBar></FooterBar>
  </div>
</template>

<script>
import HeaderNavBar from "@/components/layout/HeaderNavBar.vue";
import FooterBar from "@/components/layout/FooterBar.vue";
export default {
  name: "App",
  components: {
    HeaderNavBar,
    FooterBar,
  },
};
</script>

<style>
@import "@/assets/css/main.css";
</style>
