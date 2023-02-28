package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_configure")
public class ConfiGureModel implements Serializable {
    /**
     * 配置id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

    /**
     * 尺码
     */
    private String size;

    /**
     * 成本价
     */
    private BigDecimal costprice;

    /**
     * 出售价格
     */
    private BigDecimal price;

    /**
     * 原价格
     */
    private BigDecimal yprice;

    /**
     * 图片
     */
    private String img;

    /**
     * 商品id
     */
    private Integer pid;

    /**
     * 总库存
     */
    private Integer total_num;

    /**
     * 剩余库存
     */
    private Integer num;

    /**
     * 单位
     */
    private String unit;

    /**
     * 条形码
     */
    private String bar_code;

    /**
     * 砍价开始价格
     */
    private BigDecimal bargain_price;

    /**
     * 状态 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架
     */
    private String status;

    /**
     * 属性
     */
    private String attribute;

    /**
     * 回收站 0.不回收 1.回收
     */
    private String recycle;

    /**
     * 产品预警值
     */
    private Integer min_inventory;

    /**
     * 修改时间
     */
    private Date ctime;

    /**
     * 属性ID字符串
     */
    private String attribute_str;

    /**
     * 获取配置id
     *
     * @return id - 配置id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置配置id
     *
     * @param id 配置id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取配置名称
     *
     * @return name - 配置名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配置名称
     *
     * @param name 配置名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取颜色
     *
     * @return color - 颜色
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置颜色
     *
     * @param color 颜色
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    /**
     * 获取尺码
     *
     * @return size - 尺码
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置尺码
     *
     * @param size 尺码
     */
    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    /**
     * 获取成本价
     *
     * @return costprice - 成本价
     */
    public BigDecimal getCostprice() {
        return costprice;
    }

    /**
     * 设置成本价
     *
     * @param costprice 成本价
     */
    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    /**
     * 获取出售价格
     *
     * @return price - 出售价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置出售价格
     *
     * @param price 出售价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取原价格
     *
     * @return yprice - 原价格
     */
    public BigDecimal getYprice() {
        return yprice;
    }

    /**
     * 设置原价格
     *
     * @param yprice 原价格
     */
    public void setYprice(BigDecimal yprice) {
        this.yprice = yprice;
    }

    /**
     * 获取图片
     *
     * @return img - 图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片
     *
     * @param img 图片
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    /**
     * 获取商品id
     *
     * @return pid - 商品id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置商品id
     *
     * @param pid 商品id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取总库存
     *
     * @return total_num - 总库存
     */
    public Integer getTotal_num() {
        return total_num;
    }

    /**
     * 设置总库存
     *
     * @param total_num 总库存
     */
    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    /**
     * 获取剩余库存
     *
     * @return num - 剩余库存
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置剩余库存
     *
     * @param num 剩余库存
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 获取条形码
     *
     * @return bar_code - 条形码
     */
    public String getBar_code() {
        return bar_code;
    }

    /**
     * 设置条形码
     *
     * @param bar_code 条形码
     */
    public void setBar_code(String bar_code) {
        this.bar_code = bar_code == null ? null : bar_code.trim();
    }

    /**
     * 获取砍价开始价格
     *
     * @return bargain_price - 砍价开始价格
     */
    public BigDecimal getBargain_price() {
        return bargain_price;
    }

    /**
     * 设置砍价开始价格
     *
     * @param bargain_price 砍价开始价格
     */
    public void setBargain_price(BigDecimal bargain_price) {
        this.bargain_price = bargain_price;
    }

    /**
     * 获取状态 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架
     *
     * @return status - 状态 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架
     *
     * @param status 状态 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取属性
     *
     * @return attribute - 属性
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 设置属性
     *
     * @param attribute 属性
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    /**
     * 获取回收站 0.不回收 1.回收
     *
     * @return recycle - 回收站 0.不回收 1.回收
     */
    public String getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.不回收 1.回收
     *
     * @param recycle 回收站 0.不回收 1.回收
     */
    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取产品预警值
     *
     * @return min_inventory - 产品预警值
     */
    public Integer getMin_inventory() {
        return min_inventory;
    }

    /**
     * 设置产品预警值
     *
     * @param min_inventory 产品预警值
     */
    public void setMin_inventory(Integer min_inventory) {
        this.min_inventory = min_inventory;
    }

    /**
     * 获取修改时间
     *
     * @return ctime - 修改时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置修改时间
     *
     * @param ctime 修改时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取属性ID字符串
     *
     * @return attribute_str - 属性ID字符串
     */
    public String getAttribute_str() {
        return attribute_str;
    }

    /**
     * 设置属性ID字符串
     *
     * @param attribute_str 属性ID字符串
     * @description: 该字段用于区分 自己添加的商品和复制的商品(自选);字段里存的都是复制商品的id
     */
    public void setAttribute_str(String attribute_str) {
        this.attribute_str = attribute_str == null ? null : attribute_str.trim();
    }
}