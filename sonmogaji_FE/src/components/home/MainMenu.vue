<template>
  <div id="main-menu">
    <b-container>
      <div class="d-flex my-3">
        <div class="circle bg-cyan mx-1"></div>
        <div class="circle bg-light-gray mx-1"></div>
        <div class="circle bg-light-gray mx-1"></div>
      </div>
      <b-row>
        <b-col cols="9">
          <b-card id="banner" class="shadow-lg rounded p-5">
            <h1 class="display-4 fw-semibold my-5">약속은 즐거운 추억입니다!</h1>
            <p class="h2 fw-light my-3">
              손모가지는, 친구들과 실시간으로 각서를 작성하며<br />
              추억을 만들 수 있는 블록체인 기반 각서 플랫폼입니다.
            </p>
          </b-card>
        </b-col>
        <b-col cols="3">
          <div class="menu-box bg-navy shadow-lg rounded p-4">
            <div class="mb-4">
              <p class="h3">각서 쓰러가기</p>
              <p class="fw-light">진실의 방에서 친구와 돌이킬 수 없는 약속을 만들어 보세요!</p>
            </div>
            <div class="d-flex justify-content-center text-center">
              <div class="mx-auto">
                <b-icon class="icon-button mx-auto" icon="plus-square-dotted" @click="createRoom" />
                <p class="fw-light">방 생성하기</p>
              </div>
              <div class="mx-auto">
                <b-icon class="icon-button mx-auto" icon="search" @click="joinRoom" />
                <p class="fw-light">방 참여하기</p>
              </div>
            </div>
          </div>
          <div class="menu-box bg-cyan shadow-lg rounded p-4">
            <div class="mb-5">
              <p class="h3">각서 인증하기</p>
              <p class="fw-light">친구의 각서가 진짜인지 알아봅시다</p>
            </div>
            <div class="mx-auto text-center" @click="authImage">
              <b-icon class="icon-button mx-auto" icon="image" />
              <p class="fw-light">사진 업로드</p>
            </div>
          </div>
        </b-col>
      </b-row>
    </b-container>
    <div class="my-5 text-center">
      <div v-animate-css.hover="'flash'" style="width: 200px" class="mx-auto" @click="scroll">
        <p class="h4 mb-3">체인 둘러보기</p>
        <img src="@/assets/icons/down.png" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainMenu",
  methods: {
    scroll() {
      window.scrollTo({ left: 0, top: screen.height - 100, behavior: "smooth" });
      console.log("hi");
    },
    createRoom() {
      this.$router.push({ name: "createRoom" });
    },
    joinRoom() {
      this.$router.push({ name: "beforeEnter" });
    },
    authImage() {
      this.$router.push({ name: "upload" });
    },
  },
};
</script>

<style>
#main-menu {
  height: 100vh;
}
.icon-button {
  width: 60px;
  height: 60px;
  color: white;
  cursor: pointer;
}
</style>
