<?php

//PV结算奖金倍数
define('E_PV',1.25);

//复消奖额度
define('FX',360);
//复消结算比例
define("FXJJBL",0.03);

// 电子货币操作类型 1，报单。2，充值。3，转账。4，提现。5，零售,6 转换
define('E_MONEY_BAODAN',	1);
define('E_MONEY_CHONGZHI',	2);
define('E_MONEY_EXCHANGE',	3);
define('E_MONEY_CASH',		4);
define('E_MONEY_ORDER',		5);
define('E_MONEY_ZHUANHUAN',	6);

//订单类型 1.注册订单  2普通卡升级订单 3 vip升级订单 4零售订单 5重复消费订单
define('DDTYPE1',1);
define('DDTYPE2',2);
define('DDTYPE3',3);
define('DDTYPE4',4);
define('DDTYPE5',5);


//对碰两部门封顶 PV
define('PUKA_FENG',	400);
define('YINKA_FENG',1750);
define('JINKA_FENG',5250);
define('ZUANKA_FENG',14000);

//对碰百分比 PV
define('PUKA_PENGPENG',0.09);
define('YINKA_PENGPENG',0.11);
define('JINKA_PENGPENG',0.13);
define('ZUANKA_PENGPENG',0.16);


//单部门结余封顶 PV
define('PUKA_JIEYU',60000);
define('YINKA_JIEYU',90000);
define('JINKA_JIEYU',120000);
define('ZUANKA_JIEYU',150000);


//领导奖代数分红比
define('U_DAISHU_1',0.1);
define('U_DAISHU_2',0.1);
define('U_DAISHU_3',0.1);
define('U_DAISHU_4',0.05);
define('U_DAISHU_5',0.05);
define('U_DAISHU_6',0.03);
define('U_DAISHU_7',0.03);
define('U_DAISHU_8',0.03);
define('U_DAISHU_9',0.02);
define('U_DAISHU_10',0.02);
define('U_DAISHU_11',0.02);





?>
