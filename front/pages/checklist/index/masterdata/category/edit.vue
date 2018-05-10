<template>
  <form v-on:submit.prevent="onSave">
    <div class="form-body">
      <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label class="control-label">{{ $t('category.name') }}</label>
            <input v-model="category.name" type="text" class="form-control" placeholder="ชื่อหมวดหมู่" required>
          </div>
        </div>
      </div>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-success m-r-10">
        <i class="fa fa-check"></i> บันทึก</button>
      <button type="button" class="btn btn-danger m-r-10" data-toggle="modal" data-target="#remove-category-modal">
        <i class="fa fa-times"></i> ลบหมวดหมู่</button>
      <nuxt-link to="/checklist/masterdata" class="btn btn-info">
        <i class="fa fa-chevron-left"></i> ย้อนกลับ
      </nuxt-link>
    </div>

    <div id="remove-category-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title">{{ $t('message.delete.confirm') }}</h4>
          </div>
          <div class="modal-body">
            {{ $t('message.delete.description') }}
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">{{ $t('buttons.close') }}</button>
            <button type="button" class="btn btn-danger waves-effect waves-light" v-on:click="onDelete">{{ $t('buttons.delete') }}</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
/* global $ */
import { mapGetters } from 'vuex'
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  props: ['selected', 'categories'],
  data: function () {
    return {
      category: { id: 'null', name: '' }
    }
  },
  watch: {
    selected: function (val) {
      this.$set(this, 'category', $.extend({}, val))
    }
  },
  computed: mapGetters({
    initCategory: 'category/category'
  }),
  mounted: function () {
    this.$set(this, 'category', $.extend({}, this.initCategory))
  },
  methods: {
    onSave: function () {
      var self = this
      http.post('/api/category/update', self.category, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then(response => {
          self.$router.push({ path: '/checklist/masterdata' })
        })
        .catch((e) => {
          self.$router.replace('/login')
        })
    },
    onDelete: function () {
      var self = this
      $('#remove-category-modal').modal('hide')
      return http
        .delete('/api/category/' + this.category.id, { headers: { Authorization: 'bearer ' + cookie(this).AT } })
        .then((response) => {
          self.$router.push('/checklist/masterdata')
        })
        .catch((e) => {
          self.$router.push('/checklist/masterdata')
        })
    }
  }
}
</script>
