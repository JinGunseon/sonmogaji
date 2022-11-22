<template>
  <div id="image-upload" class="d-flex justify-content-center">
    <div class="d-inline text-center">
      <h1 class="display-4 fw-semibold my-4">각서 이미지를 검증합니다.</h1>
      <Dropzone
        id="dropzone"
        ref="dropzone"
        class="w-50 mx-auto rounded"
        :options="dropOptions"
        :useCustomSlot="true"
        style="border: 0px; background-color: transparent"
        @vdropzone-complete="verifyResponse"
      >
        <img src="@/assets/icons/image-upload.png" class="w-100" />
      </Dropzone>
      <div>
        <p class="h4 my-3" style="color: #f16b51">
          * 주의사항 : 화질 변경 시 트랜잭션 해시가 인식 불가능 할 수도 있습니다.
        </p>
        <p class="h3 fw-light mt-3">
          손모가지가 발급한 각서 원본 이미지를 업로드하세요!<br />
          블록체인 상에 존재하는 지 확인할 수 있습니다.
        </p>
      </div>
    </div>
    <b-modal id="modal" ref="modal" :title="modalData.title" @hidden="hideModal()">
      <div v-if="modalData.isSuccessed == true">
        <div class="text-center mb-4">
          <img src="@/assets/icons/success.png" />
        </div>
        <div
          class="w-75 mx-auto mb-2 bg-cyan rounded text-white text-center text-lg"
          style="font-family: 'Prentdard' sans-serif"
        >
          해당 각서는 <b>블록체인</b>에 보관된, <br />
          꼭 지켜져야 할 <b>약속</b>이 맞습니다.
        </div>
        <div>
          <img :src="modalData.imageFile" alt="" class="w-100" />
        </div>
        <div>
          <ul>
            <li><b>블록체인 : </b>{{ modalData.blockchain }}</li>
            <li><b>해시 : </b>{{ modalData.hash }}</li>
            <li><b>생성일 : </b>{{ modalData.createDate }}</li>
          </ul>
        </div>
      </div>
      <div v-else>
        <div class="text-center mb-4">
          <img src="@/assets/icons/fail.png" />
        </div>
        <div
          class="w-75 mx-auto mb-2 rounded text-white text-center text-lg"
          style="background-color: #777777"
        >
          이 각서에 대해 아는 바가 없습니다.
        </div>
        <ul>
          <li>메신저, 이메일을 거쳐 <span style="color: #f16b51">변형</span>된 경우</li>
          <li>누군가 고의로 내용을 <span style="color: #f16b51">조작</span>한 경우</li>
          <li>손모가지에서 다운로드한 각서가 아닌 경우</li>
        </ul>
      </div>
    </b-modal>
  </div>
</template>
<script>
import { mapState } from "vuex";
import Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
import { getMemoryImage } from "@/api/index.js";

const Web3 = require("web3");
const rpcURL = "https://j7a3081.p.ssafy.io";
const web3 = new Web3(rpcURL);
const userStore = "userStore";

const fake = {
  txAddress: "sdfsdhjflk",
  txTitle: "소고기 각서",
  txContent: "취직한 사람 소고기 사세요",
  imageTitle: "추억사진",
  imageUrl: "s3.image",
  imageIsSecret: "false",
  txIsSecret: "false",
  txCreateDate: "null",
  txExpDate: "null",
  txNftUrl: "null",
  signees: "abc",
};

export default {
  name: "ImageUpload",
  components: {
    Dropzone,
  },
  computed: {
    ...mapState(userStore, ["account"]),
  },
  data() {
    return {
      dropOptions: {
        url: "/api/transaction/verify", // 파일을 업로드할 서버 주소 url.
        maxFiles: 1, // 업로드 파일수
        acceptedFiles: ".jpeg,.jpg,.png,.gif,.JPEG,.JPG,.PNG,.GIF", // 이미지 파일 포맷만 허용
        addRemoveLinks: true,
        dictRemoveFile: "삭제",
      },
      modalData: {
        isSuccessed: true,
        imageFile: null,
        blockchain: "Hyperledger Besu",
        createDate: "",
        hash: "",
      },
    };
  },
  methods: {
    truncate(str, maxlength) {
      return str.length > maxlength ? str.slice(0, maxlength - 1) + "…" : str;
    },
    showModal(hash) {
      // hash 값으로 모달에 띄울 데이터를 받아온다.
      this.$bvModal.show("modal");
    },
    hideModal() {
      // this.rotateList();
    },
    async verifyResponse(response) {
      // 00. 이미지를 올리고 받은 요청에서 해시값을 꺼낸다.
      // * 테스트용 해시 값
      // 0x341d3b71824ad7bed85d6a810853a810f3b05ac0df8c5a943a8ad9ba4743b0b4
      // 0xd8530ba190785a01b1747162cb35ae3b47239fb5a21056a1c7f6be2952492baa
      // 0x249a3c526602c32553a46fc1955ddba11ca4919feb0e8d956e8eb97712581862
      var txHash = response.xhr.response;

      // 01. 해시값으로 블록체인 네트워크에서 해당 트랜잭션을 가져온다.
      web3.eth
        .getTransaction(txHash)
        .then((data) => {
          // 02. 만약 온 데이터가 없다면, 해당 해시에 대한 트랜잭션이 없는 것으로, false 화면을 출력한다.
          if (data == null) {
            this.modalData.isSuccessed = false;
            this.modalData.title = "인증 실패";
            this.showModal();
          }
          // 03. 만약 온 데이터가 있다면 파싱해서 modal에 들어갈 데이터를 채운다.
          else {
            const decodedData = web3.eth.abi.decodeParameters(["string"], data.input)[0];
            const parsedData = decodedData.split(" ");
            this.modalData.title = "인증 성공";
            this.modalData.isSuccessed = true;
            this.modalData.imageFile = parsedData[3];
            this.modalData.createDate = parsedData[6];
            this.modalData.hash = this.truncate(txHash, 30);
            this.showModal();
          }
        })
        // 04. 만약 에러가 발생했다면, 해당 해시에 대한 트랜잭션이 없는 것으로, false 화면을 출력한다.
        .catch((error) => {
          this.modalData.isSuccessed = false;
          this.modalData.title = "인증 실패";
          this.showModal();
        });
    },
  },
};
</script>
<style scoped>
#image-upload {
  width: 100vw;
  height: 100vh;
}
</style>
