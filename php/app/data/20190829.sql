
-- 修改图片表seller_id设置可以为空
ALTER TABLE `lkt_product_img` CHANGE `seller_id` `seller_id` CHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户id';

-- 修改商品表product_number字段设置可以为空
ALTER TABLE `lkt_product_list` CHANGE `product_number` `product_number` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品编号';

-- 在商品表中添加分销参数设置字段
ALTER TABLE `lkt_product_list` ADD `leve` INT(2) NOT NULL DEFAULT '0' COMMENT '向上返几级' AFTER `initial`, ADD `leve1` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '一级佣金比例' AFTER `leve`, ADD `leve2` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '二级佣金比例' AFTER `leve1`, ADD `leve3` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '三级佣金比例' AFTER `leve2`, ADD `leve4` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '四级佣金比例' AFTER `leve3`, ADD `leve5` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '五级佣金比例' AFTER `leve4`, ADD `type` INT(2) NOT NULL DEFAULT '1' COMMENT '佣金发放类型，1 支付成功 2.确认收货' AFTER `leve5`, ADD `commissions` FLOAT(6) NOT NULL DEFAULT '0.00' COMMENT '分销佣金所需手续费' AFTER `type`;

-- 分销佣金明细表
CREATE TABLE `lkt_detailed_commission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userid` varchar(50) DEFAULT NULL,
  `sNo` varchar(255) DEFAULT NULL COMMENT '订单号',
  `money` float(10,2) DEFAULT '0.00' COMMENT '应发佣金',
  `s_money` float(10,2) DEFAULT '0.00' COMMENT '实发佣金',
  `status` int(2) DEFAULT '1' COMMENT '1.未发放，2.已发放',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `type` int(2) DEFAULT NULL COMMENT '类型',
  `Referee` varchar(50) DEFAULT NULL COMMENT '上级',
  `recycle` int(2) DEFAULT '0' COMMENT '0 不回收  1.回收',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='分销佣金明细表';