<template>
  <b-container>
    <b-row>
      <b-col cols="1">
        <b-row class="activecircledot" />
        <b-row>제목 / 내용</b-row>
      </b-col>
      <b-col><b-row class="activebar" /></b-col>
      <b-col cols="1"><b-row class="activecircledot" /> <b-row>옵션 추가</b-row></b-col>
      <b-col><b-row class="activebar" /></b-col>
      <b-col cols="1"><b-row class="activecircledot" /><b-row>이미지 등록</b-row></b-col>
      <b-col><b-row class="activebar" /></b-col>
      <b-col cols="1"><b-row class="activecircledot" /><b-row>서명 추가</b-row></b-col>
      <b-col><b-row class="activebar" /></b-col>
      <b-col cols="1"><b-row class="activecircledot" /><b-row>최종 확인</b-row></b-col>
    </b-row>
    <br /><br />
    <b-row align-h="center" class="title">불만이 있다면 지금 얘기하세요!</b-row>
    <br />
    <b-row align-h="center">
      <div class="content" id="previewzone">
        <img :src="this.getMemorandumPreview" id="memorandumpreview" />
      </div>
    </b-row>
    <br /><br />
    <b-row>
      <b-col class="agreestate"
        >{{ this.getAgree6 }} / {{ Object.keys(this.getMemorandumState.agree[5]).length - 1 }} 명이
        동의했습니다</b-col
      > </b-row
    ><br />

    <b-row>
      <b-col class="content"><b-button @click="toMain7">진행</b-button></b-col>
      <b-col class="content"><b-button @click="vote6">동의</b-button></b-col> </b-row
    ><br /><br />
  </b-container>
</template>

<script>
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
export default {
  name: "SessionMain6",
  components: {},
  created() {
    this.sendRequirePreview();

    console.log("sessionMain6");
  },
  computed: {
    ...mapState(["memorandumPreview", "agree6", "memorandumState"]),
    ...mapGetters(["getMemorandumPreview", "getAgree6", "getMemorandumState"]),
  },
  data() {
    return {};
  },
  methods: {
    ...mapActions(["sendRequirePreview","roomNext","roomVote"]),
    toMain7() {
      this.roomNext(7);
    },
    vote6() {
      this.roomVote(6);
    },
  },
};
</script>

<style>
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

.activebar {
  margin: auto;
  width: 100px;
  height: 5px;
  background: rgb(0, 191, 216);
}
.bar {
  margin: auto;
  width: 100px;
  height: 5px;
  background: rgb(172, 172, 172);
}
#previewzone {
  overflow: scroll;
  height:50vh;
}
</style>
