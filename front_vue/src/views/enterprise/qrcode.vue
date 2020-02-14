<template>
  <div class="survey">
    <div class="survey-hd">
      <h3 class="title">{{enterprise.name}}</h3>
      <div class="summary">企业“一员一档”登记卡</div>
    </div>
    <div class="survey-qrcode">
      <div class="pic">
        <div id="qrcode"></div>
      </div>
      <div class="text">
        扫描二维码进入
      </div>
    </div>
  </div>
</template>

<script>
import HttpUtil from '@/utils/HttpUtil'
import Security from '@/utils/Security'
import QRCode from 'qrcodejs2'

export default {
  data () {
    return {
      enterprise: {}
    }
  },
  mounted () {
    this.initStorage()
    this.createQRCode()
  },
  methods: {
    initStorage () {
      let epInfo = sessionStorage.getItem('ep_info') || ''
      epInfo = Security.base64decode(epInfo)
      epInfo = JSON.parse(epInfo)
      if (!epInfo.id && !epInfo.name && !epInfo.code) {
        this.$router.push('/enterprise')
        return
      }
      this.enterprise = epInfo
    },
    createQRCode: function () {
      let url = HttpUtil.url.root + '/#/person/' + this.enterprise.id
      console.log(url)
      /* eslint-disable no-new */
      this.$nextTick(function () {
        document.getElementById('qrcode').innerHTML = ''
        let qrcode = new QRCode('qrcode', {
          text: url,
          width: 200,
          height: 200,
          colorDark: '#333333',
          colorLight: '#ffffff',
          correctLevel: QRCode.CorrectLevel.L
        })
        console.log(qrcode)
      })
    }
  }
}
</script>

<style scoped>
</style>
