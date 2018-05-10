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

      <StateBoard :groups="groups"></StateBoard>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">รายการกฎหมายที่มอบหมายให้ Compliance Coordinator</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-3 m-b-10">
              </div>
              <div class="col-md-6 m-b-10">
              </div>
              <div class="col-md-3 m-b-10">
                <nuxt-link to="/checklist/group/add" class="btn btn-block btn-info">{{ $t('group.add')}}</nuxt-link>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <table class="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Compliance Coordinator</th>
                        <th class="text-center">Admin Assign</th>
                        <th class="text-center">Assign to Owner</th>
                        <th class="text-center">ประเมินแล้ว</th>
                        <th class="text-center">{{ $t('group.management')}}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr :key="index" v-for="(legalgroup,index) in groups" v-if="legalgroup.legalDuties.length > 0">
                        <td>
                          <nuxt-link :to="'/checklist/group/'+legalgroup.id">{{legalgroup.buName}}</nuxt-link>
                        </td>
                        <td class="text-center">{{legalgroup.legalDuties.length}}</td>
                        <td class="text-center">{{countDuty(legalgroup).total}}</td>
                        <td class="text-center">{{countDuty(legalgroup).process}}</td>
                        <td class="text-center">
                          <nuxt-link :to="'/checklist/group/manage/'+legalgroup.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="จัดหมวดหมู่">
                            <i class="ti-direction-alt"></i>
                          </nuxt-link>
                          <nuxt-link :to="'/checklist/group/edit/'+legalgroup.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="แก้ไข">
                            <i class="ti-marker-alt"></i>
                          </nuxt-link>
                          <nuxt-link :to="'/checklist/group/copy/'+legalgroup.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="คัดลอก">
                            <i class="ti-stamp"></i>
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
import StateBoard from '~/components/StateBoard'

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
  components: {
    StateBoard
  },
  mounted: function() {
    $('[data-toggle="tooltip"]').tooltip()
  },
  data: function() {
    return {
      deleteGroup: {},
      progress: {}
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

      this.deleteGroup.legalDuties = []
      return http
        .post('/api/legalgroup', self.deleteGroup, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.onLoad()
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onConfirmDelete: function(legalgroup) {
      $('#group-remove-modal').modal('show')
      this.$set(this, 'deleteGroup', legalgroup)
    },
    countDuty: function(group) {
      var data = {
        total: 0,
        process: 0
      }
      group.legalCategories.forEach(function(category) {
        category.accords.forEach(function(accord) {
          if (accord.accorded) {
            data.process = data.process + 1
          }
          data.total = data.total + 1
        })
      })
      return data
    }
  }
}
</script>

<style lang="scss">
.white-box .box-title {
  text-transform: none;
}
</style>
