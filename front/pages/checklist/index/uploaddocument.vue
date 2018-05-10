<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="form-group p-t-20" v-if="!files.file">
        <div class="col-md-12 text-center is-fileinput">
          <span class="btn btn-info btn-file">
            <i class="zmdi zmdi-swap-vertical"></i>
            {{ $t('buttons.upload.file') }}
            <input style="display:" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,.pdf,.ppf,.doc,.docx" v-on:change="onBrowse(baseUrl+'/api/fileupload',$event)">
          </span>
        </div>
      </div>
      <br>
      <a class="form-group p-t-20" href="https://compliance.mitrphol.com/public/download/1" id="download">
        <div class="col-md-12 text-center is-fileinput">
          <span class="btn btn-info btn-file">
            <i class="zmdi zmdi-swap-vertical"></i>
            download
          </span>
        </div>
      </a>

      <div class="form-group" v-if="files.file">
        <div class="col-md-12">
          <progressUpload :props="files.file" :url="files.url" :fileid="$route.params.id"></progressUpload>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProgressUpload from '~/components/ProgressUpload'
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  components: {
    ProgressUpload
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl,
      files: {}
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(index) {
      let legalFile = {}
      legalFile['name'] = this.files.file.name
      http
        .post('/api/file/', legalFile, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          var obj = {}
          obj[index] = undefined
          this.$set(this, 'files', obj)

          this.$router.push('/checklist/masterdata')
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
      this.$set(this, 'files', obj)
    }
  }
}
</script>
