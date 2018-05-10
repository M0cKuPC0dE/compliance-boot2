<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">รายงาน</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>{{ $t('dashboards.title') }}</li>
            <li class="active">รายงาน</li>
          </ol>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-4">
                <h3 class="box-title">รายงาน</h3>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div>
                  <ul>
                    <li><nuxt-link to="/checklist/reports/checklist">รายงาน CheckList</nuxt-link></li>
                  </ul>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>

    </div>
  </div>
</template>
