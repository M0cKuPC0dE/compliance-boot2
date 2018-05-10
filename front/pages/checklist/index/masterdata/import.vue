<template>
  <div class="container-fluid">

    <div class="form-group p-t-20" v-if="!files.file">
      <div class="col-md-12 text-center is-fileinput">
        <span class="btn btn-info btn-file m-r-10">
          <i class="zmdi zmdi-swap-vertical"></i>
          {{ $t('buttons.upload.template') }}
          <input style="display:" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" v-on:change="onBrowse(baseUrl+'/api/templateupload',$event)">
        </span>
        <nuxt-link to="/checklist/masterdata" class="btn btn-info" style="height:35px">
          <i class="fa fa-chevron-left"></i> {{ $t('buttons.back') }}
        </nuxt-link>
      </div>
    </div>

    <div class="form-group" v-if="files.file">
      <div class="col-md-12">
        <progressUpload :props="files.file" :url="files.url" :fileid="$route.params.id"></progressUpload>
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
      files: {}
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(index) {
      var obj = {}
      obj[index] = undefined
      this.$set(this, 'files', obj)
      this.$router.push('/checklist/masterdata')
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
