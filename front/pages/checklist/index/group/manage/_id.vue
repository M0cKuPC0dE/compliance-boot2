<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.group') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('menu.dashboards') }}</li>
            <li class="active">{{ $t('menu.group') }}</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">{{ $t('menu.group') }}</h3>
              </div>
            </div>

            <form class="form-horizontal" v-on:submit.prevent="onSave">

              <div class="form-group">
                <label class="col-md-12">ผ่าย/แผนก</label>
                <div class="col-md-12">
                  <select id="department" class="form-control" required>
                    <option></option>
                    <option :value="department.id" v-for="department in departments" :key="department.id">{{department.name}}</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-12">ผู้ดูแล</label>
                <div class="col-md-12">
                  <div class="ui-widget">
                    <input id="search-owner" type="text" class="form-control" placeholder="ผู้ดูแล">
                  </div>
                </div>
                <div class="col-md-12 p-t-20 p-l-20 p-r-20">
                  <ul class="list-group list-group-full">
                    <li class="list-group-item" :key="owner.userId" v-for="owner in legalcategory.owners">
                      <span class="badge badge-danger" v-on:click="removeOwner(owner)">
                        <i class="fa fa-times"></i>
                      </span> {{owner.nameTh}} </li>
                  </ul>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-12">ผู้อนุมัติ</label>
                <div class="col-md-12">
                  <div class="ui-widget">
                    <input id="search-approver" type="text" class="form-control" placeholder="ผู้อนุมัติ">
                  </div>
                </div>
                <div class="col-md-12 p-t-20 p-l-20 p-r-20">
                  <ul class="list-group list-group-full">
                    <li class="list-group-item" :key="approver.userId" v-for="approver in legalcategory.approvers">
                      <span class="badge badge-danger" v-on:click="removeApprover(approver)">
                        <i class="fa fa-times"></i>
                      </span> {{approver.nameTh}} </li>
                  </ul>
                </div>
              </div>

              <form v-on:submit.prevent="onSearch">
                <div class="form-group">
                  <div class="col-md-9">
                    <div class="input-group bootstrap-touchspin">
                      <input id="search-tree" type="text" class="form-control" style="display: block" autocomplete="off" placeholder="ค้นหา">
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-default btn-outline">
                          <i class="fa fa-search"></i>
                        </button>
                      </span>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <button type="button" id="toggleExpand" class="btn btn-flat btn-info">Expand All</button>
                  </div>
                </div>
              </form>

              <div class="row">
                <div class="col-md-12">
                  <div id="allview" class="treeview"></div>
                </div>
              </div>

              <div class="form-actions text-center">
                <button type="submit" class="btn btn-success m-r-10">
                  <i class="fa fa-check"></i> บันทึก</button>
                <nuxt-link to="/checklist/group" class="btn btn-info">
                  <i class="fa fa-chevron-left"></i> ย้อนกลับ
                </nuxt-link>
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
  async asyncData(context) {
    let categories = await http
      .get('/api/category/compliance', {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
    let legalgroup = await http
      .get('/api/legalgroup/' + context.params.id, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
    let department = await http
      .get('/api/lookup/department', {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
    return {
      categories: categories.data,
      legalgroup: legalgroup.data,
      departments: department.data
    }
  },
  data: function() {
    return {
      category: { id: 'null' },
      legalcategory: {
        party: '',
        department: { id: '' },
        owners: [],
        approvers: [],
        legalDuties: [],
        legalGroup: {}
      }
    }
  },
  mounted: function() {
    var self = this
    this.allview(this.categories)
    this.initSuggestion()
    this.initApprover()

    $('#department').select2({ placeholder: 'เลือกฝ่าย/แผนก' })
    $('#department').on('select2:select', function(e) {
      self.$set(self.legalcategory.department, 'id', $(this).val())
    })

    $(document).on('click', '#toggleExpand', function (e) {
      var state = $(this).text()
      if (state === 'Collapse All') {
        $('#allview').treeview('collapseAll')
        $(this).text('Expand All')
      } else {
        $('#allview').treeview('expandAll')
        $(this).text('Collapse All')
      }
    })
  },
  methods: {
    onSave: function() {
      var self = this
      self.legalcategory.legalGroup = self.legalgroup
      http
        .post('/api/legalcategory', self.legalcategory, {
          headers: { Authorization: 'bearer ' + cookie(this).AT }
        })
        .then(response => {
          self.$router.push({ path: '/checklist/group' })
        })
        .catch(e => {
          self.$router.replace('/checklist/login')
        })
    },
    allview: function(categories) {
      var self = this
      $('#allview').treeview({
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        showTags: false,
        showCheckbox: true,
        data: self.cat2node(categories),
        onNodeChecked: function(event, data) {
          if (data.nodes) {
            data.nodes.forEach(function(node) {
              $('#allview').treeview('checkNode', [
                node.nodeId,
                { silent: true }
              ])
              if (node.value) {
                self.addLegalDuty(node.value)
              }
              if (node.nodes) {
                node.nodes.forEach(function(subNode) {
                  $('#allview').treeview('checkNode', [
                    subNode.nodeId,
                    { silent: true }
                  ])
                  if (subNode.value) {
                    self.addLegalDuty(subNode.value)
                  }
                })
              }
            })
          } else {
            if (data.value) {
              self.addLegalDuty(data.value)
            }
          }
        },
        onNodeUnchecked: function(event, data) {
          if (data.nodes) {
            data.nodes.forEach(function(node) {
              $('#allview').treeview('uncheckNode', [
                node.nodeId,
                { silent: true }
              ])
              if (node.value) {
                self.removeLegalDuty(node.value)
              }
              if (node.nodes) {
                node.nodes.forEach(function(subNode) {
                  $('#allview').treeview('uncheckNode', [
                    subNode.nodeId,
                    { silent: true }
                  ])
                  if (subNode.value) {
                    self.removeLegalDuty(subNode.value)
                  }
                })
              }
            })
          } else {
            if (data.value) {
              self.removeLegalDuty(data.value)
            }
          }
        }
      })
      $('#allview').treeview('collapseAll', { silent: true })
    },
    cat2node: function(categories) {
      var self = this
      var nodes = []
      if (!categories) return

      categories.forEach(function(category) {
        var node = {
          text: category.name,
          icon: 'fa fa-folder',
          selectable: false,
          nodes:
            category.childs.length === 0 ? [] : self.cat2node(category.childs)
        }
        var compNode = self.compliance2node(category.compliances)

        node.nodes = compNode.concat(node.nodes)
        node.nodes = node.nodes.length === 0 ? '' : node.nodes
        if (node.nodes.length !== 0) {
          nodes.push(node)
        }
      })
      return nodes
    },
    compliance2node: function(compliances) {
      var self = this
      var nodes = []
      if (!compliances) return

      compliances.forEach(function(compliance) {
        var node = {
          text: compliance.legalName,
          icon: 'fa fa-file-text-o',
          selectable: false,
          nodes:
            compliance.legalDuties.length === 0
              ? []
              : self.legalduty2node(compliance.legalDuties)
        }
        if (node.nodes.length !== 0) {
          nodes.push(node)
        }
      })
      return nodes
    },
    search: function(e) {
      var pattern = $('#search-tree').val()
      if (pattern === this.lastPattern) {
        return
      }
      this.lastPattern = pattern
      var tree = $('#allview').treeview(true)
      this.reset(tree)
      if (pattern.length < 3) {
        tree.clearSearch()
      } else {
        tree.search(pattern)
        var roots = tree.getSiblings(0)
        roots.push(tree.getNode(0))
        var unrelated = this.collectUnrelated(roots)
        tree.disableNode(unrelated, { silent: true })
      }
    },
    reset: function(tree) {
      tree.collapseAll()
      tree.enableAll()
    },
    collectUnrelated: function(nodes) {
      var unrelated = []
      var self = this
      $.each(nodes, function(i, n) {
        if (!n.searchResult && !n.state.expanded) {
          unrelated.push(n.nodeId)
        }
        if (!n.searchResult && n.nodes) {
          $.merge(unrelated, self.collectUnrelated(n.nodes))
        }
      })
      return unrelated
    },
    legalduty2node: function(legalDuties) {
      var self = this
      var nodes = []
      if (!legalDuties) return

      legalDuties.forEach(function(legalDuty) {
        var node = {
          text: legalDuty.name,
          icon: 'fa fa-tag',
          selectable: false,
          value: legalDuty
        }
        if (self.isChecked(legalDuty) === true) {
          nodes.push(node)
        }
      })
      return nodes
    },
    initSuggestion: function() {
      var self = this
      $('#search-owner').autocomplete({
        source: function(request, response) {
          $.ajax({
            url: 'https://api.mitrphol.com:3001/employee/find',
            dataType: 'json',
            headers: {
              'Api-Key':
                '$2y$10$Pc0lTscxUAlq9O5V8Arwau6VpgLlMEj9xLAPymFqbay2mbM3qJJee',
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            data: {
              keyword: request.term
            },
            success: function(data) {
              response(self.searchTransform(data, request.term))
            }
          })
        },
        minLength: 3,
        select: function(event, ui) {
          var checker = $.grep(self.legalcategory.owners, function(obj) {
            return obj.userId === ui.item.userId
          })
          self.legalcategory.owners = []
          if (checker.length === 0) {
            self.legalcategory.owners.push(ui.item)
            self.$set(self.legalgroup, 'owners', self.legalgroup.owners)
          }
        },
        close: function(el) {
          el.target.value = ''
        }
      })
    },
    initApprover: function() {
      var self = this
      $('#search-approver').autocomplete({
        source: function(request, response) {
          $.ajax({
            url: 'https://api.mitrphol.com:3001/employee/find',
            dataType: 'json',
            headers: {
              'Api-Key':
                '$2y$10$Pc0lTscxUAlq9O5V8Arwau6VpgLlMEj9xLAPymFqbay2mbM3qJJee',
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            data: {
              keyword: request.term
            },
            success: function(data) {
              response(self.searchTransform(data, request.term))
            }
          })
        },
        minLength: 3,
        select: function(event, ui) {
          var checker = $.grep(self.legalcategory.approvers, function(obj) {
            return obj.userId === ui.item.userId
          })
          if (checker.length === 0) {
            self.legalcategory.approvers.push(ui.item)
            self.$set(self.legalgroup, 'approvers', self.legalgroup.approvers)
          }
        },
        close: function(el) {
          el.target.value = ''
        }
      })
    },
    searchTransform: function(data, term) {
      var nodes = []
      if (data.success) {
        data.success.data.forEach(function(user) {
          var node = {
            userId: user.user_info.id,
            label: user.user_info.fullname.th,
            nameTh: user.user_info.fullname.th
          }
          nodes.push(node)
        })
      }

      var ust = [
        {
          userId: '99999999',
          label: 'วิจะยะ กลิ่นเกษร',
          nameTh: 'วิจะยะ กลิ่นเกษร'
        },
        {
          userId: '99999998',
          label: 'กิตติยา คล้ายสังข์',
          nameTh: 'กิตติยา คล้ายสังข์'
        },
        {
          userId: '99999997',
          label: 'อชิรวิชย์ สุวรรณโรจน์',
          nameTh: 'อชิรวิชย์ สุวรรณโรจน์'
        },
        {
          userId: '99999996',
          label: 'อาภรณ์ สิงห์โต',
          nameTh: 'อาภรณ์ สิงห์โต'
        },
        {
          userId: '99999995',
          label: 'กมลทิพย์ ศรีรอด',
          nameTh: 'กมลทิพย์ ศรีรอด'
        },
        {
          userId: '99999994',
          label: 'อรุณทิพย์ กวาวทอง',
          nameTh: 'อรุณทิพย์ กวาวทอง'
        },
        {
          userId: '99999993',
          label: 'วรางคณา ศิริมา',
          nameTh: 'วรางคณา ศิริมา'
        }
      ]

      let ustNode = ust.find(o => o.label.search(term) !== -1)
      if (ustNode) {
        nodes.push(ustNode)
      }

      return nodes
    },
    removeOwner: function(val) {
      var checker = $.grep(this.legalcategory.owners, function(obj) {
        return obj.userId !== val.userId
      })
      this.$set(this.legalcategory, 'owners', checker)
    },
    removeApprover: function(val) {
      var checker = $.grep(this.legalcategory.approvers, function(obj) {
        return obj.userId !== val.userId
      })
      this.$set(this.legalcategory, 'approvers', checker)
    },
    addLegalDuty: function(legalDuty) {
      this.legalcategory.legalDuties.push(legalDuty)
      this.$set(
        this.legalcategory,
        'legalDuties',
        this.legalcategory.legalDuties
      )
    },
    removeLegalDuty: function(legalDuty) {
      var legalDuties = $.grep(this.legalcategory.legalDuties, function(elm) {
        return elm.id !== legalDuty.id
      })
      this.$set(this.legalcategory, 'legalDuties', legalDuties)
    },
    isChecked: function(val) {
      if (this.legalgroup.legalDuties.filter(e => e.id === val.id).length > 0) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>
