<template>
  <b-container>
    <main id="app">
      <section ref="chatArea" class="chat-area">
        <ul v-for="(message, index) in this.getChatmessages" :key="index">
          <li v-if="message.sender === getSenderNickName" class="message message-out">
            {{ message.message }}
          </li>
          <li v-else class="message message-in">
            {{ message.message }}
          </li>
        </ul>
      </section>

      <section class="chat-inputs">
        <form @submit.prevent="sendMessage()" id="person2-form" class="shaodow-lg">
          <b-row>
            <b-col cols="9"
              ><input
                v-model="chatinput"
                id="person2-input"
                type="text"
                placeholder="채팅을 입력하세요!"
                style="border: 0px; background-color: #f5f5f5"
            /></b-col>
            <b-col cols="3"
              ><b-button
                type="submit"
                style="background-color: #223359; border: 0px; font-size: 20px"
                ><b-icon class="send"></b-icon></b-button
            ></b-col>
          </b-row>
        </form>
      </section>
    </main>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
export default {
  name: "SessionChat",
  components: {},
  computed: {
    ...mapState(["chatmessages", "stomp", "roomId", "nickname"]),
    ...mapGetters(["getChatmessages", "getSenderNickName"]),
  },
  created() {},
  mounted() {
    console.log("sessionChat");
    console.log(this.getSenderNickName);
  },
  props: {},
  data() {
    return {
      chatinput: "",
    };
  },
  methods: {
    ...mapActions(["chat"]),
    sendMessage() {
      this.chat(this.chatinput);
      this.chatinput = "";
    },
  },
};
</script>

<style>
.headline {
  text-align: center;
  font-weight: 100;
  color: white;
}
.chat-area {
  /*   border: 1px solid #ccc; */
  background: white;
  height: 50vh;
  padding: 1em;
  overflow-y: auto;
  max-width: 350px;
  margin: 0 auto 2em auto;
  box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, 0.3);
}
.message {
  width: 45%;
  border-radius: 10px;
  padding: 0.5em;
  /*   margin-bottom: .5em; */
  font-size: 0.8em;
}
.message-out {
  background: #407fff;
  color: white;
  margin-left: 50%;
}
.message-in {
  background: #f1f0f0;
  color: black;
}
.chat-inputs {
  display: flex;
  justify-content: space-between;
}
#person2-input {
  padding: 0.5em;
}
</style>
