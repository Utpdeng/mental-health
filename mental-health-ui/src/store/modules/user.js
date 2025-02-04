import { login, getUserInfo } from '@/api/user'

const state = {
  token: localStorage.getItem('token'),
  userId: localStorage.getItem('userId'),
  username: '',
  nickname: '',
  role: '',
  avatar: '',
  email: '',
  bio: '',
  gender: '',
  birthdate: '',
  location: '',
  preferences: '',
  status: ''
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USER_ID: (state, userId) => {
    state.userId = userId
  },
  SET_USER_INFO: (state, userInfo) => {
    state.username = userInfo.username
    state.nickname = userInfo.nickname
    state.role = userInfo.role
    state.avatar = userInfo.avatarUrl
    state.email = userInfo.email
    state.bio = userInfo.bio
    state.gender = userInfo.gender
    state.birthdate = userInfo.birthdate
    state.location = userInfo.location
    state.preferences = userInfo.preferences
    state.status = userInfo.status
  },
  CLEAR_USER_INFO: (state) => {
    state.token = ''
    state.userId = ''
    state.username = ''
    state.nickname = ''
    state.role = ''
    state.avatar = ''
    state.email = ''
    state.bio = ''
    state.gender = ''
    state.birthdate = ''
    state.location = ''
    state.preferences = ''
    state.status = ''
  }
}

const actions = {
  // 用户登录
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login(userInfo).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_USER_ID', data.userId)
        
        localStorage.setItem('token', data.token)
        localStorage.setItem('userId', data.userId)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getUserInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getUserInfo(state.userId).then(response => {
        const { data } = response
        commit('SET_USER_INFO', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 退出登录
  logout({ commit }) {
    return new Promise(resolve => {
      commit('CLEAR_USER_INFO')
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
} 