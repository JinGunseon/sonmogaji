<template>
<div>
  <b-container>
    <b-row>
      <b-col cols="9"><router-view> </router-view></b-col>
      <b-col cols="3"> <Chatt :roomId="roomId" /></b-col
    ></b-row>
  </b-container>
</div>
</template>

<script>
import Chatt from "../../components/session/SessionChat.vue";

import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
import { map } from 'sockjs-client/lib/transport-list';
export default {
  name: "SessionMain",
  data() {
    return {
      ...mapState(["roomId","senderNickName"]),
      ...mapGetters(["getRoomId","getSenderNickName"])
    };
  },
  components: {
    Chatt
  },
  created() {
    console.log("sessionMain");
    // this.enterRoomId("testroom")
    this.stompConnect();
    //stomp room join
  },
  destroyed(){
    console.log('disconnect')
    this.stompDisconnect();
  },
  methods: {
    ...mapActions(["stompConnect","enterRoomId","stompDisconnect"]),
  },
};
</script>
