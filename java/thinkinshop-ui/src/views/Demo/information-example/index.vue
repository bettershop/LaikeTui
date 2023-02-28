<template>
  <div class="information-example">
    <div class="container-full">
        <div class="container">
            <div class="form-container">
                <el-button :plain="true" @click="open1">{{ $t('DemoPage.informationExamplePage.message') }}</el-button>
                <el-button :plain="true" @click="open2">{{ $t('DemoPage.informationExamplePage.success') }}</el-button>
                <el-button :plain="true" @click="open3">{{ $t('DemoPage.informationExamplePage.warning') }}</el-button>
                <el-button :plain="true" @click="open4">{{ $t('DemoPage.informationExamplePage.error') }}</el-button>
                <el-button :plain="true" @click="handleDelete2">{{ $t('DemoPage.informationExamplePage.Popup_confirm') }}</el-button>
                <el-button :plain="true" @click="dialogVisible = true">{{ $t('DemoPage.informationExamplePage.Popup_dialog') }}</el-button>
                <el-dialog
                    title="删除提示"
                    :visible.sync="dialogVisible"
                    width="30%">
                    <span>是否确认删除</span>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="qr">确 定</el-button>
                    </span>
                </el-dialog>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'information-example',

    data () {
        return {
            parentVM: null,
            dialogVisible: false
        }
    },
    methods: {
        qr() {
            this.$message({
                type: 'success',
                message: '删除成功!'
            });
            this.dialogVisible = false
        },
        
        async handleDelete2(id) {
            if (id) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

                // this.parentVM.dialog('删除提示', '是否确认删除', true);
                // console.log(VM);
                // VM.$data.dialogVisible = true
                // console.log(VM);
            } else {
                // 批量删除
            }
        },

        handleConfirm () {
            this.$message({
                showClose: true,
                message: '删除成功',
                type: 'success'
            });
            setTimeout(() => {
                this.parentVM.$data.dialogVisible = false;
            }, 1200)

        },
        open1() {
            this.$message({
                showClose: true,
                message: '这是一条消息提示'
            });
        },

        open2() {
            this.$message({
                showClose: true,
                message: '恭喜你，这是一条成功消息',
                type: 'success'
            });
        },

        open3() {
            this.$message({
                showClose: true,
                message: '警告哦，这是一条警告消息',
                type: 'warning'
            });
        },

        open4() {
            this.$message({
                showClose: true,
                message: '错了哦，这是一条错误消息',
                type: 'error'
            });
        },
    }
}
</script>

<style scoped>
.container-full {
  padding: 21px 20px;
  flex: 1;
}
.container-full .container {
  background: #fff;
  padding-top: 40px;
  padding-bottom: 40px;
  height: 100%;
  box-sizing: border-box;
}
.container-full .container .form-container {
  width: 684px;
  margin: 0 auto;
  height: 100%;
}
.container-full .container .form-container .el-form {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}
.container-full .container .form-container .el-form .line {
  text-align: center;
}
.container-full .container .form-container .el-form .form-scroll {
  height: calc(100vh - 60px - 21px - 40px - 40px - 40px - 21px - 22px);
  overflow: auto;
  margin-bottom: 22px;
  padding-right: 10px;
}
.container-full .container .form-container .el-form .form-scroll .el-form-item:last-child {
  margin-bottom: 0;
}
.container-full .container .form-container .el-form .form-footer {
  height: 40px;
}

</style>