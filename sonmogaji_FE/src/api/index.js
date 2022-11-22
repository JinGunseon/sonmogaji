import axios from 'axios'
// import store from '@/store/index.js'

export const instance = axios.create(
)

instance.interceptors.request.use(
  (config) => {
    // config.headers.Authorization = store.state.accessToken
    // config.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    config.headers.Authorization = localStorage.getItem('accessToken')
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)