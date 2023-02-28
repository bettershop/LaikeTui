package com.laiketui.common.mapper;

import com.laiketui.domain.product.ProductListModel;
import com.laiketui.root.exception.LaiKeApiException;

/**
 * 商品表 扩展sql
 *
 * @author Trick
 * @date 2020/11/20 15:04
 */
public interface ProductListModelExtendMapper {

    /**
     * 保存商品
     *
     * @param model -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/18 16:27
     */
    int saveGoods(ProductListModel model) throws LaiKeApiException;
}
