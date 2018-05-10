<template>
  <section id="wrapper" class="new-login-register">
    <div class="lg-info-panel"></div>
    <div class="module-select">

      <div class="row">
        <div class="col-md-12">
          <div class="row">
            <div class="col-lg-6 col-sm-6 col-xs-12">
              <div class="white-box border-black" v-on:click="changePage('/library')">
                <h3 class="box-title">LIBRARY</h3>
                <ul class="list-inline card-block">
                  <li>
                    <i class="ti-wallet text-success"></i>
                  </li>
                  <li class="text-right">
                    <span class="counter">ห้องสมุดกฎหมาย</span>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-xs-12">
              <div class="white-box border-black" v-on:click="changePage('/checklist')">
                <h3 class="box-title">CHECKLIST</h3>
                <ul class="list-inline card-block">
                  <li>
                    <i class="ti-wallet text-success"></i>
                  </li>
                  <li class="text-right">
                    <span class="counter">COMPLIANCE</span>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-xs-12">
              <div class="white-box border-black">
                <h3 class="box-title">MANUAL</h3>
                <ul class="list-inline card-block">
                  <li>
                    <i class="ti-wallet text-success"></i>
                  </li>
                  <li class="text-right">
                    <span class="counter">คู่มือการใช้งาน</span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </section>
</template>

<script>
export default {
  methods: {
    changePage: function(url) {
      this.$router.push(url)
    }
  }
}
</script>


<style lang="scss">
.module-select {
  margin-left: 30%;
  margin-top: 10%;
  width: 600px;

  .border-black {
    border: 1px solid #666;
    .card-block {
      li {
        width: 18%;
        span {
          font-size: 25px;
          font-weight: 100;
          font-family: Rubik, sans-serif;
        }
        i {
          font-size: 25px;
        }
      }
      li:last-child {
        width: 80%;
      }
    }

    &:hover {
      border: 1px solid #999;
      background-color: #00c0ef;
      cursor: pointer;
      color: #fff;
      h3 {
        color: #fff;
      }
    }
  }
}

@media (max-width: 1350px) {
  .module-select {
    margin-left: 500px;
  }
}

@media (max-width: 1023px) {
  .module-select {
    margin: 0px auto;
    margin-top: 10%;
  }
}

@media (max-width: 767px) {
  .module-select {
    margin: 0px auto;
    width: 500px;
    margin-top: 10%;
  }
}

@media (max-width: 480px) {
  .module-select {
    margin: 0px auto;
    width: 300px;
    margin-top: 10%;
  }
}
</style>
