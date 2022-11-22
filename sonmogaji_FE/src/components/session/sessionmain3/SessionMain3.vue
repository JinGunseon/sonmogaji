<template>
  <div id="main3">
    <b-container>
      <b-row>
        <b-col cols="1">
          <b-row class="activecircledot" />
          <b-row>제목 / 내용</b-row>
        </b-col>
        <b-col><b-row class="activebar" /></b-col>
        <b-col cols="1"><b-row class="activecircledot" /> <b-row>옵션 추가</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>이미지 등록</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>서명 추가</b-row></b-col>
        <b-col><b-row class="bar" /></b-col>
        <b-col cols="1"><b-row class="circledot" /><b-row>최종 확인</b-row></b-col>
      </b-row>
      <br /><br />
      <b-row class="title">각서 공개 여부</b-row>
      <br/>
      <b-row v-if="this.getIsHost">
        <b-col
          ><b-form-radio v-model="selected" name="some-radios" value="A" @change="secretchange"
            >공개</b-form-radio
          ></b-col
        >
        <b-col
          ><b-form-radio v-model="selected" name="some-radios" value="B" @change="secretchange"
            >비공개</b-form-radio
          ></b-col
        >
      </b-row>
      <b-row v-else>
        <b-col
          ><b-form-radio
            v-model="selected"
            name="some-radios"
            value="A"
            onclick="return(false);"
            @change="secretchange"
            >공개</b-form-radio
          ></b-col
        >
        <b-col
          ><b-form-radio
            v-model="selected"
            name="some-radios"
            value="B"
            onclick="return(false);"
            @change="secretchange"
            >비공개</b-form-radio
          ></b-col
        >
      </b-row>
      <br/><br/>
      <b-row class="title">각서 만료 날짜</b-row>
      <br/>
      <b-row v-if="this.getIsHost">
        <b-col
          ><b-form-datepicker
            v-model="expire"
            id="ex-disabled-readonly"
            @input="datechange"
          ></b-form-datepicker>
        </b-col>
       
      </b-row>
      
      <b-row v-else>
        <b-col
          ><b-form-datepicker
            v-model="expire"
            id="ex-disabled-readonly"
            readonly
          ></b-form-datepicker>
        </b-col>
        
      </b-row>
      <br/><br/>
      <b-row>
        <b-col  class="agreestate"
          >{{ this.getAgree3 }} /
          {{ Object.keys(this.getMemorandumState.agree[2]).length - 1 }} 명이 동의했습니다</b-col
        >
      </b-row><br/>

      <b-row >
        <b-col class="content"><b-button @click="toMain4">진행</b-button></b-col>
        <b-col class="content"><b-button @click="vote3">동의</b-button></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
export default {
  name: "SessionMain3",
  components: {},
  created() {
    //this.$router.push({name:"main1Slide1"})
    console.log("sessionMain3");
    console.log(this.getIsHost);
  },
  computed: {
    ...mapState(["memorandumState", "agree3", "secret", "isHost", "expire"]),
    ...mapGetters(["getMemorandumState", "getAgree3", "getSecret", "getIsHost", "getExpire"]),
    disabled() {
      return this.getExpire === "disabled";
    },
    selected: {
      get() {
        // return this.$store.state.title;
        return this.getSecret;
      },
      set(value) {
        this.setSecret(value);

        //this.$store.commit("updateMessage", value);
      },
    },
    expire: {
      get() {
        // return this.$store.state.title;
        return this.getExpire;
      },
      set(value) {
        this.setExpire(value);

        //this.$store.commit("updateMessage", value);
      },
    },
  },
  updated() {},
  data() {
    return {
      //status: "",
    };
  },
  methods: {
    ...mapActions(["roomVote", "roomVoteCancel", "roomNext", "sendSecret", "sendDate"]),
    ...mapMutations(["setSecret", "setExpire"]),
    toMain4() {
      this.roomNext(4);
    },
    vote3() {
      this.roomVote(3);
    },
    secretchange() {
      console.log(this.selected);
      this.sendSecret();
    },
    datechange() {
      console.log(this.getExpire);
      this.sendDate();
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
#main3 {
  height: 70vh;
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
