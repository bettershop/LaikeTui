<template>
  <div class="container">
    <el-form ref="ruleForm" class="form-search" label-width="130px">
        <div class="header">
            <span>DIY模板管理</span>
        </div>
        <div class="div-content" id="iframe_tem">
            <div class="left-show">
                <div class="show-header">
                    <div class="title">
                        <span>当前应用：默认模板</span>
                    </div>
                    <div class="editor" @click="editor">
                        <i class="el-icon-edit-outline"></i>
                        <span>编辑</span>
                    </div>
                </div>
                <div class="show-content" style="width:300px">
                    <iframe :src="H5_domain" id="mobsf" scrolling="auto" frameborder="0"></iframe>
                </div>
            </div>
            <div class="right-template">
                <div class="template-header">
                    <div class="title">
                        <span>模板选择</span>
                    </div>
                    <!-- <div class="add">
                        <el-button type="primary"  @click="addCoupons">新建模板</el-button>
                    </div> -->
                </div>
                <div class="template-content">
                    <ul>
                        <li class="default">
                            <img src="../../../assets/imgs/default.png">
                            <div class="operation">
                                <div class="btn-item">
                                    <el-button class="edit" type="primary"  @click="editor">编辑</el-button>
                                </div>
                            </div>
                            <p class="default-p">默认模板</p>
                        </li>
                        <!-- <li v-for="(item,index) in imgList" :key="index">
                            <img :src="item.img">
                            <div class="operation">
                                <div class="btn-item">
                                    <el-button type="primary"  @click="Application">应用</el-button>
                                    <el-button class="edit" type="primary"  @click="editor">编辑</el-button>
                                    <el-button class="del" type="primary"  @click="Delete">删除</el-button>
                                </div>
                            </div>
                        </li> -->
                        <li class="default">
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </el-form>
  </div>
</template>

<script>
import divTemplate from '@/webManage/js/plug_ins/template/divTemplate'
export default divTemplate
</script>

<style scoped lang='less'>
@import '../../../webManage/css/plug_ins/template/divTemplate.less';
</style>