<template>
  <form class="form-horizontal" v-on:submit.prevent="onSave">
    <div class="form-group">
      <label class="col-md-12">
        <span class="help">ชื่อกฎหมาย</span>
      </label>
      <div class="col-md-12">
        <input type="text" class="form-control readonly" :value="compliance.legalName" required>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-12">หน้าที่ตามกฎหมาย</label>
      <div class="col-sm-12">
        <div id="summernote"></div>
        <!-- <textarea class="form-control" rows="5" v-model="legalDuty.name" required></textarea> -->
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-12">ประเภท</label>
      <div class="col-md-12">
        <div class="radio radio-success">
          <input type="radio" name="type" id="type1" value="LICENSE" v-model="legalDuty.legalType" required>
          <label for="type1"> ใบอนุญาต </label>
        </div>
        <div class="radio radio-danger">
          <input type="radio" name="type" id="type2" value="EVIDENCE" v-model="legalDuty.legalType" required>
          <label for="type2"> กฎหมายทั่วไป </label>
        </div>
      </div>
    </div>
    <button type="submit" class="btn btn-success waves-effect waves-light m-r-10">บันทึก</button>
    <nuxt-link to="/checklist/legalduty" class="btn btn-info">
      <i class="fa fa-chevron-left"></i> ย้อนกลับ
    </nuxt-link>

  </form>
</template>

<script>
/* global $ */
import { mapGetters } from 'vuex'
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  props: ['selected'],
  data: function () {
    return {
      compliance: {},
      legalDuty: {
        name: '',
        compliance: {}
      }
    }
  },
  watch: {
    selected: function (val) {
      this.$set(this, 'compliance', val)
    }
  },
  computed: mapGetters({
    initCompliance: 'category/compliance'
  }),
  mounted: function () {
    this.$set(this, 'compliance', this.initCompliance)
    $('.readonly').on('keydown paste', function (e) {
      e.preventDefault()
    })

    $('#summernote').summernote({
      height: 200,
      toolbar: [
        ['style', ['bold', 'italic', 'underline', 'clear']],
        ['font', ['strikethrough', 'superscript', 'subscript']],
        ['fontsize', ['fontsize']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']]
      ]
    })
  },
  methods: {
    onSave: function () {
      var self = this
      this.legalDuty.compliance = { id: this.compliance.id }
      this.legalDuty.name = $('#summernote').summernote('code')
      return http
        .post('/api/legalduty', this.legalDuty, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then((response) => {
          self.$router.replace('/checklist/legalduty')
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    }
  }
}
</script>
