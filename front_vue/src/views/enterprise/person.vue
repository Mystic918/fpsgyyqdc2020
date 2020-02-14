<template>
  <div class="information">
    <div class="survey-hd">
      <h3 class="title">员工信息列表<span class="allnum">总数：{{length}}</span>
      <!-- excel 表格导出 -->
      <download-excel
            type="xls" 
            :data = "this.form.data"
            :fields = "this.excel.json_fields"
            name = "员工信息列表.xls">
            <el-button type="primary" size="small" >导出列表</el-button>
        </download-excel>
     
      </h3>
    </div>
   <el-table
   ref="multipleTable"
    :data="this.form.data"
    :row-class-name="tableRowClassName"
    style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <!-- 详情数据 -->
        <el-form label-position="left" inline class="demo-table-expand allnews">

            <div><span>籍贯：</span><span v-html="Sensitive(props.row.ext_place)"></span></div>
            <div><span>现居住地：</span><span  v-html="Sensitive(props.row.ext_address)"></span></div>

          <div>
            <span>离开肇庆时间：</span><span>{{ props.row.ext_travel_leave }}</span>
          </div>

          <div>
            <span>回肇庆时间：</span><span>{{ props.row.ext_travel_back }}</span>
          </div>

          <div>
            <span>随行人员：</span><span>{{ props.row.ext_travel_partner }}</span>
          </div>
          <div>
            <span>近14日旅行史：</span><span v-html="Sensitive(props.row.ext_travel_history)"></span>
          </div>
          <!-- <div class="line-wrap"></div> -->

          <div>
            <span>是否路经湖北或与湖北籍人员接触：</span><span>{{ props.row.ext_travel_pass }}</span>
          </div>

          <div>
            <span>是否与疑似或确诊病例密切接触：</span><span>{{ props.row.ext_travel_touch }}</span>
          </div>

          <div>
            <span>是否发热：</span><span>{{ props.row.ext_body_hot }}</span>
          </div>
          <div>
            <span>是否有咳嗽症状：</span><span>{{ props.row.ext_body_cough }}</span>
          </div>

          <div>
            <span>隔离情况：</span><span>{{ props.row.ext_body_apart }}</span>
          </div>

          <div>
            <span>父母健康情况：</span><span>{{ props.row.ext_relative_parent }}</span>
          </div>
          <div>
            <span>配偶健康情况：</span><span>{{ props.row.ext_relative_spouse }}</span>
          </div>
          <div>
            <span>子女健康情况：</span><span>{{ props.row.ext_relative_child }}</span>
          </div>


          <div>
            <span>居住安排：</span><span>{{ props.row.ext_back_live }}</span>
          </div>

          <div>
            <span>企业是否安排集中用餐：</span><span>{{ props.row.ext_back_eat }}</span>
          </div>

          <div>
            <span>其他需说明的情况：</span><span>{{ props.row.ext_state_other }}</span>
          </div>

          <div>
            <span>填报时间：</span><span>{{ props.row.created_at }}</span>
          </div>

        </el-form>
        <!--  -->
      </template>
    </el-table-column>
    <el-table-column
    type="index"
      label="序号">
    </el-table-column>
    <el-table-column
      label="姓名"
      prop="name">
    </el-table-column>
    <el-table-column
      label="身份号码"
      prop="code">
    </el-table-column>
    <el-table-column
      label="手机号码"
      prop="mobile">
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
import HttpUtil from '@/utils/HttpUtil'
import Security from '@/utils/Security'
import JsonExcel from 'vue-json-excel'
export default {
  components:{
     "download-excel":JsonExcel
  },
data(){
  return {
       item:1,
      form: {
        data:[],
        sign:/湖北|湖南|江西|重庆|河南|浙江|安徽|广州|深圳|珠海|佛山|东莞|中山|惠州|封开/   
      },
      enterprise: {},
      length:0,
      excel:{
        // 定义excel字段名
           json_fields: {

        "序号":  {
                field: this.item,
                callback: (value) => {
                    return this.item++;
                }
            },  
        "姓名": "name",
        "身份号码": "code",
        "手机号码": "mobile",
        "籍贯": "ext_place",
        "现居住地": "ext_address",
        "离开肇庆时间": "ext_travel_leave",
        "回肇庆时间": "ext_travel_back",
        "随行人员": "ext_travel_partner",
        "近14日旅行史": "ext_travel_history",
        "是否路经湖北或与湖北籍人员接触": "ext_travel_pass",
        "是否与疑似或确诊病例密切接触": "ext_travel_touch",
        "是否发热": "ext_body_hot",
        "是否有咳嗽症状": "ext_body_cough",
        "隔离情况": "ext_body_apart",
        "父母健康情况": "ext_relative_parent",
        "配偶健康情况": "ext_relative_spouse",
        "居住安排": "ext_back_live",
        "企业是否安排集中用餐": "ext_back_eat",
        "其他需说明的情况": "ext_state_other",
        "填报时间": "created_at"
      },
      json_data: [
      ],
      json_meta: [
        [
          {
            " key ": " charset ",
            " value ": " utf- 8 "
          }
        ]
      ]
    
      }
      }
  },
  methods: {
    async initStorage () {

      let epInfo = sessionStorage.getItem('ep_info') || ''
      if(!epInfo){
        this.$router.push('/enterprise')
        return }
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
        let url = HttpUtil.url.personList
        let result = await HttpUtil.get(url, this.enterprise)
        if (result.code === 200) {
          let arr = result.data
          var pattern = this.form.sign
          for(var  i =0; i<arr.length;i++){
            let ext_address = arr[i].ext_address
            let ext_place =  arr[i].ext_place
            let ext_travel_history =  arr[i].ext_travel_history
            if(pattern.test(ext_address) || pattern.test(ext_place) || pattern.test(ext_travel_history) ){
                this.$set(arr[i],"isSensitive",true)
            }else{
              this.$set(arr[i],"isSensitive",false)
            }
          }
          this.form.data = result.data
          this.length=result.data.length
        }
      } else {
        this.form = {
          name: this.enterprise.name,
          code: this.enterprise.code,
          password: this.enterprise.password
        }
      }
    },
   tableRowClassName({row, rowIndex}) {
        if (row.isSensitive) {
          return 'warning-row';
        }
        return '';
      },
      Sensitive:function(value){
        var pattern = this.form.sign         
            if( pattern.test(value)){
                return '<e style="color:red;">'+value+'</e>'
            }else{
                 return value
            }
      }
    },
    mounted () {
        this.initStorage()
        this.getDetail()
    }

}

</script>
<style >
.information{padding: 20px 0px;}
.el-table__expanded-cell{padding: 10px 30px !important;height: 70%;overflow-y:scroll;}
.news-wrap{position: relative;}
.allnum{color: #606266;padding:0px 10px;font-size: 16px;}
.allnews div{ padding: 5px;}
.allnews div span:first-child{color:   #bbb;}

.allnews div.line-wrap::after{background-color:  #ccc;height: 1px;content: '';display: block;}
.warning-row{color: red !important;}
</style>
