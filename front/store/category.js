export const state = () => {
  return {
    category: {},
    compliance: {}
  }
}

export const getters = {
  category: (state) => {
    return state.category
  },
  compliance: (state) => {
    return state.compliance
  }
}

export const mutations = {
  setcategory: (state, payload) => {
    state.category = payload.category
  },
  setcompliance: (state, payload) => {
    state.compliance = payload.compliance
  }
}

export const actions = {
  setcategory: (context, vc) => {
    context.commit('setcategory', { category: vc })
  },
  setcompliance: (context, vc) => {
    context.commit('setcompliance', { compliance: vc })
  }
}
