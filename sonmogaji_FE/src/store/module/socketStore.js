import Stomp from "stompjs";
import SockJS from "sockjs-client";
import routes from "../../router/index.js";
const socketStore = {
  state: {
    isHost: false,
    roomCode: "",
    senderNickName: "",
    roll: "",
    socket: {},
    stomp: {},
    chatmessages: [],
    agree1: 0,
    agree2: 0,
    agree3: 0,
    agree4: 0,
    agree5: 0,
    agree6: 0,
    agree7: 0,
    sign: {},
    title: "",
    content: "",
    secret: "A",
    expire: "",
    memoryImage: "",
    memorySecret: "A",
    memorandumPreview: "",
    memorandumState: {
      agree: [],
      sign: [],
    },
    memorandumFinal:{},
    memorandumFinalImage:"",
  },
  getters: {
    getRoomCode(state) {
      return state.roomCode;
    },
    getSecret(state) {
      return state.secret;
    },
    getExpire(state) {
      return state.expire;
    },
    getMemoryImage(state) {
      return state.memoryImage;
    },
    getMemorySecret(state) {
      return state.memorySecret;
    },
    getIsHost(state) {
      return state.isHost;
    },
    getSenderNickName(state) {
      return state.senderNickName;
    },
    getTitle(state) {
      return state.title;
    },
    getContent(state) {
      return state.content;
    },
    getRoll(state) {
      return state.roll;
    },
    getStomp(state) {
      return state.stomp;
    },
    getSocket(state) {
      return state.socket;
    },
    getChatmessages(state) {
      return state.chatmessages;
    },
    getAgree1(state) {
      return state.agree1;
    },
    getAgree2(state) {
      return state.agree2;
    },
    getAgree3(state) {
      return state.agree3;
    },
    getAgree4(state) {
      return state.agree4;
    },
    getAgree5(state) {
      return state.agree5;
    },
    getAgree6(state) {
      return state.agree6;
    },
    getSign(state) {
      return state.sign;
    },
    getMemorandumPreview(state) {
      return state.memorandumPreview;
    },
    getMemorandumState(state) {
      return state.memorandumState;
    },
    getMemorandumFinal(state){
      return state.memorandumFinal
    },
    getMemorandumFinalImage(state){
      return state.memorandumFinalImage
    }
  },
  mutations: {
    setRoomCode(state, roomCode) {
      state.roomCode = roomCode;
    },
    setIsHost(state, isHost) {
      state.isHost = isHost;
    },
    setSenderNickName(state, senderNickName) {
      state.senderNickName = senderNickName;
    },
    setRoll(state, roll) {
      state.roll = roll;
    },
    setTitle(state, title) {
      state.title = title;
    },
    setContent(state, content) {
      state.content = content;
    },
    setSecret(state, secret) {
      state.secret = secret;
    },
    setExpire(state, expire) {
      state.expire = expire;
    },
    setMemoryImage(state, memoryImage) {
      state.memoryImage = memoryImage;
    },
    setMemorySecret(state, memorySecret) {
      state.memorySecret = memorySecret;
    },
    setStomp(state, stomp) {
      state.stomp = stomp;
    },
    setSocket(state, socket) {
      state.socket = socket;
    },
    setChatmessages(state, messages) {
      state.chatmessages = messages;
    },
    receiveChatmessages(state, message) {
      state.chatmessages.push(message);
    },
    setAgree1(state, count) {
      state.agree1 = count;
    },
    setAgree2(state, count) {
      state.agree2 = count;
    },
    setAgree3(state, count) {
      state.agree3 = count;
    },
    setAgree4(state, count) {
      state.agree4 = count;
    },
    setAgree5(state, count) {
      state.agree5 = count;
    },
    setAgree6(state, count) {
      state.agree6 = count;
    },
    setSign(state, sign) {
      state.sign = sign;
    },
    setMemorandumState(state, memorandumstate) {
      state.memorandumState = memorandumstate;
    },
    setMemorandumPreview(state, memorandumPreview) {
      state.memorandumPreview = memorandumPreview;
    },
    clearState(state) {
      state.roomCode = "";
      state.senderNickName = "";
      state.roll = "";
      state.socket = "";
      state.stomp = "";
      state.chatmessages = "";
      state.agree1 = 0;
      state.agree2 = 0;
      state.agree3 = 0;
      state.agree4 = 0;
      state.agree5 = 0;
      state.agree6 = 0;
      state.sign = {};
      state.title = "";
      state.content = "";
      state.secret = "A";
      state.expire = "";
      state.memoryImage = "";
      state.memorySecret = "A";
      state.memorandumState = { agree: [], sign: [] };
      state.memorandumPreview = "";
    },
    setMemorandumFinal(state, memorandumFinal) {
      state.memorandumFinal = memorandumFinal;
    },
    setMemorandumFinalImage(state, memorandumFinalImage) {
      state.memorandumFinalImage = memorandumFinalImage;
    },
  },
  actions: {
    enterNickName({ commit, state }, senderNickName) {
      commit("setSenderNickName", senderNickName);
    },
    enterRoll({ commit, state }, roll) {
      commit("setRoll", roll);
    },
    enterRoomCode({ commit, state }, roomCode) {
      commit("setRoomCode", roomCode);
    },
    changeAgree1({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[0]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree1", count);
    },
    changeAgree2({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[1]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree2", count);
    },
    changeAgree3({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[2]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree3", count);
    },
    changeAgree4({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[3]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree4", count);
    },
    changeAgree5({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[4]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree5", count);
    },
    changeAgree6({ commit, state }) {
      let count = 0;
      Object.entries(state.memorandumState.agree[5]).forEach(([key, value]) => {
        if (value) {
          count++;
        }
      });
      commit("setAgree6", count);
    },
    changeSign({ commit, state }) {
      let signstate = [];
      Object.entries(state.memorandumState.sign).forEach(([key, value]) => {
        // do something with key and val
        if (value != "") {
          let t = new Object();
          t.nickname = key;
          t.state = true;
          signstate.push(t);
        }
      });
      commit("setSign", signstate);
    },
    roomStart({ commit, state }) {
      state.stomp.send(
        "/pub/memorandum/start",
        {},
        JSON.stringify({
          roomCode: state.roomCode,
        })
      );
    },
    roomVote({ commit, state }, index) {
      state.stomp.send(
        "/pub/memorandum/vote",
        {},
        JSON.stringify({
          roomCode: state.roomCode,
          message: index - 1,
        })
      );
    },
    roomVoteCancel({ commit, state }, index) {
      state.stomp.send(
        "/pub/memorandum/votecancel",
        {},
        JSON.stringify({
          roomCode: state.roomCode,
          message: index - 1,
        })
      );
    },
    roomNext({ commit, state }, index) {
      state.stomp.send(
        "/pub/memorandum/next",
        {},
        JSON.stringify({
          roomCode: state.roomCode,
          message: index - 1,
        })
      );
    },
    chat({ commit, state }, message) {
      console.log(state.senderNickName + ": " + message);
      state.stomp.send(
        "/pub/chat/message",
        {},
        JSON.stringify({
          sender: state.senderNickName,
          message: message,
          roomCode: state.roomCode,
        })
      );
    },
    clearChat({ commit }) {
      commit("setChatmessages", []);
    },
    sendContent({ commit, state }) {
      state.stomp.send(
        "/pub/memorandum/content",
        {},
        JSON.stringify({
          title: state.title,
          content: state.content,
          roomCode: state.roomCode,
        })
      );
    },
    sendSecret({ commit, state }) {
      let secret = true;
      if (state.secret == "B") {
        secret = false;
      }
      state.stomp.send(
        "/pub/memorandum/secret",
        {},
        JSON.stringify({
          secret: secret,
          roomCode: state.roomCode,
        })
      );
    },
    sendMemoryImage({ commit, state }) {
      state.stomp.send(
        "/pub/memorandum/memoryimage",
        {},
        JSON.stringify({
          memoryImage: state.memoryImage,
          roomCode: state.roomCode,
        })
      );
    },
    sendMemorySecret({ commit, state }) {
      let MemorySecret = true;
      if (state.memorySecret == "B") {
        MemorySecret = false;
      }
      state.stomp.send(
        "/pub/memorandum/memorysecret",
        {},
        JSON.stringify({
          memorySecret: MemorySecret,
          roomCode: state.roomCode,
        })
      );
    },
    sendDate({ commit, state }) {
      state.stomp.send(
        "/pub/memorandum/expire",
        {},
        JSON.stringify({
          expire: state.expire,
          roomCode: state.roomCode,
        })
      );
    },
    sendSign({ commit, state }, sign) {
      state.stomp.send(
        "/pub/memorandum/sign",
        {},
        JSON.stringify({
          sign: sign,
          roomCode: state.roomCode,
          senderNickName: state.senderNickName,
        })
      );
    },
    sendRequirePreview({ commit, state }) {
      state.stomp.send(
        "/pub/memorandum/preview",
        {},
        JSON.stringify({
          roomCode: state.roomCode,
        })
      );
    },
    sendTxHash({ commit, state },txhash){
      state.stomp.send(
        "/pub/memorandum/txhash",
        {},
        JSON.stringify({
          roomCode:state.roomCode,
          txHash: txhash,
        })
      );
    },

    stompConnect({ commit, state, dispatch }) {
      // const serverURL = "https://j7a308.p.ssafy.io/room";
      const serverURL = "/room";
      commit("setSocket", new SockJS(serverURL));
      console.log(state.socket);
      console.log("param nick : " + state.senderNickName);
      commit("setStomp", Stomp.over(state.socket));
      console.log(state.stomp);
      state.stomp.connect(
        {},
        () => {
          state.stomp.connected = true;
          commit("setStomp", state.stomp);
          commit("setChatmessages", []);
          console.log(state.chatmessages);
          console.log("소켓 연결 성공" + state.senderNickName + " " + state.roomCode);

          state.stomp.subscribe("/sub/memorandum/join/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            console.log(content);
            if (content.message != "ok") {
              dispatch("stompDisconnect");
              
              // this.stompDisconnect();
            }
            commit("setSign", content.signState);
              console.log(state.sign)
          });
          state.stomp.subscribe("/sub/memorandum/start/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            console.log(content);
            if (content.message == "start") {
              commit("setMemorandumState", content.memorandumState);
              commit("setSign",content.signState)
              dispatch("changeAgree1");
              // dispatch("changeSign");
              routes.push({ name: "sessionMain1" });
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/vote/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "1") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree1");
              // dispatch("changeSign");
            } else if (content.message == "2") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree2");
              // dispatch("changeSign");
            } else if (content.message == "3") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree3");
              // dispatch("changeSign");
            } else if (content.message == "4") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree4");
              // dispatch("changeSign");
            } else if (content.message == "5") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree5");
              // dispatch("changeSign");
            } else if (content.message == "6") {
              commit("setMemorandumState", content.memorandumState);
              dispatch("changeAgree6");
              // dispatch("changeSign");
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/next/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "2") {
              routes.push({ name: "sessionMain2" });
            } else if (content.message == "3") {
              routes.push({ name: "sessionMain3" });
            } else if (content.message == "4") {
              routes.push({ name: "sessionMain4" });
            } else if (content.message == "5") {
              routes.push({ name: "sessionMain5" });
            } else if (content.message == "6") {
              routes.push({ name: "sessionMain6" });
            } else if (content.message == "7") {
              routes.push({ name: "sessionMain7" });
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/content/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            commit("setTitle", content.title);
            commit("setContent", content.content);
            //commit("setMemorandumState",content.memorandumState)
            //commit("receiveChatmessages", { sender: content.sender, message: content.message });
          });
          state.stomp.subscribe("/sub/memorandum/secret/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              if (content.memorandumState.secret) {
                commit("setSecret", "A");
              } else {
                commit("setSecret", "B");
              }
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/memoryimage/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              commit("setMemoryImage", content.memoryImage);
            } else {
              console.log(content.message);
            }
            //commit("setMemorandumState",content.memorandumState)
            //commit("receiveChatmessages", { sender: content.sender, message: content.message });
          });
          state.stomp.subscribe("/sub/memorandum/expire/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              console.log(content.memorandumState.expire);
              commit("setExpire", content.memorandumState.expire);
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/memorysecret/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              if (content.memorySecret) {
                commit("setMemorySecret", "A");
              } else {
                commit("setMemorySecret", "B");
              }
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/sign/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              console.log(content.signState);
              commit("setSign", content.signState);
            } else {
              console.log(content.message);
            }
          });
          state.stomp.subscribe("/sub/memorandum/preview/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              commit("setMemorandumPreview", "data:image/png;base64,"+ content.preview);
              commit("setMemorandumFinal",content.tdto);
              console.log(content.tdto)
            } else {
              console.log(content.message);
            }

            //commit("setMemorandumState",content.memorandumState)
            //commit("receiveChatmessages", { sender: content.sender, message: content.message });
          });

          state.stomp.subscribe("/sub/memorandum/txhash/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            if (content.message == "ok") {
              //완성된 각서이미지 받아옴
              commit("setMemorandumFinalImage","data:image/png;base64,"+content.memorandumFinal)
            } else {
              console.log(content.message);
            }

           });

          state.stomp.subscribe("/sub/chat/message/" + state.roomCode, (res) => {
            var content = JSON.parse(res.body);
            commit("receiveChatmessages", { sender: content.sender, message: content.message });
          });
          

          
          state.stomp.send(
            "/pub/memorandum/join",
            {},
            JSON.stringify({
              roomCode: state.roomCode,
              senderNickName: state.senderNickName,
            })
          );
        },
        (error) => {
          // 소켓 연결 실패
          // 소켓 연결 끊김
          console.log("소켓 연결 실패", error);
        }
      );
    },
    stompDisconnect({ commit, state }) {
      state.stomp.disconnect();
      console.log("disconnect");
      commit("setStomp", state.stomp);
    },
  },
};
export default socketStore;