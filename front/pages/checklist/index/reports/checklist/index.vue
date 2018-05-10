<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">รายงาน</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('dashboards.title') }}</li>
            <li class="active">รายงาน</li>
          </ol>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">รายงาน CheckList</h3>
              </div>
            </div>

            <form class="form-horizontal" v-on:submit.prevent="onFilter()">
              <div class="form-group">
                <div class="col-md-4">
                  <select id="owner" class="form-control"  v-if="authority === 'Coordinator'">
                    <option value='null'>-- เลือก Owner --</option>
                    <option :key="index" :value="owner" v-for="(owner,index) in uniqueOwner()">{{owner}}</option>
                  </select>
                </div>

                <div class="col-md-4">
                  <select id="department" class="form-control">
                    <option value='null'>-- เลือกฝ่าย --</option>
                    <option :key="index" :value="department" v-for="(department,index) in uniqueDepartment()">{{department}}</option>
                  </select>
                </div>

                <div class="col-md-4">
                  <select id="legalName" class="form-control">
                    <option value='null'>-- เลือกกฎหมาย --</option>
                    <option :key="index" :value="legalName" v-for="(legalName,index) in uniqueLegalName()">{{legalName}}</option>
                  </select>
                </div>
              </div>

              <div class="form-actions text-center m-t-20">
                <button type="submit" class="btn btn-success m-l-10">สร้างรายงาน</button>
              </div>

            </form>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <table class="table table-striped table-hover" :key="index" v-for="(group,index) in filterGroup">
                    <thead v-if="index === 0">
                      <tr>
                        <th>กฎหมาย</th>
                        <th>ฝ่าย/แผนก</th>
                        <th class="text-center">No</th>
                        <th class="text-center">หน้าที่ตามกฎหมาย</th>
                        <th class="text-center">ผลการประเมิน</th>
                        <th class="text-center col-md-2">Summary</th>
                      </tr>
                    </thead>
                    <tbody :key="lcIndex" v-for="(legalCategorie,lcIndex) in group.legalCategories">
                      <tr :key="acIndex" v-for="(accord,acIndex) in legalCategorie.accords" v-if="renderFilter(legalCategorie.owners[0].nameTh,legalCategorie.department.name,accord.legalDuty.compliance.legalName)">
                        <td>{{accord.legalDuty.compliance.legalName}}</td>
                        <td>{{legalCategorie.department.name}}</td>
                        <td>{{acIndex + 1}}</td>
                        <td>
                          <div v-html="accord.legalDuty.name"></div>
                        </td>
                        <td>{{asText(accord.accorded)}}</td>
                        <td style="vertical-align: middle;" :rowspan="legalCategorie.accords.length" v-if="acIndex === 0" v-html="sumAccorded(legalCategorie)">
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
  </div>
</template>

<script>
/* global $ */
import http from '~/utils/http'
import cookie from '~/utils/cookie'
import { mapGetters } from 'vuex'

export default {
  asyncData: function(context) {
    return http
      .get('/api/report/checklist/' + cookie(context).AU, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .then(response => {
        return { groups: response.data }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
  },
  computed: mapGetters({
    authority: 'auth/authority'
  }),
  data: function() {
    return {
      owner: 'null',
      department: 'null',
      legalName: 'null',
      filterGroup: []
    }
  },
  mounted: function() {
    var self = this

    $('#owner').select2({ placeholder: 'เลือก Owner' })
    $('#owner').on('select2:select', function(e) {
      self.$set(self, 'owner', $(this).val())
    })

    $('#department').select2({ placeholder: 'เลือกฝ่าย' })
    $('#department').on('select2:select', function(e) {
      self.$set(self, 'department', $(this).val())
    })

    $('#legalName').select2({ placeholder: 'เลือกกฎหมาย' })
    $('#legalName').on('select2:select', function(e) {
      self.$set(self, 'legalName', $(this).val())
    })
  },
  updated: function() {
    $('#owner').addClass('select2-hidden-accessible')
    $('#department').addClass('select2-hidden-accessible')
    $('#legalName').addClass('select2-hidden-accessible')
  },
  methods: {
    asText: function(text) {
      if (text === 'ACCORDED') {
        return 'สอดคล้อง'
      } else if (text === 'NOT_ACCORDED') {
        return 'ไม่สอดคล้อง'
      } else {
        return 'ยังไม่ดำเนินการ'
      }
    },
    sumAccorded: function(legalCategorie) {
      var response = {
        accord: 0,
        notAccord: 0,
        other: 0
      }

      legalCategorie.accords.forEach(accord => {
        if (accord.accorded === 'ACCORDED') {
          response.accord = response.accord + 1
        } else if (accord.accorded === 'NOT_ACCORDED') {
          response.notAccord = response.notAccord + 1
        } else {
          response.other = response.other + 1
        }
      })

      return (
        'สอดคล้อง = ' +
        response.accord +
        '<br>ไม่สอดคล้อง = ' +
        response.notAccord +
        '<br>ยังไม่ดำเนินการ = ' +
        response.other +
        ''
      )
    },
    uniqueOwner: function() {
      var owners = []
      this.groups.forEach(group => {
        group.legalCategories.forEach(legalCategorie => {
          owners.push(legalCategorie.owners[0].nameTh)
        })
      })
      return owners.filter((v, i, a) => a.indexOf(v) === i)
    },
    uniqueDepartment: function() {
      var departments = []
      this.groups.forEach(group => {
        group.legalCategories.forEach(legalCategorie => {
          departments.push(legalCategorie.department.name)
        })
      })
      return departments.filter((v, i, a) => a.indexOf(v) === i)
    },
    uniqueLegalName: function() {
      var legalNames = []
      this.groups.forEach(group => {
        group.legalCategories.forEach(legalCategorie => {
          legalCategorie.accords.forEach(accord => {
            legalNames.push(accord.legalDuty.compliance.legalName)
          })
        })
      })
      return legalNames.filter((v, i, a) => a.indexOf(v) === i)
    },
    onFilter: function() {
      this.$set(this, 'filterGroup', this.groups)
    },
    renderFilter: function(owner, department, legalName) {
      var ot = false
      var dt = false
      var lt = false
      if (this.owner === 'null' || this.owner === owner) {
        ot = true
      }
      if (this.department === 'null' || this.department === department) {
        dt = true
      }
      if (this.legalName === 'null' || this.legalName === legalName) {
        lt = true
      }
      return ot && dt && lt
    }
  }
}
</script>
