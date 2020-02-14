<template>
  <div class="survey">
    <div class="survey-hd">
      <h3 class="title">修改密码</h3>
    </div>
    <el-form ref="form" :model="form" label-position="top" @submit.native.prevent>
      <el-form-item label="新密码" prop="password_new"
                    :rules="[
                      { required: true, message: '请输入6位数字的查询密码'},
                      { pattern: /^\d{6}$/g, message: '查询密码格式不正确'}
                    ]"
      >
        <el-input v-model="form.password_new" type="password" show-password placeholder="请输入6位数字的查询密码" @keyup.enter.native="onsubmit()"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="password_confirm"
                    :rules="[
                      { required: true, message: '请输入6位数字的查询密码'},
                      { pattern: /^\d{6}$/g, message: '查询密码格式不正确'}
                    ]"
      >
        <el-input v-model="form.password_confirm" type="password" show-password placeholder="再次输入新密码" @keyup.enter.native="onSubmit()"></el-input>
      </el-form-item>
      <el-form-item class="action">
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import HttpUtil from '@/utils/HttpUtil'
import Security from '@/utils/Security'
import PageUtil from '@/utils/PageUtil'

export default {
  data () {
    return {
      form: {
      }
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
    async onSubmit () {
      let valid = await PageUtil.validate(this.$refs['form'])
      if (valid) {
        if (this.form.password_new !== this.form.password_confirm) {
          PageUtil.message('warning', '两次输入密码不一致')
        }

        let params = this.enterprise
        params.password_new = Security.base64encode(this.form.password_new)
        let url = HttpUtil.url.enterprisePassword
        let result = await HttpUtil.post(url, params)
        if (result.code === 200) {
          PageUtil.message('success', '密码修改成功')
          this.$router.push('/enterprise/menu')
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
