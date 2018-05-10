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
                    <input type="radio" name="radio" id="radio1" value="ACCORDED" v-model="accord.accorded" required :disabled="position !== 'Owner' && accorded != null">
                    <label for="radio1"> สอดคล้อง </label>
                  </div>
                  <div class="radio radio-danger">
                    <input type="radio" name="radio" id="radio2" value="NOT_ACCORDED" v-model="accord.accorded" required :disabled="position !== 'Owner' && accorded != null">
                    <label for="radio2"> ไม่สอดคล้อง </label>
                  </div>
                  <div class="radio">
                    <input type="radio" name="radio" id="radio3" value="NOT_CONCERN" v-model="accord.accorded" required :disabled="position !== 'Owner' && accorded != null">
                    <label for="radio3"> ไม่เกี่ยวข้อง </label>
                  </div>
                </div>
                <div class="col-md-6">
                  <label class="col-md-12">
                    <strong>ประเภท</strong>
                  </label>
                  <div class="col-md-12">
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

              <!-- <div class="panel-group" id="accordion">
                                              <div class="panel panel-default">
                                                <div class="panel-heading">
                                                  <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" :href="'#collapse'+index">
                                                      -
                                                    </a>
                                                  </h4>
                                                </div>
                                                <div :id="'collapse'+index" class="panel-collapse collapse">
                                                  <div class="panel-body">
                                                    {{remark.remark}}
                                                  </div>
                                                </div>
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

              <div class="form-group">
                <label class="col-md-12">
                  <strong v-if="accord.accorded === 'ACCORDED'">หมายเหตุ</strong>
                  <strong v-if="accord.accorded === 'NOT_ACCORDED'">ระบุแผนงาน</strong>
                  <strong v-if="accord.accorded === 'NOT_CONCERN'">ระบุเหตุผล</strong>
                </label>
                <div class="col-md-12">
                  <!-- <textarea v-if="accord.accorded === 'NOT_ACCORDED' || accord.accorded === 'NOT_CONCERN'" class="form-control" rows="5" v-model="accord.remark" required></textarea> -->
                  <textarea class="form-control" rows="5" v-model="accord.remark" required></textarea>
                </div>
              </div>

              <div class="form-group" v-if="accord.accorded === 'NOT_ACCORDED'">
                <label class="col-md-12">
                  <strong>วันที่ที่จะดำเนินการเสร็จ</strong>
                </label>
                <div class="col-md-2">
                  <select class="form-control" v-model="date.publicDate" required>
                    <option value="">วัน</option>
                    <option :key="n" v-for="n in (1, 31)" :value="n">{{n}}</option>
                  </select>
                </div>

                <div class="col-md-2">
                  <select class="form-control" v-model="date.publicMonth" required>
                    <option value="">เดือน</option>
                    <option :key="n" v-for="(n,index) in getMonth()" :value="index+1">{{n}}</option>
                  </select>
                </div>

                <div class="col-md-2">
                  <select class="form-control" v-model="date.publicYear" required>
                    <option value="">ปี</option>
                    <option :key="n" v-for="n in getYear()" :value="n">{{n+543}}</option>
                  </select>
                </div>
              </div>

              <div class="form-group" v-if="accord.remarkCoordinator">
                <label class="col-md-12">
                  <strong>หมายเหตุ Coordinator</strong>
                </label>
                <div class="col-md-12">
                  <span>{{accord.remarkCoordinator}}</span>
                </div>
              </div>

              <div class="form-group" v-if="accord.remarkApprover">
                <label class="col-md-12">
                  <strong>หมายเหตุ Approver</strong>
                </label>
                <div class="col-md-12">
                  <span>{{accord.remarkApprover}}</span>
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
                          <th>มีวันหมดอายุ</th>
                          <th class="text-center">จัดการ</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr :key="file.index" v-for="(file,index) in accord.licenseFiles">
                          <td class="col-md-3">
                            <div class="input-group">
                              <input v-if="file.expired" type="text" class="form-control" :id="'warningDate-'+index" v-model="file.warningDate" required></input>
                              <input v-if="!file.expired" type="text" class="form-control" :id="'warningDate-'+index" v-model="file.warningDate" disabled></input>
                              <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar"></i>
                              </span>
                            </div>
                          </td>
                          <td class="col-md-3">
                            <div class="input-group">
                              <input v-if="file.expired" type="text" class="form-control" :id="'expireDate-'+index" v-model="file.expireDate" required></input>
                              <input v-if="!file.expired" type="text" class="form-control" :id="'expireDate-'+index" v-model="file.expireDate" disabled></input>
                              <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar"></i>
                              </span>
                            </div>
                          </td>
                          <td style="vertical-align: middle;">
                            <a :href="baseUrl+'/public/download/license/'+file.id">{{file.name}}</a>
                          </td>
                          <td>
                            <div class="checkbox checkbox-success">
                              <input :id="'checkbox'+index" type="checkbox" v-model="file.expired">
                              <label :for="'checkbox'+index"> มีวันหมดอายุ </label>
                            </div>
                          </td>
                          <td style="vertical-align: middle;" class="text-center">
                            <a href="javascript:void(0)" v-on:click="onConfirmDelete('license',index)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                              <i class="ti-trash"></i>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="form-group" v-if="!files.file && accord.legalDuty.legalType === 'LICENSE'">
                <div class="col-md-12 text-center is-fileinput">
                  <span class="btn btn-info btn-file">
                    <i class="zmdi zmdi-swap-vertical"></i>
                    นำเข้าใบอนุญาต
                    <input :required="checkValid()" data-toggle="tooltip" title="hahaha" style="display:" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,.pdf,.ppf,.doc,.docx" v-on:change="onBrowse(baseUrl+'/api/licenseupload',$event)">
                  </span>
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
                          <th>เอกสาร</th>
                          <th>มีวันหมดอายุ</th>
                          <th class="text-center">จัดการ</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr :key="file.index" v-for="(file,index) in accord.evidenceFiles">
                          <td class="col-md-3">
                            <div class="input-group">
                              <input v-if="file.expired" type="text" class="form-control" :id="'warningDate-'+index" v-model="file.warningDate" required></input>
                              <input v-if="!file.expired" type="text" class="form-control" :id="'warningDate-'+index" v-model="file.warningDate" disabled></input>
                              <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar"></i>
                              </span>
                            </div>
                          </td>
                          <td class="col-md-3">
                            <div class="input-group">
                              <input v-if="file.expired" type="text" class="form-control" :id="'expireDate-'+index" v-model="file.expireDate" required></input>
                              <input v-if="!file.expired" type="text" class="form-control" :id="'expireDate-'+index" v-model="file.expireDate" disabled></input>
                              <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar"></i>
                              </span>
                            </div>
                          </td>
                          <td style="vertical-align: middle;">
                            <a :href="baseUrl+'/public/download/evidence/'+file.id">{{file.name}}</a>
                          </td>
                          <td>
                            <div class="checkbox checkbox-success">
                              <input :id="'checkbox'+index" type="checkbox" v-model="file.expired">
                              <label :for="'checkbox'+index"> มีวันหมดอายุ </label>
                            </div>
                          </td>
                          <td style="vertical-align: middle;" class="text-center">
                            <a href="javascript:void(0)" v-on:click="onConfirmDelete('evidence',index)" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ลบ">
                              <i class="ti-trash"></i>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="form-group" v-if="!files.file && accord.legalDuty.legalType === 'EVIDENCE'">
                <div class="col-md-12 text-center is-fileinput">
                  <span class="btn btn-info btn-file">
                    <i class="zmdi zmdi-swap-vertical"></i>
                    นำเข้าเอกสาร
                    <input :required="checkValid()" style="display:" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,.pdf,.ppf,.doc,.docx" v-on:change="onBrowse(baseUrl+'/api/evidenceupload',$event)">
                  </span>
                </div>
              </div>

              <div class="form-group" v-if="files.file">
                <div class="col-md-12">
                  <progressUpload :props="files.file" :url="files.url" :fileid="$route.params.id"></progressUpload>
                </div>
              </div>

              <div class="form-actions text-center m-t-20">
                <nuxt-link to="/checklist/accord" class="btn btn-info m-r-10">
                  <i class="fa fa-chevron-left"></i> ย้อนกลับ
                </nuxt-link>
                <button type="submit" class="btn btn-success m-r-10">
                  <i class="fa fa-check"></i> บันทึก</button>
                <button type="button" class="btn btn-info" v-on:click="nextPage" v-if="isNext()">
                  ถัดไป
                  <i class="fa fa-chevron-right"></i>
                </button>
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

    let legalCategory = await http
      .get('/api/legalcategory/' + context.params.accord, {
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
      accorded: accord.data.accorded,
      legalCategory: legalCategory.data,
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
        publicYear: '',
        warnDate: '',
        expDate: ''
      },
      position: ''
    }
  },
  created: function() {
    this.$on('onCompleteUpload', function(json) {
      var obj = {}
      obj['file'] = undefined
      this.$set(this, 'files', obj)
      if (this.accord.legalDuty.legalType === 'EVIDENCE') {
        this.accord.evidenceFiles.push(JSON.parse(json))
        this.$set(this.accord, 'evidenceFiles', this.accord.evidenceFiles)
      } else {
        this.accord.licenseFiles.push(JSON.parse(json))
        this.$set(this.accord, 'licenseFiles', this.accord.licenseFiles)
      }
    })
  },
  mounted: function() {
    var files =
      this.accord.legalDuty.legalType === 'LICENSE'
        ? this.accord.licenseFiles
        : this.accord.evidenceFiles
    for (var i = 0; i < files.length; i++) {
      this.showCalendar(i, files[i])
    }
    this.calculatePosition(this.accord)
  },
  updated: function() {
    var files =
      this.accord.legalDuty.legalType === 'LICENSE'
        ? this.accord.licenseFiles
        : this.accord.evidenceFiles
    for (var i = 0; i < files.length; i++) {
      this.showCalendar(i, files[i])
    }
    this.calculatePosition(this.accord)
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
    checkValid: function() {
      var files =
        this.accord.legalDuty.legalType === 'LICENSE'
          ? this.accord.licenseFiles
          : this.accord.evidenceFiles
      if (this.accord.accorded === 'ACCORDED' && files.length === 0) {
        return true
      } else {
        return false
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
          self.$router.push({ path: '/checklist/accord' })
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
      var _self = this
      $('#warningDate-' + index)
        .datepicker({
          startDate: '+1d',
          clearBtn: true,
          language: 'th',
          thaiyear: true,
          format: 'dd/mm/yyyy',
          orientation: 'bottom left',
          autoclose: !0
        })
        .on('changeDate', selected => {
          file.warningDate = $('#warningDate-' + index).val()
          _self.date.warnDate = new Date(selected.date.valueOf())
        })
      $('#expireDate-' + index)
        .datepicker({
          startDate: '+1d',
          clearBtn: true,
          language: 'th',
          thaiyear: true,
          format: 'dd/mm/yyyy',
          orientation: 'bottom left',
          autoclose: !0
        })
        .on('changeDate', selected => {
          file.expireDate = $('#expireDate-' + index).val()
          var endDate = new Date(selected.date.valueOf())
          endDate.setDate(endDate.getDate(new Date(selected.date.valueOf())))
          $('#warningDate-' + index).datepicker('setEndDate', endDate)
          if (!_self.date.warnDate) {
            $('#warningDate-' + index).datepicker('setDate', endDate)
          }
          if (_self.date.warnDate.getTime() > endDate.getTime()) {
            $('#warningDate-' + index).datepicker('setDate', endDate)
            file.warningDate = $('#warningDate-' + index).val()
          }
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
    calculatePosition: function(accord) {
      if (!accord) return
      var data = 'Completed'
      if (
        accord.accept === null &&
        accord.accorded === null &&
        accord.approve === null
      ) {
        data = 'Owner'
      } else if (
        accord.accept === null &&
        accord.accorded !== null &&
        accord.approve === null
      ) {
        data = 'Coordinator'
      } else if (
        accord.accept !== null &&
        accord.accept !== false &&
        accord.accorded !== null &&
        accord.approve === null
      ) {
        data = 'Approver'
      } else if (accord.accept === false || accord.approve === false) {
        data = 'Owner'
      }
      this.$set(this, 'position', data)
    },
    nextPage: function() {
      var index = this.legalCategory.accords.findIndex(
        obj => obj.id === this.accord.id
      )
      if (index < this.legalCategory.accords.length - 1) {
        this.$router.push(
          '/checklist/accord/' +
            this.$route.params.accord +
            '/compliance/' +
            this.legalCategory.accords[index + 1].legalDuty.id
        )
      }
    },
    isNext: function() {
      var index = this.legalCategory.accords.findIndex(
        obj => obj.id === this.accord.id
      )
      if (index < this.legalCategory.accords.length - 1) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>

<style lang="scss">
.remark-block {
  color: #737373;
}
</style>

