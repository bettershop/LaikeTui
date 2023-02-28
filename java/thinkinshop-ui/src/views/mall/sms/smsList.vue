<template>
  <div class="container">
    <el-main>
      <!-- tbl页 -->
      <div class="btn-nav">
        <el-radio-group v-model="tabPosition" @change="tbl">
          <el-radio-button label="1">短信列表</el-radio-button>
          <el-radio-button label="2">短信模板</el-radio-button>
          <el-radio-button label="3">核心设置</el-radio-button>
        </el-radio-group>
      </div>
      <div class="hr"></div>
      <div class="jump-list" v-show="tabPosition!=='3'">
        <el-button class="bgColor laiketui laiketui-add" type="primary" @click="Save()">{{ tabPosition == '1' ? '添加' : '添加模板'}}</el-button>
      </div>
      <!-- 短信列表 -->
      <div class="m-table" v-if="tabPosition!=='3'">
        <div class="merchants-list">
          <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
            class="el-table"
            style="width: 100%"
            :key="flag"
            :height="623">
            <div v-if="tabPosition==='1'">
              <el-table-column label="修改时间">
                <template slot-scope="scope">
                  {{scope.row.add_time | dateFormat}}
                </template>
              </el-table-column>
              <el-table-column prop="id" label="ID">
              </el-table-column>
              <el-table-column prop="NAME" label="短信模板名称">
              </el-table-column>
              <el-table-column label="类型" width="228">
                <template slot-scope="scope">
                  <div v-if="scope.row.type===0">短信验证码</div>
                  <div v-else>通知</div>
                </template>
              </el-table-column>
              <el-table-column prop="content1" label="内容">
              </el-table-column>
            </div>
            <!-- 短信模板 -->
            <div v-else-if="tabPosition==='2'">
              <el-table-column prop="add_time" label="修改时间">
                <template slot-scope="scope">
                  {{scope.row.add_time | dateFormat}}
                </template>
              </el-table-column>
              <el-table-column width="88" prop="id" label="ID">
              </el-table-column>
              <el-table-column label="短信模板名称" prop="name">
              </el-table-column>
              <el-table-column label="类型" width="228">
                <template slot-scope="scope">
                  <div v-if="scope.row.type===0">短信验证码</div>
                  <div v-else>通知</div>
                </template>
              </el-table-column>
              <el-table-column label="短信模板Code" prop="TemplateCode">
              </el-table-column>
              <el-table-column label="内容" prop="content">
              </el-table-column>
            </div>

            <el-table-column fixed="right" label="操作" width="250">
              <template slot-scope="scope">
                <div class="OP-button">
                  <div class="OP-button-top">
                    <el-button icon="el-icon-edit-outline" @click="Save(scope.row.id)">编辑</el-button>
                    <el-button icon="el-icon-delete" @click="Delete(scope.row.id)">删除</el-button>
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 短信列表 end -->
      <div class="core-set" v-else>
        <el-form :model="mainForm" :rules="mainForm.rules" ref="ruleForm" class="picture-ruleForm" label-width="150px">
          <el-form-item label="accessKeyId" prop="accessKeyId">
            <el-input v-model="mainForm.accessKeyId"></el-input>
            <span class="left">(阿里云API的密钥ID！)</span>
          </el-form-item>
          <el-form-item label="accessKeySecret" prop="accessKeySecret">
            <el-input v-model="mainForm.accessKeySecret"></el-input>
            <span class="left">(阿里云API的密钥！)</span>
          </el-form-item>

          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" type="primary" @click="saveConfig('ruleForm')">{{ $t('DemoPage.tableFromPage.save')
                }}
              </el-button>
              <!-- <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}
              </el-button> -->
            </el-form-item>
          </div>
          <!-- <div class="footer-button">
            <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
            <el-button type="primary" class="footer-save bgColor mgleft" @click="saveConfig('ruleForm')">保存</el-button>
          </div> -->
        </el-form>
      </div>

    </el-main>
  </div>
</template>

<script>
import main from "@/webManage/js/mall/sms/smsList";
export default main
</script>

<style scoped lang="less">
@import "../../../webManage/css/mall/sms/smsList";
</style>
