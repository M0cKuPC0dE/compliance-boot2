<template>
  <div id="page-wrapper">
    <div class="container-fluid">

      <div class="row bg-title">
        <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
          <h4 class="page-title">{{ $t('compliance.legalname') }}</h4>
        </div>
        <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
          <ol class="breadcrumb">
            <li>หน้าหลัก</li>
            <li class="active">{{ $t('compliance.legalname') }}</li>
          </ol>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="white-box">

            <div class="row">
              <div class="col-md-12">
                <h3 class="box-title">{{compliance.legalDuty}}</h3>
              </div>
            </div>

            <form class="form-horizontal">

              <div class="form-group">
                <label class="col-md-12">
                  <strong>{{ $t('compliance.category') }}</strong>
                </label>
                <div class="col-md-12">
                  {{compliance.category.name}}
                </div>
              </div>

              <div class="row">
                <div class="col-md-3 col-sm-12">
                  <div class="form-group">
                    <label class="col-md-12">
                      <strong>{{ $t('compliance.status') }}</strong>
                    </label>
                    <div class="col-md-12">
                      <span class="label label-success" v-if="compliance.status === 'ACTIVE'">{{compliance.status}}</span>
                      <span class="label label-warning" v-if="compliance.status === 'INACTIVE'">{{compliance.status}}</span>
                    </div>
                  </div>
                </div>

                <div class="col-md-6 col-md-offset-3 col-sm-12">
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

              <div class="form-group">
                <label class="col-md-12">
                  <strong>{{ $t('compliance.legalname') }}</strong>
                </label>
                <div class="col-md-12">
                  {{compliance.legalName}}
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

              <div class="row">
                <div class="col-md-6 col-sm-12">
                  <div class="form-group">
                    <label class="col-md-12">
                      <strong>หน่วยงานที่เกี่ยวข้อง</strong>
                    </label>
                    <div class="col-md-12">
                    </div>
                  </div>
                </div>

                <div class="col-md-6 col-sm-12">
                  <div class="form-group">
                    <label class="col-md-12">
                      <strong>ฝ่ายที่เกี่ยวข้อง</strong>
                    </label>
                    <div class="col-md-12">
                    </div>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 col-sm-12">
                  <div class="form-group">
                    <label class="col-md-12">
                      <strong>แผนกที่เกี่ยวข้อง</strong>
                    </label>
                    <div class="col-md-12">
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
                <label class="col-md-12">
                  <strong>Keyword Tags</strong>
                </label>
                <div class="col-md-12">
                  {{compliance.tags}}
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-12">
                  <strong>หน้าที่ตามกฎหมาย</strong>
                </label>
                <div class="col-md-12">
                  <div>
                    <table class="table table-striped table-hover">
                      <tbody>
                        <tr :key="index" v-for="(legalDuty,index) in compliance.legalDuties">
                          <td>{{legalDuty.name}}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
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
                            <a :href="baseUrl+'/public/download/legal'+file.id" class="btn btn-sm btn-info m-r-5" data-toggle="tooltip" title="" title="ดาวน์โหลด">
                              <i class="fa fa-download"></i>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <div class="col-md-12 m-b-10 text-center">
                  <nuxt-link :to="'/checklist/masterdata/compliance/edit/'+compliance.id" class="btn btn-warning m-r-10">แก้ไขข้อกฎหมาย</nuxt-link>
                  <nuxt-link to="/checklist/masterdata" class="btn btn-info">
                    <i class="fa fa-chevron-left"></i> ย้อนกลับ</nuxt-link>
                </div>
              </div>

            </form>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import http from '~/utils/http'
import cookie from '~/utils/cookie'

export default {
  asyncData: function(context) {
    return http
      .get('/api/compliance/' + context.params.id, {
        headers: { Authorization: 'bearer ' + cookie(context).AT }
      })
      .then(response => {
        return { compliance: response.data }
      })
      .catch(e => {
        context.redirect('/checklist/login')
      })
  },
  data: function() {
    return {
      baseUrl: process.env.baseUrl
    }
  }
}
</script>
