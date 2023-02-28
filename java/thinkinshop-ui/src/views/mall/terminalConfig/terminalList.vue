<template>
  <div class="container">
    <el-main>
      <!-- tbl页 -->
      <div class="el-radio-group">
        <el-radio-group v-model="tabPosition" @change="tbl">
          <el-radio-button label="2">App</el-radio-button>
          <el-radio-button label="1">微信小程序</el-radio-button>
        </el-radio-group>
      </div>
      <div class="hr"></div>
      <!-- App配置 -->
      <div v-if="tabPosition==='2'">
        <el-form :model="appForm" :rules="rules" element-loading-text="拼命加载中..." ref="ruleForm" label-width="115px"
                 class="demo-ruleForm">
          <div class="card">
            <div class="title">版本配置</div>
            <el-row :gutter="3" style="margin-left:30px;">
              <el-col :lg="7">
                <el-form-item label="APP名称" prop="appname">
                  <el-input v-model="appForm.appname" placeholder="请输入App名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :lg="7">
                <el-form-item label="版本号" prop="editions">
                  <el-input v-model="appForm.editions" placeholder="请输入版本号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :lg="7">
                <el-form-item label="安卓下载地址" prop="android_url" style="margin-left:30px;">
                  <el-input v-model="appForm.android_url" placeholder="请输入安卓下载地址"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3" style="margin-left:30px;">
              <el-col :lg="7">
                <el-form-item label="IOS下载地址" prop="ios_url">
                  <el-input v-model="appForm.ios_url" placeholder="请输入IOS下载地址"></el-input>
                </el-form-item>
              </el-col>
              <el-col :lg="7">
                <el-form-item label="APP域名" prop="edition">
                  <el-input v-model="appForm.edition" placeholder="请输入APP域名"></el-input>
                </el-form-item>
              </el-col>
              <span style="margin-left: 13px;line-height:27px;color: #97A0B4;font-size:14px">H5的配置</span>
            </el-row>
            <el-row :gutter="1" style="margin-left:30px;">
              <el-col :lg="7">
                <el-form-item label="自动更新提示" prop="type">
                  <el-switch
                      v-model="appForm.type"
                      active-color="#00ce6d"
                      inactive-color="#d5dbe8"
                      active-value="0"
                      inactive-value="1"
                      @change="change_type">
                  </el-switch>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="1" style="margin-left:30px;">
              <el-col :lg="21">
                <el-form-item label="详细内容" prop="content">
                  <div class="context-box">
                    <vue-editor v-model="appForm.content" @image-added="handleImageAdded"></vue-editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>

          </div>
        </el-form>
      </div>
      <!-- 微信小程序配置 -->
      <div v-else>
        <div class="merchants-list api-config">
          <el-form :model="weiXinForm" :rules="wxRules" element-loading-text="拼命加载中..." ref="ruleForm" label-width="115px"
            class="demo-ruleForm">
            <div class="card">
              <div class="title">接口配置</div>
              <el-row :gutter="3" style="margin-left:30px;">
                <el-col :lg="8">
                  <el-form-item label="小程序ID" prop="appId">
                    <el-input v-model="weiXinForm.appId" placeholder="请输入小程序ID"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :lg="8" style="margin-left:25px">
                  <el-form-item label="小程序密钥" prop="appSecret">
                    <el-input v-model="weiXinForm.appSecret" placeholder="请输入小程序密钥"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-form>
        </div>
        <div class="merchants-list">
          <el-form :model="weiXinForm" ref="wxRules" label-width="115px" class="demo-ruleForm">
            <div class="card">
              <div class="title">订阅消息</div>
              <el-row :gutter="3" style="margin-left:30px">
                <el-col :lg="8">
                  <el-form-item label="购买成功通知">
                    <el-input v-model="weiXinForm.pay_success" placeholder="请输入购买成功通知"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :lg="8">
                  <el-form-item style="margin-left:25px" label="订单发货提醒">
                    <el-input v-model="weiXinForm.delivery" placeholder="请输入订单发货提醒" style="width:408px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :lg="7">
                  <el-form-item label="退款通知" style="margin-left:20px">
                    <el-input v-model="weiXinForm.refund_res" placeholder="请输入退款通知"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-form>
        </div>
        <div class="merchants-list">
          <el-form :model="weiXinForm" ref="wxRules" label-width="115px" class="demo-ruleForm">
            <div class="card">
              <div class="title">审核配置</div>
              <el-row :gutter="3" style="margin-left:30px">
                <el-col :lg="7">
                  <el-form-item label="隐藏钱包">
                    <el-switch v-model="weiXinForm.Hide_your_wallet" />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-form>
        </div>
      </div>

      <!-- 引导图 begin -->
      <div class="merchants-list intro">

        <div class="card">
          <div class="title">引导图
            <el-button @click="showisAddGuidBox()">{{ $t('DemoPage.tableExamplePage.add_Guid')}}</el-button>
          </div>


          <!-- 弹框组件 -->
          <div class="dialog-block">
            <el-dialog
              title="编辑引导图"
              :visible.sync="dialogVisible"
              :before-close="close"
            >
              <el-form :model="guidForm" :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
                <div class="pass-input">
                  <el-form-item label="引导图" prop="image">
                    <l-upload :limit="1"
                      v-model="guidForm.image"
                      text="建议尺寸：122*40px，白色"
                    >
                    </l-upload>
                  </el-form-item>

                  <el-form-item label="类型">
                    <template>
                      <el-radio v-model="guidForm.type" label="1">启动</el-radio>
                      <el-radio v-model="guidForm.type" label="0">安装</el-radio>
                    </template>
                  </el-form-item>
                  <el-form-item label="序号">
                    <el-input v-model="guidForm.sort" placeholder="请输入序号"></el-input>
                  </el-form-item>
                </div>
                <div class="form-footer">
                  <el-form-item>
                    <el-button @click="cancel()" class="qxcolor">取 消</el-button>
                    <el-button type="primary" @click="saveGuid(guidForm.id,'ruleForm2')" class="qdcolor">确 定</el-button>
                  </el-form-item>
                </div>
              </el-form>
            </el-dialog>
          </div>
          <!-- 弹框组件 end -->

          <!-- 弹框组件 -->
          <div class="dialog-block">
            <el-dialog
              title="添加引导图"
              :visible.sync="isAddGuidBox"
              :before-close="close2"
            >
              <el-form :model="guidFormAdd" :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
                <div class="pass-input">
                  <el-form-item label="引导图" prop="image">
                    <l-upload :limit="1"
                      v-model="guidFormAdd.image"
                      ref="upload"
                      text="建议尺寸：122*40px，白色"
                    >
                    </l-upload>
                  </el-form-item>

                  <el-form-item label="类型">
                    <template>
                      <el-radio v-model="guidFormAdd.type" label="1">启动</el-radio>
                      <el-radio v-model="guidFormAdd.type" label="0">安装</el-radio>
                    </template>
                  </el-form-item>
                  <el-form-item label="序号">
                    <el-input v-model="guidFormAdd.sort" type="number" @blur="exgNumber()" placeholder="请输入序号"></el-input>
                  </el-form-item>
                </div>
                <div class="form-footer">
                  <el-form-item>
                    <el-button @click="cancel()" class="qxcolor">取 消</el-button>
                    <el-button type="primary" @click="saveGuid('','ruleForm2')" class="qdcolor">确 定</el-button>
                  </el-form-item>
                </div>
              </el-form>
            </el-dialog>
          </div>
          <!-- 弹框组件 end -->

          <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
                    class="el-table"
                    style="width: 100%"
                    :height="200">
            <el-table-column label="序号" width="220" align="center">
              <template slot-scope="scope">
                {{ scope.$index+1 }}
              </template>
            </el-table-column>
            <el-table-column label="图片" width="250" align="center">
              <template slot-scope="scope">
                <img :src="scope.row.image" alt=""/>
              </template>
            </el-table-column>
            <el-table-column label="类型" width="250" align="center">
              <template slot-scope="scope">{{scope.row.type===1?"启动":"安装"}}</template>
            </el-table-column>
            <el-table-column label="排序号" prop="sort" width="250" align="center">
            </el-table-column>
            <el-table-column label="添加时间" align="center">
              <template slot-scope="scope">
                {{ scope.row.add_date | dateFormat }}
              </template>
            </el-table-column>

            <el-table-column fixed="right" label="操作" width="250" align="center">
              <template slot-scope="scope">
                <div class="OP-button">
                  <div class="OP-button-top">
                    <el-button icon="el-icon-edit-outline" @click="loadGuid(scope.row.id)">{{
                      $t('DemoPage.tableExamplePage.edit')}}
                    </el-button>
                    <el-button icon="el-icon-delete" @click="Delete(scope.row.id)">{{
                      $t('DemoPage.tableExamplePage.delete')}}
                    </el-button>
                  </div>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <div class="pageBox">
            <div class="pageLeftText">显示</div>
            <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
                           @size-change="handleSizeChange"
                           :page-sizes="page.pagesizes" :current-page="page.pagination.page"
                           @current-change="handleCurrentChange"
                           :total="page.total">
              <div class="pageRightText">当前显示{{page.currpage}}-{{page.current_num}}条，共 {{page.total}} 条记录</div>
            </el-pagination>
          </div>
        </div>
      </div>
      <!-- 引导图 end -->
      <div class="footerBox">
        <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save')}}
        </el-button>
      </div>
    </el-main>
  </div>
</template>

<script>
import main from "@/webManage/js/mall/terminalConfig/terminalList";
export default main;
</script>


<style scoped lang="less">
  @import "../../../webManage/css/mall/terminalConfig/terminalList";
</style>
