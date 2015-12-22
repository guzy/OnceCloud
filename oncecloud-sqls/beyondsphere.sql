/*
Navicat MySQL Data Transfer

Source Server         : MySQL-135.9
Source Server Version : 50173
Source Host           : 133.133.135.9:3306
Source Database       : beyondsphere

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-12-22 20:04:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alarm
-- ----------------------------
DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm` (
  `al_uuid` char(36) NOT NULL,
  `al_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `al_desc` varchar(80) DEFAULT NULL COMMENT '备注',
  `al_time` datetime DEFAULT NULL COMMENT '创建时间',
  `al_uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`al_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for al_log
-- ----------------------------
DROP TABLE IF EXISTS `al_log`;
CREATE TABLE `al_log` (
  `log_uuid` char(36) NOT NULL,
  `log_desc` varchar(80) DEFAULT NULL,
  `log_time` datetime DEFAULT NULL,
  `log_flag` int(11) DEFAULT NULL,
  `rule_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`log_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for al_rules
-- ----------------------------
DROP TABLE IF EXISTS `al_rules`;
CREATE TABLE `al_rules` (
  `rule_id` char(36) NOT NULL,
  `rule_name` int(1) DEFAULT NULL COMMENT '监控项',
  `rule_term` int(1) DEFAULT NULL COMMENT '条件',
  `rule_threshold` int(3) DEFAULT NULL COMMENT '阈值',
  `al_uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`rule_id`),
  KEY `al_uuid` (`al_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_detail
-- ----------------------------
DROP TABLE IF EXISTS `cost_detail`;
CREATE TABLE `cost_detail` (
  `cost_detail_uuid` char(36) NOT NULL COMMENT '成本详细信息id',
  `cost_type_uuid` char(36) NOT NULL COMMENT '本成类型id',
  `cost_type_detail_uuid` char(36) NOT NULL COMMENT '成本详细分类uuid',
  `cost_detail_name` varchar(80) NOT NULL COMMENT '成本详细信息名称',
  `cost_detail_type` varchar(80) DEFAULT NULL COMMENT '成本的分类，例如操作系统类型，带宽类型等',
  `cost_unit_price` double(10,0) NOT NULL COMMENT '成本单价',
  `cost_number` int(10) DEFAULT NULL COMMENT '成本的数量',
  `cost_price_type` int(1) NOT NULL COMMENT '成本单价类型：0代表按月，1代表按年',
  `cost_state` int(1) DEFAULT NULL COMMENT '运行状态：1代表正常，0代表异常',
  `cost_createtime` datetime DEFAULT NULL COMMENT '上架时间',
  `cost_deletetime` datetime DEFAULT NULL COMMENT '下架时间',
  PRIMARY KEY (`cost_detail_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_month
-- ----------------------------
DROP TABLE IF EXISTS `cost_month`;
CREATE TABLE `cost_month` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '总投入id',
  `total_cost` double DEFAULT NULL COMMENT '每个月的总投入成本',
  `create_time` date DEFAULT NULL COMMENT '计算时间',
  `cost_share` double DEFAULT NULL COMMENT '均摊的总的费用（每月总成本* (实际CPU使用率) * 1.5）',
  `cost_cpu` double DEFAULT NULL COMMENT 'coat_share*CPU比例 (3:5:2)',
  `cost_mem` double DEFAULT NULL COMMENT 'coat_share*内存比例 (3:5:2)',
  `cost_vol` double DEFAULT NULL COMMENT 'coat_share*硬盘比例 (3:5:2)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_month_detail
-- ----------------------------
DROP TABLE IF EXISTS `cost_month_detail`;
CREATE TABLE `cost_month_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_type_uuid` varchar(255) DEFAULT NULL,
  `cost_detail_uuid` varchar(255) DEFAULT NULL,
  `detail_cost` double DEFAULT NULL COMMENT '每个详细分类每个月的投入',
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_type
-- ----------------------------
DROP TABLE IF EXISTS `cost_type`;
CREATE TABLE `cost_type` (
  `cost_type_uuid` char(36) NOT NULL COMMENT '成本分类id',
  `cost_type_name` varchar(80) NOT NULL COMMENT '成本分类名称',
  `cost_type_des` varchar(80) DEFAULT NULL COMMENT '成本分类描述',
  PRIMARY KEY (`cost_type_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_type_detail
-- ----------------------------
DROP TABLE IF EXISTS `cost_type_detail`;
CREATE TABLE `cost_type_detail` (
  `cost_type_detail_uuid` char(36) NOT NULL COMMENT '成本详细类型id',
  `cost_type_uuid` char(36) NOT NULL COMMENT '成本类型id',
  `cost_type_detail_name` varchar(80) NOT NULL COMMENT '成本细分名称',
  `cost_type_detail_des` varchar(80) DEFAULT NULL COMMENT '成本细分描述',
  PRIMARY KEY (`cost_type_detail_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for host_information
-- ----------------------------
DROP TABLE IF EXISTS `host_information`;
CREATE TABLE `host_information` (
  `id` int(7) NOT NULL AUTO_INCREMENT,
  `user_id` int(7) DEFAULT NULL COMMENT '用户id',
  `net_id` int(7) DEFAULT NULL COMMENT 'valn的id',
  `ip_segment` varchar(30) DEFAULT NULL COMMENT '主机所在ip段',
  `router_ip` varchar(30) DEFAULT NULL COMMENT '创建路由器是绑定的ip',
  `router_netmask` varchar(30) DEFAULT NULL COMMENT '路由器设置的子网掩码',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=333348 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for host_sr
-- ----------------------------
DROP TABLE IF EXISTS `host_sr`;
CREATE TABLE `host_sr` (
  `bind_id` int(11) NOT NULL AUTO_INCREMENT,
  `host_uuid` char(36) NOT NULL DEFAULT '',
  `sr_uuid` char(36) NOT NULL DEFAULT '',
  PRIMARY KEY (`bind_id`)
) ENGINE=MyISAM AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_action
-- ----------------------------
DROP TABLE IF EXISTS `oc_action`;
CREATE TABLE `oc_action` (
  `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(36) DEFAULT NULL,
  `action_introduce` varchar(300) DEFAULT NULL COMMENT 'action说明',
  `action_relative_url` varchar(100) DEFAULT NULL COMMENT 'action的请求Url，是唯一的，也是Aop拦截的主要依据',
  `action_type` int(1) NOT NULL COMMENT '代表action的行为类型，0:页面，1：操作/按钮，2:子页面，3弹出层',
  `action_html_id` varchar(100) DEFAULT NULL COMMENT 'action对应的前台对象(如果存在的话），在html中的id',
  `action_html_name` varchar(100) DEFAULT NULL COMMENT 'action对应的前台对象(如果存在的话），在html中的name',
  `action_html_parentobj` varchar(100) DEFAULT NULL COMMENT 'action对应的前台对象(如果存在的话），在html中的父级对象id，可以控制显示隐藏',
  `action_remarks` varchar(300) DEFAULT NULL COMMENT '关于action的备注信息',
  `parent_id` int(11) DEFAULT '0',
  PRIMARY KEY (`action_id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_app
-- ----------------------------
DROP TABLE IF EXISTS `oc_app`;
CREATE TABLE `oc_app` (
  `app_uuid` char(36) NOT NULL,
  `app_uid` int(11) NOT NULL,
  `app_name` varchar(64) NOT NULL,
  `app_state` int(1) NOT NULL,
  `app_type` int(3) NOT NULL,
  `app_description` text,
  `app_date` datetime NOT NULL,
  `vm_uuid` char(36) NOT NULL,
  `app_port` int(11) NOT NULL,
  `app_monitor` text,
  PRIMARY KEY (`app_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_datacenter
-- ----------------------------
DROP TABLE IF EXISTS `oc_datacenter`;
CREATE TABLE `oc_datacenter` (
  `dc_uuid` char(36) NOT NULL,
  `dc_name` varchar(80) DEFAULT NULL,
  `dc_desc` varchar(256) DEFAULT NULL,
  `dc_location` varchar(80) DEFAULT NULL,
  `dc_status` int(1) DEFAULT NULL,
  `dc_createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`dc_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_dhcp
-- ----------------------------
DROP TABLE IF EXISTS `oc_dhcp`;
CREATE TABLE `oc_dhcp` (
  `dhcp_mac` varchar(17) NOT NULL COMMENT 'dhcp的对应的MAC地址',
  `dhcp_ip` varchar(15) DEFAULT NULL COMMENT 'dhcp分配的ip地址',
  `tenant_uuid` char(36) DEFAULT NULL COMMENT '租户的uuid（可以是路由器的uuid，或者其他）',
  `depen_type` int(1) DEFAULT NULL COMMENT '租用者角色（0代表虚拟机，1代表路由器）',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`dhcp_mac`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_exception
-- ----------------------------
DROP TABLE IF EXISTS `oc_exception`;
CREATE TABLE `oc_exception` (
  `exception_id` int(11) NOT NULL AUTO_INCREMENT,
  `exception_type` int(1) NOT NULL,
  `exception_uid` int(11) NOT NULL,
  `resource_uuid` char(36) NOT NULL,
  `resource_type` int(1) NOT NULL,
  `exception_information` text NOT NULL,
  `exception_date` datetime NOT NULL,
  PRIMARY KEY (`exception_id`)
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_group
-- ----------------------------
DROP TABLE IF EXISTS `oc_group`;
CREATE TABLE `oc_group` (
  `group_uuid` char(36) NOT NULL COMMENT '分组Id',
  `group_uid` int(11) NOT NULL COMMENT '分组建立者的id',
  `group_name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  `group_color` varchar(10) DEFAULT NULL COMMENT '分组颜色',
  `group_loc` varchar(255) DEFAULT NULL COMMENT '地域分组信息',
  `group_des` varchar(255) DEFAULT NULL COMMENT '分组描述信息',
  `create_time` datetime DEFAULT NULL COMMENT '分组创建时间',
  PRIMARY KEY (`group_uuid`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_ha
-- ----------------------------
DROP TABLE IF EXISTS `oc_ha`;
CREATE TABLE `oc_ha` (
  `pool_uuid` char(36) NOT NULL,
  `ha_path` varchar(255) DEFAULT NULL,
  `ha_start_flag` int(1) DEFAULT NULL,
  `host_monitor` int(1) DEFAULT NULL,
  `access_control_policy` int(1) DEFAULT '0',
  `left_host` int(10) DEFAULT '0',
  `slot_policy` int(1) DEFAULT '0' COMMENT '0',
  `slot_cpu` int(2) DEFAULT '0',
  `slot_memory` int(4) DEFAULT '0',
  `cpu_percent` int(2) DEFAULT '0',
  `memory_percent` int(2) DEFAULT '0',
  `migratory_host_uuid` varchar(640) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pool_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_host
-- ----------------------------
DROP TABLE IF EXISTS `oc_host`;
CREATE TABLE `oc_host` (
  `host_uuid` char(60) NOT NULL,
  `host_pwd` varchar(80) DEFAULT NULL,
  `host_name` varchar(80) DEFAULT NULL,
  `host_type` varchar(36) DEFAULT NULL,
  `host_desc` varchar(80) DEFAULT '',
  `host_ip` varchar(15) DEFAULT NULL,
  `host_mem` int(11) DEFAULT NULL,
  `host_cpu` int(4) DEFAULT NULL,
  `kernel_version` varchar(80) DEFAULT NULL,
  `xen_version` varchar(80) DEFAULT NULL,
  `host_status` int(1) DEFAULT NULL,
  `pool_uuid` char(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`host_uuid`),
  KEY `oc_host_ibfk_1` (`pool_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_image
-- ----------------------------
DROP TABLE IF EXISTS `oc_image`;
CREATE TABLE `oc_image` (
  `image_uuid` char(36) NOT NULL,
  `image_name` varchar(80) DEFAULT NULL,
  `image_pwd` varchar(80) DEFAULT '',
  `image_uid` int(11) DEFAULT NULL,
  `image_disk` int(4) DEFAULT NULL,
  `image_platform` int(1) DEFAULT NULL,
  `image_status` int(1) DEFAULT NULL,
  `pool_uuid` char(36) DEFAULT NULL,
  `image_desc` varchar(80) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `host_uuid` varchar(255) DEFAULT NULL,
  `reference_uuid` char(36) DEFAULT NULL,
  `pre_allocate` int(10) DEFAULT '-1',
  PRIMARY KEY (`image_uuid`),
  KEY `oc_image_ibfk_1` (`image_uid`),
  KEY `oc_image_ibfk_2` (`pool_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_iso
-- ----------------------------
DROP TABLE IF EXISTS `oc_iso`;
CREATE TABLE `oc_iso` (
  `iso_uuid` char(36) NOT NULL,
  `iso_name` varchar(200) DEFAULT NULL,
  `iso_filesrc` varchar(300) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `userid` int(10) unsigned DEFAULT NULL,
  `iso_remarks` varchar(500) DEFAULT NULL,
  `iso_status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`iso_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_log
-- ----------------------------
DROP TABLE IF EXISTS `oc_log`;
CREATE TABLE `oc_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_uid` int(11) NOT NULL,
  `log_object` int(3) NOT NULL,
  `log_action` int(3) NOT NULL,
  `log_status` int(3) NOT NULL,
  `log_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `log_elapse` int(3) DEFAULT NULL,
  `log_info` text,
  PRIMARY KEY (`log_id`),
  KEY `log_uid` (`log_uid`)
) ENGINE=MyISAM AUTO_INCREMENT=8513 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_log_backup
-- ----------------------------
DROP TABLE IF EXISTS `oc_log_backup`;
CREATE TABLE `oc_log_backup` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_uid` int(11) NOT NULL,
  `log_object` int(3) NOT NULL,
  `log_action` int(3) NOT NULL,
  `log_status` int(3) NOT NULL,
  `log_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `log_elapse` int(3) DEFAULT NULL,
  `log_info` text,
  PRIMARY KEY (`log_id`),
  KEY `log_uid` (`log_uid`)
) ENGINE=MyISAM AUTO_INCREMENT=7942 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_net
-- ----------------------------
DROP TABLE IF EXISTS `oc_net`;
CREATE TABLE `oc_net` (
  `net_id` int(11) NOT NULL AUTO_INCREMENT,
  `vlan_type` int(1) DEFAULT NULL,
  `vlan_id` int(7) DEFAULT NULL,
  `vlan_net` varchar(15) DEFAULT NULL COMMENT '私有网络的网络段',
  `vlan_gate` varchar(15) DEFAULT NULL COMMENT '私有网络的网关',
  `vlan_start` varchar(15) DEFAULT NULL COMMENT 'dhcp起始地址',
  `vlan_end` varchar(15) DEFAULT NULL COMMENT 'dhcp末地址',
  `dhcp_status` int(1) DEFAULT NULL COMMENT 'DHCP是否开启',
  `vlan_router` char(36) DEFAULT NULL COMMENT 'vlan对应的路由器UUid',
  `vlan_status` int(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `vlan_uid` int(7) DEFAULT NULL COMMENT 'userid',
  `vif_uuid` char(36) DEFAULT NULL COMMENT '网卡uuid',
  `vif_mac` char(36) CHARACTER SET utf8 COLLATE utf8_icelandic_ci DEFAULT NULL COMMENT '网卡mac地址',
  PRIMARY KEY (`net_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1111111136 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_pool
-- ----------------------------
DROP TABLE IF EXISTS `oc_pool`;
CREATE TABLE `oc_pool` (
  `pool_uuid` char(36) NOT NULL,
  `pool_name` varchar(80) DEFAULT NULL,
  `pool_type` varchar(36) DEFAULT NULL,
  `pool_desc` varchar(80) DEFAULT NULL,
  `pool_master` char(60) DEFAULT NULL,
  `pool_status` int(1) DEFAULT '0',
  `dc_uuid` char(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ha_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pool_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_rack
-- ----------------------------
DROP TABLE IF EXISTS `oc_rack`;
CREATE TABLE `oc_rack` (
  `rack_uuid` char(36) NOT NULL,
  `rack_name` varchar(80) DEFAULT NULL,
  `rack_desc` varchar(80) DEFAULT NULL,
  `rack_status` int(1) DEFAULT '0',
  `dc_uuid` char(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`rack_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_registry
-- ----------------------------
DROP TABLE IF EXISTS `oc_registry`;
CREATE TABLE `oc_registry` (
  `reg_uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `reg_name` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_ip` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_port` int(6) DEFAULT NULL,
  `reg_status` int(1) DEFAULT NULL,
  `reg_createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`reg_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for oc_repository
-- ----------------------------
DROP TABLE IF EXISTS `oc_repository`;
CREATE TABLE `oc_repository` (
  `repo_uuid` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `repo_name` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL,
  `repo_version` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL,
  `repo_address` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL,
  `repo_createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`repo_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for oc_router
-- ----------------------------
DROP TABLE IF EXISTS `oc_router`;
CREATE TABLE `oc_router` (
  `rt_uuid` char(36) NOT NULL COMMENT '路由器唯一标志',
  `rt_pwd` varchar(80) DEFAULT NULL COMMENT '路由器密码',
  `rt_uid` int(11) DEFAULT '0' COMMENT '路由器所属用户ID',
  `rt_name` varchar(80) DEFAULT NULL COMMENT '路由器名称',
  `rt_desc` varchar(80) DEFAULT NULL COMMENT '路由器描述信息',
  `rt_ip` char(15) DEFAULT NULL COMMENT '路由的ip地址',
  `rt_mac` char(17) DEFAULT NULL COMMENT '路由器MAC地址',
  `rt_power` int(1) DEFAULT NULL COMMENT '路由器电源状态',
  `rt_status` int(1) DEFAULT NULL COMMENT '路由器的运行状态',
  `host_uuid` char(36) DEFAULT NULL COMMENT '路由器的宿主机uuid',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `alarm_uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`rt_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `oc_snapshot`;
CREATE TABLE `oc_snapshot` (
  `snapshot_id` varchar(80) NOT NULL,
  `snapshot_name` varchar(80) DEFAULT NULL,
  `snapshot_size` int(11) DEFAULT NULL,
  `backup_date` datetime DEFAULT NULL,
  `snapshot_vm` char(36) DEFAULT NULL,
  `snapshot_volume` char(36) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`snapshot_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for oc_sr
-- ----------------------------
DROP TABLE IF EXISTS `oc_sr`;
CREATE TABLE `oc_sr` (
  `sr_uuid` char(36) NOT NULL,
  `sr_name` varchar(80) DEFAULT NULL,
  `sr_desc` varchar(80) DEFAULT NULL,
  `sr_address` varchar(20) DEFAULT NULL,
  `sr_dir` varchar(80) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `sr_status` int(1) unsigned DEFAULT '1',
  `sr_type` varchar(12) DEFAULT NULL COMMENT 'bfs,mfs,ocfs2',
  `disk_uuid` char(36) DEFAULT NULL,
  `iso_uuid` char(36) DEFAULT NULL,
  `ha_uuid` char(36) DEFAULT NULL,
  `rack_uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`sr_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_statistics
-- ----------------------------
DROP TABLE IF EXISTS `oc_statistics`;
CREATE TABLE `oc_statistics` (
  `st_uuid` char(90) NOT NULL,
  `st_name` varchar(255) DEFAULT NULL,
  `st_type` int(10) DEFAULT NULL,
  `st_create` datetime DEFAULT NULL,
  `st_delete` datetime DEFAULT NULL,
  `unit_price` double(11,2) DEFAULT NULL,
  `st_userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`st_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_unitprice
-- ----------------------------
DROP TABLE IF EXISTS `oc_unitprice`;
CREATE TABLE `oc_unitprice` (
  `price_type` varchar(11) NOT NULL COMMENT '价格类型',
  `price_value` double(10,2) NOT NULL COMMENT '单价：例如0.06元/1分钟，则记为0.06元'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_vdi
-- ----------------------------
DROP TABLE IF EXISTS `oc_vdi`;
CREATE TABLE `oc_vdi` (
  `vdi_uuid` char(36) NOT NULL,
  `tpl_uuid` char(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_vm
-- ----------------------------
DROP TABLE IF EXISTS `oc_vm`;
CREATE TABLE `oc_vm` (
  `vm_uuid` char(36) NOT NULL,
  `vm_pwd` varchar(80) DEFAULT NULL,
  `vm_group` char(36) DEFAULT NULL COMMENT '虚拟所在分组的id',
  `vm_uid` int(11) DEFAULT '0',
  `vm_name` varchar(80) DEFAULT NULL,
  `vm_desc` varchar(80) DEFAULT '',
  `vm_platform` int(1) DEFAULT NULL,
  `vm_vlan` varchar(36) DEFAULT '-1',
  `vm_mac` char(110) DEFAULT NULL,
  `vm_mem` int(11) DEFAULT NULL,
  `vm_cpu` int(3) DEFAULT NULL,
  `vm_disk` int(11) DEFAULT NULL,
  `vm_power` int(1) DEFAULT NULL,
  `vm_type` int(1) DEFAULT NULL,
  `vm_status` int(1) DEFAULT NULL,
  `host_uuid` char(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `backup_date` datetime DEFAULT NULL,
  `tpl_uuid` char(36) DEFAULT NULL,
  `alarm_uuid` char(36) DEFAULT NULL,
  `vm_importance` int(11) DEFAULT NULL,
  PRIMARY KEY (`vm_uuid`),
  KEY `vm_uid` (`vm_uid`),
  KEY `oc_vm_ibfk_2` (`host_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_vm_ip
-- ----------------------------
DROP TABLE IF EXISTS `oc_vm_ip`;
CREATE TABLE `oc_vm_ip` (
  `ip_id` int(11) NOT NULL AUTO_INCREMENT,
  `vm_mac` char(17) DEFAULT NULL COMMENT 'vm的mac地址',
  `vm_ip` char(15) DEFAULT NULL COMMENT 'vm的ip地址',
  `vm_vlan` int(36) DEFAULT '-1' COMMENT '虚拟机vlan的ID号',
  `vm_netmask` char(15) DEFAULT NULL,
  `vm_gateway` char(15) DEFAULT NULL,
  `vm_dns` char(15) DEFAULT NULL,
  PRIMARY KEY (`ip_id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oc_volume
-- ----------------------------
DROP TABLE IF EXISTS `oc_volume`;
CREATE TABLE `oc_volume` (
  `volume_uuid` char(36) NOT NULL,
  `volume_name` varchar(80) DEFAULT NULL,
  `volume_size` int(11) DEFAULT NULL,
  `volume_uid` int(11) DEFAULT NULL,
  `volume_dependency` char(36) DEFAULT NULL,
  `volume_description` varchar(80) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `backup_date` datetime DEFAULT NULL,
  `volume_status` int(1) DEFAULT NULL,
  PRIMARY KEY (`volume_uuid`),
  KEY `uid` (`volume_uid`),
  KEY `dependency` (`volume_dependency`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for power
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power` (
  `power_uuid` char(36) NOT NULL,
  `host_uuid` char(36) NOT NULL,
  `motherboard_ip` varchar(15) DEFAULT NULL,
  `power_port` int(10) unsigned DEFAULT NULL,
  `power_username` varchar(255) DEFAULT NULL,
  `power_password` varchar(255) DEFAULT NULL,
  `power_valid` int(1) DEFAULT '0' COMMENT '是否验证通过 0 不通过 1 通过',
  PRIMARY KEY (`power_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for profit_month
-- ----------------------------
DROP TABLE IF EXISTS `profit_month`;
CREATE TABLE `profit_month` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `profit_percent` double unsigned zerofill DEFAULT NULL COMMENT '用户资源使用率',
  `profit_total` double unsigned DEFAULT NULL COMMENT '用户每月需要支付的费用',
  `create_time` date DEFAULT NULL,
  `profit_cpu` double DEFAULT NULL COMMENT '当月分摊cpu总成本',
  `profit_mem` double DEFAULT NULL COMMENT '当月分摊内存成本',
  `profit_vol` double DEFAULT NULL COMMENT '当月分摊硬盘成本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for profit_month_detail
-- ----------------------------
DROP TABLE IF EXISTS `profit_month_detail`;
CREATE TABLE `profit_month_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `cost_detail_uuid` varchar(255) NOT NULL,
  `detail_profit` double unsigned DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=916 DEFAULT CHARSET=utf8;
