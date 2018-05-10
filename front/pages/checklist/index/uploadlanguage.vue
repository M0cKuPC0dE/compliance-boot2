<template>
  <div id="page-wrapper">
    <div class="container-fluid">
      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">Upload Language File</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>Dashboard</li>
            <li class="active">Upload Language File</li>
          </ol>
        </div>
      </div>
      <form class="form-horizontal" v-on:submit.prevent="onSubmit">
        <div class="form-group" v-if="!files.file">
          <div class="col-md-6">
            <label>File Name:</label>
            <input class="form-control" type="text" required="" placeholder="File Name" v-model="filename">
            <label v-if="error != ''" class="alert">{{error}}</label>
          </div>
          <div class="col-md-6">
            <input required="" style="display:" type="file" accept="application/json" v-on:change="onBrowse(baseUrl+'/api/localeupload/' + filename,$event)">
          </div>
        </div>
        <button class="btn btn-info btn-lg btn-block btn-rounded text-uppercase waves-effect waves-light" type="submit">
          Upload
        </button>
      </form>
      <div class="form-group" v-if="files.file">
        <div class="col-md-6">
          <progressUpload :props="files.file" :url="files.url" :fileid="filename"></progressUpload>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProgressUpload from '~/components/ProgressUpload'

export default {
  components: {
    ProgressUpload
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl,
      filename: '',
      error: '',
      files: {},
      objupload: {}
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(index) {
      var obj = {}
      obj[index] = undefined
      this.$set(this, 'files', obj)
    })
  },
  methods: {
    onBrowse: function(url, e) {
      var obj = {}
      obj['file'] = e.target.files[0]
      obj['url'] = url
      this.objupload = obj
    },
    onSubmit: function() {
      this.$set(this, 'files', this.objupload)
    }
  }
}
</script>
