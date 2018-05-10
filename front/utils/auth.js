/* global FormData */

const OAUTH_TOKEN = '/oauth/token'
const OAUTH_CHECK = '/oauth/check_token?token='
const OAUTH_HASH = 'Y2xpZW50OnNlY3JldA=='
const DEV_LOGIN_URL = process.env.baseUrl

import axios from 'axios'

export default {
  login: function (context) {
    let form = new FormData()
    form.append('grant_type', 'password')
    form.append('username', context.credentials.username)
    form.append('password', context.credentials.password)

    let config = {
      headers: {
        'Authorization': 'Basic ' + OAUTH_HASH
      }
    }

    return axios.post(OAUTH_TOKEN, form, {
      baseURL: DEV_LOGIN_URL,
      headers: config.headers
    })
  },
  checkAuth: function (at) {
    return axios.get(DEV_LOGIN_URL + OAUTH_CHECK + at, {
      headers: {
        Authorization: 'Basic ' + OAUTH_HASH
      }
    })
  }
}

