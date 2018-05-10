<template>
  <nav class="library-navbar navbar navbar-default navbar-static-top m-b-0">
    <div class="navbar-header">
      <div class="top-left-part">
        <nuxt-link class="logo" to="/">
          <b>
            <img src="~assets/images/logo.png" alt="home" class="light-logo">
          </b>
        </nuxt-link>
      </div>

      <ul class="nav navbar-top-links navbar-right pull-right active">
        <li>
          <nuxt-link to="/checklist/login" class="waves-effect waves-light">
            <i class="ti-menu"></i> เข้าสู่ระบบ
          </nuxt-link>
        </li>
      </ul>

    </div>
  </nav>
</template>

<script>
export default {
}
</script>

<style lang="scss">
.library-navbar {
  .navbar-top-links {
    li {
      background-color: #00aeef;
      a {
        color: #fff;
      }
    }
  }
}
</style>
