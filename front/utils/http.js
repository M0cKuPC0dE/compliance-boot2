import axios from 'axios'
import httpr from 'http'
import https from 'https'

var http = axios.create({
  baseURL: process.env.baseUrl,
  httpAgent: new httpr.Agent({ keepAlive: true }),
  httpsAgent: new https.Agent({
    keepAlive: true,
    rejectUnauthorized: false
  })
})

export default http
