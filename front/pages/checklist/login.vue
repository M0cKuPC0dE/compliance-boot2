<template>
  <div>
    <section id="wrapper" class="new-login-register">
      <div class="lg-info-panel"></div>
      <div class="new-login-box">
        <div class="white-box">
          <h3 class="box-title m-b-0">Sign In to Compliance System</h3>
          <small>Enter your details below</small>
          <form class="form-horizontal new-lg-form" role="form" v-on:submit.prevent="onSubmit" autocomplete="off">
            <div id="errmsg" class="alert alert-danger alert-light alert-dismissible" role="alert" v-if="error != ''">
              <button type="button" class="close" aria-label="Close" v-on:click="closeAlert()">
                <i class="zmdi zmdi-close"></i>
              </button>
              <strong>
                <i class="zmdi zmdi-close-circle"></i> Error!
              </strong>
              {{error}}
            </div>

            <div class="form-group  m-t-20" v-if="!$route.query.key">
              <div class="col-xs-12">
                <label>Username</label>
                <input class="form-control" type="text" required="" placeholder="Username" v-model="credentials.username">
              </div>
            </div>
            <div class="form-group" v-if="!$route.query.key">
              <div class="col-xs-12">
                <label>Password</label>
                <input class="form-control" type="password" required="" placeholder="Password" v-model="credentials.password">
              </div>
            </div>
            <div class="form-group text-center m-t-20" v-if="!$route.query.key">
              <div class="col-xs-12">
                <button class="btn btn-info btn-lg btn-block btn-rounded text-uppercase waves-effect waves-light" type="submit">
                  Log In
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>

    <div class="modal fade" id="myModal" role="dialog">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">เลือกสิทธิ์ในการเข้าใช้งาน</h4>
          </div>
          <div class="modal-body">

            <div class="btn-group-vertical btn-block" style="width: 100%;">
              <button type="button" class="btn btn-info btn-block btn-rounded text-uppercase waves-effect waves-light" style="margin-bottom:10px;" :key="auth" v-for="auth in authority" v-on:click="onSelectAuthority(auth)">{{auth}}</button>
            </div>

          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
/* global $ */
import { mapGetters } from 'vuex'

export default {
  layout: 'login',
  data: function() {
    return {
      credentials: {
        username: '',
        password: ''
      }
    }
  },
  mounted: function() {
    if (this.$route.query.key) {
      this.credentials = { username: this.$route.query.key }
      this.$store.dispatch('auth/login', this)
    }
  },
  methods: {
    onSubmit: function() {
      if (process.BROWSER_BUILD) {
        $(document).ready(function() {
          $('.alert').show()
          if (!$('.alert').is(':visible')) {
            $('button[type="submit"]').prop('disabled', true)
          }
        })
      }
      this.$store.dispatch('auth/login', this)
    },
    closeAlert: function() {
      $('.alert').hide()
      this.$store.dispatch('auth/clearerror', this)
    },
    onSelectAuthority: function(authority) {
      $('#myModal').modal('hide')
      this.$store.dispatch('auth/authority', { vc: this, authority: authority })
    }
  },
  watch: {
    error: function() {
      $('button[type="submit"]').prop('disabled', false)
    },
    authority: function(val) {
      if (Array.isArray(val) && val.length > 1) {
        $('#myModal').modal()
      }
    }
  },
  computed: mapGetters({
    error: 'auth/error',
    authority: 'auth/authority'
  })
}
</script>
