
import axios from 'axios'
import { API_BASE_URL } from '@/config';
import { instance } from '@/api';


const apiStore = {
    namespaced: true,
    state: {
      // axios:{}
      myTxList :[],
      txList : [],
    },
  
    getters: {
        getAxios(state){
            return state.axios;
        },

        getMyTxList(state) {
            return state.myTxList;
        }
    },
  
    mutations: {
        // setAxios(state, axios){
            // state.axios=axios;
        // },
      MY_TX_All_LIST(state, myTx) {
        state.myTxList = myTx;
      },
      TX_All_LIST(state, tx) {
        state.txList = tx;
      }

    },
  
    actions: {
      //axios api 요청들

      // 나의 각서 목록 조회
      readMyTxList ({ commit }, info) {
        const params = {
          page: info.page
        }
        instance({
          url: API_BASE_URL + '/transaction/myTx/' + info.userAddress,
          method: 'GET',
          params
        })
          .then((res) => {
            console.log(res.data)
            commit('MY_TX_All_LIST', res.data)
            // commit('MY_FEED_All_LIST_COUNT', res.data.userBoardNum)
            // commit('MY_FEED_PAGINATION', res.data.userBoardNum)
          })
          .catch((err) => {
            console.log('에러')
            console.log(err)
          })
      },
      // 각서 목록 조회
      readTxList({commit}, info) {
        const params = {
          page: info.page
        }
        instance({
          url: API_BASE_URL + '/transaction/',
          method: 'GET',
          params
        })
        .then((res) => {
          console.log(res.data)
          commit('TX_All_LIST', res.data)
          // commit('MY_FEED_All_LIST_COUNT', res.data.userBoardNum)
          // commit('MY_FEED_PAGINATION', res.data.userBoardNum)
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
      },
      
      
    },
  };
  
  export default apiStore;