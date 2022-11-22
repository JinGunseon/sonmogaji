<template>
  <div id="slide2">
    <b-container class="bv-example-row">
      <b-row align-h="center"
        ><b-col cols="1"><b-row class="circledot" /> </b-col>
        <b-col cols="1"> <b-row class="activecircledot" /> </b-col
        ><b-col cols="1"><b-row class="circledot" /> </b-col>
      </b-row>
      <br />
      <br />
      <b-row align-h="center">
        <b-col cols="1" class="toleft"
          ><img @click="toSlide1" src="../../../assets/toLeftSlide.png" />
        </b-col>
        <b-col>
          <b-row align-h="center">
            <b-col class="title">절차는 방장이 진행합니다.</b-col>
          </b-row>
          <b-row align-h="center">
            <b-col class="title">모두가 동의해야 진행할 수 있으니 걱정 마세요!</b-col>
          </b-row>
          <br />
          <b-row align-h="center" id="cardrow">
            <b-card class="cards">
              <b-row id="cardcontent"
                ><b-col ><b-row id="index">1</b-row><b-row id="index">2</b-row><b-row id="index">3</b-row><b-row id="index">4</b-row></b-col
                ><b-col cols="10" 
                  ><b-row id="content"> 채팅으로 각서에 채울 내용을 협의합니다.</b-row
                  ><b-row id="content"> 방장은 협의한 내용을 각서에 적습니다.</b-row
                  ><b-row id="content"> 참여자는 작성된 내용이 만족스럽다면, 동의 버튼을 누릅니다.</b-row
                  ><b-row id="content"> 모두가 동의를 눌렀다면, 방장이 단계를 진행합니다.</b-row></b-col
                ></b-row
              >
              <b-card-text>
                <b-col> </b-col>
              </b-card-text>
            </b-card>
          </b-row>
        </b-col>
        <b-col cols="1" class="toright">
          <img @click="toSlide3" src="../../../assets/toRightSlide.png" />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "Main1Slide2",
  components: {},
  created() {
    console.log("main1slide2");
  },
  data() {
    return {
      // slide: 0,
      // sliding: null,
    };
  },
  methods: {
    toSlide3() {
      this.$router.push({ name: "main1Slide3" });
    },
    toSlide1() {
      this.$router.push({ name: "main1Slide1" });
    },
  },
};
</script>
<style>
#slide2 {
  height: 70vh;
}
#cardrow {
  height: 30vh;
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
#index{
  text-align: center;
  padding-left: 30px;
  height:40px;
  font-size: xx-large;
}
#content{
  font-size: x-large;
  height:40px;
}
#cardcontent{
  padding-top: 30px;
}
</style>

