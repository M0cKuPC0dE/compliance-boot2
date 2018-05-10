<template>
  <div class="row">
    <div class="col-sm-12">
      <div class="white-box">
        <div class="row row-in">
          <div class="col-lg-6 col-sm-12 row-in-br">
            <ul class="col-in">
              <li>
                <span class="circle circle-md bg-success">
                  <i class="ti-check-box"></i>
                </span>
              </li>
              <li class="col-last">
                <h3 class="counter text-right m-t-15">{{progress.accord}}</h3>
              </li>
              <li class="col-middle">
                <h4>สอดคล้อง</h4>
                <div class="progress">
                  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuemin="0" aria-valuemax="100" :style="'width: '+percentage(progress.accord)+'%'">
                    <span class="sr-only">40% Complete (success)</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div class="col-lg-6 col-sm-12 b-0">
            <ul class="col-in">
              <li>
                <span class="circle circle-md bg-danger">
                  <i class="ti-close"></i>
                </span>
              </li>
              <li class="col-last">
                <h3 class="counter text-right m-t-15">{{progress.notAccord}}</h3>
              </li>
              <li class="col-middle">
                <h4>ไม่สอดคล้อง</h4>
                <div class="progress">
                  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuemin="0" aria-valuemax="100" :style="'width: '+percentage(progress.notAccord)+'%'">
                    <span class="sr-only">40% Complete (success)</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div class="col-lg-6 col-sm-12 row-in-br  b-r-none">
            <ul class="col-in">
              <li>
                <span class="circle circle-md bg-warning">
                  <i class="ti-share-alt"></i>
                </span>
              </li>
              <li class="col-last">
                <h3 class="counter text-right m-t-15">{{progress.notConcern}}</h3>
              </li>
              <li class="col-middle">
                <h4>ไม่เกี่ยวข้อง</h4>
                <div class="progress">
                  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuemin="0" aria-valuemax="100" :style="'width: '+percentage(progress.notConcern)+'%'">
                    <span class="sr-only">40% Complete (success)</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div class="col-lg-6 col-sm-12  b-0">
            <ul class="col-in">
              <li>
                <span class="circle circle-md bg-info">
                  <i class="ti-new-window"></i>
                </span>
              </li>
              <li class="col-last">
                <h3 class="counter text-right m-t-15">{{progress.inprogress}}</h3>
              </li>
              <li class="col-middle">
                <h4>ยังไม่ดำเนินการ</h4>
                <div class="progress">
                  <div class="progress-bar progress-bar-info" role="progressbar" aria-valuemin="0" aria-valuemax="100" :style="'width: '+percentage(progress.inprogress)+'%'">
                    <span class="sr-only">40% Complete (success)</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['groups'],
  data: function () {
    return {
      progress: {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0
      }
    }
  },
  mounted: function () {
    this.calculateProgress()
  },
  methods: {
    percentage: function (amount) {
      return (amount / this.progress.total) * 100
    },
    calculateProgress: function () {
      var data = {
        accord: 0,
        notAccord: 0,
        notConcern: 0,
        inprogress: 0,
        total: 0
      }
      this.groups.forEach(function (group) {
        group.legalCategories.forEach(function (category) {
          category.accords.forEach(function (accord) {
            if (accord.accorded === 'ACCORDED') {
              data.accord = data.accord + 1
            } else if (accord.accorded === 'NOT_ACCORDED') {
              data.notAccord = data.notAccord + 1
            } else if (accord.accorded === 'NOT_CONCERN') {
              data.notConcern = data.notConcern + 1
            } else {
              data.inprogress = data.inprogress + 1
            }
            data.total = data.total + 1
          })
        })
      })
      this.$set(this, 'progress', data)
    }
  }
}
</script>

