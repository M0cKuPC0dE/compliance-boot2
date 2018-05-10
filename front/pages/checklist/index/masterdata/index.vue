<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-4 m-b-10">
        <nuxt-link to="/checklist/masterdata/category/edit" class="btn btn-block btn-warning">{{ $t('buttons.category.edit') }}</nuxt-link>
      </div>
      <div class="col-md-4 m-b-10">
        <nuxt-link to="/checklist/masterdata/category/add" class="btn btn-block btn-info">{{ $t('buttons.category.add') }}</nuxt-link>
      </div>
      <div class="col-md-4 m-b-10">
        <nuxt-link to="/checklist/masterdata/compliance/add" class="btn btn-block btn-info">{{ $t('buttons.compliance.add') }}</nuxt-link>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12 m-b-10 p-0">
        <form v-on:submit.prevent="onSearch">
          <div class="form-group">
            <div class="input-group bootstrap-touchspin">
              <input type="text" class="form-control" style="display: block;" v-model="search">
              <span class="input-group-btn">
                <button class="btn btn-default btn-outline" type="submit">
                  <i class="fa fa-search"></i>
                </button>
              </span>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="row">
      <div>
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>ข้อกฎหมาย</th>
              <th class="text-center">สถานะ</th>
              <th class="col-md-3 text-center">จัดการ</th>
            </tr>
          </thead>
          <tbody>
            <tr :key="index" v-for="(compliance,index) in compliances">
              <td>{{compliance.legalName}}</td>
              <td class="text-center">
                <span class="label label-success" v-if="compliance.status === 'ACTIVE'">{{compliance.status}}</span>
                <span class="label label-warning" v-if="compliance.status === 'INACTIVE'">{{compliance.status}}</span>
              </td>
              <td class="text-center">
                <nuxt-link :to="'/checklist/compliance/'+compliance.id" class="btn btn-sm btn-info m-r-5" title="" data-toggle="tooltip" title="เปิด">
                  <i class="ti-search"></i>
                </nuxt-link>
                <nuxt-link :to="'/checklist/masterdata/compliance/edit/'+compliance.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="แก้ไข">
                  <i class="ti-marker-alt"></i>
                </nuxt-link>
                <a href="javascript:void(0)" v-on:click="onConfirmDelete(compliance)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                  <i class="ti-trash"></i>
                </a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div id="masterdata-remove-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title">ยืนยันการลบ</h4>
          </div>
          <div class="modal-body">
            ต้องการลบใช่หรือไม่
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">ปิด</button>
            <button type="button" class="btn btn-danger waves-effect waves-light" v-on:click="onDelete">ลบ</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
/* global $ */
import { mapGetters } from 'vuex'
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  props: ['selected'],
  asyncData: function (context) {
    return http
      .get('/api/compliance', { headers: { Authorization: 'bearer ' + cookie(context).AT } })
      .then((response) => {
        return { compliances: response.data }
      })
      .catch((e) => {
        context.redirect('/checklist/login')
      })
  },
  data: function () {
    return {
      search: '',
      deleteCompliance: {}
    }
  },
  watch: {
    selected: function (val) {
      this.onLoad(val)
    }
  },
  computed: mapGetters({
    initCategory: 'category/category'
  }),
  mounted: function () {
    $('[data-toggle="tooltip"]').tooltip()

    var self = this
    var url = this.initCategory.id && this.initCategory.id !== 'null' ? '/api/compliance/category/' + this.initCategory.id : '/api/compliance'
    return http
      .get(url, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
      .then((response) => {
        this.$set(this, 'compliances', response.data)
      })
      .catch((e) => {
        self.$router.replace('/checklist/login')
      })
  },
  methods: {
    onLoad: function (selected) {
      var self = this
      var url = selected.id === 'null' ? '/api/compliance' : '/api/compliance/category/' + selected.id
      return http
        .get(url, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then((response) => {
          this.$set(this, 'compliances', response.data)
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    },
    onSearch: function () {
      var self = this
      return http
        .post('/api/compliance/search', { search: this.search, category: this.selected.id }, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then((response) => {
          self.$set(this, 'compliances', response.data)
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    },
    onDelete: function () {
      var self = this
      $('#masterdata-remove-modal').modal('hide')
      return http
        .delete('/api/compliance/' + this.deleteCompliance.id, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then((response) => {
          self.onLoad(self.selected)
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    },
    onConfirmDelete: function (compliance) {
      $('#masterdata-remove-modal').modal('show')
      this.$set(this, 'deleteCompliance', compliance)
    }
  }
}
</script>
