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

      <StateBoard :groups="[{legalCategories:categories}]"></StateBoard>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">หมวดหมู่กฎหมาย</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <table class="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th class="col-xs-5">ฝ่าย/แผนก</th>
                        <th>ผู้ดูแล</th>
                        <th class="col-xs-3 text-center">สถานะการดำนเนินการ</th>
                        <th class="text-center">จัดการ</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr :key="index" v-for="(category,index) in categories">
                        <td>
                          <nuxt-link :to="'/checklist/group/accord/'+category.id">{{category.department.name}}</nuxt-link>
                        </td>
                        <td>
                          {{category.owners.length != 0?category.owners[0].nameTh:''}}
                        </td>
                        <td class="text-center">
                          {{categoryProgress(category)}}
                        </td>
                        <td class="text-center">
                          <nuxt-link :to="'/checklist/group/category/edit/'+category.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="แก้ไข">
                            <i class="ti-marker-alt"></i>
                          </nuxt-link>
                          <nuxt-link :to="'/checklist/group/category/copy/'+category.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="คัดลอก">
                            <i class="ti-stamp"></i>
                          </nuxt-link>
                          <a href="javascript:void(0)" v-on:click="onConfirmDelete(category)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
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

    <div id="category-remove-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
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

    <div id="category-remove-warn-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title">สถานะ</h4>
          </div>
          <div class="modal-body" v-if="deleteCategory.department">
            หมวดหมู่กฎหมาย
            <strong>{{deleteCategory.department.name}}</strong> มีหน้าที่ตามกฎหมายรอปฏิบัติ
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
import http from '~/utils/http'
import cookie from '~/utils/cookie'
import StateBoard from '~/components/StateBoard'

export default {
  asyncData: function(context) {
    return http
      .get('/api/legalcategory/legalgroup/' + context.params.group, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .then(response => {
        return { categories: response.data }
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
      deleteCategory: {},
      progress: {}
    }
  },
  methods: {
    onLoad: function() {
      var self = this
      http
        .get('/api/legalcategory/legalgroup/' + this.$route.params.group, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$set(self, 'categories', response.data)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onDelete: function() {
      var self = this
      $('#category-remove-modal').modal('hide')
      return http
        .delete('/api/legalcategory/' + this.deleteCategory.id, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.onLoad(self.selected)
        })
        .catch(e => {
          $('#category-remove-warn-modal').modal('show')
        })
    },
    onConfirmDelete: function(category) {
      $('#category-remove-modal').modal('show')
      this.$set(this, 'deleteCategory', category)
    },
    calculateProgress: function() {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0
      }
      this.categories.forEach(function(category) {
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
        })
      })
      this.$set(this, 'progress', data)
    },
    categoryProgress: function(category) {
      var data = {
        complete: 0,
        incomplete: 0
      }
      category.accords.forEach(function(accord) {
        if (accord.approve) {
          data.complete = data.complete + 1
        }
        data.incomplete = data.incomplete + 1
      })
      return data.complete + '/' + data.incomplete
    }
  }
}
</script>
