-- 在商品属性表中增加总库存字段
ALTER TABLE `lkt_configure` ADD `total_num` INT(11) NOT NULL DEFAULT '0' COMMENT '总库存' AFTER `recycle`;

-- 创建库存记录表
CREATE TABLE `lkt_stock` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
  `attribute_id` int(11) NOT NULL DEFAULT '0' COMMENT '属性id',
  `flowing_num` int(11) NOT NULL DEFAULT '0' COMMENT '入库/出库',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型 0.入库 1.出库 2.预警',
  `add_date` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2979 DEFAULT CHARSET=utf8 COMMENT='库存记录表';

INSERT INTO `lkt_stock` (`id`, `product_id`, `attribute_id`, `flowing_num`, `type`, `add_date`) VALUES ('1', '1', '1', '64', '0', '2018-06-21 23:50:03');
-- 修改菜单列表，增加库存管理路径

UPDATE `lkt_core_menu` SET `title` = '库存管理', `module` = 'stock', `action` = 'index', `url` = 'index.php?module=stock&action=Index' WHERE `lkt_core_menu`.`id` = 28;