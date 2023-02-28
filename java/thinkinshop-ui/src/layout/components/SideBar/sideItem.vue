<template>
  <div class="sideitem">
    <ul class="list-item">
        <li  v-for="(item,index) in item" :key="index" v-show="item.meta.is_core == 1">
            <span :class="{ active : item.name === $route.path.split('/')[2]}" @click.stop="handleSelect(`${mainNav}/${item.path}`)">
                {{ generateTitle(item.meta.title) }}
                <i class="orderListNum" v-if="item.meta.title === '订单列表'">({{orderListNum}})</i>
                <i class="salesReturnListNum" v-if="item.meta.title === '退货列表'">({{refundListNum}})</i>
            </span>
        </li>
    </ul>
  </div>
</template>

<script>
export default {
    name: 'sideitem',

    props: {
        item: {
            type: Array,
            required: true
        },
        mainNav: {
            type: String,
            required: true
        }
    },

    data() {
        return {
            navlist: this.item
        }
    },

    watch: {
        item(newV,oldV) {
            this.navlist = newV
        }
    },
    
    computed: {
        navlists() {
            return this.navlist
        },

        orderListNum() {
            return this.$store.getters.orderListNum
        },

        refundListNum() {
            return this.$store.getters.refundListNum
        }
    },

    methods: {
        resolvePath(routePath) {
            if (isExternal(routePath)) {
                return routePath
            }
            if (isExternal(this.basePath)) {
                return this.basePath
            }
            return path.resolve(this.basePath, routePath)
        },

        changItem(value) {
            this.item = value
        },

        handleSelect(index) {
            //   if(index == 'plug_ins/seckill') {
            //     window.location.href = 'https://java.admin.houjiemeishi.com/seckill/'
            //   } else {
            //       this.$router.push('/' + index);
            //   }
            this.$router.push('/' + index);
        },

        generateTitle(title) {
            const hasKey = this.$te('route.' + title)
            if (hasKey) {
            const translatedTitle = this.$t('route.' + title)
            return translatedTitle
            }
            return title
        },
    }
}
</script>

<style scoped lang="scss">
.list-item {
    width: 150px;
    height: calc(100vh - 100px);
    background-color:#fff;
    box-shadow: 3px 0px 3px rgba(185, 183, 183, 0.1);
    margin: 0;
    padding: 10px !important;
    li {
        width: 130px;
        height: 40px;
        line-height: 40px;
        margin: 8px 0;
        color: #b2bcd1;
        font-size: 14px;
        border-radius: 2px;
        &:hover span {
            color: #2890ff !important;
        }
        
        span {
            width: 100%;
            height: 100%;
            display: block;
            padding-left: 6px;
            color: #414658;
            font-size: 16px;
            &.active {
                background-color: #e9f4ff;
                color: #2890ff;
            }
        }

        .orderListNum,.salesReturnListNum {
            font-style: normal;
            color: red;
            // margin-left: 8px;
        }
    }
}
</style>