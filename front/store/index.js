import jwt from 'jwt-decode'
import cookie from '~/utils/cookie'

export const actions = {
  nuxtServerInit: (context, vc) => {
    if (vc.isServer && vc.req.headers.cookie) {
      var result = cookie(vc)
      var authority = ''
      try {
        if (jwt(result.AT).authorities.includes(result.AU)) {
          authority = result.AU
        } else {
          authority = jwt(result.AT).authorities[0]
        }
        context.commit('auth/authen', { status: true, error: '', name: jwt(result.AT).name, authority: authority })
        context.commit('locale/locale', { locale: result.locale || 'TH' })
      } catch (e) {
        console.log(e.name + ' : store/index.js')
      }
    } else {
      context.commit('auth/authen', { status: false, error: '' })
    }
  }
}
