import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import {render} from 'less'

// 公共路由
export const constantRoutes = [
  // 登录
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  // 打印
  {
    path: '/print',
    component: () => import('@/views/print/index'),
    hidden: true
  },

  // 404
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  // 首页
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'dashboard'}
    }]
  },

]

// 动态路由
export const asyncRoutes = [
  // 平台
  {
    path: '/Platform',
    component: Layout,
    redirect: '/Platform/merchants',
    name: 'Platform',
    meta: {title: '平台', icon: ['platform','platform1']},
    children: [
      {
        path: 'merchants',
        name: 'merchants',
        redirect: '/Platform/merchants/merchantslist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '商城列表'},
        children: [
          {
            path: 'merchantslist',
            name: 'merchantslist',
            component: () => import('@/views/Platform/merchants/merchantslist'),
            meta: {keepAlive: true},
          },
          {
            path: 'addmerchants',
            name: 'addmerchants',
            component: () => import('@/views/Platform/merchants/addmerchants'),
            meta: {title: '添加商户'},
          },
          {
            path: 'editormerchants',
            name: 'editormerchants',
            component: () => import('@/views/Platform/merchants/editormerchants'),
            meta: {title: '编辑商户'},
          },
        ]
      },

      {
        path: 'permissions',
        name: 'permissions',
        redirect: '/Platform/permissions/menulist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '权限管理'},
        children: [
          {
            path: 'menulist',
            name: 'menulist',
            component: () => import('@/views/Platform/permissions/menulist'),
            meta: {title: '菜单列表',keepAlive: true}
          },
          {
            path: 'rolelist',
            name: 'rolelist',
            component: () => import('@/views/Platform/permissions/rolelist'),
            meta: {title: '角色列表'}
          },
          {
            path: 'editormenulevel',
            name: 'editormenulevel',
            component: () => import('@/views/Platform/permissions/editormenulevel'),
            meta: {title: ['菜单列表', '编辑菜单']}
          },
          {
            path: 'addmenulevel',
            name: 'addmenulevel',
            component: () => import('@/views/Platform/permissions/addmenulevel'),
            meta: {title: ['菜单列表', '添加菜单']}
          },
          {
            path: 'viewmenu',
            name: 'viewmenu',
            component: () => import('@/views/Platform/permissions/viewmenu'),
            meta: {title: ['菜单列表', '查看下级']}
          },
          {
            path: 'addrole',
            name: 'addrole',
            component: () => import('@/views/Platform/permissions/addrole'),
            meta: {title: ['角色列表', '添加角色'], returns: 'return'}
          },
          {
            path: 'viewrole',
            name: 'viewrole',
            component: () => import('@/views/Platform/permissions/viewrole'),
            meta: {title: ['角色列表', '查看角色']}
          },
          {
            path: 'rolepermission',
            name: 'rolepermission',
            component: () => import('@/views/Platform/permissions/rolepermission'),
            meta: {title: ['角色列表', '权限修改'], returns: 'return'}
          },
        ]
      },

      {
        path: 'graphics',
        name: 'graphics',
        component: () => import('@/views/Platform/graphics'),
        meta: {title: '图片设置'}
      },

      {
        path: 'numerical',
        name: 'numerical',
        redirect: '/Platform/numerical/dictionarylist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '数值管理'},
        children: [
          {
            path: 'dictionarylist',
            name: 'dictionarylist',
            component: () => import('@/views/Platform/numerical/dictionarylist'),
            meta: {keepAlive: true},
          },
          {
            path: 'adddictionary',
            name: 'adddictionary',
            component: () => import('@/views/Platform/numerical/adddictionary'),
            meta: {title: '添加数据字典'},
          },
          {
            path: 'datanamemanagement',
            name: 'datanamemanagement',
            component: () => import('@/views/Platform/numerical/datanamemanagement'),
            meta: {title: '数据名称管理'},
          },
          {
            path: 'compile',
            name: 'compile',
            component: () => import('@/views/Platform/numerical/compile'),
            meta: {title: '编辑'},
          }
        ]
      },

      {
        path: 'goodsSKU',
        name: 'goodsSKU',
        redirect: '/Platform/goodsSKU/goodsSKUlist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '商品SKU'},
        children: [
          {
            path: 'goodsSKUlist',
            name: 'goodsSKUlist',
            component: () => import('@/views/Platform/goodsSKU/goodsSKUlist'),
            meta: {},
          },
          {
            path: 'SKUdetails',
            name: 'SKUdetails',
            component: () => import('@/views/Platform/goodsSKU/SKUdetails'),
            meta: {title: '查看详情'},
          },
          {
            path: 'addSKU',
            name: 'addSKU',
            component: () => import('@/views/Platform/goodsSKU/addSKU'),
            meta: {title: '添加SKU'},
          },
          {
            path: 'editorSKU',
            name: 'editorSKU',
            component: () => import('@/views/Platform/goodsSKU/editorSKU'),
            meta: {title: '添加SKU'},
          },
          {
            path: 'addSKUlist',
            name: 'addSKUlist',
            component: () => import('@/views/Platform/goodsSKU/addSKUlist'),
            meta: {title: '添加SKU1'},
          }
        ]
      },

      {
        path: 'bulletin',
        name: 'bulletin',
        redirect: '/Platform/bulletin/announcementlist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '公告管理'},
        children: [
          {
            path: 'announcementlist',
            name: 'announcementlist',
            component: () => import('@/views/Platform/bulletin/announcementlist'),
            meta: {},
          },
          {
            path: 'announcementrelease',
            name: 'announcementrelease',
            component: () => import('@/views/Platform/bulletin/announcementrelease'),
            meta: {title: '发布公告'},
          },
          {
            path: 'editorannouncement',
            name: 'editorannouncement',
            component: () => import('@/views/Platform/bulletin/editorannouncement'),
            meta: {title: '编辑公告'},
          }
        ]
      },

      {
        path: 'parameter',
        name: 'parameter',
        component: () => import('@/views/Platform/parameter'),
        meta: {title: '参数设置'},
      },

      {
        path: 'system',
        name: 'system',
        redirect: '/Platform/system/systemConfig',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '系统设置'},
        children: [
          {
            path: 'systemConfig',
            name: 'systemConfig',
            component: () => import('@/views/Platform/system/systemConfig'),
            meta: {title: ''},
          },
        ]
      },

      {
        path: 'reportManagement',
        name: 'reportManagement',
        redirect: '/Platform/reportManagement/businessReport',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '报表管理'},
        children: [
          {
            path: 'businessReport',
            name: 'businessReport',
            component: () => import('@/views/Platform/reportManagement/businessReport.vue'),
            meta: {title: '商户营业报表'}
          },
          {
            path: 'addUsersReport',
            name: 'addUsersReport',
            component: () => import('@/views/Platform/reportManagement/addUsersReport.vue'),
            meta: {title: '商户新增用户报表'}
          },
          {
            path: 'orderReport',
            name: 'orderReport',
            component: () => import('@/views/Platform/reportManagement/orderReport.vue'),
            meta: {title: '商户订单报表'}
          },
        ]
      },

      {
        path: 'logisticsCompanyManage',
        name: 'logisticsCompanyManage',
        redirect: '/Platform/logisticsCompanyManage/logisticsCompanyList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '物流公司管理'},
        children: [
          {
            path: 'logisticsCompanyList',
            name: 'logisticsCompanyList',
            component: () => import('@/views/Platform/logisticsCompanyManage/logisticsCompanyList'),
            meta: {title: ''},
          },
          {
            path: 'logisticsCompanySave',
            name: 'logisticsCompanySave',
            component: () => import('@/views/Platform/logisticsCompanyManage/logisticsCompanySave'),
            meta: {title: '添加物流公司'},
          }, {
            path: 'logisticsCompanySave',
            name: 'logisticsCompanyEdit',
            component: () => import('@/views/Platform/logisticsCompanyManage/logisticsCompanySave'),
            meta: {title: '编辑物流公司'},
          },
        ]
      },

    ]
  },
  
  // 商城
  {
    path: '/mall',
    component: Layout,
    redirect: '/mall/functionnavigation',
    name: 'mall',
    meta: {title: '商城', icon: ['mall','mall1'], roles: 152},
    children: [
      {
        path: 'functionnavigation',
        name: 'functionnavigation',
        redirect: '/mall/functionnavigation/mainnav',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '功能导览', roles: 189},
        children: [
          {
            path: 'mainnav',
            name: 'mainnav',
            component: () => import('@/views/mall/functionnavigation/mainnav'),
            meta: {},
          },
          {
            path: 'editornav',
            name: 'editornav',
            component: () => import('@/views/mall/functionnavigation/editornav'),
            meta: {title: '编辑导览'},
          },
          {
            path: 'viewnav',
            name: 'viewnav',
            component: () => import('@/views/mall/functionnavigation/viewnav'),
            meta: {title: ['编辑导览', '商城', '查看下级']},
          },
        ]
      },
      {
        path: 'aftersaleAddress',
        name: 'aftersaleAddress',
        redirect: '/mall/aftersaleAddress/list',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '售后地址', roles: 155},
        children: [
          {
            path: 'list',
            name: 'list',
            component: () => import('@/views/mall/aftersaleAddress/addressList'),
            meta: {},
          },
          {
            path: 'save',
            name: 'save',
            component: () => import('@/views/mall/aftersaleAddress/addressSave'),
            meta: {title: '添加地址'},
          },
          {
            path: 'edit',
            name: 'edit',
            component: () => import('@/views/mall/aftersaleAddress/addressSave'),
            meta: {title: '编辑地址'},
          }
        ]
      },
      {
        path: 'systemNotice',
        name: 'systemNotice',
        redirect: '/mall/systemNotice/noticeList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '公告列表', roles: 153},
        children: [
          {
            path: 'noticeList',
            name: 'noticeList',
            component: () => import('@/views/mall/systemNotice/noticeList'),
            meta: {title: ''},
          },
          {
            path: 'noticeDetail',
            name: 'noticeDetail',
            component: () => import('@/views/mall/systemNotice/noticeDetail'),
            meta: {title: '公告详情'},
          },
        ]
      },
      {
        path: 'payManagement',
        name: 'payManagement',
        redirect: '/mall/payManagement/payList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '支付管理', roles: 161},
        children: [
          {
            path: 'payList',
            name: 'payList',
            component: () => import('@/views/mall/payManagement/payList'),
            meta: {keepAlive: true},
          },
          {
            path: 'parameterModify',
            name: 'parameterModify',
            component: () => import('@/views/mall/payManagement/parameterModify'),
            meta: {title: '参数修改'},
          }
        ]
      },
      {
        path: 'sms',
        name: 'sms',
        redirect: '/mall/sms/smsList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '短信配置', roles: 439},
        children: [
          {
            path: 'smsList',
            name: 'smsList',
            component: () => import('@/views/mall/sms/smsList'),
            meta: {title: ''},
          },
          {
            path: 'smsSave',
            name: 'smsSave',
            component: () => import('@/views/mall/sms/smsSave'),
            meta: {title: '添加短信'},
          },
          {
            path: 'smsEdit',
            name: 'smsEdit',
            component: () => import('@/views/mall/sms/smsSave'),
            meta: {title: '编辑短信'},
          }, {
            path: 'smsTemplateSave',
            name: 'smsTemplateSave',
            component: () => import('@/views/mall/sms/smsTemplateSave'),
            meta: {title: '添加模板'},
          }, {
            path: 'smsTemplateEdit',
            name: 'smsTemplateEdit',
            component: () => import('@/views/mall/sms/smsTemplateSave'),
            meta: {title: '编辑模板'},
          },
        ]
      },
      {
        path: 'searchConfig',
        name: 'searchConfig',
        redirect: '/mall/searchConfig/config',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '搜索配置', roles: 176},
        children: [
          {
            path: 'config',
            name: 'config',
            component: () => import('@/views/mall/search/searchConfig'),
            meta: {},
          }
        ]
      },
      {
        path: 'terminalConfig',
        name: 'terminalConfig',
        redirect: '/mall/terminalConfig/terminalList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '终端配置', roles: 384},
        children: [
          {
            path: 'terminalList',
            name: 'terminalList',
            component: () => import('@/views/mall/terminalConfig/terminalList'),
            meta: {title: ''},
          },
        ]
      },
      {
        path: 'basicConfiguration',
        name: 'basicConfiguration',
        component: () => import('@/views/mall/systemManagement/basicConfiguration.vue'),
        meta: {title: '基础配置', roles: 181}
      },
      {
        path: 'agreement',
        name: 'agreement',
        // component: () => import('@/views/mall/systemManagement/protocolConfiguration.vue'),
        component: {
          render(c) {
            return c('router-view')
          }
        },
        redirect: '/mall/agreement/protocolConfiguration',
        meta: {title: '协议配置', roles: 177},
        children: [
          {
            path: 'protocolConfiguration',
            name: 'protocolConfiguration',
            component: () => import('@/views/mall/systemManagement/agreement/protocolConfiguration.vue'),
            meta: {},
          },
          {
            path: 'addAgreement',
            name: 'addAgreement',
            component: () => import('@/views/mall/systemManagement/agreement/addAgreement.vue'),
            meta: {title: '添加协议'},
          },
          {
            path: 'editorAgreement',
            name: 'editorAgreement',
            component: () => import('@/views/mall/systemManagement/agreement/editorAgreement.vue'),
            meta: {title: '编辑协议'},
          },
        ]
      },
      // {
      //   path: 'addAgreement',
      //   name: 'addAgreement',
      //   component: () => import('@/views/mall/systemManagement/addAgreement.vue'),
      //   meta: {title: ['协议配置', '添加协议']},
      //   hidden: true
      // },
      // {
      //   path: 'editorAgreement',
      //   name: 'editorAgreement',
      //   component: () => import('@/views/mall/systemManagement/editorAgreement.vue'),
      //   meta: {title: ['协议配置', '编辑协议']},
      //   hidden: true
      // },
      {
        path: 'commonProblem',
        name: 'commonProblem',
        component: () => import('@/views/mall/systemManagement/commonProblem.vue'),
        meta: {title: '常见问题', roles: 440}
      },
      {
        path: 'newbieGuide',
        name: 'newbieGuide',
        component: () => import('@/views/mall/systemManagement/newbieGuide.vue'),
        meta: {title: '新手指南', roles: 441}
      },
      {
        path: 'afterSales',
        name: 'afterSales',
        component: () => import('@/views/mall/systemManagement/afterSales.vue'),
        meta: {title: '售后服务', roles: 442}
      },
      {
        path: 'aboutUs',
        name: 'aboutUs',
        component: () => import('@/views/mall/systemManagement/aboutUs.vue'),
        meta: {title: '关于我们', roles: 443}
      },
    ]
  },

  // 首页
  {
    path: '/home',
    component: Layout,
    redirect: '/home/homeReport',
    name: 'home',
    meta: {title: '首页', icon: ['home_page','home_page1'], roles: 187},
    children: [
      {
        path: 'homeReport',
        name: 'homeReport',
        component: () => import('@/views/home/homeReport'),
        meta: {title: '商城首页'}
      },
    ]
  },

  // 商品
  {
    path: '/goods',
    component: Layout,
    redirect: '/goods/goodslist',
    name: 'goods',
    meta: {title: '商品', icon: ['goods','goods1'], roles: 58},
    children: [
      {
        path: 'goodslist',
        name: 'goodslist',
        redirect: '/goods/goodslist/physicalgoods',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '商品列表', roles: 59},
        children: [
          {
            path: 'physicalgoods',
            name: 'physicalgoods',
            component: () => import('@/views/goods/goodslist/physicalgoods'),
            meta: {title: '实物商品',keepAlive: true}
          },
          {
            path: 'releasephysical',
            name: 'releasephysical',
            component: () => import('@/views/goods/goodslist/releasephysical'),
            meta: {title: ['实物商品', '发布商品']}
          },
          {
            path: 'editorphysical',
            name: 'editorphysical',
            component: () => import('@/views/goods/goodslist/editorphysical'),
            meta: {title: ['实物商品', '编辑商品']}
          },
          {
            path: 'copyphysical',
            name: 'copyphysical',
            component: () => import('@/views/goods/goodslist/copyphysical'),
            meta: {title: ['实物商品', '复制商品']}
          },
          {
            path: 'virtualgoods',
            name: 'virtualgoods',
            component: () => import('@/views/goods/goodslist/virtualgoods'),
            meta: {title: '虚拟商品'}
          },
        ]
      },
      {
        path: 'brandmanagement',
        name: 'brandmanagement',
        redirect: '/goods/brandmanagement/brandlist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '品牌管理', roles: 74},
        children: [
          {
            path: 'brandlist',
            name: 'brandlist',
            component: () => import('@/views/goods/brandmanagement/brandlist'),
            meta: {keepAlive: true},
          },
          {
            path: 'addbrand',
            name: 'addbrand',
            component: () => import('@/views/goods/brandmanagement/addbrand'),
            meta: {title: '新增品牌'},
          },
          {
            path: 'editorbrand',
            name: 'editorbrand',
            component: () => import('@/views/goods/brandmanagement/editorbrand'),
            meta: {title: '编辑品牌'},
          },
        ]
      },
      {
        path: 'goodsclassify',
        name: 'goodsclassify',
        redirect: '/goods/goodsclassify/goodsclassifylist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '商品分类', roles: 68},
        children: [
          {
            path: 'goodsclassifylist',
            name: 'goodsclassifylist',
            component: () => import('@/views/goods/goodsclassify/goodsclassifylist'),
            meta: {keepAlive: true},
          },
          {
            path: 'addgoodsclass1',
            name: 'addgoodsclass1',
            component: () => import('@/views/goods/goodsclassify/addgoodsclass1'),
            meta: {title: '新增分类'},
          },
          {
            path: 'addgoodsclass2',
            name: 'addgoodsclass2',
            component: () => import('@/views/goods/goodsclassify/addgoodsclass2'),
            meta: {title: '添加子类'},
          },
          {
            path: 'addgoodsclass3',
            name: 'addgoodsclass3',
            component: () => import('@/views/goods/goodsclassify/addgoodsclass3'),
            meta: {title: '添加子类'},
          },
          {
            path: 'addgoodsclass4',
            name: 'addgoodsclass4',
            component: () => import('@/views/goods/goodsclassify/addgoodsclass4'),
            meta: {title: '添加子类'},
          },
          {
            path: 'addgoodsclass5',
            name: 'addgoodsclass5',
            component: () => import('@/views/goods/goodsclassify/addgoodsclass5'),
            meta: {title: '添加子类'},
          },
          {
            path: 'editorgoodsclass1',
            name: 'editorgoodsclass1',
            component: () => import('@/views/goods/goodsclassify/editorgoodsclass1'),
            meta: {title: '编辑分类'},
          },
          {
            path: 'editorgoodsclass2',
            name: 'editorgoodsclass2',
            component: () => import('@/views/goods/goodsclassify/editorgoodsclass2'),
            meta: {title: '编辑分类'},
          },
          {
            path: 'editorgoodsclass3',
            name: 'editorgoodsclass3',
            component: () => import('@/views/goods/goodsclassify/editorgoodsclass3'),
            meta: {title: '编辑分类'},
          },
          {
            path: 'editorgoodsclass4',
            name: 'editorgoodsclass4',
            component: () => import('@/views/goods/goodsclassify/editorgoodsclass4'),
            meta: {title: '编辑分类'},
          },
          {
            path: 'editorgoodsclass5',
            name: 'editorgoodsclass5',
            component: () => import('@/views/goods/goodsclassify/editorgoodsclass5'),
            meta: {title: '编辑分类'},
          },
          {
            path: 'viewgoodsclasslower',
            name: 'viewgoodsclasslower',
            component: () => import('@/views/goods/goodsclassify/viewgoodsclasslower'),
            meta: {title: '查看下级'},
          },
        ]
      },
      {
        path: 'freightmanagement',
        name: 'freightmanagement',
        redirect: '/goods/freightmanagement/freightlist',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '运费管理', roles: 387},
        children: [
          {
            path: 'freightlist',
            name: 'freightlist',
            component: () => import('@/views/goods/freightmanagement/freightlist'),
            meta: {},
          },
          {
            path: 'addtemplate',
            name: 'addtemplate',
            component: () => import('@/views/goods/freightmanagement/addtemplate'),
            meta: {title: '添加模板'},
          },
          {
            path: 'editortemplate',
            name: 'editortemplate',
            component: () => import('@/views/goods/freightmanagement/editortemplate'),
            meta: {title: '编辑模板'},
          }
        ]
      },
      {
        path: 'taobaoAssistant',
        name: 'taobaoAssistant',
        redirect: '/goods/taobaoAssistant/taskList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '淘宝助手', roles: 88},
        children: [
          {
            path: 'taskList',
            name: 'taskList',
            component: () => import('@/views/goods/taobaoAssistant/taskList'),
            meta: {},
          },
          {
            path: 'viewTask',
            name: 'viewTask',
            component: () => import('@/views/goods/taobaoAssistant/viewTask'),
            meta: {title: '任务详情'},
          },
        ]
      },
      {
        path: 'inventoryManagement',
        name: 'inventoryManagement',
        redirect: '/goods/inventoryManagement/inventoryList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '库存管理', roles: 80},
        children: [
          {
            path: 'inventoryList',
            name: 'inventoryList',
            component: () => import('@/views/goods/inventoryManagement/inventoryList'),
            meta: {title: '库存列表'}
          },
          {
            path: 'InventoryDetails',
            name: 'InventoryDetails',
            component: () => import('@/views/goods/inventoryManagement/InventoryDetails'),
            meta: {title: '库存详情'}
          },
        ]
      },
      {
        path: 'InventoryWarnings',
        name: 'InventoryWarnings',
        redirect: '/goods/InventoryWarnings/InventoryWarning',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '库存预警', roles: 452},
        children: [
          {
            path: 'InventoryWarning',
            name: 'InventoryWarning',
            component: () => import('@/views/goods/InventoryWarnings/InventoryWarning.vue'),
            meta: {title: ''}
          },
          {
            path: 'warningRecord',
            name: 'warningRecord',
            component: () => import('@/views/goods/InventoryWarnings/warningRecord.vue'),
            meta: {title: '预警记录'}
          },
        ]
      },
      {
        path: 'cargoDetail',
        name: 'cargoDetail',
        component: () => import('@/views/goods/cargoDetails/cargoDetails.vue'),
        meta: {title: '入货详情', roles: 426}
      },
      {
        path: 'shippingDetail',
        name: 'shippingDetail',
        component: () => import('@/views/goods/shippingDetails/shippingDetails.vue'),
        meta: {title: '出货详情', roles: 427}
      },
      {
        path: 'tag',
        name: 'tag',
        redirect: '/goods/tag/tagList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '商品标签', roles: 407},
        children: [
          {
            path: 'tagList',
            name: 'tagList',
            component: () => import('@/views/goods/tag/tagList'),
            meta: {},
          }
        ]
      },

    ]
  },

  // 订单
  {
    path: '/order',
    component: Layout,
    redirect: '/order/orderList',
    name: 'order',
    meta: {title: '订单', icon: ['order','order1'], roles: 96},
    children: [
      {
        path: 'orderList',
        name: 'orderList',
        redirect: '/order/orderList/orderLists',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '订单列表', roles: 97},
        children: [
          {
            path: 'orderLists',
            name: 'orderLists',
            component: () => import('@/views/order/orderList/orderLists'),
            meta: {title: '订单列表'}
          },
          {
            path: 'orderDetails',
            name: 'orderDetails',
            component: () => import('@/views/order/orderList/orderDetails'),
            meta: {title: '订单详情'}
          },
          {
            path: 'editorOrder',
            name: 'editorOrder',
            component: () => import('@/views/order/orderList/editorOrder'),
            meta: {title: '编辑订单'}
          },
          {
            path: 'goodsDelivery',
            name: 'goodsDelivery',
            component: () => import('@/views/order/orderList/goodsDelivery'),
            meta: {title: '发货'}
          },
          {
            path: 'valetOrder',
            name: 'valetOrder',
            component: () => import('@/views/order/orderList/valetOrder'),
            meta: {title: '代客下单'}
          },
        ]
      },
      {
        path: 'salesReturn',
        name: 'salesReturn',
        redirect: '/order/salesReturn/salesReturnList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '退货列表', roles: 105},
        children: [
          {
            path: 'salesReturnList',
            name: 'salesReturnList',
            component: () => import('@/views/order/salesReturn/salesReturnList'),
            meta: {title: ''}
          },
          {
            path: 'salesReturnDetails',
            name: 'salesReturnDetails',
            component: () => import('@/views/order/salesReturn/salesReturnDetails'),
            meta: {title: '售后详情'}
          },
        ]
      },
      {
        path: 'commentManage',
        name: 'commentManage',
        redirect: '/order/commentManage/commentList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '评价管理', roles: 109},
        children: [
          {
            path: 'commentList',
            name: 'commentList',
            component: () => import('@/views/order/commentManage/commentList'),
            meta: {title: ''}
          }, {
            path: 'commentReply',
            name: 'commentReply',
            component: () => import('@/views/order/commentManage/commentReply'),
            meta: {title: '回复'}
          }, {
            path: 'commentEdit',
            name: 'commentEdit',
            component: () => import('@/views/order/commentManage/commentEdit'),
            meta: {title: '修改'}
          },
        ]
      },
      {
        path: 'orderSet',
        name: 'orderSet',
        redirect: '/order/orderSet/orderSetPage',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '订单设置', roles: 114},
        children: [{
          path: 'orderSetPage',
          name: 'orderSetPage',
          component: () => import('@/views/order/orderSet/orderSetPage'),
          meta: {title: ''}
        }]
      },
      {
        path: 'orderSettlement',
        name: 'orderSettlementList',
        redirect: '/order/orderSettlement/orderSettlementList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '订单结算', roles: 452},
        children: [{
          path: 'orderSettlementList',
          name: 'orderSettlementList',
          component: () => import('@/views/order/orderSettlement/orderSettlementList'),
          meta: {title: '列表'}
        }, {
          path: 'orderSettlementDetail',
          name: 'orderSettlementDetail',
          component: () => import('@/views/order/orderSettlement/orderSettlementDetail'),
          meta: {title: '查看'}
        },]
      },
    ]
  },

  // 会员
  {
    path: '/members',
    component: Layout,
    redirect: '/members/membersList',
    name: 'members',
    meta: {title: '会员', icon: ['vip','vip1'], roles: 115},
    children: [
      {
        path: 'membersList',
        name: 'membersList',
        redirect: '/members/membersList/membersLists',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '会员列表', roles: 116},
        children: [
          {
            path: 'membersLists',
            name: 'membersLists',
            component: () => import('@/views/members/membersList/membersLists'),
            meta: {title: '',keepAlive: true}
          },
          {
            path: 'membersLevel',
            name: 'membersLevel',
            component: () => import('@/views/members/membersList/membersLevel'),
            meta: {title: '会员等级'}
          },
          {
            path: 'membersSet',
            name: 'membersSet',
            component: () => import('@/views/members/membersList/membersSet'),
            meta: {title: '会员设置'}
          },
          {
            path: 'addLevel',
            name: 'addLevel',
            component: () => import('@/views/members/membersList/addLevel'),
            meta: {title: '添加会员等级'}
          },
          {
            path: 'addMembers',
            name: 'addMembers',
            component: () => import('@/views/members/membersList/addMembers'),
            meta: {title: '添加会员'}
          },
          {
            path: 'viewMembers',
            name: 'viewMembers',
            component: () => import('@/views/members/membersList/viewMembers'),
            meta: {title: '会员详情'}
          },
          {
            path: 'editorMembers',
            name: 'editorMembers',
            component: () => import('@/views/members/membersList/editorMembers'),
            meta: {title: '会员编辑'}
          },
        ]
      },
      {
        path: 'addMember',
        name: 'addMember',
        redirect: '/members/membersList/addMembers',
        meta: {title: '添加会员', roles: 432},
      },
      {
        path: 'addMembersLevels',
        name: 'addMembersLevels',
        redirect: '/members/membersList/addLevel',
        meta: {title: '添加会员等级', roles: 431},
      },
      {
        path: 'membersLevels',
        name: 'membersLevels',
        redirect: '/members/membersList/membersLevel',
        meta: {title: '会员等级', roles: 429},
      },
      {
        path: 'membersSets',
        name: 'membersSets',
        redirect: '/members/membersList/membersSet',
        meta: {title: '会员设置', roles: 431},
      },
    ]
  },

  // 财务
  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/withdrawalManage',
    name: 'finance',
    meta: {title: '财务', icon: ['shuju','shuju1'], roles: 129},
    children: [
      {
        path: 'withdrawalManage',
        name: 'withdrawalManage',
        redirect: '/finance/withdrawalManage/withdrawalExamineList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '提现管理', roles: 130},
        children: [
          {
            path: 'withdrawalExamineList',
            name: 'withdrawalExamineList',
            component: () => import('@/views/finance/withdrawalManage/withdrawalExamineList'),
            meta: {title: '提现审核'}
          }, {
            path: 'withdrawalRecordList',
            name: 'withdrawalRecordList',
            component: () => import('@/views/finance/withdrawalManage/withdrawalRecordList'),
            meta: {title: '提现记录'}
          }, {
            path: 'walletConfig',
            name: 'walletConfig',
            component: () => import('@/views/finance/withdrawalManage/walletConfig'),
            meta: {title: '钱包参数'}
          },
        ]
      },
    ]
  },

  // 数据
  {
    path: '/Data',
    component: Layout,
    redirect: '/Data/addvip-report',
    name: 'Data',
    meta: {title: '数据', icon: ['shuju','shuju1'], roles: 142},
    children: [
      {
        path: 'addvip-report',
        name: 'addvip-report',
        component: () => import('@/views/Data/addvip-report'),
        meta: {title: '新增会员报表', roles: 143}
      },
      {
        path: 'vipconsumption-report',
        name: 'vipconsumption-report',
        component: () => import('@/views/Data/vipconsumption-report'),
        meta: {title: '会员消费报表', roles: 435}
      },
      {
        path: 'vip-proportion',
        name: 'vip-proportion',
        component: () => import('@/views/Data/vip-proportion'),
        meta: {title: '会员比例', roles: 436}
      },
      {
        path: 'orde-report',
        name: 'orde-report',
        component: () => import('@/views/Data/orde-report'),
        meta: {title: '订单报表', roles: 147}
      },
      {
        path: 'goods-report',
        name: 'goods-report',
        component: () => import('@/views/Data/goods-report'),
        meta: {title: '商品报表', roles: 148}
      }
    ]
  },

  // 插件
  {
    path: '/plug_ins',
    component: Layout,
    redirect: '/plug_ins/coupons',
    name: 'plug_ins',
    meta: {title: '插件', icon: ['chajian','chajian1'], roles: 149},
    children: [
      {
        path: 'coupons',
        name: 'coupons',
        redirect: '/plug_ins/coupons/couponsList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '卡券', roles: 215},
        children: [
          {
            path: 'couponsList',
            name: 'couponsList',
            component: () => import('@/views/plug_ins/coupons/couponsList.vue'),
            meta: {title: '优惠券列表',keepAlive: true}
          },
          {
            path: 'addCoupons',
            name: 'addCoupons',
            component: () => import('@/views/plug_ins/coupons/addCoupons.vue'),
            meta: {title: '添加优惠券'}
          },
          {
            path: 'viewCoupons',
            name: 'viewCoupons',
            component: () => import('@/views/plug_ins/coupons/viewCoupons.vue'),
            meta: {title: '查看优惠券'}
          },
          {
            path: 'editorCoupons',
            name: 'editorCoupons',
            component: () => import('@/views/plug_ins/coupons/editorCoupons.vue'),
            meta: {title: '编辑优惠券'}
          },
          {
            path: 'getRecord',
            name: 'getRecord',
            component: () => import('@/views/plug_ins/coupons/getRecord.vue'),
            meta: {title: '领取记录'}
          },
          {
            path: 'givingRecords',
            name: 'givingRecords',
            component: () => import('@/views/plug_ins/coupons/givingRecords.vue'),
            meta: {title: '赠送记录'}
          },
          {
            path: 'couponsSet',
            name: 'couponsSet',
            component: () => import('@/views/plug_ins/coupons/couponsSet.vue'),
            meta: {title: '优惠券设置'}
          },
        ]
      },
      {
        path: 'stores',
        name: 'stores',
        redirect: '/plug_ins/stores/store',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '店铺', roles: 234},
        children: [
          {
            path: 'store',
            name: 'store',
            component: () => import('@/views/plug_ins/stores/store.vue'),
            meta: {title: ''}
          },
          {
            path: 'auditList',
            name: 'auditList',
            component: () => import('@/views/plug_ins/stores/auditList.vue'),
            meta: {title: '审核列表'}
          },
          {
            path: 'addStore',
            name: 'addStore',
            component: () => import('@/views/plug_ins/stores/addStore.vue'),
            meta: {title: '添加自营店'}
          },
          {
            path: 'viewStore',
            name: 'viewStore',
            component: () => import('@/views/plug_ins/stores/viewStore.vue'),
            meta: {title: '查看'}
          },
          {
            path: 'editorStore',
            name: 'editorStore',
            component: () => import('@/views/plug_ins/stores/editorStore.vue'),
            meta: {title: '编辑'}
          },
          {
            path: 'goodsAudit',
            name: 'goodsAudit',
            component: () => import('@/views/plug_ins/stores/goodsAudit.vue'),
            meta: {title: '商品审核'}
          },
          {
            path: 'viewGoods',
            name: 'viewGoods',
            component: () => import('@/views/plug_ins/stores/viewGoods.vue'),
            meta: {title: '商品审核记录'}
          },
          {
            path: 'withdrawalAudit',
            name: 'withdrawalAudit',
            component: () => import('@/views/plug_ins/stores/withdrawalAudit.vue'),
            meta: {title: '提现审核'}
          },
          {
            path: 'withdrawalRecord',
            name: 'withdrawalRecord',
            component: () => import('@/views/plug_ins/stores/withdrawalRecord.vue'),
            meta: {title: '提现记录'}
          },
          {
            path: 'storeSet',
            name: 'storeSet',
            component: () => import('@/views/plug_ins/stores/storeSet.vue'),
            meta: {title: '店铺设置'}
          },
        ]
      },
      {
        path: 'template',
        name: 'template',
        redirect: '/plug_ins/template/divTemplate',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '模板管理', roles: 274},
        children: [
          {
            path: 'divTemplate',
            name: 'divTemplate',
            component: () => import('@/views/plug_ins/template/divTemplate.vue'),
            meta: {title: 'DIY模板管理'}
          },
          {
            path: 'playlist',
            name: 'playlist',
            component: () => import('@/views/plug_ins/template/playlist.vue'),
            meta: {title: '轮播图列表', roles: 275}
          },
          {
            path: 'addSlideShow',
            name: 'addSlideShow',
            component: () => import('@/views/plug_ins/template/addSlideShow.vue'),
            meta: {title: ['轮播图', '添加轮播图']}
          },
          {
            path: 'editorSlideShow',
            name: 'editorSlideShow',
            component: () => import('@/views/plug_ins/template/editorSlideShow.vue'),
            meta: {title: ['轮播图', '编辑轮播图']}
          },
          {
            path: 'navigationBar',
            name: 'navigationBar',
            component: () => import('@/views/plug_ins/template/navigationBar.vue'),
            meta: {title: 'UI导航栏', roles: 280}
          },
          {
            path: 'classification',
            name: 'classification',
            component: () => import('@/views/plug_ins/template/classification.vue'),
            meta: {title: '分类管理', roles: 285}
          },
          {
            path: 'activity',
            name: 'activity',
            component: () => import('@/views/plug_ins/template/activity.vue'),
            meta: {title: '活动管理', roles: 287}
          },
          {
            path: 'addActivity',
            name: 'addActivity',
            component: () => import('@/views/plug_ins/template/addActivity.vue'),
            meta: {title: ['活动管理', '添加活动']}
          },
          {
            path: 'editorActivity',
            name: 'editorActivity',
            component: () => import('@/views/plug_ins/template/editorActivity.vue'),
            meta: {title: ['活动管理', '编辑活动']}
          },
          {
            path: 'activityGoods',
            name: 'activityGoods',
            component: () => import('@/views/plug_ins/template/activityGoods.vue'),
            meta: {title: ['活动管理', '活动商品']}
          },
        ]
      },
    ]
  },

  // 权限管理
  {
    path: '/authority',
    component: Layout,
    redirect: '/authority/adminManage',
    name: 'authority',
    meta: {title: '权限', icon: ['quanxian','quanxian1'], roles: 190},
    children: [
      {
        path: 'adminManage',
        name: 'adminManage',
        redirect: '/authority/adminManage/adminUserList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '管理员列表', roles: 191},
        children: [{
          path: 'adminUserList',
          component: () => import('@/views/authority/adminUserManage/adminUserList'),
          // meta: {title: '管理员列表'},
        }, {
          path: 'addAdminUser',
          name: 'addAdminUser',
          component: () => import('@/views/authority/adminUserManage/addAdminUser'),
          meta: {title: '添加管理员'},
        }, {
          path: 'editAdminUser',
          name: 'editAdminUser',
          component: () => import('@/views/authority/adminUserManage/addAdminUser'),
          meta: {title: '编辑管理员'},
        }]
      },
      {
        path: 'roleManagement',
        name: 'roleManagement',
        redirect: '/authority/roleManagement/roleList',
        component: {
          render(c) {
            return c('router-view')
          }
        },
        meta: {title: '角色管理', roles: 200},
        children: [
          {
            path: 'roleList',
            name: 'roleList',
            component: () => import('@/views/authority/roleManagement/roleList.vue'),
            meta: {title: '',keepAlive: true}
          },
          {
            path: 'addRoles',
            name: 'addRoles',
            component: () => import('@/views/authority/roleManagement/addRoles.vue'),
            meta: {title: '添加角色'}
          },
          {
            path: 'editorRoles',
            name: 'editorRoles',
            component: () => import('@/views/authority/roleManagement/editorRoles.vue'),
            meta: {title: '编辑角色'}
          },
          {
            path: 'viewRoles',
            name: 'viewRoles',
            component: () => import('@/views/authority/roleManagement/viewRoles.vue'),
            meta: {title: '查看角色'}
          },
        ]
      },
      {
        path: 'adminLoggerList',
        name: 'adminLoggerList',
        component: () => import('@/views/authority/adminLoggerManage/adminLoggerList'),
        meta: {title: '管理员日志', roles: 197}
      },
    ]
  },

  // 资源管理
  {
    path: '/resources',
    component: Layout,
    redirect: '/resources/imageList',
    name: 'resources',
    meta: {title: '资源', icon: ['ziyuan','ziyuan1'], roles: 150},
    children: [
      {
        path: 'imageList',
        name: 'imageList',
        component: () => import('@/views/resources/imageList/list'),
        meta: {title: '图片管理', roles: 151}
      },
    ]
  },

  // Demo
  {
    path: '/Demo',
    component: Layout,
    redirect: '/Demo/table-example',
    name: 'Demo',
    meta: {title: 'Demo', icon: ['demo','demo1']},
    children: [
      {
        path: 'table-example',
        name: 'table-example',
        component: () => import('@/views/Demo/table-example'),
        meta: {title: 'tableExample'}
      },
      {
        path: 'table-from',
        name: 'table-from',
        component: () => import('@/views/Demo/table-from'),
        meta: {title: 'tableFrom'}
      },
      {
        path: 'complex-table',
        name: 'complex-table',
        component: () => import('@/views/Demo/complex-table'),
        meta: {title: 'complexTable'}
      },
      {
        path: 'information-example',
        name: 'information-example',
        component: () => import('@/views/Demo/information-example'),
        meta: {title: 'informationExample'}
      },
      {
        path: 'universal-module',
        name: 'universal-module',
        component: () => import('@/views/Demo/universal-module'),
        meta: {title: 'universalModule'}
      },
    ]
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
]


const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})


const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
