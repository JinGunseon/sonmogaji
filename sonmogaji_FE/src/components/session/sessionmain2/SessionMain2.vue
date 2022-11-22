<template>
  <div id="main2">
    <b-container>
      <b-row>
        <b-col cols="1">
          <b-row class="activecircledot" />
          <b-row>제목 / 내용</b-row>
        </b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /> <b-row>옵션 추가</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>이미지 등록</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>서명 추가</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>최종 확인</b-row></b-col> </b-row
      ><br /><br />
      <b-row class="title">제목</b-row>
      <b-row v-if="this.getIsHost">
        <b-form-input
          id="input-default"
          size="sm"
          placeholder="각서의 제목을 입력하세요"
          v-model="title"
          @input="change"
        >
        </b-form-input>
      </b-row>
      <b-row v-else>
        <b-form-input
          id="input-default"
          size="sm"
          placeholder="각서의 제목을 입력하세요"
          v-model="title"
          readonly
          @input="change"
        >
        </b-form-input>
      </b-row>
      <br /><br />
      <b-row class="title">내용</b-row>
      <b-row v-if="this.getIsHost">
        <b-form-textarea
          id="textarea-rows"
          rows="8"
          placeholder="약속할 내용을 입력하세요"
          v-model="content"
          @input="change"
        >
        </b-form-textarea>
      </b-row>
      <b-row v-else>
        <b-form-textarea
          id="textarea-rows"
          rows="8"
          placeholder="약속할 내용을 입력하세요"
          readonly
          v-model="content"
          @input="change"
        >
        </b-form-textarea>
      </b-row>
      <br />
      <b-row>
        <b-col class="agreestate"
          >{{ this.getAgree2 }} /
          {{ Object.keys(this.getMemorandumState.agree[1]).length - 1 }} 명이 동의했습니다</b-col
        > </b-row
      ><br />

      <b-row>
        <b-col class="content"><b-button @click="toMain3">진행</b-button></b-col>
        <b-col class="content"><b-button @click="vote2"> 동의</b-button></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
export default {
  name: "SessionMain2",
  components: {},
  computed: {
    ...mapState(["memorandumState", "agree2", "title", "content", "isHost"]),
    ...mapGetters(["getMemorandumState", "getAgree2", "getTitle", "getContent", "getIsHost"]),
    title: {
      get() {
        // return this.$store.state.title;
        return this.getTitle;
      },
      set(value) {
        this.setTitle(value);

        //this.$store.commit("updateMessage", value);
      },
    },
    content: {
      get() {
        // return this.$store.state.content;
        return this.getContent;
      },
      set(value) {
        this.setContent(value);
        //this.$store.commit("updateMessage", value);
      },
    },
  },
  created() {
    //this.$router.push({name:"main1Slide1"})
    console.log("sessionMain2");
  },
  data() {
    return {
      typing: false,
    };
  },
  updated() {},
  methods: {
    ...mapActions(["roomVote", "roomVoteCancel", "roomNext", "sendContent"]),
    ...mapMutations(["setTitle", "setContent"]),
    toMain3() {
      this.roomNext(3);
    },
    vote2() {
      this.roomVote(2);
    },
    change() {
      console.log("type");
      clearTimeout(this.typing);
      this.typing = setTimeout(this.donetype, 1000);
    },
    donetype() {
      console.log("sent");
      this.sendContent();
    },
  },
};
</script>

<style>
#main2 {
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
.title {
  font-size: x-large;
}
.content {
  text-align: center;
}
.agreestate {
  text-align: center;
  color: red;
}
</style>
