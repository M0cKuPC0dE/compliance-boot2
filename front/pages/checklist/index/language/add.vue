<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.languageupload') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('menu.dashboard') }}</li>
            <li class="active">{{ $t('menu.languageupload') }}</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">{{ $t('menu.languageupload') }}</h3>
              </div>
            </div>

            <form class="form-horizontal" v-on:submit.prevent="onSubmit">
              <div class="form-group">
                <label class="col-md-12">{{ $t('language.code') }}</label>
                <div class="col-md-6">
                  <input type="text" class="form-control" style="text-transform:uppercase" placeholder="รหัส" v-model="locale.code" required>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-12">{{ $t('language.name') }}</label>
                <div class="col-md-12">
                  <input type="text" class="form-control" placeholder="ชื่อ" v-model="locale.fullName">
                </div>
              </div>

              <div class="form-group" v-if="!files.file">
                <div class="col-md-12  is-fileinput">
                  <span class="btn btn-info btn-file m-r-10">
                    <i class="zmdi zmdi-swap-vertical"></i>
                    {{ $t('buttons.upload.template') }}
                    <input required="" style="display:" type="file" accept="application/json" v-on:change="onBrowse(baseUrl+'/api/localeupload/' + locale.code.toUpperCase(),$event)">
                  </span>
                  <span>{{displayname}}</span>
                </div>
              </div>
              <div class="form-actions text-center">
                <button type="submit" class="btn btn-success m-r-10">
                  <i class="fa fa-check"></i> {{ $t('buttons.save') }}</button>
                <nuxt-link to="/checklist/language" class="btn btn-info">
                  <i class="fa fa-chevron-left"></i> {{ $t('buttons.back') }}
                </nuxt-link>
              </div>
            </form>
            <div class="form-group" v-if="files.file">
              <div class="col-md-6">
                <progressUpload :props="files.file" :url="files.url" :fileid="locale.code"></progressUpload>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import http from '~/utils/http'
import cookie from '~/utils/cookie'
import ProgressUpload from '~/components/ProgressUpload'

export default {
  components: {
    ProgressUpload
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl,
      displayname: '',
      error: '',
      files: {},
      objupload: {},
      locale: {
        code: '',
        fullName: ''
      }
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(index) {
      var obj = {}
      obj[index] = undefined
      this.$set(this, 'files', obj)
      var self = this
      self.locale.code = self.locale.code.toUpperCase()
      http
        .post('/api/locales', self.locale, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$router.push({ path: '/checklist/language' })
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    })
  },
  methods: {
    onBrowse: function(url, e) {
      var obj = {}
      obj['file'] = e.target.files[0]
      obj['url'] = url
      this.objupload = obj
      this.displayname = e.target.files[0].name
    },
    onSubmit: function() {
      this.$set(this, 'files', this.objupload)
    }
  }
}
</script>
