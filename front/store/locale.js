/* eslint space-before-function-paren: "off" */

import * as Cookies from 'js-cookie'

export const state = () => ({
  locale: 'TH',
  locales: [],
  messages: []
})

export const getters = {
  locale: (state) => {
    return state.locale
  },
  locales: (state) => {
    return state.locales
  },
  messages: (state) => {
    return state.messages
  }
}

export const mutations = {
  locale: (state, payload) => {
    state.locale = payload.locale
  },
  locales: (state, payload) => {
    state.locales = payload.locales
  },
  messages: (state, payload) => {
    state.messages = payload.messages
  }
}

export const actions = {
  changeLanguage: (context, locale) => {
    Cookies.set('locale', locale)
    context.commit('locale', { locale: locale })
  }
}
