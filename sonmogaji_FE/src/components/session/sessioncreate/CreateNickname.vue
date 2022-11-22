<template>
  <div id="enterNickname">
    <b-container>
      <b-row align-h="center">
        <b-col id="nicknameLabel"><p class="h2">별명을 입력하세요!</p></b-col>
      </b-row>
      <br />
      <b-row align-h="center"
        ><b-col id="nicknameStatus">{{ this.status }}</b-col></b-row
      ><br />
      <b-row align-h="center">
        <b-col id="nicknameInput"
          ><input v-model="nicknameinput" id="nickname" type="text"
        /></b-col> </b-row
      ><br />
      <b-row align-h="center">
        <b-col id="nicknameButton">
          <b-button
            @click="create()"
            style="background-color: #223359; border: 0px; font-size: 20px"
            >방만들기</b-button
          ></b-col
        >
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
import axios from "axios";
export default {
  name: "enterNickname",
  components: {},
  computed: {
    ...mapState(["senderNickName"]),
    ...mapGetters(["getSenderNickName"]),
  },
  created() {
    this.clearState();
  },
  mounted() {
    console.log("enterNickname");
  },
  data() {
    return {
      nicknameinput: "",
      status: "",
    };
  },
  methods: {
    ...mapActions(["enterNickName", "enterRoomCode", "enterRoll"]),
    ...mapMutations(["setIsHost", "clearState"]),
    create() {
      if (this.nicknameinput != "") {
        this.enterNickName(this.nicknameinput);
        axios
          .post("/api/room/create", {
            senderNickName: this.getSenderNickName,
          })
          .then((response) => {
            this.enterRoll("signee");
            console.log(response.data.roomCode);
            this.enterRoomCode(response.data.roomCode);
            this.setIsHost(true);
            this.$router.push({ name: "session" });
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      } else {
        this.status = "별명을 확인해주세요";
      }
    },
  },
};
</script>
<style scoped>
#enterNickname {
  padding: 20vh 0;
  height: 70vh;
}
#nicknameLabel {
  text-align: center;
}
#nicknameStatus {
  color: red;
  text-align: center;
}
#nicknameInput {
  text-align: center;
}
#nickname {
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
  font-size: 24px;
  text-align: center;
}
#nicknameButton {
  text-align: center;
}
</style>
