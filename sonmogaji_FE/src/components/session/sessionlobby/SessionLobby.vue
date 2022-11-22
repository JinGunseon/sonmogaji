<template>
  <div id="lobby">
    <b-container>
      <b-row>
        <b-col>
          <b-row>
            <div class="menu-box bg-navy shadow-lg rounded p-4 lobbybox">
              <div class="mb-4">
                <p class="h3 mb-3">환영합니다!</p>
                <p class="fw-light">
                  화면에 내 별명이 보이나요?<br />
                  모두 참여할 때 까지 기다려주세요
                </p>
              </div>
            </div>
          </b-row>
          <b-row>
            <div class="menu-box bg-cyan shadow-lg rounded p-4 lobbybox align-middle">
              <b-row
                ><b-col><p class="h3">방 코드</p></b-col>
                <b-col
                  ><p class="h3">{{ this.getRoomCode }}</p></b-col
                ></b-row
              >
              <b-row
                ><b-col><p class="h3">링크 복사</p></b-col> <b-col></b-col
              ></b-row>
            </div>
          </b-row>
        </b-col>
        <b-col>
          <div class="menu-box shadow-lg rounded p-4 memberbox">
            <b-row>
              <b-col
                cols="3"
                class="membermargin"
                v-for="(value, key, index) in this.getSign"
                v-bind:key="index"
              >
                <div class="bg-white shadow-md rounded members">{{ key }}</div>
              </b-col>
            </b-row>

            <b-button id="startbutton" variant="success" @click="start()">시작</b-button>
          </div>
          <!-- <b-jumbotron bg-variant="info" text-variant="white" border-variant="dark">
          <b-button variant="success" @click="start()">시작</b-button>
        </b-jumbotron> -->
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import axios from "axios";
export default {
  name: "SessionLobby",
  components: {},
  computed: {
    ...mapState(["sign"]),
    ...mapGetters(["getRoomCode", "getSign"]),
  },
  created() {},
  mounted() {
    console.log("sessionLobby");
  },
  data() {
    return {};
  },
  methods: {
    ...mapActions(["memorandumStart", "roomStart"]),
    start() {
      console.log("시작");
      this.roomStart();

      // this.$router.push({ name: "sessionMain1" });
    },
  },
};
</script>
<style scoped>
#lobby {
  height: 70vh;
}
.lobbybox {
  padding-top: 15% !important;
  padding-bottom: 15% !important;
}
.memberbox {
  background-color: rgb(204, 204, 204);
  position: relative;
  height: 95%;
}
.members {
  color: black;
  text-align: center;
}
.membermargin {
  margin: 5px;
}
#startbutton {
  position: absolute;
  right: 10px;
  bottom: 10px;
}
</style>
