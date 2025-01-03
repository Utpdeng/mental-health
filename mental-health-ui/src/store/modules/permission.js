export default {
  namespaced: true,
  state: {
    isLoggedIn: false,
    user: null
  },
  mutations: {
    SET_AUTH(state, { isLoggedIn, user }) {
      state.isLoggedIn = isLoggedIn
      state.user = user
    }
  },
  actions: {
    login({ commit }, userData) {
      commit('SET_AUTH', { isLoggedIn: true, user: userData })
    },
    logout({ commit }) {
      commit('SET_AUTH', { isLoggedIn: false, user: null })
    }
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    userRole: state => state.user?.role
  }
} 