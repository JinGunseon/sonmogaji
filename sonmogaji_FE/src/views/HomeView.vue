<template>
  <div>
    <MainMenu></MainMenu>
    <ChainSearch></ChainSearch>
  </div>
</template>

<script>
// @ is an alias to /src
import MainMenu from "@/components/home/MainMenu.vue";
import ChainSearch from "@/components/home/ChainSearch.vue";

export default {
  name: "HomeView",
  components: {
    MainMenu,
    ChainSearch,
  },
};
</script>
