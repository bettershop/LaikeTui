<template>
  <div class="container">
    <div class="editor-nav" @click="$router.push('/mall/functionnavigation/editornav')">
      <span>编辑导览</span>
    </div>
    <div class="nav-content">
        <div class="operation-prompt">
            <div class="logo">
                <img src="@/assets/imgs/czts.png" alt="">
                <span>操作提示</span>
            </div>
            <div class="prompt-content">
                <p>① 在平台-权限管理中添加菜单（导览），会同步到编辑导览列表中</p>
                <p>② 点击右上角编辑导览，在编辑导览列表页中直接设置是否显示该功能模块在功能导览中，在下级列表中直接设置是否显示该导览细则内容</p>
            </div>
        </div>
        <div class="nav-entrance">
            <div class="plug-in" v-for="(item,index) in navList" :key="index">
                <div class="plug-container" v-if="item.list.length !== 0">
                    <h2>{{ item.title }}</h2>
                    <div class="plug-nav">
                        <div class="card" v-for="(items,index) in item.list" :key="index" @click="$router.push(`${items.url}`)">
                            <div class="img">
                                <img :src="items.image" alt="">
                            </div>
                            <div class="introduce">
                                <span>{{ items.title }}</span>
                                <span>{{ items.introduce }}</span>
                            </div>
                        </div>                    
                    </div>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import { index } from '@/api/mall/functionNavigation'
export default {
    name: "mainnavv",

    data() {
        return {
            navList: []
        }
    },

    created() {
        this.indexs()
    },
    methods: {
        async indexs() {
            const res = await index({
                api: 'admin.overview.index',
            })
            console.log(res);
            this.navList = res.data.data.list
        }
    }
}
</script>

<style scoped lang="less">
.container {
    width: 100%;
    height: 737px;
    overflow: hidden;
    overflow-y: auto;
    .editor-nav {
        width: 96px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        border: 1px solid #2890FF;
        border-radius: 4px;
        position: fixed;
        top: 70px;
        right: 30px;
        cursor: pointer;
        span {
            font-size: 14px;
            font-weight: 400;
            color: #2890FF;
        }
    }

    .nav-content {
        .operation-prompt {
            width: 100%;
            height: 114px;
            background: #E9F4FF;
            border: 1px dashed #2890FF;
            border-radius: 4px;
            padding: 17px 0 0 17px;
            .logo {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                img {
                    width: 20px;
                    height: 20px;
                }
                span {
                    font-size: 14px;
                    font-weight: bold;
                    color: #2890FF;
                }
            }
            .prompt-content {
                padding-left: 23px;
                p {
                    color: #6A7076;
                    &:not(:first-child) {
                        margin-top: 8px;
                    }
                }
            }
        }
        .nav-entrance {
            width: 100%;
            .plug-in {
                width: 100%;
                .plug-container {
                    h2 {
                        margin: 20px 0;
                        font-size: 16px;
                        font-weight: 400;
                        color: #414658;
                    }
                    .plug-nav {
                        width: 100%;
                        display: grid;
                        justify-content: space-between;
                        grid-template-columns: repeat(auto-fill, 306px);
                        // grid-gap: 30px;
                        .card {
                            width: 306px;
                            height: 100px;
                            background-color: #fff;
                            border-radius: 4px;
                            margin-bottom: 30px;
                            display: flex;
                            align-items: center;
                            padding: 0 24px;
                            &:hover{
                                box-shadow: 0px 0px 24px 0px rgba(38, 41, 52, 0.14);
                            }
                            .img {
                                width: 64px;
                                height: 64px;
                                margin-right: 20px;
                                img {
                                  width: 64px;
                                  height: 64px;  
                                }
                            }
                            .introduce {
                                height: 64px;
                                display: flex;
                                flex-direction: column;
                                justify-content: space-between;
                            }
                        }
                    }
                }
            }
        }
    }
}
</style>