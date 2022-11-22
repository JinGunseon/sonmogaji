<template>
  <div id="enterRoomCode">
    <b-container>
      <b-row align-h="center">
        <b-col id="roomcodeLabel"><p class="h2">방 코드를 입력하세요!</p></b-col>
      </b-row>
      <br />
      <b-row align-h="center"
        ><b-col id="roomcodeStatus">{{ this.status }}</b-col></b-row
      >
      <br /><b-row align-h="center">
        <b-col id="roomcodeInput"
          ><input v-model="codeinput" id="code" type="text"
        /></b-col> </b-row
      ><br />
      <b-row align-h="center">
        <b-col id="roomcodeButton"
          ><b-button @click="next()" style="background-color: #223359; border: 0px; font-size: 20px"
            >다음</b-button
          ></b-col
        >
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
export default {
  name: "RoomCode",
  components: {},
  computed: {
    ...mapState(["senderNickName"]),
    ...mapGetters(["getSenderNickName"]),
  },
  created() {
    //url 에 roomcode 있으면 바로 별명페이지 route
    //6자리 숫자 roomcode
    this.clearState();
  },
  mounted() {
    console.log("RoomCode");
  },
  data() {
    return {
      codeinput: "",
      status: "",
    };
  },
  methods: {
    ...mapActions(["enterRoomCode"]),
    ...mapMutations(["clearState"]),
    next() {
      if (this.codeinput != "") {
        // console.log(this.codeinput)
        this.enterRoomCode(this.codeinput);

        this.$router.push({ name: "enterNickname" });
      } else {
        this.status = "코드를 입력해주세요";
      }
    },
  },
};
</script>
<style scoped>
#enterRoomCode {
  padding: 20vh 0;
  height: 70vh;
}
#roomcodeLabel {
  text-align: center;
}
#roomcodeStatus {
  color: red;
  text-align: center;
}
#roomcodeInput {
  text-align: center;
}
#roomcodeButton {
  text-align: center;
}
#code {
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
  font-size: 24px;
  text-align: center;
}
</style>
