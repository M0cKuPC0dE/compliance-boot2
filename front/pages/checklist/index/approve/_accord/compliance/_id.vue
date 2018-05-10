<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('compliance.legalname') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>หน้าหลัก</li>
            <li class="active">{{ $t('compliance.legalname') }}</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <form class="form-horizontal" v-on:submit.prevent="onSave">

              <div class="form-group">
                <label class="col-md-12">
                  <strong>{{ $t('compliance.legalname') }}</strong>
                </label>
                <div class="col-md-12">
                  <a href="javascript:void(0)" v-on:click="showModal">{{accord.legalDuty.compliance.legalName}}</a>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-12">
                  <strong>หน้าที่ตามกฎหมาย</strong>
                </label>
                <div class="col-md-12">
                  <div v-html="accord.legalDuty.name"></div>
                </div>
              </div>

              <div class="row m-b-20">
                <div class="col-md-6">
                  <strong>ประเมินความสอดคล้อง</strong>
                  <div class="radio radio-success">
                    <input type="radio" name="radio" id="radio1" value="ACCORDED" v-model="accord.accorded" disabled>
                    <label for="radio1"> สอดคล้อง </label>
                  </div>
                  <div class="radio radio-danger">
                    <input type="radio" name="radio" id="radio2" value="NOT_ACCORDED" v-model="accord.accorded" disabled>
                    <label for="radio2"> ไม่สอดคล้อง </label>
                  </div>
                  <div class="radio">
                    <input type="radio" name="radio" id="radio3" value="NOT_CONCERN" v-model="accord.accorded" disabled>
                    <label for="radio3"> ไม่เกี่ยวข้อง </label>
                  </div>
                </div>
                <div class="col-md-6">
                  <strong>ประเภท</strong>
                  <div>
                    <p class="" v-if="accord.legalDuty.legalType === 'LICENSE'">ใบอนุญาต</p>
                    <p class="" v-if="accord.legalDuty.legalType === 'EVIDENCE'">กฎหมายทั่วไป</p>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <div class="col-md-12">
                  <span class="remark-block">สอดคล้อง หมายถึง ปฎิบัติตามกฎหมาย</span><br>
                  <span class="remark-block">ไม่สอดคล้อง หมายถึง ไม่ปฎิบัติหรืออยู่ระหว่างดำเนินการตามกฎหมาย</span><br>
                  <span class="remark-block">ไม่เกี่ยวข้อง หมายถึง กรณีอื่นๆ เช่น กฎหมายถูกยกเลิก, ไม่เกี่ยวข้องกับกฎหมายนั้น หรือไม่ได้เป็นผู้รับผิดชอบกฎหมายนั้นแล้ว</span>
                </div>
              </div>

              <!-- <div class="form-group">
                                  <label class="col-md-12">
                                    <strong v-if="accord.accorded === 'ACCORDED'">หมายเหตุ Owner</strong>
                                    <strong v-if="accord.accorded === 'NOT_ACCORDED'">แผนงาน Owner</strong>
                                    <strong v-if="accord.accorded === 'NOT_CONCERN'">เหตุผล Owner</strong>
                                  </label>
                                  <div class="col-md-12">
                                    {{accord.remark}}
                                  </div>
                                </div> -->

              <div class="form-group" v-if="accord.accorded === 'NOT_ACCORDED'">
                <label class="col-md-12">
                  <strong>วันที่ที่จะดำเนินการเสร็จ</strong>
                </label>
                <div class="col-md-6">
                  {{date.publicDate}} {{getMonth()[date.publicMonth-1]}} {{(parseInt(date.publicYear) + 543)}}
                </div>
              </div>

              <!-- <div class="form-group" v-if="accord.remarkCoordinator">
                                  <label class="col-md-12">
                                    <strong>หมายเหตุ Coordinator</strong>
                                  </label>
                                  <div class="col-md-12">
                                    <span>{{accord.remarkCoordinator}}</span>
                                  </div>
                                </div> -->

              <div id="accordion" class="panel-group">
                <div class="panel panel-info">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="'#collapse_0" aria-expanded="true">ข้อคิดเห็น</a>
                    </h4>
                  </div>
                  <div id="collapse_0" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body">

                      <div class="media" :key="index" v-for="(remark,index) in accord.accordRemark">
                        <div class="media-body">
                          <h4 class="media-heading">{{remark.user.nameTh}}
                            <span class="label label-info pull-right ">{{remark.createDate}}</span>
                            <span class="label label-warning pull-right ">{{remarkTranslate(remark.status)}}</span>
                          </h4>
                          {{remark.remark}}
                        </div>
                      </div>

                    </div>
                  </div>
                </div>
              </div>

              <div class="form-group" v-if="accord.accorded && (accord.approve === false || accord.approve === null) && accord.accept === true">
                <label class="col-md-12">
                  <strong>หมายเหตุ</strong>
                </label>
                <div class="col-md-12">
                  <textarea class="form-control" rows="5" v-model="accord.remark" required></textarea>
                </div>
              </div>

              <div class="form-group m-t-20" v-if="accord.legalDuty.legalType === 'LICENSE'">
                <div class="col-md-12">
                  <div>
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>วันแจ้งเตือน</th>
                          <th>วันหมดอายุ</th>
                          <th>ใบอนุญาต</th>
                          <th class="text-center">จัดการ</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr :key="file.index" v-for="(file,index) in accord.licenseFiles">
                          <td class="col-md-3">
                            {{file.warningDate}}
                          </td>
                          <td class="col-md-3">
                            {{file.expireDate}}
                          </td>
                          </td>
                          <td style="vertical-align: middle;">{{file.name}}</td>
                          <td style="vertical-align: middle;" class="text-center">
                            <a :href="baseUrl+'/public/download/license/'+file.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                              <i class="fa fa-download"></i>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

              <div class="form-group m-t-20" v-if="accord.legalDuty.legalType === 'EVIDENCE'">
                <div class="col-md-12">
                  <div>
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>วันแจ้งเตือน</th>
                          <th>วันหมดอายุ</th>
                          <th>หลักฐาน</th>
                          <th class="text-center col-md-1">จัดการ</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr :key="file.index" v-for="(file,index) in accord.evidenceFiles">
                          <td class="col-md-3">
                            {{file.warningDate}}
                          </td>
                          <td class="col-md-3">
                            {{file.expireDate}}
                          </td>
                          <td style="vertical-align: middle;">{{file.name}}</td>
                          <td style="vertical-align: middle;" class="text-center">
                            <a :href="baseUrl+'/public/download/evidence/'+file.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                              <i class="fa fa-download"></i>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

              <div class="form-group" v-if="files.file">
                <div class="col-md-12">
                  <progressUpload :props="files.file" :url="files.url" :fileid="$route.params.id"></progressUpload>
                </div>
              </div>

              <div class="form-actions text-center m-t-20">
                <nuxt-link to="/checklist/approve" class="btn btn-info">
                  <i class="fa fa-chevron-left"></i> ย้อนกลับ
                </nuxt-link>
                <button v-on:click="approve" type="button" class="btn btn-success m-l-10" v-if="accord.accorded && (accord.approve === false || accord.approve === null) && accord.accept === true">อนุมัติ</button>
                <button v-on:click="notApprove" type="button" class="btn btn-danger m-l-10" v-if="accord.accorded && (accord.approve === false || accord.approve === null) && accord.accept === true">ไม่อนุมัติ</button>
              </div>

            </form>

          </div>
        </div>
      </div>
    </div>

    <div id="masterdata-add-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
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

    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title" id="myLargeModalLabel">รายละเอียดกฎหมาย</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-12">
                <div class="white-box">

                  <form class="form-horizontal">

                    <div class="form-group">
                      <label class="col-md-12">
                        <strong>{{ $t('compliance.legalname') }}</strong>
                      </label>
                      <div class="col-md-12">
                        {{accord.legalDuty.compliance.legalName}}
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="col-md-12">
                            <strong>{{ $t('compliance.effectivedate') }}</strong>
                          </label>
                          <div class="col-md-12">
                            {{accord.legalDuty.compliance.effectiveDate}}
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-6 col-sm-12">
                        <div class="form-group">
                          <label class="col-md-12">
                            <strong>{{ $t('compliance.department') }}</strong>
                          </label>
                          <div class="col-md-12">
                            {{accord.legalDuty.compliance.department}}
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6 col-sm-12">
                        <div class="form-group">
                          <label class="col-md-12">
                            <strong>{{ $t('compliance.ministry') }}</strong>
                          </label>
                          <div class="col-md-12">
                            {{accord.legalDuty.compliance.ministry}}
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-md-12">
                        <strong>{{ $t('compliance.important') }}</strong>
                      </label>
                      <div class="col-md-12">
                        <!-- <span style="white-space: pre-wrap;">{{accord.legalDuty.compliance.important}}</span> -->
                        <div v-html="accord.legalDuty.compliance.important"></div>
                      </div>
                    </div>

                    <div class="form-group">
                      <div class="col-md-12">
                        <div>
                          <table class="table table-striped table-hover">
                            <tbody>
                              <tr :key="file.index" v-for="(file,index) in accord.legalDuty.compliance.legalFiles">
                                <td>{{file.name}}</td>
                                <td class="text-center col-md-1">
                                  <a :href="baseUrl+'/public/download/legal'+file.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ดาวน์โหลด">
                                    <i class="fa fa-download"></i>
                                  </a>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>

                  </form>

                </div>
              </div>
            </div>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger waves-effect text-left" data-dismiss="modal">ปิด</button>
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
import ProgressUpload from '~/components/ProgressUpload'

