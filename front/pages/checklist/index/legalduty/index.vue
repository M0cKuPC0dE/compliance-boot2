<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-8 m-b-10">
      </div>
      <div class="col-md-4 m-b-10">
        <nuxt-link to="/checklist/legalduty/add" class="btn btn-block btn-info">เพิ่มหน้าที่ตามกฎหมาย</nuxt-link>
      </div>
    </div>
    <div class="row">
      <div>
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>หน้าที่ตามกฎหมาย</th>
              <th class="text-center col-md-3">จัดการ</th>
            </tr>
          </thead>
          <tbody>
            <tr :key="index" v-for="(legalDuty,index) in compliance.legalDuties">
              <td v-html="legalDuty.name"></td>
              <td class="text-center">
                <nuxt-link :to="'/checklist/legalduty/edit/'+legalDuty.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="แก้ไข">
                  <i class="ti-marker-alt"></i>
                </nuxt-link>
                <a href="javascript:void(0)" v-on:click="onConfirmDelete(legalDuty)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                  <i class="ti-trash"></i>
                </a>
                <a href="javascript:void(0)" v-on:click="showCoordinator(legalDuty)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                  <i class="ti-user"></i>
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

    <div id="masterdata-coordinator-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title">Compliance Coordinator</h4>
          </div>
          <div class="modal-body">
            <ul>
              <li :key="index" v-for="(cogroup,index) in coordinatories">
                <strong>{{cogroup.buName}}</strong>
                <ul>
                  <li :key="index" v-for="(coordinator,index) in cogroup.coordinates">
                    {{coordinator.nameTh}}
                  </li>
                </ul>
              </li>
            </ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">ปิด</button>
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
  data: function() {
    return {
      compliance: {},
      deleteLegalDuty: '',
      coordinatories: {}
    }
  },
  computed: mapGetters({
    initCompliance: 'category/compliance'
  }),
  watch: {
    selected: function(val) {
      this.onLoad(val)
    }
  },
  mounted: function() {
    $('[data-toggle="tooltip"]').tooltip()
  },
  methods: {
    onLoad: function(selected) {
      var self = this
      return http
        .get('/api/compliance/' + selected.id, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          this.$set(this, 'compliance', response.data)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onDelete: function() {
      var self = this
      $('#masterdata-remove-modal').modal('hide')
      return http
        .delete('/api/legalduty/' + this.deleteLegalDuty.id, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.onLoad(self.selected)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onConfirmDelete: function(legalDuty) {
      $('#masterdata-remove-modal').modal('show')
      this.$set(this, 'deleteLegalDuty', legalDuty)
    },
    showCoordinator(legalDuty) {
      $('#masterdata-coordinator-modal').modal('show')

      var self = this
      return http
        .get('/api/legalgroup/legalduty/' + legalDuty.id, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          this.$set(this, 'coordinatories', response.data)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    }
  }
}
</script>
