<template>
  <div class="container">
    <div class="jump-list">
      <el-button @click="$router.push('/Platform/bulletin/announcementrelease')" class="bgColor" type="primary" icon="el-icon-circle-plus-outline" >发布公告</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
        <el-table :data="tableData" ref="table" class="el-table" style="width: 100%" :height="tableHeight">
            <el-table-column prop="id" label="序号" width="90">
            </el-table-column>
            <el-table-column prop="title" label="公告标题" width="166">
            </el-table-column>
            <el-table-column prop="type" label="公告类型" width="166">
                <template slot-scope="scope">
                    <span>{{ type(scope.row.type) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="公告内容" width="385">
                <template slot-scope="scope">
                    <div v-html="delP(scope.row.content)"></div>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="有效时间" width="363">
                <template slot-scope="scope">
                    <p>开始时间：{{ scope.row.startdate }}</p>
                    <p>结束时间：{{ scope.row.enddate }}</p>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="公告状态" width="76">
            </el-table-column>
            <el-table-column prop="add_date" label="创建时间" width="246">
                <template slot-scope="scope">
                    <span>{{ scope.row.add_time | dateFormat }}</span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" prop="operation" label="操作" width="160">
                <template slot-scope="scope">
                    <div class="OP-button">
                        <div class="OP-button-top">
                            <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                            <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
                        </div>
                    </div>
                </template>
            </el-table-column>
		</el-table>
        <div class="pageBox" ref="pageBox">
            <div class="pageLeftText">显示</div>
            <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
            :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
            <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
            </el-pagination>
        </div>
    </div>
  </div>
</template>

<script>
import { getSysNoticeInfo, delSysNoticeInfo } from '@/api/Platform/bulletin.js'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'announcementlist',
    mixins: [mixinstest],
    data() {
        return {
            tableData: [],

            // table高度
            tableHeight: null,
        }
    },

    created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
        }
        this.getSysNoticeInfos()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
        async getSysNoticeInfos() {
            const res = await getSysNoticeInfo({
                api: 'saas.sysNotice.getSysNoticeInfo',
                token: 'eyJUeXBlIjoiSnd0IiwiYWxnIjoiSFMyNTYifQ.eyJpYXQiOjE2MjIxMDE3NzUsImV4cCI6MTYyMjE4ODE3NX0.mDs4OK-7eI5MViGAAIqMEzLLTmLiuWsGfA7croIE_7k',
                storeType: 8,
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
            })
            console.log(res);
            this.total = res.data.data.list.length
            if(res.data.data.list.length < 10) {
                this.current_num = this.total
            }
            this.tableData = res.data.data.list
        },

        //选择一页多少条
        handleSizeChange(e){
            console.log(e);
            this.current_num = e
            this.pageSize = e
        },
        //点击上一页，下一页
        handleCurrentChange(e){
            this.dictionaryNum = e
            this.currpage = ((e - 1) * this.pageSize) + 1
            this.getSysNoticeInfos()().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
            })
        },

        Edit(value) {
            this.$router.push({
                name: 'editorannouncement',
                params: value,
                query: {
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
            console.log(value);
        },

        Delete(val) {
            console.log(val);
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                delSysNoticeInfo({
                    api: 'saas.sysNotice.delSysNoticeInfo',
                    storeId: val.store_id,
                    id: val.id
                }).then(res => {
                    this.getSysNoticeInfos()
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    console.log(res);
                })
            }).catch(() => {
                this.$message({
                type: 'info',
                message: '已取消删除'
                });          
            })
        },

        type(val) {
            if(val === 1) {
                return '系统维护'
            } else {
                return '版本更新'
            }
        },

        delP(val) {
            // return val.replace(/<\/?p[^>]*>/gi,'')
            return val.substring(0,val.length)
        }
    }
}
</script>

<style scoped lang="less">
.container {
    display: flex;
    flex-direction: column;
    .jump-list {
        width: 100%;
        display: flex;
        align-items: center;
        margin-bottom: 16px;
        button {
            width: 120px;
            height: 40px;
            background: #28B6FF;
            border-radius: 4px;
        }
    }

    .dictionary-list {
        // height: 605px;
        flex: 1;
        background: #FFFFFF;
        border-radius: 4px;
        /deep/.el-table__header {
        thead {
            tr {
            th{
                height: 61px;
                text-align: center;
                font-size: 14px;
                font-weight: bold;
                color: #414658;
            }
            }
        }
        }
        /deep/.el-table__body {
        tbody {
            tr {
            td{
                height: 92px;
                text-align: center;
                font-size: 14px;
                color: #414658;
                font-weight: 400;
            }
            }
        }
        }

        /deep/.el-table{
            .OP-button{
                .OP-button-top{
                    margin-bottom: 8px;
                }
            }
        }
    }

}
</style>