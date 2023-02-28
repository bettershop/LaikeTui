ALTER TABLE `lkt_plug_ins` CHANGE `code` `code` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '插件标志';
ALTER TABLE `lkt_cart` ADD `plugin` VARCHAR(255) NULL DEFAULT NULL COMMENT '插件标志' AFTER `Size_id`;
