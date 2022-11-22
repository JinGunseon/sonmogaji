<template>
  <b-container>
    <router-view> 

      
    </router-view>

  </b-container>
</template>

<script>
export default {
  name: "SessionMain1",
  components: {
  },
  created() {
    this.$router.push({name:"main1Slide1"})
    console.log("sessionMain1");
  },
  data() {
    return {
    };
  },
  methods: {
  },
};
</script>
