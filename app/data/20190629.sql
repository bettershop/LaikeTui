-- 在商品列表（lkt_product_list）中添加初始值（initial）字段，用于存储初始的商品属性


ALTER TABLE `lkt_product_list` ADD `initial` VARCHAR(225) NOT NULL COMMENT '初始值' AFTER `recycle`;





