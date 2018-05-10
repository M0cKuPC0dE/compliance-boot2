<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.masterdata') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('dashboards.title') }}</li>
            <li class="active">{{ $t('menu.masterdata') }}</li>
          </ol>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12">
          <div class="white-box">
            <div class="row row-in">
              <div class="col-lg-6 col-sm-12 row-in-br">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-success">
                      <i class="ti-check-box"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15">{{progress.accord}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4>สอดคล้อง</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12 b-0">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-danger">
                      <i class="ti-close"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15">{{progress.notAccord}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4>ไม่สอดคล้อง</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12 row-in-br  b-r-none">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-warning">
                      <i class="ti-share-alt"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15">{{progress.notConcern}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4>ไม่เกี่ยวข้อง</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12  b-0">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-info">
                      <i class="ti-new-window"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15">{{progress.inprogress}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4>ยังไม่ดำเนินการ</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">จัดหมวดหมู่กฎหมาย</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <table class="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th class="col-xs-4">หน่วยงานของ Coordinator</th>
                        <th class="text-center">Owner</th>
                        <th class="text-center">Coordinator</th>
                        <th class="text-center">Approver</th>
                        <th class="text-center">Completed</th>
                        <th class="text-center">จำนวนหน้าที่ตามกฎหมาย</th>
                        <th class="text-center">จัดการ</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr :key="index" v-for="(legalgroup,index) in groups">
                        <td>
                          <nuxt-link :to="'/checklist/category/'+legalgroup.id">{{legalgroup.buName}} ({{legalgroup.legalDuties.length}})</nuxt-link>
                        </td>
                        <td class="text-center">{{calculatePosition(legalgroup).owner === 0?'-':calculatePosition(legalgroup).owner}}</td>
                        <td class="text-center">{{calculatePosition(legalgroup).coordinator === 0?'-':calculatePosition(legalgroup).coordinator}}</td>
                        <td class="text-center">{{calculatePosition(legalgroup).approver === 0?'-':calculatePosition(legalgroup).approver}}</td>
                        <td class="text-center">{{calculatePosition(legalgroup).completed === 0?'-':calculatePosition(legalgroup).completed}}</td>
                        <td class="text-center">{{calculatePosition(legalgroup).total}}</td>
                        <td class=" text-center ">
                          <nuxt-link :to=" '/checklist/category/manage/'+legalgroup.id " class="btn btn-sm btn-info m-r-5 " data-toggle="tooltip " title="จัดหมวดหมู่ ">
                            <i class="ti-direction-alt "></i>
                          </nuxt-link>
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

    <div id="group-remove-modal " class="modal fade " tabindex="-1 " role="dialog " aria-labelledby="myModalLabel " aria-hidden="true " style="display: none; ">
      <div class="modal-dialog ">
        <div class="modal-content ">
          <div class="modal-header ">
            <button type="button " class="close " data-dismiss="modal " aria-hidden="true ">×</button>
            <h4 class="modal-title ">ยืนยันการลบ</h4>
          </div>
          <div class="modal-body ">
            ต้องการลบใช่หรือไม่
          </div>
          <div class="modal-footer ">
            <button type="button " class="btn btn-default waves-effect " data-dismiss="modal ">ปิด</button>
            <button type="button " class="btn btn-danger waves-effect waves-light " v-on:click="onDelete ">ลบ</button>
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
    this.calculateProgress()
  },
  data: function() {
    return {
      deleteGroup: {},
      progress: {},
      position: {}
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
    },
    calculateProgress: function() {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0,
        total: 0
      }
      this.groups.forEach(function(group) {
        group.legalCategories.forEach(function(category) {
          category.accords.forEach(function(accord) {
            if (accord.accorded === 'ACCORDED') {
              data.accord = data.accord + 1
            } else if (accord.accorded === 'NOT_ACCORDED') {
              data.notAccord = data.notAccord + 1
            } else if (accord.accorded === 'NOT_CONCERN') {
              data.notConcern = data.notConcern + 1
            } else {
              data.inprogress = data.inprogress + 1
            }
            data.total = data.total + 1
          })
        })
      })
      this.$set(this, 'progress', data)
    },
    calculatePosition: function(group) {
      var data = {
        owner: 0,
        coordinator: 0,
        approver: 0,
        completed: 0,
        total: 0
      }
      group.legalCategories.forEach(function(category) {
        category.accords.forEach(function(accord) {
          if (
            accord.accept === null &&
            accord.accorded === null &&
            accord.approve === null
          ) {
            data.owner = data.owner + 1
          } else if (
            accord.accept === null &&
            accord.accorded !== null &&
            accord.approve === null
          ) {
            data.coordinator = data.coordinator + 1
          } else if (
            accord.accept !== null &&
            accord.accept !== false &&
            accord.accorded !== null &&
            accord.approve === null
          ) {
            data.approver = data.approver + 1
          } else if (accord.accept === false || accord.approve === false) {
            data.owner = data.owner + 1
          }
          data.total = data.total + 1
        })
      })
      data.completed =
        data.total - (data.owner + data.coordinator + data.approver)
      return data
    }
  }
}
</script>
