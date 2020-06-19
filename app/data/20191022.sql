ALTER TABLE `lkt_plug_ins` ADD `code` VARCHAR(11) NULL COMMENT '插件标志' AFTER `Customer_id`;

UPDATE `lkt_plug_ins` SET `code` = 'YHQ' WHERE `lkt_plug_ins`.`id` = 1;

UPDATE `lkt_plug_ins` SET `code` = 'QB' WHERE `lkt_plug_ins`.`id` = 2;

UPDATE `lkt_plug_ins` SET `code` = 'QD' WHERE `lkt_plug_ins`.`id` = 3;

UPDATE `lkt_plug_ins` SET `code` = 'PT' WHERE `lkt_plug_ins`.`id` = 5;

UPDATE `lkt_plug_ins` SET `code` = 'FX' WHERE `lkt_plug_ins`.`id` = 6;


