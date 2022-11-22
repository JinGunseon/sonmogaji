<template>
  <div id="enterroll">
    <b-container>
      <b-row align-h="center">
        <b-col  class="cards">
          <b-card
            @click="chooseSignee()"
            title="참여자"
            :img-src="require('../../../assets/user.png')"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
          >
            <b-card-text> 각서에 직접 이름을 올리고 서명합니다. </b-card-text>
          </b-card>
        </b-col>
        <b-col  class="cards">
          <b-card
            @click="chooseObserver()"
            title="관전자"
            :img-src="require('../../../assets/usergroup.png')"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
          >
            <b-card-text> 강 건너 불구경 하러 왔습니다. </b-card-text>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
import axios from "axios";
export default {
  name: "ChooseRoll",
  components: {},
  computed: {
    ...mapState(["roll"]),
    ...mapGetters(["getRoll", "getSenderNickName", "getRoomCode"]),
  },
  created() {},
  mounted() {
    console.log("ChooseRoll");
  },
  data() {
    return {};
  },
  methods: {
    ...mapActions(["enterRoll", "enterRoomCode"]),
    ...mapMutations(["setIsHost"]),
    chooseSignee() {
      console.log(this.getRoomCode);
      console.log(this.getSenderNickName);
      axios
        .post("/api/room/isAvail", {
          roomCode: this.getRoomCode,
          senderNickName: this.getSenderNickName,
        })
        .then((response) => {
          console.log(response.data);
          this.enterRoll("signee");
          if (response.data.message == "yes") {
            this.$router.push({ name: "session" });
          } else {
            alert("방이 존재하지 않습니다");
          }
        })
        .catch((error) => {
          // alert("방이 존재하지 않습니다");
          console.log(error);
        })
        .finally(() => {});
    },
    chooseObserver() {
      axios
        .post("/api/room/isAvail", {
          roomCode: this.getRoomCode,
          senderNickName: this.getSenderNickName,
        })
        .then((response) => {
          console.log(response.data);
          this.enterRoll("observer");
          this.setIsHost(false);

          this.$router.push({ name: "session" });
        })
        .catch((error) => {
          console.log(error);
          alert("방이 존재하지 않습니다");
        })
        .finally(() => {});
    },
  },
};
</script>
<style scoped>
#enterroll {
  padding: 20vh 0;
  height: 70vh;
}
.selectroll {
  text-align: center;
}
.cards{
  padding: 0 10%;
   text-align: center;
}
</style>
