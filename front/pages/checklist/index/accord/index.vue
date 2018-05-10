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
              <div class="col-lg-6 col-sm-12 row-in-br filter-info" v-on:click="onFilter('ACCORDED')">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-success">
                      <i class="ti-check-box"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15" v-if="filter === 'ACCORDED'">
                      <strong>{{progress.accord}}</strong>
                    </h3>
                    <h3 class="counter text-right m-t-15" v-if="filter !== 'ACCORDED'">
                      {{progress.accord}}
                    </h3>
                  </li>
                  <li class="col-middle">
                    <h4 v-if="filter === 'ACCORDED'">
                      <strong>สอดคล้อง</strong>
                    </h4>
                    <h4 v-if="filter !== 'ACCORDED'">
                      สอดคล้อง
                    </h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12 b-0  filter-info" v-on:click="onFilter('NOT_ACCORDED')">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-danger">
                      <i class="ti-close"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15" v-if="filter === 'NOT_ACCORDED'">
                      <strong>{{progress.notAccord}}</strong>
                    </h3>
                    <h3 class="counter text-right m-t-15" v-if="filter !== 'NOT_ACCORDED'">{{progress.notAccord}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4 v-if="filter === 'NOT_ACCORDED'">
                      <strong>ไม่สอดคล้อง</strong>
                    </h4>
                    <h4 v-if="filter !== 'NOT_ACCORDED'">ไม่สอดคล้อง</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12 row-in-br b-r-none  filter-info" v-on:click="onFilter('NOT_CONCERN')">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-warning">
                      <i class="ti-share-alt"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15" v-if="filter === 'NOT_CONCERN'">
                      <strong>{{progress.notConcern}}</strong>
                    </h3>
                    <h3 class="counter text-right m-t-15" v-if="filter !== 'NOT_CONCERN'">{{progress.notConcern}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4 v-if="filter === 'NOT_CONCERN'">
                      <strong>ไม่เกี่ยวข้อง</strong>
                    </h4>
                    <h4 v-if="filter !== 'NOT_CONCERN'">ไม่เกี่ยวข้อง</h4>
                    <div class="progress">
                      <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6 col-sm-12  b-0  filter-info" v-on:click="onFilter(null)">
                <ul class="col-in">
                  <li>
                    <span class="circle circle-md bg-info">
                      <i class="ti-new-window"></i>
                    </span>
                  </li>
                  <li class="col-last">
                    <h3 class="counter text-right m-t-15" v-if="filter === null">
                      <strong>{{progress.inprogress}}</strong>
                    </h3>
                    <h3 class="counter text-right m-t-15" v-if="filter !== null">{{progress.inprogress}}</h3>
                  </li>
                  <li class="col-middle">
                    <h4 v-if="filter === null">
                      <strong>ยังไม่ดำเนินการ</strong>
                    </h4>
                    <h4 v-if="filter !== null">ยังไม่ดำเนินการ</h4>
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
                <h3 class="box-title">ประเมินความสอดคล้อง</h3>
              </div>
            </div>

            <div class="panel-group" id="accordion">

              <div class="panel panel-default" :key="index" v-for="(category,index) in categories" v-if="filter === '' || isInFilter(filter,category)">
                <div class="panel-heading">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" :href="'#collapse'+index">
                      หน่วยงาน Owner: {{category.department.name}}, ผู้ประสานงาน: {{onShow(index)}} ({{countInFilter(filter,category)}})
                    </a>
                  </h4>
                </div>
                <div :id="'collapse'+index" class="panel-collapse collapse">
                  <div class="panel-body">
                    <table class="table table-hover">
                      <thead>
                        <tr>
                          <th class="col-md-1">#</th>
                          <th class="col-md-2">ชื่อกฎหมาย</th>
                          <th class="col-md-4">หน้าที่ตามกฎหมาย</th>
                          <th class="col-md-1">ประเภท</th>
                          <th class="col-md-1 text-center">Process</th>
                          <th class="col-md-2 text-center">สถานะ</th>
                          <th class="col-md-1 text-center">ประเมิน</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr :key="accord.id" v-for="(accord,acIndex) in category.accords" v-if="filter === '' || filter === accord.accorded">
                          <td>{{acIndex+1}}</td>
                          <td>{{accord.legalDuty.compliance.legalName}}
                            <span class="label label-info" data-toggle="tooltip" v-if="accord.legalDuty.compliance.updated" :title="'อัพเดทเมื่อ '+accord.legalDuty.compliance.updateDate">อัพเดท</span>
                          </td>
                          <td>
                            <nuxt-link :to="'/checklist/accord/'+category.id+'/compliance/'+accord.legalDuty.id">
                              <div v-html="eclipsis(accord.legalDuty.name,0,200)"></div>
                            </nuxt-link>
                          </td>
                          <td>
                            <span class="" v-if="accord.legalDuty.legalType === 'LICENSE'">ใบอนุญาต</span>
                            <span class="" v-if="accord.legalDuty.legalType === 'EVIDENCE'">กฎหมายทั่วไป</span>
                          </td>
                          <td class="text-center">
                            <span :class="calculatePosition(accord) === 'Owner'?'heartbit':''">{{calculatePosition(accord)}}</span>
                          </td>
                          <td class="text-center">
                            <span class="label label-success" v-if="accord.accorded === 'ACCORDED'">สอดคล้อง</span>
                            <span class="label label-danger" v-if="accord.accorded === 'NOT_ACCORDED'">ไม่สอดคล้อง</span>
                            <span class="label label-info" v-if="accord.accorded === 'NOT_CONCERN'">ไม่เกี่ยวข้อง</span>
                            <span class="label label-primary" v-if="!accord.accorded">ยังไม่ดำเนินการ</span>
                          </td>
                          <td class="text-center">
                            <nuxt-link :to="'/checklist/accord/'+category.id+'/compliance/'+accord.legalDuty.id" class="btn btn-sm btn-info" data-toggle="tooltip" title="" title="ประเมิน">
                              <i class="ti-marker-alt"></i>
                            </nuxt-link>
                          </td>
                        </tr>
                      </tbody>
                    </table>

                    <!-- <div class="col-md-12 text-right">
                      <button class="btn btn-success" v-on:click="onApprove(category)">อนุมัติทั้งหมด</button>
                      <button class="btn btn-danger m-l-20" v-on:click="onReject(category)">ไม่อนุมัติทั้งหมด</button>
                    </div> -->

                  </div>
                </div>
              </div>

            </div>

            <!-- begin -->
            <!-- <div class="row" :key="index" v-for="(category,index) in categories" v-if="filter === '' || isInFilter(filter,category)">
              <div class="col-md-12">
                <div>
                  <strong>ผู้ดูแล(ฝ่าย/แผนก):</strong> {{category.department.name}},
                  <strong>ผู้ประสานงาน:</strong> {{onShow(category.legalGroup.coordinates)}}
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th class="col-md-1">#</th>
                        <th class="col-md-2">ชื่อกฎหมาย</th>
                        <th class="col-md-4">หน้าที่ตามกฎหมาย</th>
                        <th class="col-md-1">ประเภท</th>
                        <th class="col-md-1 text-center">Process</th>
                        <th class="col-md-2 text-center">สถานะ</th>
                        <th class="col-md-1 text-center">ประเมิน</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr :key="accord.id" v-for="(accord,acIndex) in category.accords" v-if="filter === '' || filter === accord.accorded">
                        <td>{{acIndex+1}}</td>
                        <td>{{accord.legalDuty.compliance.legalName}}
                          <span class="label label-info" data-toggle="tooltip" v-if="accord.legalDuty.compliance.updated" :title="'อัพเดทเมื่อ '+accord.legalDuty.compliance.updateDate">อัพเดท</span>
                        </td>
                        <td>
                          <nuxt-link :to="'/checklist/accord/'+category.id+'/compliance/'+accord.legalDuty.id">
                            <div v-html="accord.legalDuty.name"></div>
                          </nuxt-link>
                        </td>
                        <td>
                          <span class="" v-if="accord.legalDuty.legalType === 'LICENSE'">ใบอนุญาต</span>
                          <span class="" v-if="accord.legalDuty.legalType === 'EVIDENCE'">กฎหมายทั่วไป</span>
                        </td>
                        <td class="text-center">{{calculatePosition(accord)}}</td>
                        <td class="text-center">
                          <span class="label label-success" v-if="accord.accorded === 'ACCORDED'">สอดคล้อง</span>
                          <span class="label label-danger" v-if="accord.accorded === 'NOT_ACCORDED'">ไม่สอดคล้อง</span>
                          <span class="label label-info" v-if="accord.accorded === 'NOT_CONCERN'">ไม่เกี่ยวข้อง</span>
                          <span class="label label-primary" v-if="!accord.accorded">ยังไม่ดำเนินการ</span>
                        </td>
                        <td class="text-center">
                          <nuxt-link :to="'/checklist/accord/'+category.id+'/compliance/'+accord.legalDuty.id" class="btn btn-sm btn-info" data-toggle="tooltip" title="" title="ประเมิน">
                            <i class="ti-marker-alt"></i>
                          </nuxt-link>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div> -->
            <!-- end -->

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
      .get('/api/legalcategory/list', {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .then(response => {
        return { categories: response.data }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
  },
  mounted: function() {
    $('[data-toggle="tooltip"]').tooltip()
    this.calculateProgress()
    this.calculatePosition()
  },
  data: function() {
    return {
      progress: {},
      position: '',
      filter: '',
      temp: ''
    }
  },
  methods: {
    onLoad: function() {
      var self = this
      http
        .get('/api/legalcategory', {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$set(self, 'categories', response.data)
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
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
      return data
    },
    onFilter: function(accorded) {
      if (accorded === this.filter) {
        this.$set(this, 'filter', '')
      } else {
        this.$set(this, 'filter', accorded)
      }
    },
    isInFilter: function(filter, category) {
      var num = 0
      for (var accord in category.accords) {
        if (category.accords[accord].accorded === filter) {
          num++
        }
      }
      return num !== 0
    },
    countInFilter: function(filter, category) {
      if (filter === '') {
        return category.accords.length
      }
      var num = 0
      for (var accord in category.accords) {
        if (category.accords[accord].accorded === filter) {
          num++
        }
      }
      return num
    },
    onShow: function(index) {
      if (!this.categories[index].legalGroup.coordinates) {
        var outdex = index
        while (!this.categories[outdex].legalGroup.coordinates) {
          outdex = outdex - 1
        }
        return this.categories[outdex].legalGroup.coordinates[0].nameTh
      }
      return this.categories[index].legalGroup.coordinates[0].nameTh
    },
    eclipsis: function(str, start, length) {
      var countTags = 0
      var returnString = ''
      var writeLetters = 0
      while (!(writeLetters >= length && countTags === 0)) {
        var letter = str.charAt(start + writeLetters)
        if (letter === '<') {
          countTags++
        }
        if (letter === '>') {
          countTags--
        }
        returnString += letter
        writeLetters++
      }
      return returnString.length < str.length
        ? returnString + '&hellip;'
        : returnString
    }
  }
}
</script>

<style lang="scss">
.filter-info {
  cursor: pointer;
}
.heartbit {
  color: #fff;
  border: 5px solid #fe7376;
  background-color: #fe7376;
  border-radius: 90px;
  // -moz-animation: heartbit 1s ease-out;
  // -moz-animation-iteration-count: infinite;
  // -o-animation: heartbit 1s ease-out;
  // -o-animation-iteration-count: infinite;
  // -webkit-animation: heartbit 1s ease-out;
  // -webkit-animation-iteration-count: infinite;
  // animation-iteration-count: infinite;
}
</style>
