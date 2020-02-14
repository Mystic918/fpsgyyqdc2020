<template>
  <div class="survey">
    <template v-if="!enterprise">
      <div class="survey-hd">
        <h3 class="title">企业不存在</h3>
      </div>
    </template>
    <template v-if="enterprise">
      <template v-if="action == 'form'">
        <div class="survey-hd">
          <h3 class="title">肇庆市高要区企业“一员一档”登记卡</h3>
          <div class="summary">该表格提供给{{enterprise.name}}员工使用填写</div>
        </div>
        <el-form ref="form" :model="form" label-position="top" @submit.native.prevent>
          <div>一、基本信息</div>
          <el-form-item label="姓名" prop="name" required>
            <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            <div slot="error" class="el-form-item__error">请输入姓名</div>
          </el-form-item>
          <el-form-item label="身份证号" prop="code"
                        :rules="[
                    { required: true, message: '请输入身份证号'},
                    { pattern: /^(\d{18}|\d{17}X)$/g, message: '身份证号格式不正确'}
                  ]"
          >
            <el-input v-model="form.code" placeholder="请输入身份证号" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item label="籍贯" prop="ext_place" required>
            <v-distpicker @selected="onSelected" hide-area></v-distpicker>
            <!--<el-input v-model="form.ext_place" placeholder="请输入籍贯"></el-input>-->
            <div slot="error" class="el-form-item__error">请输入籍贯</div>
          </el-form-item>
          <el-form-item label="手机号码" prop="mobile"
                        :rules="[
                    { required: true, message: '请输入手机号码'},
                    { pattern: /^1[3456789]\d{9}$/g, message: '手机格式不正确'}
                  ]"
          >
            <el-input v-model="form.mobile" placeholder="请输入手机号码" prefix-icon="el-icon-mobile"></el-input>
          </el-form-item>
          <el-form-item label="工作单位" prop="ext_work_unit">
            <el-input v-model="form.ext_work_unit" disabled></el-input>
          </el-form-item>
          <el-form-item label="现居住地（人在哪里填写哪里）" prop="ext_address" required>
            <el-input v-model="form.ext_address" placeholder="请输入现居住地"></el-input>
            <div slot="error" class="el-form-item__error">请输入现居住地</div>
          </el-form-item>
          <div>二、假期外出情况</div>
          <el-form-item label="离开肇庆时间" prop="ext_travel_leave">
            <el-date-picker
                v-model="form.ext_travel_leave"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
                required>
            </el-date-picker>
          </el-form-item>
          <el-form-item label="回肇庆时间" prop="ext_travel_back">
            <el-date-picker
                v-model="form.ext_travel_back"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
                required>
            </el-date-picker>
          </el-form-item>
          <el-form-item label="随行人员" prop="ext_travel_partner">
            <el-input v-model="form.ext_travel_partner" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <el-form-item label="近14日旅行史" prop="ext_travel_history">
            <el-input v-model="form.ext_travel_history" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <el-form-item label="是否路经湖北或与湖北籍人员接触" prop="ext_travel_pass" required>
            <el-radio-group v-model="form.ext_travel_pass">
              <el-radio label="是">是</el-radio>
              <el-radio label="否">否</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <el-form-item label="是否与疑似或确诊病例密切接触" prop="ext_travel_touch" required>
            <el-radio-group v-model="form.ext_travel_touch">
              <el-radio label="是">是</el-radio>
              <el-radio label="否">否</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <div>三、身体状况</div>
          <el-form-item label="是否发热" prop="ext_body_hot" required>
            <el-radio-group v-model="form.ext_body_hot">
              <el-radio label="是">是</el-radio>
              <el-radio label="否">否</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <el-form-item label="是否有咳嗽症状" prop="ext_body_cough" required>
            <el-radio-group v-model="form.ext_body_cough">
              <el-radio label="是">是</el-radio>
              <el-radio label="否">否</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <el-form-item label="隔离情况（起止时间、隔离地点、居家隔离/集中隔离）" prop="ext_body_apart">
            <el-input v-model="form.ext_body_apart" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <div>四、直系亲属健康状况（有无发热、咳嗽等症状）</div>
          <el-form-item label="父母" prop="ext_relative_parent">
            <el-input v-model="form.ext_relative_parent" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <el-form-item label="配偶" prop="ext_relative_spouse">
            <el-input v-model="form.ext_relative_spouse" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <el-form-item label="子女" prop="ext_relative_child">
            <el-input v-model="form.ext_relative_child" placeholder="没有的话，可以不填"></el-input>
          </el-form-item>
          <div>五、复产后员工食宿安排</div>
          <el-form-item label="企业安排集中居住还是分散居住" prop="ext_back_live" required>
            <el-radio-group v-model="form.ext_back_live">
              <el-radio label="集中居住">集中居住</el-radio>
              <el-radio label="分散居住">分散居住</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <el-form-item label="企业是否安排集中用餐" prop="ext_back_eat" required>
            <el-radio-group v-model="form.ext_back_eat">
              <el-radio label="是">是</el-radio>
              <el-radio label="否">否</el-radio>
            </el-radio-group>
            <div slot="error" class="el-form-item__error">请选择情况</div>
          </el-form-item>
          <div>六、其他需说明的情况</div>
          <el-form-item prop="ext_state_other">
            <el-input type="textarea" rows="3" v-model="form.ext_state_other"></el-input>
          </el-form-item>
          <el-form-item prop="agree" required>
            <el-checkbox v-model="form.agree">本人承诺：对所报告信息的真实性、准确性和完整性负责，主动接受疫情防控检查，一旦发生瞒报、虚报造成疫情防控事故的，愿意承担相关法律责任。</el-checkbox>
            <div slot="error" class="el-form-item__error">请同意承诺</div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
          </el-form-item>
        </el-form>
      </template>
      <template v-if="action == 'result'">
        <div class="survey-hd">
          <h3 class="title">肇庆市高要区企业“一员一档”登记卡</h3>
          <div class="summary">填写结果</div>
        </div>
        <div class="survey-result">
          <div class="icon">
            <i class="el-icon-success"></i>
          </div>
          <div class="text">谢谢您的提交</div>
        </div>
        <div class="survey-menu">
          <div class="item">
            <el-button type="primary" @click="onReload()">继续填写</el-button>
          </div>
          <div class="item">
            <el-button type="default" @click="onClose()">关闭窗口</el-button>
          </div>
        </div>
      </template>
    </template>
  </div>
