package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 推广设置
 *
 * @author Trick
 * @date 2020/12/25 11:04
 */
@Table(name = "lkt_extension")
public class ExtensionModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private String store_id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 排序号
     */
    private String sort;

    /**
     * x坐标
     */
    private String x;

    /**
     * y坐标
     */
    private String y;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 二维码宽度
     */
    private Integer kuan;

    /**
     * 海报类型 1.文章海报 2.红包海报 3.商品海报 4.分销海报 5.邀请海报 6.竞拍海报
     */
    private Integer type;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 是否默认
     */
    private Integer isdefault;

    /**
     * 背景图片
     */
    private String bg;

    /**
     * 颜色
     */
    private String color;

    /**
     * 等待语
     */
    private String waittext;

    /**
     * 排序的数据
     */
    private String data;

    /**
     * 来源 1.小程序 2.app
     */
    private Integer store_type;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public String getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(String store_id) {
        this.store_id = store_id == null ? null : store_id.trim();
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取图片路径
     *
     * @return image - 图片路径
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置图片路径
     *
     * @param image 图片路径
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    /**
     * 获取x坐标
     *
     * @return x - x坐标
     */
    public String getX() {
        return x;
    }

    /**
     * 设置x坐标
     *
     * @param x x坐标
     */
    public void setX(String x) {
        this.x = x == null ? null : x.trim();
    }

    /**
     * 获取y坐标
     *
     * @return y - y坐标
     */
    public String getY() {
        return y;
    }

    /**
     * 设置y坐标
     *
     * @param y y坐标
     */
    public void setY(String y) {
        this.y = y == null ? null : y.trim();
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
     * 获取二维码宽度
     *
     * @return kuan - 二维码宽度
     */
    public Integer getKuan() {
        return kuan;
    }

    /**
     * 设置二维码宽度
     *
     * @param kuan 二维码宽度
     */
    public void setKuan(Integer kuan) {
        this.kuan = kuan;
    }

    /**
     * 获取海报类型 1.文章海报 2.红包海报 3.商品海报 4.分销海报 5.邀请海报 6.竞拍海报
     *
     * @return type - 海报类型 1.文章海报 2.红包海报 3.商品海报 4.分销海报 5.邀请海报 6.竞拍海报
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置海报类型 1.文章海报 2.红包海报 3.商品海报 4.分销海报 5.邀请海报 6.竞拍海报
     *
     * @param type 海报类型 1.文章海报 2.红包海报 3.商品海报 4.分销海报 5.邀请海报 6.竞拍海报
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取链接地址
     *
     * @return url - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取是否默认
     *
     * @return isdefault - 是否默认
     */
    public Integer getIsdefault() {
        return isdefault;
    }

    /**
     * 设置是否默认
     *
     * @param isdefault 是否默认
     */
    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    /**
     * 获取背景图片
     *
     * @return bg - 背景图片
     */
    public String getBg() {
        return bg;
    }

    /**
     * 设置背景图片
     *
     * @param bg 背景图片
     */
    public void setBg(String bg) {
        this.bg = bg == null ? null : bg.trim();
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
     * 获取等待语
     *
     * @return waittext - 等待语
     */
    public String getWaittext() {
        return waittext;
    }

    /**
     * 设置等待语
     *
     * @param waittext 等待语
     */
    public void setWaittext(String waittext) {
        this.waittext = waittext == null ? null : waittext.trim();
    }

    /**
     * 获取排序的数据
     *
     * @return data - 排序的数据
     */
    public String getData() {
        return data;
    }

    /**
     * 设置排序的数据
     *
     * @param data 排序的数据
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    /**
     * 获取来源 1.小程序 2.app
     *
     * @return store_type - 来源 1.小程序 2.app
     */
    public Integer getStore_type() {
        return store_type;
    }

    /**
     * 设置来源 1.小程序 2.app
     *
     * @param store_type 来源 1.小程序 2.app
     */
    public void setStore_type(Integer store_type) {
        this.store_type = store_type;
    }
}