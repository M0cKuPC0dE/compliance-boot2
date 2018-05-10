<template>
  <div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav" style="overflow: hidden; width: auto; height: 100%;">
      <div class="sidebar-head">
        <h3>
          <span class="fa-fw open-close">
            <i class="ti-close ti-menu"></i>
          </span>
          <span class="hide-menu">เมนู</span>
        </h3>
      </div>
      <ul class="nav" id="side-menu">
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/masterdata" class="waves-effect">
            <i class="zmdi zmdi-archive fa-fw"></i>
            <span class="hide-menu">{{ $t('menu.masterdata') }}</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/legalduty" class="waves-effect">
            <i class="fa fa-id-card-o fa-fw"></i>
            <span class="hide-menu">หน้าที่ตามกฎหมาย</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/coordinator" class="waves-effect">
            <i class="fa fa-users fa-fw"></i>
            <span class="hide-menu">จัดการผู้ประสานงาน</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/group" class="waves-effect">
            <i class="zmdi zmdi-group fa-fw"></i>
            <span class="hide-menu">{{ $t('menu.group') }}</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Coordinator'">
          <nuxt-link to="/checklist/category" class="waves-effect">
            <i class="zmdi zmdi-ungroup fa-fw"></i>
            <span class="hide-menu">จัดหมวดหมู่กฎหมาย</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Owner'">
          <nuxt-link to="/checklist/accord" class="waves-effect">
            <i class="zmdi zmdi-check-all fa-fw"></i>
            <span class="hide-menu">ประเมินความสอดคล้อง</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Approver'">
          <nuxt-link to="/checklist/approve" class="waves-effect">
            <i class="zmdi zmdi-check-circle-u fa-fw"></i>
            <span class="hide-menu">อนุมัติ</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/language" class="waves-effect">
            <i class="zmdi zmdi-text-format fa-fw"></i>
            <span class="hide-menu">{{ $t('menu.languageupload') }}</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Administrator'">
          <nuxt-link to="/checklist/usermanage" class="waves-effect">
            <i class="fa fa-user fa-fw"></i>
            <span class="hide-menu">จัดการผู้ใช้งาน</span>
          </nuxt-link>
        </li>
        <li v-if="false">
          <nuxt-link to="/checklist/uploaddocument" class="waves-effect">
            <i class="zmdi zmdi-text-format fa-fw"></i>
            <span class="hide-menu">upload document</span>
          </nuxt-link>
        </li>
        <li v-if="authority === 'Coordinator' || authority === 'Owner'">
          <nuxt-link to="/checklist/reports" class="waves-effect">
            <i class="zmdi zmdi-receipt fa-fw"></i>
            <span class="hide-menu">รายงาน</span>
          </nuxt-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: mapGetters({
    authenticated: 'auth/authenticated',
    name: 'auth/name',
    authority: 'auth/authority'
  })
}
</script>