export default {
  components: {
    ProgressUpload
  },
  async asyncData(context) {
    let accord = await http
      .get('/api/accord/' + context.params.accord + '/' + context.params.id, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })

    var date = {
      publicDate: accord.data.completeDate
        ? accord.data.completeDate.split('/')[0].replace(/^0+/, '')
        : '',
      publicMonth: accord.data.completeDate
        ? accord.data.completeDate.split('/')[1].replace(/^0+/, '')
        : '',
      publicYear: accord.data.completeDate
        ? accord.data.completeDate.split('/')[2].replace(/^0+/, '')
        : ''
    }

    return {
      accord: accord.data,
      date: date
    }
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl,
      files: {},
      deleteIndex: {},
      deleteType: '',
      date: {
        publicDate: '',
        publicMonth: '',
        publicYear: ''
      }
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(json) {
      var obj = {}
      obj['file'] = undefined
      this.$set(this, 'files', obj)
      if ('warningDate' in JSON.parse(json)) {
        this.accord.licenseFiles.push(JSON.parse(json))
        this.$set(this.accord, 'licenseFiles', this.accord.licenseFiles)
      } else {
        this.accord.evidenceFiles.push(JSON.parse(json))
        this.$set(this.accord, 'evidenceFiles', this.accord.evidenceFiles)
      }
    })
  },
  mounted: function() {
    for (var i = 0; i < this.accord.licenseFiles.length; i++) {
      this.showCalendar(i, this.accord.licenseFiles[i])
    }
  },
  updated: function() {
    for (var i = 0; i < this.accord.licenseFiles.length; i++) {
      this.showCalendar(i, this.accord.licenseFiles[i])
    }
  },
  methods: {
    remarkTranslate: function(val) {
      if (val === 'ACCORDED') {
        return 'สอดคล้อง'
      } else if (val === 'NOT_ACCORDED') {
        return 'ไม่สอดคล้อง'
      } else if (val === 'NOT_CONCERN') {
        return 'ไม่เกี่ยวข้อง'
      } else if (val === 'ACCEPT') {
        return 'เห็นชอบ'
      } else if (val === 'NOT_ACCEPT') {
        return 'ไม่เห็นชอบ'
      } else if (val === 'APPROVE') {
        return 'อนุมัติ'
      } else if (val === 'NOT_APPROVE') {
        return 'ไม่อนุมัติ'
      }
    },
    onSave: function() {
      var self = this
      self.accord.accept = null
      self.accord.completeDate =
        self.accord.accorded === 'NOT_ACCORDED'
          ? self.date.publicDate +
            '/' +
            self.date.publicMonth +
            '/' +
            self.date.publicYear
          : ''
      self.accord.legalCategory = {}
      self.accord.legalCategory.id = this.$route.params.accord
      http
        .post('/api/accord', self.accord, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$router.push({ path: '/checklist/category' })
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    onBrowse: function(url, e) {
      var obj = {}
      obj['file'] = e.target.files[0]
      obj['url'] = url
      this.$set(this, 'files', obj)
    },
    showCalendar: function(index, file) {
      $('#warningDate-' + index)
        .datepicker({
          language: 'th',
          thaiyear: true,
          format: 'dd/mm/yyyy',
          orientation: 'bottom left',
          autoclose: !0,
          todayHighlight: !0
        })
        .on('changeDate', () => {
          file.warningDate = $('#warningDate-' + index).val()
        })
      $('#expireDate-' + index)
        .datepicker({
          language: 'th',
          thaiyear: true,
          format: 'dd/mm/yyyy',
          orientation: 'bottom left',
          autoclose: !0,
          todayHighlight: !0
        })
        .on('changeDate', () => {
          file.expireDate = $('#expireDate-' + index).val()
        })
      return true
    },
    onConfirmDelete: function(type, index) {
      $('#masterdata-add-modal').modal('show')
      this.$set(this, 'deleteIndex', index)
      this.$set(this, 'deleteType', type)
    },
    onDelete: function() {
      $('#masterdata-add-modal').modal('hide')
      if (this.deleteType === 'license') {
        this.accord.licenseFiles.splice(this.deleteIndex, 1)
      } else {
        this.accord.evidenceFiles.splice(this.deleteIndex, 1)
      }
    },
    showModal: function() {
      $('.bs-example-modal-lg').modal('show')
    },
    getYear: function() {
      var year = []
      for (
        var i = new Date().getFullYear();
        i <= new Date().getFullYear() + 2;
        i++
      ) {
        year.push(i)
      }
      return year
    },
    getMonth: function() {
      return [
        'มกราคม',
        'กุมภาพันธ์',
        'มีนาคม',
        'เมษายน',
        'พฤษภาคม',
        'มิถุนายน',
        'กรกฎาคม',
        'สิงหาคม',
        'กันยายน',
        'ตุลาคม',
        'พฤศจิกายน',
        'ธันวาคม'
      ]
    },
    approve: function() {
      var self = this
      http
        .post('/api/accord/approve/' + this.accord.id, this.accord, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$router.push({ path: '/checklist/approve' })
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    notApprove: function() {
      var self = this
      http
        .post('/api/accord/reject/' + this.accord.id, this.accord, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$router.push({ path: '/checklist/approve' })
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    }
  }
}
</script>

<style lang="scss">
.remark-block {
  color: #737373;
}
</style>

