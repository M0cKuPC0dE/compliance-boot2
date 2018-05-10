<template>
  <div>
    <LibraryNavbar></LibraryNavbar>
    <div class="library-wrapper">
      <div class="container-fluid">
        <div class="row m-t-10 p-t-20">
          <div class="col-md-12">
            <form v-on:submit.prevent="onSearch">
              <div class="form-group">
                <div class="input-group bootstrap-touchspin">
                  <input id="search-tree" type="text" class="form-control" style="display: block" autocomplete="off" placeholder="ค้นหา">
                  <span class="input-group-btn">
                    <button type="button" class="btn btn-default btn-outline">
                      <i class="fa fa-search"></i>
                    </button>
                  </span>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div id="allview" class="treeview"></div>
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
                        <span class="label label-info" data-toggle="tooltip" v-if="compliance.updated" :title="'อัพเดทเมื่อ '+compliance.updateDate">อัพเดท</span> {{compliance.legalName}}
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="col-md-12">
                            <strong>{{ $t('compliance.effectivedate') }}</strong>
                          </label>
                          <div class="col-md-12">
                            {{compliance.effectiveDate}}
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
                            {{compliance.department}}
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6 col-sm-12">
                        <div class="form-group">
                          <label class="col-md-12">
                            <strong>{{ $t('compliance.ministry') }}</strong>
                          </label>
                          <div class="col-md-12">
                            {{compliance.ministry}}
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-md-12">
                        <strong>{{ $t('compliance.important') }}</strong>
                      </label>
                      <div class="col-md-12">
                        <!-- <span style="white-space: pre-wrap;">{{compliance.important}}</span> -->
                        <div v-html="compliance.important"></div>
                      </div>
                    </div>

                    <div class="form-group">
                      <div class="col-md-12">
                        <div>
                          <table class="table table-striped table-hover">
                            <tbody>
                              <tr :key="file.index" v-for="(file,index) in compliance.legalFiles">
                                <td>{{file.name}}</td>
                                <td class="text-center">
                                  <a :href="baseUrl+'/public/download/legal/'+file.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="ดาวน์โหลด">
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
import LibraryNavbar from '~/components/LibraryNavbar'

export default {
  layout: 'index',
  async asyncData(context) {
    let categories = await http.get('/public/library')
    return {
      categories: categories.data
    }
  },
  components: {
    LibraryNavbar
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl,
      lastPattern: '',
      compliance: {
        legalName: '',
        year: '',
        publicDate: '',
        effectiveDate: '',
        department: '',
        ministry: '',
        importain: '',
        legalDuty: ''
      }
    }
  },
  mounted: function() {
    this.allview(this.categories)
    $('#search-tree').on('keyup', this.search)
  },
  methods: {
    onSearch: function() {
      return false
    },
    allview: function(categories) {
      var self = this
      $('#allview').treeview({
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        data: self.cat2node(categories),
        onNodeSelected: function(event, data) {
          http
            .get('/public/library/compliance/' + data.value)
            .then(response => {
              self.$set(self, 'compliance', response.data)
              $('.bs-example-modal-lg').modal('show')
              $('.bs-example-modal-lg').on('hidden.bs.modal', function() {
                $('#allview').treeview('unselectNode', [
                  data.nodeId,
                  { silent: true }
                ])
              })
            })
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
      var nodes = []
      if (!compliances) return

      compliances.forEach(function(compliance) {
        var node = {
          text:
            compliance.legalName +
            '<span style="display:none">' +
            compliance.tags +
            '</span>' +
            (compliance.updated
              ? ' <span class="label label-info" title="อัพเดทเมื่อ ' +
                compliance.updateDate +
                '">อัพเดท</span>'
              : ''),
          icon: 'fa fa-file-text-o',
          selectable: true,
          value: compliance.id
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
    }
  }
}
</script>

<style lang="scss">
.library-wrapper {
  background-color: #edf1f5;
  margin-top: 60px;
  width: 100%;
  min-height: calc(100vh - 60px);
  .row {
    background-color: #fff;
  }
  #allview .node-disabled {
    display: none;
  }
}
</style>
