import auth from '~/utils/auth'
import jwt from 'jwt-decode'
import * as Cookies from 'js-cookie'

export const state = () => {
  return {
    authenticated: false,
    name: '',
    authority: '',
    error: ''
  }
}

export const getters = {
  authenticated: (state) => {
    return state.authenticated
  },
  name: (state) => {
    return state.name
  },
  authority: (state) => {
    return state.authority
  },
  error: (state) => {
    return state.error
  }
}

export const mutations = {
  authen: (state, payload) => {
    state.authenticated = payload.status
    state.name = payload.name
    state.authority = payload.authority
    state.error = payload.error
  },
  authority: (state, payload) => {
    state.authority = payload.authority
  }
}

export const actions = {
  login: (context, vc) => {
    auth.login(vc).then((data) => {
      Cookies.set('AT', data.data.access_token, { expires: 1, secure: false })
      Cookies.set('RT', data.data.refresh_token, { expires: 1, secure: false })
      if (jwt(data.data.access_token).authorities.length > 1) {
        context.commit('authen', { status: true, error: '', name: jwt(data.data.access_token).name, authority: jwt(data.data.access_token).authorities })
      } else {
        Cookies.set('AU', jwt(data.data.access_token).authorities[0], { expires: 1, secure: false })
        context.commit('authen', { status: true, error: '', name: jwt(data.data.access_token).name, authority: jwt(data.data.access_token).authorities[0] })
        vc.$router.push('/checklist')
      }
    }, (data) => {
      if (data.response.status === 500) {
        context.commit('authen', { status: false, error: 'เกิดความผิดพลาดจากการเชื่อมต่อ Mirtphol API' })
      } else if (data.response.status === 401) {
        context.commit('authen', { status: false, error: 'Username หรือ Password ไม่ถูกต้อง' })
      } else {
        context.commit('authen', { status: false, error: 'ไม่มีสิทธิ์ในระบบ' })
      }
    })
  },
  logout: (context, vc) => {
    Cookies.remove('AT', { secure: false })
    Cookies.remove('RT', { secure: false })
    context.commit('authen', { status: false, error: '' })
    vc.$router.replace('/checklist/login')
  },
  authority: (context, vc) => {
    Cookies.set('AU', vc.authority, { expires: 1, secure: false })
    context.commit('authority', { authority: vc.authority })
    vc.vc.$router.push('/checklist')
  },
  clearerror: (context, vc) => {
    context.commit('authen', { status: false, error: '' })
  },
  checkAuth: (context, router) => {
    let rt = Cookies.get('AT')
    if (rt) {
      auth.checkAuth(rt).then((data) => {
        context.commit('authen', { status: true, error: '', name: jwt(rt).name, authority: jwt(rt).authorities[0] })
      }, (data) => {
        context.commit('authen', { status: false, error: 'Session ของคุณหมดอายุ' })
        router.replace('/')
      })
    }
  }
}

