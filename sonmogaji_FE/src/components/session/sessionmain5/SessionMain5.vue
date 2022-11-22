<template>
<div id="main5">
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
      <b-col><b-row class="bar" /></b-col>
      <b-col cols="1"><b-row class="circledot" /><b-row>최종 확인</b-row></b-col>
    </b-row>
    <br/><br/>
    <b-row  align-h="center">
      <b-row  align-h="center" v-for="(value, key,index) in this.getSign" v-bind:key="index">
        <b-col cols="3" class="content"> 
          {{key}}
        </b-col>
        <b-col cols="3" class="content" v-if="value"> 
          OK
        </b-col>
        <b-col cols="3"  class="content" v-else> 
          X
        </b-col>
      </b-row>
    </b-row>
    <br/>
    <b-row class="title" align-h="center">각서에 서명하세요!</b-row><br/>
    <b-row >
      <b-col class="content"
        ><canvas
          id="myCanvas"
          width="300"
          height="150"
          @mousedown="beginDrawing"
          @mousemove="keepDrawing"
          @mouseup="stopDrawing"
      /></b-col>
    </b-row>
    <b-row>
      <b-col class="content"><b-button @click="sendsign">서명 완료</b-button></b-col>
    </b-row><br/><br/>

    <b-row>
      <b-col class="agreestate"
        >{{ this.getAgree5 }} / {{ Object.keys(this.getMemorandumState.agree[4]).length - 1 }} 명이
        서명했습니다</b-col
      >
    </b-row><br/>

    <b-row>
      <b-col class="content"><b-button @click="toMain6">진행</b-button></b-col>
      <!-- <b-col><b-button @click="vote5">동의</b-button></b-col> -->
    </b-row>
    <br/><br/>
  </b-container>
</div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
export default {
  name: "SessionMain3",
  components: {},
  created() {
    //this.$router.push({name:"main1Slide1"})
    console.log("sessionMain3");
    console.log(this.getSign)
  },
  computed: {
    ...mapState(["memorandumState", "agree5", "sign"]),
    ...mapGetters(["getMemorandumState", "getAgree5", "getSign"]),
    disabled() {
      return this.status === "disabled";
    },
  },
  data() {
    return {
      x: 0,
      y: 0,
      isDrawing: false,
      canvas: null,
    };
  },
  methods: {
    ...mapActions(["roomVote", "roomVoteCancel", "roomNext","sendSign"]),
    toMain6() {
      this.roomNext(6);
    },
    vote5() {
      this.roomVote(5);
    },
    drawLine(x1, y1, x2, y2) {
      let ctx = this.canvas;
      ctx.beginPath();
      ctx.strokeStyle = "black";
      ctx.lineWidth = 1;
      ctx.moveTo(x1, y1);
      ctx.lineTo(x2, y2);
      ctx.stroke();
      ctx.closePath();
    },
    beginDrawing(e) {
      this.x = e.offsetX;
      this.y = e.offsetY;
      this.isDrawing = true;
    },
    keepDrawing(e) {
      if (this.isDrawing === true) {
        this.drawLine(this.x, this.y, e.offsetX, e.offsetY);
        this.x = e.offsetX;
        this.y = e.offsetY;
      }
    },
    stopDrawing(e) {
      if (this.isDrawing === true) {
        this.drawLine(this.x, this.y, e.offsetX, e.offsetY);
        this.x = 0;
        this.y = 0;
        this.isDrawing = false;
      }
    },
    sendsign() {
      let sign = this.mycanvas.toDataURL("image/png");
      this.sendSign(sign)
    },
  },
  mounted() {
    // let c=this.$el.getContext('2d');
    // let c = this.$refs["refcanvas"];

    this.mycanvas = document.getElementById("myCanvas");
    // console.log(c);
    this.canvas = this.mycanvas.getContext("2d");

    // this.canvas=c;
    // this.$refs["canvas"]
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
#myCanvas {
  border: 1px solid grey;
}

#preview {
  width: auto;
  height: 300px;
}

#main5 {
  /* height: 70vh; */
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
