<template>
  <div class="survey">
    <div class="survey-hd">
      <h3 class="title">企业信息管理</h3>
    </div>
    <div class="survey-menu">
      <div class="item">
        <el-button type="primary" @click="onMenu('qrcode')">查看二维码</el-button>
      </div>
      <div class="item">
        <el-button type="primary" @click="onMenu('form')">修改资料</el-button>
      </div>
      <div class="item">
        <el-button type="primary" @click="onMenu('password')">修改密码</el-button>
      </div>
      <div class="item">
        <el-button type="primary" @click="onMenu('person')">员工信息</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Security from '@/utils/Security'

export default {
  data () {
    return {
      enterprise: {}
    }
  },
  mounted () {
    this.initStorage()
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
    onMenu (action) {
      this.$router.push('/enterprise/' + action)
    }
  }
}
</script>

<style scoped>
</style>
