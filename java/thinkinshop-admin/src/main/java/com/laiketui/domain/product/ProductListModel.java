package com.laiketui.domain.product;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_product_list")
public class ProductListModel {
    /**
     * 商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 商品编号
     */
    private String product_number;

    /**
     * 商品名字
     */
    private String product_title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 商品标签
     */
    private String label;

    /**
     * 条形码
     */
    private String scan;

    /**
     * 产品类别
     */
    private String product_class;

    /**
     * 产品图片
     */
    private String imgurl;

    /**
     * 产品内容
     */
    private String content;

    /**
     * 前端店铺商品详情插件
     */
    @Column(name = "richList")
    private String richList;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 上架时间
     */
    private Date upper_shelf_time;

    /**
     * 销量
     */
    private Integer volume;

    /**
     * 初始值
     */
    private String initial;

    /**
     * 产品值属性 1：新品,2：热销，3：推荐
     */
    private String s_type;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 库存预警
     */
    private Integer min_inventory;

    /**
     * 状态 1:待上架 2:上架 3:下架
     */
    private String status;

    /**
     * 品牌ID
     */
    private Integer brand_id;

    /**
     * 是否为分销商品
     */
    private Integer is_distribution;

    /**
     * 是否默认比例
     */
    private Integer is_default_ratio;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 重量
     */
    private String weight;

    /**
     * 重量单位
     */
    private String weight_unit;

    /**
     * 分销等级id 购买就升级
     */
    private Integer distributor_id;

    /**
     * 运费
     */
    private String freight;

    /**
     * 是否开启会员
     */
    private Integer is_zhekou;

    /**
     * 单独分销
     */
    private String separate_distribution;

    /**
     * 回收站 0.显示 1.回收
     */
    private String recycle;

    /**
     * 供应商
     */
    private String gongyingshang;

    /**
     * 是否支持线下核销:0--不支持　1--支持
     */
    private String is_hexiao;

    /**
     * 核销地址
     */
    private String hxaddress;

    /**
     * 支持活动:1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍 5--会员特惠
     */
    private String active;

    /**
     * 商户ID
     */
    private Integer mch_id;

    /**
     * 审核状态：1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     */
    private String mch_status;

    /**
     * 拒绝原因
     */
    private String refuse_reasons;

    /**
     * 搜索次数
     */
    private Integer search_num;

    /**
     * 展示位置:1.
     * 首页 2.购物车
     */
    private String show_adr;

    /**
     * 发布人
     */
    private String publisher;

    /**
     * 是否自选 0.自选 1.不是自选
     */
    private String is_zixuan;

    /**
     * 商品ID字符串
     */
    private String commodity_str;

    /**
     * 来源 1.自选 2.上传
     */
    private String source;

    /**
     * 评论数
     */
    private Integer comment_num;

    /**
     * 封面图
     */
    private String cover_map;

    /**
     * 获取商品id
     *
     * @return id - 商品id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品id
     *
     * @param id 商品id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取商品编号
     *
     * @return product_number - 商品编号
     */
    public String getProduct_number() {
        return product_number;
    }

    /**
     * 设置商品编号
     *
     * @param product_number 商品编号
     */
    public void setProduct_number(String product_number) {
        this.product_number = product_number == null ? null : product_number.trim();
    }

    /**
     * 获取商品名字
     *
     * @return product_title - 商品名字
     */
    public String getProduct_title() {
        return product_title;
    }

    /**
     * 设置商品名字
     *
     * @param product_title 商品名字
     */
    public void setProduct_title(String product_title) {
        this.product_title = product_title == null ? null : product_title.trim();
    }

    /**
     * 获取副标题
     *
     * @return subtitle - 副标题
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * 设置副标题
     *
     * @param subtitle 副标题
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    /**
     * 获取商品标签
     *
     * @return label - 商品标签
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置商品标签
     *
     * @param label 商品标签
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 获取条形码
     *
     * @return scan - 条形码
     */
    public String getScan() {
        return scan;
    }

    /**
     * 设置条形码
     *
     * @param scan 条形码
     */
    public void setScan(String scan) {
        this.scan = scan == null ? null : scan.trim();
    }

    /**
     * 获取产品类别
     *
     * @return product_class - 产品类别
     */
    public String getProduct_class() {
        return product_class;
    }

    /**
     * 设置产品类别
     *
     * @param product_class 产品类别
     */
    public void setProduct_class(String product_class) {
        this.product_class = product_class == null ? null : product_class.trim();
    }

    /**
     * 获取产品图片
     *
     * @return imgurl - 产品图片
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置产品图片
     *
     * @param imgurl 产品图片
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * 获取产品内容
     *
     * @return content - 产品内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置产品内容
     *
     * @param content 产品内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取前端店铺商品详情插件
     *
     * @return richList - 前端店铺商品详情插件
     */
    public String getRichList() {
        return richList;
    }

