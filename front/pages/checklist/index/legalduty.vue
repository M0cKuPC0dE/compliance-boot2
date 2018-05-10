<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <breadcumb></breadcumb>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">หน้าที่ตามกฎหมาย</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-4 m-b-10">
                <nuxt-link v-show="$route.path === '/checklist/masterdata'" to="/checklist/masterdata/import" class="btn btn-block btn-success m-b-10">นำเข้าข้อมูล</nuxt-link>
                <div id="category" class="treeview"></div>
              </div>
              <div class="col-md-8">

                <nuxt-child :selected="compliance" :categories="categories"></nuxt-child>

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
import { mapGetters } from 'vuex'
import http from '~/utils/http'
import cookie from '~/utils/cookie'
import Breadcumb from '~/components/Breadcumb'

export default {
  components: {
    Breadcumb
  },
  asyncData: function (context) {
    return http
      .get('/api/category/compliance', { headers: { Authorization: 'bearer ' + cookie(context).AT } })
      .then((response) => {
        return { categories: response.data }
      })
      .catch((e) => {
        context.redirect('/checklist/login')
      })
  },
  data: function () {
    return { compliance: { id: 'null' } }
  },
  computed: mapGetters({
    initCompliance: 'category/compliance'
  }),
  mounted: function () {
    this.renderTreeview(this.categories)
  },
  watch: {
    $route: function (val) {
      var self = this
      if (val.path === '/checklist/legalduty') {
        return http
          .get('/api/category/compliance', { headers: { Authorization: 'bearer ' + cookie(self).AT } })
          .then((response) => {
            self.renderTreeview(response.data)
          })
          .catch((e) => {
            self.$router.replace('/checklist/login')
          })
      }
    }
  },
  methods: {
    renderTreeview: function (categories) {
      var self = this
      $('#category').treeview({
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        showTags: true,
        data: self.cat2node(categories),
        onNodeSelected: function (event, data) {
          if (data.value) {
            self.$store.dispatch('category/setcompliance', data.value)
            self.$set(self, 'compliance', data.value)
          }
        }
      })
      $('#category').treeview('collapseAll', { silent: true })
    },
    cat2node: function (categories) {
      var self = this
      var nodes = []
      if (!categories) return

      categories.forEach(function (category) {
        var node = {
          text: category.name,
          icon: 'fa fa-folder',
          selectable: false,
          nodes: category.childs.length === 0 ? [] : self.cat2node(category.childs)
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
    compliance2node: function (compliances) {
      var nodes = []
      if (!compliances) return

      compliances.forEach(function (compliance) {
        var node = {
          text: compliance.legalName,
          icon: 'fa fa-file-text-o',
          selectable: true,
          state: {
            selected: self.initCompliance && self.initCompliance.id === compliance.id
          },
          value: compliance
        }
        nodes.push(node)
      })
      return nodes
    }
  }
}
</script>
