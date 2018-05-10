<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('menu.group') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('menu.dashboard') }}</li>
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
                <label class="col-md-12">หน่วยงาน</label>
                <div class="col-md-12">
                  <select class="form-control" v-model="selLegalGroup" required>
                    <option value="">เลือกหน่วยงาน</option>
                    <option :key="lg.id" v-for="lg in legalgroups" :value="lg" v-if="lg.legalDuties.length === 0">{{lg.buName}}</option>
                  </select>
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
    let legalgroups = await http
      .get('/api/legalgroup/authority/' + cookie(context).AU, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
    return {
      categories: categories.data,
      legalgroup: legalgroup.data,
      legalgroups: legalgroups.data
    }
  },
  data: function() {
    return {
      category: { id: 'null' },
      selLegalGroup: ''
    }
  },
  mounted: function() {
    this.allview(this.categories)
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
      this.selLegalGroup.legalDuties = this.legalgroup.legalDuties
      http
        .post('/api/legalgroup', self.selLegalGroup, {
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
        nodes.push(node)
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
        nodes.push(node)
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
          state: {
            checked: self.isChecked(legalDuty)
          },
          value: legalDuty
        }
        nodes.push(node)
      })
      return nodes
    },
    addLegalDuty: function(legalDuty) {
      this.legalgroup.legalDuties.push(legalDuty)
      this.$set(this.legalgroup, 'legalDuties', this.legalgroup.legalDuties)
    },
    removeLegalDuty: function(legalDuty) {
      var legalDuties = $.grep(this.legalgroup.legalDuties, function(elm) {
        return elm.id !== legalDuty.id
      })
      this.$set(this.legalgroup, 'legalDuties', legalDuties)
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
