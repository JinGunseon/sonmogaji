<template>
  <div id="slide3">
    <b-container class="bv-example-row">
      <b-row align-h="center"
        ><b-col cols="1"><b-row class="circledot" /> </b-col>
        <b-col cols="1"><b-row class="circledot" /> </b-col
        ><b-col cols="1"> <b-row class="activecircledot" /> </b-col>
      </b-row>
      <br />
      <br />
      <b-row align-h="center">
        <b-col cols="1" class="toleft"><img @click="toSlide2" src="../../../assets/toLeftSlide.png" /> </b-col>
        <b-col >
          <b-row align-h="center">
            <b-col class="title">돌이킬 수 없는 약속을 할 준비가 됐나요?</b-col>
          </b-row><br /><br />
      <br />
          <b-row align-h="center">
            <b-col class="content">아래 버튼을 눌러 시작하세요.</b-col>
          </b-row><br />
          <b-row align-h="center">
            <b-col class="agreestate"
              >{{ this.getAgree1 }} /
              {{ Object.keys(this.getMemorandumState.agree[0]).length - 1 }} 명이
              동의했습니다</b-col
            >
          </b-row><br />
          <b-row align-h="center">
            <b-col class="content"><b-button @click="toMain2">진행</b-button></b-col>
            <b-col class="content"><b-button @click="vote1">동의</b-button></b-col>
          </b-row>
        </b-col>
        <b-col cols="1"> </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
export default {
  name: "Main1Slide3",
  components: {},
  computed: {
    ...mapState(["memorandumState", "agree1"]),
    ...mapGetters(["getMemorandumState", "getAgree1"]),
  },
  created() {
    console.log("main1slide3");
  },
  data() {
    return {
      memberCount: 4,
      // slide: 0,
      // sliding: null,
    };
  },
  methods: {
    ...mapActions(["roomVote", "roomVoteCancel", "roomNext"]),
    toSlide2() {
      this.$router.push({ name: "main1Slide2" });
    },
    toMain2() {
      this.roomNext(2);
    },
    vote1() {
      this.roomVote(1);
    },
  },
};
</script>

<style>
#slide3 {
  height: 70vh;
}
.activecircledot {
  margin: auto;
  width: 20px;
  height: 20px;
  background: rgb(0, 191, 216);
  border-radius: 50%;
}
.circledot {
  margin: auto;
  width: 20px;
  height: 20px;
  background: rgb(172, 172, 172);
  border-radius: 50%;
}
.title {
  font-size: xx-large;
  text-align: center;
}
.cards {
  height: 100%;
}
.toright {
  padding: 20% 0;
  text-align: center;
}
.toleft {
  padding: 20% 0;
  text-align: center;
}
.content{
  text-align: center;
}
.agreestate{
  text-align: center;
  color:red;
}
</style>
