<template>
  <div class="container">
    <div class="header">
      <span>代客下单</span>
    </div>
    <div class="text item">
        <div class="flex">
            <div class="BuyUser">
                <span>*</span>
                <label>购买用户</label>
            </div>
            <div class="changeUser" style="max-height: 350px;">
                <el-button plain class="cancel" @click="AddUser">选择用户</el-button>
                <el-table :data="tableData" style="max-height: 350px;" v-if="tableData.length > 0" :header-cell-style="header">
                    <el-table-column prop="user_id" align="center" label="会员ID">
                    </el-table-column>
                    <el-table-column prop="zhanghao" align="center" label="会员账号">
                    </el-table-column>
                    <el-table-column prop="grade" align="center" label="会员等级">
                    </el-table-column>
                    <el-table-column prop="mobile" align="center" label="手机号码">
                    </el-table-column>
                    <el-table-column prop="money" align="center" label="余额">
                    </el-table-column>
                    <el-table-column prop="" align="center" label="收货地址">
                        <template slot-scope="scope">
                            <div class="addressInfo" v-if="scope.row.userAddress">
                                <div>
                                    <span>{{ scope.row.userAddress.name }}</span>
                                    <span style="padding-left:5px;">{{ scope.row.userAddress.tel }}</span>
                                </div>
                                <p>{{  scope.row.userAddress.sheng }}
                                    {{ scope.row.userAddress.city }}
                                    {{ scope.row.userAddress.quyu }}
                                    {{ scope.row.userAddress.address_xq }}
                                </p>
                            </div>
                            <a class="addA" @click="addAddress(scope.$index)" v-else>添加地址</a>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <div class="flex">
            <div class="BuyUser">
                <span>*</span>
                <label>购买商品</label>
            </div>
            <div class="changeUser" style="height: 330px;">
                <el-button plain @click="AddPro" class="cancel">添加商品</el-button>
                <el-table :data="ProData" v-if="ProData.length > 0" height="100%" :header-cell-style="header">
                    <el-table-column prop="goodsId" align="center" label="商品编号">
                    </el-table-column>
                    <el-table-column prop="proName" align="center" label="商品名称">
                        <template slot-scope="scope">
                            <div class="Info">
                                <img :src="scope.row.imgurl" width="40" height="40" alt="">
                                <span>{{ scope.row.product_title  }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="attribute" align="center" label="规格">
                    </el-table-column>
                    <el-table-column prop="price" align="center" label="价格">
                    </el-table-column>
                    <el-table-column prop="nums" align="center" label="购买数量">
                    </el-table-column>
                    <el-table-column prop="name" align="center" label="所属店铺">
                    </el-table-column>
                    <el-table-column prop="action" align="center" label="操作">
                        <template slot-scope="scope">
                            <el-button class="delete" @click="ChangeProdel(scope.$index)" plain icon="el-icon-delete">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <div class="BottomPrice">
            <div class="Price">
                <label for="">商品总价：</label>
                <span>¥{{ total_price || 0 }}</span>
            </div>
            <div class="Price">
                <label for="">会员折扣：</label>
                <span>-¥{{ discount_price ? discount_price.toFixed(2) : 0 }}</span>
            </div>
            <div class="Price">
                <label for="">立减：</label>
                <!-- <el-input v-model="wipe_off" @blur="wipeBlur" placeholder=""></el-input> -->
                <el-input v-model="knock" @blur="wipeBlur" placeholder=""></el-input>
            </div>
            <div class="Price Total">
                <label for="">合计支付：</label>
                <span>¥{{ combined || 0 }}</span>
            </div>
        </div>
	</div>

    <el-dialog title="选择用户" :visible.sync="dialogVisible" width="920px">
        <div class="">
            <div class="Search">
                <el-select class="Search-select" clearable v-model="gradeValue" placeholder="请选择会员级别">
                    <el-option v-for="(item,index) in gradeList" :key="index" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <el-input class="Search-input" v-model="search" placeholder="请输入会员ID/会员名称/手机号"></el-input>
                <el-button @click="Reset" plain>重置</el-button>
                <el-button @click="query2" type="primary">查询</el-button>
            </div>
            <div class="Table">
                <el-table height="350" :data="userdata"  style="width: 100%" @current-change="handleSelectionChange">
                    <el-table-column label="选择" align="center" width="55">
                        <template slot-scope="scope">
                            <el-radio :label="scope.row" v-model="tableRadio" ><i></i></el-radio>
                        </template>
                    </el-table-column>
                    <el-table-column prop="user_id" align="center" label="会员ID">
                    </el-table-column>
                    <el-table-column prop="user_name" align="center" label="会员名称">
                    </el-table-column>
                    <el-table-column prop="mobile" align="center" label="手机号码">
                    </el-table-column>
                    <el-table-column prop="grade" align="center" label="会员等级">
                    </el-table-column>
                </el-table>
                <div class="pageBox">
                    <div class="pageLeftText">显示</div>
                    <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
                        @size-change="handleSizeChange" :page-sizes="pagesizes" :pager-count="5"
                        :current-page="pagination.page" @current-change="handleCurrentChange"
                        :total="total">
                        <div class="pageRightText">当前显示{{showItem}}条，共 {{total}} 条记录</div>
                    </el-pagination>
                </div>
            </div>

        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirm">确 定</el-button>
        </span>
	</el-dialog>

    <el-dialog title="添加地址" :visible.sync="dialogVisible2" :before-close="handleClose" width="680px">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="收货人" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="tel">
                <el-input v-model="ruleForm.tel"></el-input>
            </el-form-item>
            <el-form-item class="cascadeAddress" label="联系地址" prop="xian">
                <div class="cascadeAddress-block">
                    <el-form-item prop="sheng">
                        <el-select class="select-input" v-model="ruleForm.sheng" placeholder="省">
                            <el-option v-for="(item,index) in shengList" :key="index" :label="item.g_CName" :value="item.g_CName">
                            <div @click="getShi(item.groupID)">{{ item.g_CName }}</div>
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="city">
                        <el-select class="select-input" v-model="ruleForm.city" placeholder="市">
                            <el-option v-for="(item,index) in shiList" :key="index" :label="item.g_CName" :value="item.g_CName">
                            <div @click="getXian(item.groupID)">{{ item.g_CName }}</div>
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="">
                        <el-select class="select-input" v-model="ruleForm.quyu" placeholder="县">
                            <el-option v-for="(item,index) in xianList" :key="index" :label="item.g_CName" :value="item.g_CName">
                            <div>{{ item.g_CName }}</div>
                            </el-option>
                        </el-select>
                    </el-form-item>
                </div>
              </el-form-item>
            <el-form-item label="详情地址" prop="address_xq">
                <el-input placeholder="请输入详情地址" v-model="ruleForm.address_xq"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取 消</el-button>
            <el-button type="primary" @click="Addconfirm">确 定</el-button>
        </span>
    </el-dialog>
				
    <el-dialog title="添加商品" :visible.sync="dialogVisible3" width="920px">
        <div class="">
            <el-radio-group v-model="tabKey">
                <el-radio-button label="0">选择商品</el-radio-button>
                <el-radio-button label="1">已选商品</el-radio-button>
            </el-radio-group>
            <div class="Search" style="margin-top: 20px;">
                <el-cascader
                    v-model="goodsClass"
                    class="select-input"
                    ref="myCascader"
                    placeholder="请选择商品分类"
                    :options="classList"
                    :props="{ checkStrictly: true }"
                    @change="changeProvinceCity"
                    clearable>
                </el-cascader>
                <el-select class="Search-select" v-model="brand" placeholder="请选择商品品牌">
                    <el-option v-for="item in brandList" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
                    </el-option>
                </el-select>
                <el-input class="Search-input" placeholder="请输入商品名称" v-model="Proname"></el-input>
                <el-button @click="Reset" plain>重置</el-button>
                <el-button @click="query" type="primary">查询</el-button>
            </div>
            <div class="Table" v-show="tabKey == '0'">
                <el-table :data="ProList" :row-key="rowKeys" style="width: 100%" @selection-change="handleSelectionChange2" height="350">
                    <el-table-column :reserve-selection="true" align="center" type="selection" width="55"></el-table-column>
                    <el-table-column prop="ProName" align="center" label="商品名称">
                        <template slot-scope="scope">
                            <div class="Info">
                                <img :src="scope.row.imgurl" width="40" height="40" alt="">
                                <span>{{scope.row.product_title}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="attribute" align="center" label="规格">
                    </el-table-column>
                    <el-table-column prop="price" align="center" label="价格">
                    </el-table-column>
                    <el-table-column prop="num" align="center" label="库存">
                    </el-table-column>
                    <el-table-column prop="name" align="center" label="所属店铺">
                    </el-table-column>
                </el-table>
                <div class="pageBox">
                    <div class="pageLeftText">显示</div>
                    <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
                        @size-change="handleSizeChange" :page-sizes="pagesizes" :pager-count="5"
                        :current-page="pagination.page" @current-change="handleCurrentChange"
                        :total="total2">
                        <!-- {{currpage}}-{{current_num}} -->
                        <div class="pageRightText">当前显示{{showItem}}条，共 {{total2}} 条记录</div>
                    </el-pagination>
                </div>
            </div>
            <div class="Table" v-show="tabKey == '1'">
                <el-table height="350" :data="ChangeProList">
                    <el-table-column prop="ProName" align="center" label="商品名称">
                        <template slot-scope="scope">
                            <div class="Info">
                                <img :src="scope.row.imgurl" width="40" height="40" alt="">
                                <span>{{scope.row.product_title}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="attribute" align="center" label="规格">
                    </el-table-column>
                    <el-table-column prop="price" align="center" label="价格">
                    </el-table-column>
                    <el-table-column prop="nums" align="center" width="200" label="购买数量">
                        <template slot-scope="scope">
                            <el-input-number :value="scope.row.nums" @change="handleChange($event,scope.$index)" :min="1" :max="Number(scope.row.num)" label="描述文字"></el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" align="center" label="所属店铺">
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible3 = false">取 消</el-button>
            <el-button type="primary" @click="AddProconfirm">确 定</el-button>
        </span>
    </el-dialog>

    <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
    </div>
  </div>
</template>

<script>
import valetOrder from '@/webManage/js/order/orderList/valetOrder'
export default valetOrder
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/orderList/valetOrder.less';
</style>