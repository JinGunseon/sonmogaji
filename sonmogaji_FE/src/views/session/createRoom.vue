<template>
<div>
      <router-view> </router-view>
      </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { map } from "sockjs-client/lib/transport-list";
import EnterNickname from "../../components/session/beforeEnter/RoomCode.vue"
import ChooseRoll from "../../components/session/beforeEnter/ChooseRoll.vue"
export default {
  name: "createRoom",
  data() {
    return {
      ...mapState(["roomId", "nickname"]),
    };
  },
  components: {
  },
  created() {
    console.log("createRoom");
    //메타마스크 로그인 했는지 vuex로 확인 필요. 로그인 했으면 바로 session 으로 route
  },
  methods: {
    ...mapActions([]),
  },
};
</script>
