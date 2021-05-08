ALTER TABLE `lkt_product_list` CHANGE `product_title` `product_title` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '产品名字';
ALTER TABLE `lkt_order_details` CHANGE `p_name` `p_name` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称';