    /**
     * 设置前端店铺商品详情插件
     *
     * @param richList 前端店铺商品详情插件
     */
    public void setRichList(String richList) {
        this.richList = richList == null ? null : richList.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取添加时间
     *
     * @return add_date - 添加时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置添加时间
     *
     * @param add_date 添加时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * 获取上架时间
     *
     * @return upper_shelf_time - 上架时间
     */
    public Date getUpper_shelf_time() {
        return upper_shelf_time;
    }

    /**
     * 设置上架时间
     *
     * @param upper_shelf_time 上架时间
     */
    public void setUpper_shelf_time(Date upper_shelf_time) {
        this.upper_shelf_time = upper_shelf_time;
    }

    /**
     * 获取销量
     *
     * @return volume - 销量
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * 设置销量
     *
     * @param volume 销量
     */
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    /**
     * 获取初始值
     *
     * @return initial - 初始值
     */
    public String getInitial() {
        return initial;
    }

    /**
     * 设置初始值
     *
     * @param initial 初始值
     */
    public void setInitial(String initial) {
        this.initial = initial == null ? null : initial.trim();
    }

    /**
     * 获取产品值属性 1：新品,2：热销，3：推荐
     *
     * @return s_type - 产品值属性 1：新品,2：热销，3：推荐
     */
    public String getS_type() {
        return s_type;
    }

    /**
     * 设置产品值属性 1：新品,2：热销，3：推荐
     *
     * @param s_type 产品值属性 1：新品,2：热销，3：推荐
     */
    public void setS_type(String s_type) {
        this.s_type = s_type == null ? null : s_type.trim();
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取库存预警
     *
     * @return min_inventory - 库存预警
     */
    public Integer getMin_inventory() {
        return min_inventory;
    }

    /**
     * 设置库存预警
     *
     * @param min_inventory 库存预警
     */
    public void setMin_inventory(Integer min_inventory) {
        this.min_inventory = min_inventory;
    }

    /**
     * 获取状态 1:待上架 2:上架 3:下架
     *
     * @return status - 状态 1:待上架 2:上架 3:下架
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1:待上架 2:上架 3:下架
     *
     * @param status 状态 1:待上架 2:上架 3:下架
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取品牌ID
     *
     * @return brand_id - 品牌ID
     */
    public Integer getBrand_id() {
        return brand_id;
    }

    /**
     * 设置品牌ID
     *
     * @param brand_id 品牌ID
     */
    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * 获取是否为分销商品
     *
     * @return is_distribution - 是否为分销商品
     */
    public Integer getIs_distribution() {
        return is_distribution;
    }

    /**
     * 设置是否为分销商品
     *
     * @param is_distribution 是否为分销商品
     */
    public void setIs_distribution(Integer is_distribution) {
        this.is_distribution = is_distribution;
    }

    /**
     * 获取是否默认比例
     *
     * @return is_default_ratio - 是否默认比例
     */
    public Integer getIs_default_ratio() {
        return is_default_ratio;
    }

    /**
     * 设置是否默认比例
     *
     * @param is_default_ratio 是否默认比例
     */
    public void setIs_default_ratio(Integer is_default_ratio) {
        this.is_default_ratio = is_default_ratio;
    }

    /**
     * 获取关键词
     *
     * @return keyword - 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置关键词
     *
     * @param keyword 关键词
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    /**
     * 获取重量
     *
     * @return weight - 重量
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    /**
     * 获取重量单位
     *
     * @return weight_unit - 重量单位
     */
    public String getWeight_unit() {
        return weight_unit;
    }

    /**
     * 设置重量单位
     *
     * @param weight_unit 重量单位
     */
    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit == null ? null : weight_unit.trim();
    }

    /**
     * 获取分销等级id 购买就升级
     *
     * @return distributor_id - 分销等级id 购买就升级
     */
    public Integer getDistributor_id() {
        return distributor_id;
    }

    /**
     * 设置分销等级id 购买就升级
     *
     * @param distributor_id 分销等级id 购买就升级
     */
    public void setDistributor_id(Integer distributor_id) {
        this.distributor_id = distributor_id;
    }

    /**
     * 获取运费
     *
     * @return freight - 运费
     */
    public String getFreight() {
        return freight;
    }

    /**
     * 设置运费
     *
     * @param freight 运费
     */
    public void setFreight(String freight) {
        this.freight = freight == null ? null : freight.trim();
    }

    /**
     * 获取是否开启会员
     *
     * @return is_zhekou - 是否开启会员
     */
    public Integer getIs_zhekou() {
        return is_zhekou;
    }

    /**
     * 设置是否开启会员
     *
     * @param is_zhekou 是否开启会员
     */
    public void setIs_zhekou(Integer is_zhekou) {
        this.is_zhekou = is_zhekou;
    }

    /**
     * 获取单独分销
     *
     * @return separate_distribution - 单独分销
     */
    public String getSeparate_distribution() {
        return separate_distribution;
    }

    /**
     * 设置单独分销
     *
     * @param separate_distribution 单独分销
     */
    public void setSeparate_distribution(String separate_distribution) {
        this.separate_distribution = separate_distribution == null ? null : separate_distribution.trim();
    }

    /**
     * 获取回收站 0.显示 1.回收
     *
     * @return recycle - 回收站 0.显示 1.回收
     */
    public String getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.显示 1.回收
     *
     * @param recycle 回收站 0.显示 1.回收
     */
    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取供应商
     *
     * @return gongyingshang - 供应商
     */
    public String getGongyingshang() {
        return gongyingshang;
    }

    /**
     * 设置供应商
     *
     * @param gongyingshang 供应商
     */
    public void setGongyingshang(String gongyingshang) {
        this.gongyingshang = gongyingshang == null ? null : gongyingshang.trim();
    }

    /**
     * 获取是否支持线下核销:0--不支持　1--支持
     *
     * @return is_hexiao - 是否支持线下核销:0--不支持　1--支持
     */
    public String getIs_hexiao() {
        return is_hexiao;
    }

    /**
     * 设置是否支持线下核销:0--不支持　1--支持
     *
     * @param is_hexiao 是否支持线下核销:0--不支持　1--支持
     */
    public void setIs_hexiao(String is_hexiao) {
        this.is_hexiao = is_hexiao;
    }

    /**
     * 获取核销地址
     *
     * @return hxaddress - 核销地址
     */
    public String getHxaddress() {
        return hxaddress;
    }

    /**
     * 设置核销地址
     *
     * @param hxaddress 核销地址
     */
    public void setHxaddress(String hxaddress) {
        this.hxaddress = hxaddress == null ? null : hxaddress.trim();
    }

    /**
     * 获取支持活动:1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍 5--会员特惠
     *
     * @return active - 支持活动:1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍 5--会员特惠
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置支持活动:1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍 5--会员特惠
     *
     * @param active 支持活动:1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍 5--会员特惠
     */
    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    /**
     * 获取商户ID
     *
     * @return mch_id - 商户ID
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置商户ID
     *
     * @param mch_id 商户ID
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取审核状态：1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     *
     * @return mch_status - 审核状态：1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     */
    public String getMch_status() {
        return mch_status;
    }

    /**
     * 设置审核状态：1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     *
     * @param mch_status 审核状态：1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     */
    public void setMch_status(String mch_status) {
        this.mch_status = mch_status;
    }

    /**
     * 获取拒绝原因
     *
     * @return refuse_reasons - 拒绝原因
     */
    public String getRefuse_reasons() {
        return refuse_reasons;
    }

    /**
     * 设置拒绝原因
     *
     * @param refuse_reasons 拒绝原因
     */
    public void setRefuse_reasons(String refuse_reasons) {
        this.refuse_reasons = refuse_reasons == null ? null : refuse_reasons.trim();
    }

    /**
     * 获取搜索次数
     *
     * @return search_num - 搜索次数
     */
    public Integer getSearch_num() {
        return search_num;
    }

    /**
     * 设置搜索次数
     *
     * @param search_num 搜索次数
     */
    public void setSearch_num(Integer search_num) {
        this.search_num = search_num;
    }

    /**
     * 获取展示位置:1.
     * 首页 2.购物车
     *
     * @return show_adr - 展示位置:1.
     * 首页 2.购物车
     */
    public String getShow_adr() {
        return show_adr;
    }

    /**
     * 设置展示位置:1.
     * 首页 2.购物车
     *
     * @param show_adr 展示位置:1.
     *                 首页 2.购物车
     */
    public void setShow_adr(String show_adr) {
        this.show_adr = show_adr == null ? null : show_adr.trim();
    }

    /**
     * 获取发布人
     *
     * @return publisher - 发布人
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置发布人
     *
     * @param publisher 发布人
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    /**
     * 获取是否自选 0.自选 1.不是自选
     *
     * @return is_zixuan - 是否自选 0.自选 1.不是自选
     */
    public String getIs_zixuan() {
        return is_zixuan;
    }

    /**
     * 设置是否自选 0.自选 1.不是自选
     *
     * @param is_zixuan 是否自选 0.自选 1.不是自选
     */
    public void setIs_zixuan(String is_zixuan) {
        this.is_zixuan = is_zixuan;
    }

    /**
     * 获取商品ID字符串
     *
     * @return commodity_str - 商品ID字符串
     */
    public String getCommodity_str() {
        return commodity_str;
    }

    /**
     * 设置商品ID字符串
     *
     * @param commodity_str 商品ID字符串
     */
    public void setCommodity_str(String commodity_str) {
        this.commodity_str = commodity_str == null ? null : commodity_str.trim();
    }

    /**
     * 获取来源 1.自选 2.上传
     *
     * @return source - 来源 1.自选 2.上传
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源 1.自选 2.上传
     *
     * @param source 来源 1.自选 2.上传
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取评论数
     *
     * @return comment_num - 评论数
     */
    public Integer getComment_num() {
        return comment_num;
    }

    /**
     * 设置评论数
     *
     * @param comment_num 评论数
     */
    public void setComment_num(Integer comment_num) {
        this.comment_num = comment_num;
    }

    public String getCover_map() {
        return cover_map;
    }

    public void setCover_map(String cover_map) {
        this.cover_map = cover_map;
    }
}