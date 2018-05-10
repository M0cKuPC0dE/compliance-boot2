<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.masterdata') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('menu.dashboard') }}</li>
            <li class="active">{{ $t('menu.masterdata') }}</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">จัดการผู้ประสานงาน</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-3 m-b-10">
              </div>
              <div class="col-md-6 m-b-10">
              </div>
              <div class="col-md-3 m-b-10">
                <nuxt-link to="/checklist/coordinator/add" class="btn btn-block btn-info">เพิ่มกลุ่มผู้ประสานงาน</nuxt-link>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <table class="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>กลุ่มผู้ประสานงาน</th>
                        <th class="text-center col-md-2">{{ $t('group.management')}}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr :key="index" v-for="(legalgroup,index) in groups">
                        <td>{{legalgroup.buName}}</td>
                        <td class="text-center">
                          <nuxt-link :to="'/checklist/coordinator/edit/'+legalgroup.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="แก้ไข">
                            <i class="ti-marker-alt"></i>
                          </nuxt-link>
                          <a href="javascript:void(0)" v-on:click="onConfirmDelete(legalgroup)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                            <i class="ti-trash"></i>
                          </a>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>

    </div>

    <div id="group-remove-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title">{{ $t('message.delete.confirm') }}</h4>
          </div>
          <div class="modal-body">
            {{ $t('message.delete.description') }}
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">{{ $t('buttons.close') }}</button>
            <button type="button" class="btn btn-danger waves-effect waves-light" v-on:click="onDelete">{{ $t('buttons.delete') }}</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
/* global $ */
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  asyncData: function(context) {
    return http
      .get('/api/legalgroup/authority/' + cookie(context).AU, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .then(response => {
        return { groups: response.data }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
  },
  mounted: function() {
    $('[data-toggle="tooltip"]').tooltip()
  },
  data: function() {
    return {
      deleteGroup: {}
    }
  },
  methods: {
    onLoad: function() {
      var self = this
      http
        .get('/api/legalgroup/authority/' + cookie(this).AU, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$set(self, 'groups', response.data)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onDelete: function() {
      var self = this
      $('#group-remove-modal').modal('hide')
      return http
        .delete('/api/legalgroup/' + this.deleteGroup.id, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.onLoad(self.selected)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onConfirmDelete: function(legalgroup) {
      $('#group-remove-modal').modal('show')
      this.$set(this, 'deleteGroup', legalgroup)
    }
  }
}
</script>

<style lang="scss">
.white-box .box-title {
  text-transform: none;
}
</style>
