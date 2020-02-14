<template>
  <div class="survey">
    <div class="survey-hd">
      <h3 class="title">企业基本情况</h3>
    </div>
    <el-form ref="form" :model="form" label-position="top" @submit.native.prevent>
      <el-form-item label="企业名称" prop="name">
        <el-input :value="form.name" disabled></el-input>
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="code">
        <el-input :value="form.code" disabled></el-input>
      </el-form-item>
      <el-form-item label="企业地址" prop="ext_address" required>
        <el-input v-model="form.ext_address" placeholder="请输入企业地址"></el-input>
        <div slot="error" class="el-form-item__error">请输入企业地址</div>
      </el-form-item>
      <el-form-item label="24小时值班电话" prop="ext_phone_24"
                    :rules="[
                    { required: true, message: '请输入电话号码'},
                    { pattern: /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/g, message: '电话格式不正确'}
                  ]"
      >
        <el-input v-model="form.ext_phone_24" placeholder="请输入电话" prefix-icon="el-icon-phone-outline"></el-input>
      </el-form-item>
      <el-form-item label="法定代表人" prop="ext_fr" required>
        <el-input v-model="form.ext_fr" placeholder="请输入法定代表人"></el-input>
        <div slot="error" class="el-form-item__error">请输入法定代表人</div>
      </el-form-item>
      <el-form-item label="联系人" prop="ext_contact" required>
        <el-input v-model="form.ext_contact" placeholder="请输入联系人"></el-input>
        <div slot="error" class="el-form-item__error">请输入联系人</div>
      </el-form-item>
      <el-form-item label="联系电话" prop="ext_phone"
                    :rules="[
                    { required: true, message: '请输入电话号码'},
                    { pattern: /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/g, message: '电话格式不正确'}
                  ]"
      >
        <el-input v-model="form.ext_phone" placeholder="请输入联系电话" prefix-icon="el-icon-phone-outline"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="ext_mobile"
                    :rules="[
                    { required: true, message: '请输入手机号码'},
                    { pattern: /^1[3456789]\d{9}$/g, message: '手机格式不正确'}
                  ]"
      >
        <el-input v-model="form.ext_mobile" placeholder="请输入手机号码" prefix-icon="el-icon-mobile"></el-input>
      </el-form-item>
      <el-form-item label="员工总人数" prop="ext_total_person" required>
        <el-input type="number" v-model="form.ext_total_person" placeholder="请输入员工总人数"></el-input>
        <div slot="error" class="el-form-item__error">请输入员工总人数</div>
      </el-form-item>
      <el-form-item label="其中，来自或去过疫情重点地区人数" prop="ext_key_person">
        <el-input type="number" v-model="form.ext_key_person" placeholder="请输入重点地区人数"></el-input>
      </el-form-item>
      <el-form-item label="返岗人数" prop="ext_back_person" required>
        <el-input type="number" v-model="form.ext_back_person" placeholder="请输入返岗人数"></el-input>
        <div slot="error" class="el-form-item__error">请输入返岗人数</div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Security from '@/utils/Security'
import PageUtil from '@/utils/PageUtil'
import HttpUtil from '../../utils/HttpUtil'

export default {
  data () {
    return {
      form: {},
      enterprise: {}
    }
  },
  mounted () {
    this.initStorage()
    this.getDetail()
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
    async getDetail () {
      let id = this.enterprise.id
      if (id > 0) {
        let url = HttpUtil.url.enterprise
        let result = await HttpUtil.get(url, this.enterprise)
        if (result.code === 200) {
          this.form = result.data
        }
      } else {
        this.form = {
          name: this.enterprise.name,
          code: this.enterprise.code,
          password: this.enterprise.password
        }
      }
    },
    async onSubmit () {
      let valid = await PageUtil.validate(this.$refs['form'])
      if (valid) {
        let confirm = await PageUtil.confirm('确定要提交吗？')
        if (!confirm) {
          return
        }

        this.form.password = this.enterprise.password
        let params = JSON.parse(JSON.stringify(this.form))
        let url = HttpUtil.url.enterprise
        let result = await HttpUtil.post(url, params)
        if (result.code === 200) {
          PageUtil.message('success', '信息提交成功')
          this.enterprise.id = result.data
          let epInfo = Security.base64encode(JSON.stringify(this.enterprise))
          sessionStorage.setItem('ep_info', epInfo)
          this.$router.push('/enterprise/menu')
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
