ALTER TABLE `lkt_product_list` DROP `scan`, DROP `keyword`;



DROP TABLE `lkt_group_buy`, `lkt_group_config`, `lkt_group_open`, `lkt_group_product`;

CREATE TABLE `lkt_group_config` (
  `id` int(11) unsigned NOT NULL COMMENT 'id',
  `refunmoney` smallint(6) NOT NULL COMMENT '退款方式: 1,自动 2,手动',
  `group_time` int(11) NOT NULL COMMENT '拼团时限',
  `open_num` int(11) NOT NULL COMMENT '开团数量',
  `can_num` int(11) NOT NULL COMMENT '参团数量',
  `can_again` tinyint(1) NOT NULL COMMENT '是否可重复参团1 是 0 否',
  `open_discount` tinyint(1) NOT NULL COMMENT '是否开启团长优惠 1 是 0 否',
  `rule` text NOT NULL COMMENT '规则',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='拼团参数配置表';

CREATE TABLE `lkt_group_open` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(40) NOT NULL DEFAULT '' COMMENT '用户id',
  `ptgoods_id` int(11) DEFAULT NULL COMMENT '拼团商品id',
  `ptcode` varchar(50) DEFAULT NULL COMMENT '拼团编号',
  `ptnumber` int(11) DEFAULT NULL COMMENT '拼团人数',
  `addtime` datetime DEFAULT NULL COMMENT '创建日期',
  `endtime` datetime DEFAULT NULL COMMENT '结束时间',
  `ptstatus` tinyint(1) DEFAULT '0' COMMENT '0:未付款 1:拼团中，2:拼团成功, 3：拼团失败, ',
  `group_id` char(10) NOT NULL COMMENT '所属拼团',
  `sNo` varchar(255) DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户拼团表';

CREATE TABLE `lkt_group_product` (
  `group_id` int(11) NOT NULL COMMENT '活动ID',
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_title` varchar(255) NOT NULL DEFAULT '' COMMENT '拼团活动标题',
  `attr_id` int(11) NOT NULL COMMENT '规格id',
  `product_id` int(11) unsigned DEFAULT NULL COMMENT '产品id',
  `group_level` varchar(200) NOT NULL DEFAULT '' COMMENT '拼团等级价格参数',
  `group_data` varchar(300) NOT NULL DEFAULT '' COMMENT '拼团参数数据',
  `g_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '活动状态: 1--未开始 2--活动中 3--已结束',
  `is_show` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `recycle` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0不回收，1.回收',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拼团产品';
  