</template>

<script>
import HttpUtil from '@/utils/HttpUtil'
import PageUtil from '@/utils/PageUtil'
import VDistpicker from 'v-distpicker'

export default {
  components: { VDistpicker },
  data () {
    return {
      eid: 0,
      enterprise: {},
      form: {},
      agree: false,
      action: 'form'
    }
  },
  mounted () {
    if (this.$route.params && this.$route.params.id) {
      this.eid = this.$route.params.id
    }
    this.getDetail()
  },
  methods: {
    async onSelected (data) {
      let form = this.form
      form.ext_place = data.province.value + data.city.value
      this.form = form
    },
    async getDetail () {
      let url = HttpUtil.url.person.replace(/:eid/g, this.eid)
      let result = await HttpUtil.get(url)
      if (result.code === 200) {
        this.enterprise = result.data
        this.form = {
          ext_work_unit: result.data.name
        }
      } else {
        this.enterprise = null
      }
    },
    async onSubmit () {
      let valid = await PageUtil.validate(this.$refs['form'])
      if (valid) {
        let confirm = await PageUtil.confirm('提交后无法修改，确定要提交吗？')
        if (!confirm) {
          return
        }
        let params = JSON.parse(JSON.stringify(this.form))
        let url = HttpUtil.url.person.replace(/:eid/g, this.eid)
        let result = await HttpUtil.post(url, params)
        if (result.code === 200) {
          PageUtil.message('success', '信息提交成功，谢谢您的参与')
          this.action = 'result'
        }
      } else {
        PageUtil.message('warning', '您还有信息未填写完')
      }
    },
    onReload () {
      location.reload()
    },
    onClose () {
      if (typeof WeixinJSBridge != 'undefined') {
        WeixinJSBridge.call('closeWindow')
      } else {
        window.opener = null
        window.open('','_self')
        window.close()
      }
    }
  }
}
</script>

<style scoped>
</style>
