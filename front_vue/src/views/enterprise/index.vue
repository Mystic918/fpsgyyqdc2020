<template>
  <div class="survey">
    <div class="survey-hd">
      <h3 class="title">企业基本情况</h3>
    </div>
    <el-form ref="form" :model="form" label-position="top" @submit.native.prevent>
      <el-form-item label="企业名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入企业名称"></el-input>
        <div slot="error" class="el-form-item__error">请输入企业名称</div>
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="code"
                    :rules="[
                      { required: true, message: '请输入统一社会信用代码'},
                      { pattern: /^[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}$/g, message: '格式不正确'}
                    ]"
      >
        <el-input v-model="form.code" placeholder="请输入统一社会信用代码"></el-input>
      </el-form-item>
      <el-form-item label="查询密码" prop="password"
                    :rules="[
                      { required: true, message: '请输入6位数字的查询密码'},
                      { pattern: /^\d{6}$/g, message: '查询密码格式不正确'}
                    ]"
      >
        <el-input v-model="form.password" type="password" show-password placeholder="请输入6位数字的查询密码" @keyup.enter.native="onSubmit()"></el-input>
      </el-form-item>
      <el-form-item class="action">
        <el-button type="primary" @click="onSubmit">下一步</el-button>
      </el-form-item>
    </el-form>
    <div class="survey-ft">
      <h3 class="tips">
        <p>提示：</p>
        <p>1、首次登录企业输入企业名称、统一社会信用代码、查询密码创建企业档案</p>
        <p>2、非首次登录企业输入企业名称、统一社会信用代码、查询密码，可以进行查询二维码、修改资料、修改密码等操作</p>
      </h3>
    </div>
  </div>
</template>

<script>
import HttpUtil from '@/utils/HttpUtil'
import PageUtil from '@/utils/PageUtil'
import Security from '@/utils/Security'

export default {
  data () {
    return {
      form: {}
    }
  },
  methods: {
    onMenu (action) {
      this.action = action
      if (action === 'qrcode') {
        this.createQRCode()
      }
    },
    async onSubmit () {
      let valid = await PageUtil.validate(this.$refs['form'])
      if (valid) {
        let params = {
          name: this.form.name,
          code: this.form.code,
          password: Security.base64encode(this.form.password)
        }
        let url = HttpUtil.url.enterprise
        let result = await HttpUtil.get(url, params)
        if (result.code === 200) {
          let id = result.data.id
          params.id = id
          let epInfo = Security.base64encode(JSON.stringify(params))
          sessionStorage.setItem('ep_info', epInfo)
          if (id > 0) {
            this.$router.push('/enterprise/menu')
          } else {
            this.$router.push('/enterprise/form')
          }
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
