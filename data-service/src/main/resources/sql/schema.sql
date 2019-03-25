/*
Navicat MySQL Data Transfer

Source Server         : 供应链项目数据库(dev)
Source Server Version : 50628
Source Host           : sh-cdb-fhama989.sql.tencentcdb.com:63156
Source Database       : scf

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2019-03-25 12:01:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `m_code`
-- ----------------------------
DROP TABLE IF EXISTS `m_code`;
CREATE TABLE `m_code` (
  `class_cd` smallint(6) NOT NULL COMMENT '分类编号',
  `class_name` varchar(40) DEFAULT NULL COMMENT '分类名称',
  `item_cd` smallint(6) NOT NULL COMMENT '选项编号',
  `item_value` smallint(6) NOT NULL COMMENT '选项值',
  `item_content` varchar(500) DEFAULT NULL COMMENT '表示内容',
  `item_info` varchar(40) DEFAULT NULL COMMENT '备用信息',
  `prep_num1` decimal(10,2) DEFAULT NULL COMMENT '预备数值1',
  `prep_num2` decimal(10,2) DEFAULT NULL COMMENT '预备数值2',
  `prep_char1` varchar(40) DEFAULT NULL COMMENT '预备字符1',
  `prep_char2` varchar(40) DEFAULT NULL COMMENT '预备字符2',
  `create_date_time` timestamp NULL DEFAULT NULL COMMENT '新增日期时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '新增人',
  `last_update_time` timestamp NULL DEFAULT NULL COMMENT '更新日期时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`class_cd`,`item_cd`)
)
