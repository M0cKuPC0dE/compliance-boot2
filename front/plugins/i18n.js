import Vue from 'vue'
import VueI18n from 'vue-i18n'
import http from '~/utils/http'
Vue.use(VueI18n)

export default async (context) => {
  let locales = await http.get('/public/locales')
  let messages = await http.get('/public/locales/TH')

  await context.store.commit('locale/locales', { locales: locales.data })
  await context.store.commit('locale/messages', { messages: { 'TH': messages.data } })

  context.app.i18n = new VueI18n({
    locale: 'TH',
    fallbackLocale: 'TH',
    messages: context.store.state.locale.messages
  })
}
