<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">จัดการผู้ใช้งาน</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('menu.dashboard') }}</li>
            <li class="active">จัดการผู้ใช้งาน</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">จัดการผู้ใช้งาน</h3>
              </div>
            </div>

            <form class="form-horizontal" v-on:submit.prevent="onSave">

              <div class="form-group">
                <label class="col-md-12">ค้นหาผู้ใช้งาน</label>
                <div class="col-md-12">
                  <div class="ui-widget">
                    <input id="search-coordinate" type="text" class="form-control" placeholder="ผู้ประสานงาน">
                  </div>
                </div>
                <div class="col-md-12 p-t-20 p-l-20 p-r-20">
                  <ul class="list-group list-group-full">
                    <li class="list-group-item" :key="user.username" v-for="user in users">
                      <span class="badge badge-danger" v-on:click="removeUser(user)">
                        <i class="fa fa-times"></i>
                      </span> {{user.nameTh}} </li>
                  </ul>
                </div>
              </div>
            </form>

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
  asyncData: function (context) {
    return http
      .get('/api/user/administrator', { headers: { Authorization: 'bearer ' + cookie(context).AT } })
      .then((response) => {
        return { users: response.data }
      })
      .catch((e) => {
        context.redirect('/checklist/login')
      })
  },
  mounted: function () {
    this.initSuggestion()
  },
  methods: {
    onSave: function (user) {
      var self = this
      http.post('/api/user/administrator', user, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then(response => {
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    },
    onDelete: function (user) {
      var self = this
      http.post('/api/user/administrator/delete', user, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then(response => {
        })
        .catch((e) => {
          self.$router.replace('/checklist/login')
        })
    },
    initSuggestion: function () {
      var self = this
      $('#search-coordinate').autocomplete({
        source: function (request, response) {
          $.ajax({
            url: 'https://api.mitrphol.com:3001/employee/find',
            dataType: 'json',
            headers: {
              'Api-Key': '$2y$10$Pc0lTscxUAlq9O5V8Arwau6VpgLlMEj9xLAPymFqbay2mbM3qJJee',
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            data: {
              keyword: request.term
            },
            success: function (data) {
              response(self.searchTransform(data, request.term))
            }
          })
        },
        minLength: 3,
        select: function (event, ui) {
          var checker = $.grep(self.users, function (obj) {
            return obj.userId === ui.item.userId
          })
          if (checker.length === 0) {
            self.users.push(ui.item)
            self.$set(self, 'users', self.users)
            self.onSave(ui.item)
          }
        },
        close: function (el) {
          el.target.value = ''
        }
      })
    },
    searchTransform: function (data, term) {
      var nodes = []
      if (data.success) {
        data.success.data.forEach(function (user) {
          var node = {
            userId: user.user_info.id,
            label: user.user_info.fullname.th,
            nameTh: user.user_info.fullname.th
          }
          nodes.push(node)
        })
      }

      var ust = [
        { userId: '99999999', label: 'วิจะยะ กลิ่นเกษร', nameTh: 'วิจะยะ กลิ่นเกษร' },
        { userId: '99999998', label: 'กิตติยา คล้ายสังข์', nameTh: 'กิตติยา คล้ายสังข์' },
        { userId: '99999997', label: 'อชิรวิชย์ สุวรรณโรจน์', nameTh: 'อชิรวิชย์ สุวรรณโรจน์' },
        { userId: '99999996', label: 'อาภรณ์ สิงห์โต', nameTh: 'อาภรณ์ สิงห์โต' },
        { userId: '99999995', label: 'กมลทิพย์ ศรีรอด', nameTh: 'กมลทิพย์ ศรีรอด' },
        { userId: '99999994', label: 'อรุณทิพย์ กวาวทอง', nameTh: 'อรุณทิพย์ กวาวทอง' },
        { userId: '99999993', label: 'วรางคณา ศิริมา', nameTh: 'วรางคณา ศิริมา' }
      ]

      let ustNode = ust.find(o => o.label.search(term) !== -1)
      if (ustNode) {
        nodes.push(ustNode)
      }

      return nodes
    },
    removeUser: function (val) {
      var checker = $.grep(this.users, function (obj) {
        return obj.userId !== val.userId
      })
      this.onDelete(val)
      this.$set(this, 'users', checker)
    }
  }
}
</script>
