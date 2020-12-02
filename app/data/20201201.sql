CREATE TABLE `lkt_score_pro` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pro_id` int(11) NOT NULL COMMENT '商品ID',
  `score` float(4,2) DEFAULT NULL COMMENT '消耗积分',
  `money` float(4,2) DEFAULT '0.00' COMMENT '消耗现金',
  `is_show` varchar(11) NOT NULL DEFAULT '0' COMMENT '是否显示（0不显示，1热销单品，2.购物车，3.个人中心）',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '商品状态状态（0：待上架，1已上架，2下架）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分商品参数设置';

CREATE TABLE `lkt_score_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `score_id` int(11) NOT NULL COMMENT '积分商品ID',
  `uid` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '0：成功，1失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分商城消耗记录表';