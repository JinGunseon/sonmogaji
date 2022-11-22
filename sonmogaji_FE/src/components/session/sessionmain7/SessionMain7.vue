<template>
  <b-container>
    <b-row align-h="center">
      <div class="content" id="imagezone">
        <img id="memorandum" :src="this.getMemorandumFinalImage" />
      </div>
    </b-row>
    <br />

    <b-row align-h="center"> <b-col cols="4" id="download"><b-button @click="downloadimage"> 이미지 저장</b-button></b-col></b-row>
  </b-container>
</template>

<script>
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
import Web3 from "web3";
export default {
  name: "SessionMain7",
  components: {},
  created() {
    console.log("sessionMain7");
  },
  mounted() {
    //트랜잭션 전송
    this.sendTransaction();
  },

  computed: {
    ...mapState(["memorandumPreview"]),
    ...mapState("userStore", ["account"]),
    ...mapGetters(["getMemorandumFinal", "getMemorandumFinalImage", "getIsHost"]),
  },
  data() {
    return {
      web3: new Web3("https://j7a3081.p.ssafy.io"),
    };
  },
  methods: {
    ...mapActions(["sendTxHash"]),
    toMain7() {
      this.roomNext(7);
    },
    vote6() {
      this.roomVote(6);
    },
    downloadimage() {
      var a = document.createElement("a"); //Create <a>
      a.href = this.getMemorandumFinalImage; //Image Base64 Goes here
      a.download = "Image.png"; //File name Here
      a.click(); //Downloaded file
    },
    sendTransaction() {
      // /* */

      if (this.getIsHost) {
        let types = ["string"];
        let gasPrice = (2000000000).toString(16);
        let mFinal = this.getMemorandumFinal;
        let signs = mFinal.signees;
        let signees = "";

        for (var key of Object.keys(signs)) {
          signees += key + " ";
        }

        let issecret = mFinal.txIsSecret ? "true" : "false";
        let imageissecret = mFinal.imageIsSecret ? "true" : "false";
        let values = [
          mFinal.txTitle +
            " " +
            mFinal.txContent +
            " " +
            mFinal.imageTitle +
            " " +
            mFinal.imageUrl +
            " " +
            imageissecret +
            " " +
            issecret +
            " " +
            mFinal.txCreateDate +
            " " +
            mFinal.txExpDate +
            " " +
            signees,
        ];
        console.log(values);
        const abi = this.web3.eth.abi.encodeParameters(types, values);
        console.log(abi);
        const txParams = {
          nonce: "0x00", // ignored by MetaMask
          gasPrice: gasPrice, // customizable by user during MetaMask confirmation.
          gas: "0xC350", // customizable by user during MetaMask confirmation.
          to: this.account, // Required except during contract publications.
          from: this.account, // must match user's active address.
          value: "0x00", // Only required to send ether to the recipient from the initiating external account.
          data: abi, // Optional, but used for defining smart contract creation and interaction.
          chainId: "0x569", // Used to prevent transaction reuse across blockchains. Auto-filled by MetaMask.
        };

        // web3.eth.sendRawTransaction(transactionParameters).then(console.log);
        window.ethereum
          .request({
            method: "eth_sendTransaction",
            params: [txParams],
          })
          .then((txHash) => {
            this.sendTxHash(txHash);
          });
      }
    },
  },
};
</script>

<style>
#imagezone {
  overflow: scroll;
  height: 50vh;
  text-align: center;
}
#download{
  text-align: center;
}
</style>
