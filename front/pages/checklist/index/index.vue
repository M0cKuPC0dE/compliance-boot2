<template>
  <div id="page-wrapper">
    <div class="container">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.dashboard') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li class="active">{{ $t('menu.dashboard') }}</li>
          </ol>
        </div>
      </div>

      <div class="row" v-if="authority != 'Administrator'">
        <div class="col-md-12">
          <div id="box-wrapper">
            <img v-if="loading" src="~/assets/images/ajax-loader.gif" style="display:block;margin:auto"></img>
            <canvas id="pie"></canvas>
            <canvas id="bar"></canvas>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import Chart from 'chart.js'
import http from '~/utils/http'
import cookie from '~/utils/cookie'
import { mapGetters } from 'vuex'

export default {
  async asyncData(context) {
    // var groups = {}
    // if (groups === {}) {
    //   groups = await http
    //     .get('/api/legalgroup', {
    //       headers: { Authorization: 'bearer ' + cookie(context).AT }
    //     })
    //     .catch(e => {
    //       context.redirect('/checklist/login')
    //     })
    // }
    // var category = await http
    //   .get('/api/legalcategory/list', {
    //     headers: { Authorization: 'bearer ' + cookie(context).AT }
    //   })
    //   .catch(e => {
    //     self.$router.replace('/checklist/login')
    //   })
    // return {
    //   groups: groups.data,
    //   categories: category.data
    // }
  },
  computed: mapGetters({
    authority: 'auth/authority'
  }),
  data: function() {
    return {
      categories: [],
      groups: [],
      loading: true
    }
  },
  mounted: function() {
    var self = this

    if (this.authority === 'Owner' || this.authority === 'Approver') {
      http
        .get('/api/legalcategory/list', {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(function(resp) {
          console.log(resp)
          self.$set(self, 'categories', resp.data)
          self.$set(self, 'loading', false)
          self.renderPie()
          self.renderBar()
        })
    }

    if (this.authority === 'Coordinator') {
      http
        .get('/api/legalgroup/authority/' + cookie(this).AU, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(function(resp) {
          console.log(resp)
          self.$set(self, 'groups', resp.data)
          self.$set(self, 'loading', false)
          self.renderPie()
          self.renderBar()
        })
    }
    // if (this.authority !== 'Administrator') {
    //   this.renderPie()
    //   this.renderBar()
    // }
  },
  methods: {
    renderPie: function() {
      var self = this
      var ctx = document.getElementById('pie')
      var myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          datasets: [
            {
              data: self.getPie(),
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'
              ],
              borderColor: ['rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)'],
              borderWidth: 1
            }
          ],
          labels: ['Incomplete', 'Completed']
        }
      })
      console.log(myChart)
    },
    renderBar: function() {
      var self = this
      var ctx = document.getElementById('bar')

      document.getElementById('box-wrapper').style.height =
        self.getBar().labels.length * 20 + 500 + 'px'

      var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
          labels: self.getBar().labels,
          datasets: [
            {
              label: 'สอดคล้อง',
              data: self.getBar().values[0],
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1
            },
            {
              label: 'ไม่สอดคล้อง',
              data: self.getBar().values[1],
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255,99,132,1)',
              borderWidth: 1
            },
            {
              label: 'ยังไม่ประเมิน',
              data: self.getBar().values[2],
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1
            }
          ]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            xAxes: [
              {
                stacked: true
              }
            ],
            yAxes: [
              {
                stacked: true,
                ticks: {
                  beginAtZero: true
                }
              }
            ]
          }
        }
      })
      console.log(myChart)
    },
    coordinatorPosition: function() {
      var data = {
        owner: 0,
        coordinator: 0,
        approver: 0,
        completed: 0,
        incomplete: 0,
        total: 0
      }
      this.groups.forEach(function(group) {
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
      })

      data.completed =
        data.total - (data.owner + data.coordinator + data.approver)
      data.incomplete = data.owner + data.coordinator + data.approver
      return data
    },
    coordinatorProgress: function() {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0,
        total: 0
      }
      var resp = {
        labels: [],
        values: []
      }
      this.groups.forEach(function(group) {
        group.legalCategories.forEach(function(category) {
          resp.labels.push(category.department.name)
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
          resp.values.push([data.accord, data.notAccord, data.inprogress])
          data = {
            accord: 0,
            notAccord: 0,
            notConcern: 0,
            inprogress: 0,
            total: 0
          }
        })
      })
      return resp
    },
    ownerPosition: function() {
      var data = {
        owner: 0,
        coordinator: 0,
        approver: 0,
        completed: 0,
        incomplete: 0,
        total: 0
      }
      this.categories.forEach(function(category) {
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
      data.incomplete = data.owner + data.coordinator + data.approver
      return data
    },
    ownerProgress: function() {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0,
        total: 0
      }
      var resp = {
        labels: [],
        values: []
      }
      this.categories.forEach(function(category) {
        resp.labels.push(category.department.name)
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
        resp.values.push([data.accord, data.notAccord, data.inprogress])
        data = {
          accord: 0,
          notAccord: 0,
          notConcern: 0,
          inprogress: 0,
          total: 0
        }
      })
      return resp
    },
    approverPosition: function() {
      var data = {
        owner: 0,
        coordinator: 0,
        approver: 0,
        completed: 0,
        incomplete: 0,
        total: 0
      }
      this.categories.forEach(function(category) {
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
      data.incomplete = data.owner + data.coordinator + data.approver
      return data
    },
    approverProgress: function() {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0,
        total: 0
      }
      var resp = {
        labels: [],
        values: []
      }
      this.categories.forEach(function(category) {
        resp.labels.push(category.department.name)
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
        resp.values.push([data.accord, data.notAccord, data.inprogress])
        data = {
          accord: 0,
          notAccord: 0,
          notConcern: 0,
          inprogress: 0,
          total: 0
        }
      })
      return resp
    },
    getPie: function() {
      var data = {}
      if (this.authority === 'Coordinator') {
        data = this.coordinatorPosition()
      } else if (this.authority === 'Owner') {
        data = this.ownerPosition()
      } else if (this.authority === 'Approver') {
        data = this.approverPosition()
      }
      return [data.incomplete, data.completed]
    },
    getBar: function() {
      var data = {}
      if (this.authority === 'Coordinator') {
        data = this.coordinatorProgress()
      } else if (this.authority === 'Owner') {
        data = this.ownerProgress()
      } else if (this.authority === 'Approver') {
        data = this.approverProgress()
      }
      var accord = []
      var notAccord = []
      var inprogress = []
      data.values.forEach(function(value) {
        accord.push(value[0])
        notAccord.push(value[1])
        inprogress.push(value[2])
      })
      return {
        labels: data.labels,
        values: [accord, notAccord, inprogress]
      }
    }
  }
}
</script>

