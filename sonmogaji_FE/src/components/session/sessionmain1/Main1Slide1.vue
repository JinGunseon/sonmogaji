<template>
<div id="slide1">
  <b-container class="bv-example-row">
    <b-row align-h="center">
      
      <b-col cols="1">
        <b-row class="activecircledot" />
      </b-col><b-col cols="1"><b-row class="circledot" /> </b-col><b-col cols="1"><b-row class="circledot" /> </b-col>
    </b-row>
    <br/>
    <br/>
    <b-row>
      <b-col cols="1"> </b-col>
      <b-col
        ><b-row align-h="center">
          <b-col class="title">손모가지 에서 돌이킬 수 없는 약속을 해보세요!</b-col>
        </b-row>
        <br/>

        <b-row>
          <b-col
            ><b-card
              title="블록체인으로 박제!"
              :img-src="require('../../../assets/icons/chain.png')"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2 cards"
            >
              <b-card-text>
                작성된 각서는 블록체인에 저장되어 수정, 삭제가 불가능합니다.
              </b-card-text>
            </b-card></b-col
          >
          <b-col
            ><b-card
              title="나만의 각서 이미지!"
              :img-src="require('../../../assets/icons/folder.png')"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2 cards"
            >
              <b-card-text>
                이미지에는 특별한 코드가 숨겨져 있어, 간편하게 인증할 수 있습니다.
              </b-card-text>
            </b-card></b-col
          >
          <b-col>
            <b-card
              title="실시간으로 함께 작성!"
              :img-src="require('../../../assets/icons/chat.png')"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2 cards"
            >
              <b-card-text> 각서 내용을 실시간으로 합의하고 마음껏 내용을 다투세요! </b-card-text>
            </b-card></b-col
          >
        </b-row>
      </b-col>
      <b-col cols="1" class="toright">
        <img @click="toSlide2" src="../../../assets/toRightSlide.png" />
      </b-col>
    </b-row>
  </b-container>
</div>
</template>

<script>
export default {
  name: "Main1Slide1",
  components: {},
  created() {
    console.log("main1slide1");
  },
  data() {
    return {
      // slide: 0,
      // sliding: null,
    };
  },
  methods: {
    toSlide2() {
      this.$router.push({ name: "main1Slide2" });
    },
    // onSlideStart(slide) {
    //   this.sliding = true;
    // },
    // onSlideEnd(slide) {
    //   this.sliding = false;
    // },
  },
};
</script>
<style>
#slide1{
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
.title{
  font-size: xx-large;
  text-align: center;
}
.cards{
  height: 100%;
}
.toright{
  padding:20% 0;
  text-align: center;
}
.toleft{
  padding:20% 0;
  text-align: center;
}
</style>
