<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <breadcumb></breadcumb>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">ข้อมูลหลัก</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-4 m-b-10">
                <nuxt-link v-show="$route.path === '/checklist/masterdata'" to="/checklist/masterdata/import" class="btn btn-block btn-success m-b-10">นำเข้าข้อมูล</nuxt-link>
                <div id="category" class="treeview"></div>
              </div>
              <div class="col-md-8">

                <nuxt-child :selected="category" :categories="categories"></nuxt-child>

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
      .get('/api/category', { headers: { Authorization: 'bearer ' + cookie(context).AT } })
      .then((response) => {
        return { categories: response.data }
      })
      .catch((e) => {
        context.redirect('/checklist/login')
      })
  },
  data: function () {
    return { category: { id: 'null' } }
  },
  computed: mapGetters({
    initCategory: 'category/category'
  }),
  mounted: function () {
    this.renderTreeview(this.categories)
  },
  watch: {
    $route: function (val) {
      var self = this
      if (val.path === '/checklist/masterdata') {
        return http
          .get('/api/category', { headers: { Authorization: 'bearer ' + cookie(self).AT } })
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
          self.$store.dispatch('category/setcategory', data.category)
          self.$set(self, 'category', data.category)
          if (self.$route.path === '/masterdata/import') {
            self.$router.push('/masterdata')
          }
        },
        onNodeUnselected: function (event, data) {
          self.$store.dispatch('category/setcategory', { id: 'null' })
          self.$set(self, 'category', { id: 'null' })
          if (self.$route.path === '/masterdata/import') {
            self.$router.push('/masterdata')
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
          category: category,
          state: {
            selected: self.initCategory.id === category.id
          },
          tags: category.childs.length === 0 ? '' : [category.childs.length],
          nodes: category.childs.length === 0 ? '' : self.cat2node(category.childs)
        }
        nodes.push(node)
      })
      return nodes
    }
  }
}
</script>
