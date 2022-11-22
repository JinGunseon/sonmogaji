const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    isMetamaskSupported: false,
    account: null,
  },
  mutations: {
    SET_IS_LOGGED_IN: (state, isLoggedIn) => {
      state.isLoggedIn = isLoggedIn;
    },
    SET_ACCOUNT: (state, account) => {
      state.account = account;
    },
    SET_IS_METAMASK_SUPPORTED: (state, isMetamaskSupported) => {
      state.isMetamaskSupported = isMetamaskSupported;
    },
  },
  actions: {
    logout({ commit }) {
      commit("SET_IS_LOGGED_IN", false);
      commit("SET_ACCOUNT", null);
    },
    async login({ commit }) {
      // 00. 지갑 연결하기
      const accounts = await window.ethereum.request({ method: "eth_requestAccounts" });
      commit("SET_IS_LOGGED_IN", true);
      commit("SET_ACCOUNT", accounts[0]);

      // 01. 네트워크 전환 시도하기
      /*
      try {
        await window.ethereum.request({
          method: "wallet_switchEthereumChain",
          params: [{ chainId: "0x539" }],
        });
      }
      */

      // 02. 에러 발생 시, 네트워크 생성하기
      /*
      catch (error) {
        if (error.code === 4902) {
          try {
            const result = await window.ethereum.request({
              method: "wallet_addEthereumChain",
              params: [
                {
                  chainId: "0x539",
                  chainName: "Ganache",
                  nativeCurrency: {
                    name: "ETH",
                    symbol: "eth",
                    decimals: 18,
                  },
                  rpcUrls: ["https://localhost:8545/"],
                },
              ],
            });
          } catch (addError) {
            console.error("addError : " + addError);
          }
        }
        console.error("error : " + error);
      }
      */

      // 03. 홈 화면으로 이동하기
      location.replace("/");
    },
    checkMetamaskSupported({ commit }) {
      commit("SET_IS_METAMASK_SUPPORTED", typeof window.ethereum !== "undefined");
    },
    // async checkMetamaskSupported() {
    //   this.isMetamaskSupported = typeof window.ethereum !== "undefined";
    // },
    // async connectWallet() {
    //   const accounts = await window.ethereum.request({ method: "eth_requestAccounts" });
    //   this.address = accounts[0];
    //   this.isLoggedIn = true;
    //   console.log(this.isLoggedIn);
    // },
    // async switchNetwork() {
    //   try {
    //     await window.ethereum.request({
    //       method: "wallet_switchEthereumChain",
    //       params: [{ chainId: "0x539" }],
    //     });
    //   } catch (error) {
    //     if (error.code === 4902) {
    //       try {
    //         const result = await window.ethereum.request({
    //           method: "wallet_addEthereumChain",
    //           params: [
    //             {
    //               chainId: "0x539",
    //               chainName: "Ganache",
    //               nativeCurrency: {
    //                 name: "ETH",
    //                 symbol: "eth",
    //                 decimals: 18,
    //               },
    //               rpcUrls: ["https://localhost:8545/"],
    //             },
    //           ],
    //         });
    //       } catch (addError) {
    //         console.error("addError : " + addError);
    //       }
    //     }
    //     console.error("error : " + error);
    //   }
    // },
  },
};

export default userStore;
