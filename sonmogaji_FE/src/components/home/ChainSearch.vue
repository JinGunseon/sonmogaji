<template>
  <div id="chain-search">
    <div class="mb-5 text-center">
      <div v-animate-css.hover="'flash'" style="width: 200px" class="mx-auto" @click="scroll">
        <img src="@/assets/icons/up.png" />
        <p class="h4 mt-3">서비스 이용하기</p>
      </div>
    </div>
    <div class="text-center my-5">
      <h1 class="display-4 fw-semibold my-5">체인 둘러보기</h1>
      <p class="h2 fw-light my-5">
        다른 사람들이 만든 각서를 확인할 수 있습니다.<br />
        클릭해서 내용을 확인해보세요!
      </p>
    </div>
    <div ref="slider" class="keen-slider d-flex overflow-hidden">
      <div class="keen-slider__slide" v-for="(item, index) in txList" :key="item.txAddress">
        <img class="item mx-5" :src="getImageSrc(index)" @click="showModal(item)" />
      </div>
    </div>
    <b-modal id="modal" ref="modal" :title="modalData.txTitle" @hidden="hideModal()">
      {{ modalData.txContent }}
    </b-modal>
  </div>
</template>

<script>
import KeenSlider from "keen-slider";
import {mapState, mapActions} from "vuex";

const apiStore = "apiStore";

let gear = 0;
let callback = null;
const animation = { duration: 10000, easing: (t) => t };

export default {
  name: "ChainSearch",
  computed: {
      ...mapState(apiStore, ["txList"]),
  },
  created() {
    const info = {page : 0}
    this.readTxList(info);

    this.chainList = this.txList;
  },
  data() {
    return {
      modalData: {
        txAddress: "",
        txTitle: "제목",
        txContent : "",
      },
      chainList: null
      // [
      //   { hash: "hash1" },
      //   { hash: "hash2" },
      //   { hash: "hash3" },
      //   { hash: "hash4" },
      //   { hash: "hash5" },
      //   { hash: "hash6" },
      //   { hash: "hash7" },
      //   { hash: "hash8" },
      //   { hash: "hash9" },
      // ]
      ,
      images: ["item-cyan.png", "item-navy.png", "item-blue.png", "item-yellow.png"],
    };
  },
  methods: {
    ...mapActions(apiStore, ['readTxList']),
    rotateList() {
      callback = setInterval(() => {
        const first = this.chainList.splice(0, 1);
        const lastIndex = this.chainList.length;
        this.chainList.splice(lastIndex, 0, first[0]);
        gear = (gear + 1) % 4;
      }, 2000);
      // setTimeout(() => {
      // }, 1000);
      // this.chainList.splice(this.chainList.length, 0, first);
      // console.log(this.chainList[0].hash);
    },
    getImageSrc(index) {
      return require(`@/assets/icons/${this.images[(index + gear) % 4]}`);
    },
    showModal(tx) {
      this.slider.animator.stop();
      clearInterval(callback);
      // hash 값으로 모달에 띄울 데이터를 받아온다.
      this.$bvModal.show("modal");
      this.modalData.txContent = tx.txContent;
      this.modalData.txAddress = tx.txAddress;
      this.modalData.txTitle = tx.txTitle;
    },
    hideModal() {
      this.slider.emit("updated");
      // this.rotateList();
    },
    scroll() {
      window.scrollTo({ left: 0, top: 0, behavior: "smooth" });
      console.log("hi");
    },
  },
  mounted() {
    // this.rotateList();
    this.slider = new KeenSlider(this.$refs.slider, {
      mode: "snap",
      loop: true,
      renderMode: "performance",
      slides: { perView: "auto" },
      created(s) {
        s.moveToIdx(8, true, animation);
      },
      updated(s) {
        s.moveToIdx(s.track.details.abs + 8, true, animation);
      },
      animationEnded(s) {
        s.moveToIdx(s.track.details.abs + 8, true, animation);
      },
      // slides: () => [
      //   {
      //     size: 0.1,
      //   },
      // ],
    });
  },
  beforeDestroy() {
    if (this.slider) this.slider.destroy();
  },
};
</script>

<style>
#chain-search {
  height: 100vh;
}
.item {
  width: 200px;
  height: 200px;
}
/* 1. declare transition */
.fade-move,
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s cubic-bezier(0.55, 0, 0.1, 1);
}

/* 2. declare enter from and leave to state */
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: scaleY(0.01) translate(30px, 0);
}

/* 3. ensure leaving items are taken out of layout flow so that moving
      animations can be calculated correctly. */
.fade-leave-active {
  position: absolute;
}
</style>
