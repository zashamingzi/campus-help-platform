-- 创建数据库
CREATE DATABASE IF NOT EXISTS school_help
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

-- 切换到这个数据库
USE school_help;

DROP TABLE IF EXISTS `task_comment`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `user`;

-- 用户表
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `password` varchar(255) NOT NULL COMMENT '密码（BCrypt加密）',
  `nickname` varchar(50) DEFAULT '' COMMENT '昵称',
  `phone` varchar(20) DEFAULT '' COMMENT '手机号',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `student_id` varchar(20) DEFAULT '' COMMENT '学号',
  `avatar` varchar(512) DEFAULT '' COMMENT '头像URL',
  `gender` tinyint DEFAULT 2 COMMENT '性别：0=女 1=男 2=未知',
  `status` tinyint DEFAULT 0 COMMENT '状态：0=正常 1=封禁',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 任务表
CREATE TABLE `task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务主键ID',
  `title` varchar(255) NOT NULL COMMENT '任务标题',
  `description` text COMMENT '任务描述',
  `reward` decimal(10,2) NOT NULL COMMENT '赏金（元）',
  `deadline` datetime NOT NULL COMMENT '截止时间',
  `category` varchar(50) NOT NULL COMMENT '分类：跑腿/二手交易/学习互助/拼车',
  `pic_url` varchar(512) DEFAULT '' COMMENT '任务图片URL',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0=待接单 1=进行中 2=已完成',
  `publisher_id` bigint NOT NULL COMMENT '发布人ID（关联user.id）',
  `receiver_id` bigint DEFAULT NULL COMMENT '接单者ID（关联user.id）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_publisher` (`publisher_id`),
  KEY `idx_receiver` (`receiver_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 任务留言表
CREATE TABLE `task_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '留言主键ID',
  `task_id` bigint NOT NULL COMMENT '关联任务ID（关联task.id）',
  `user_id` bigint NOT NULL COMMENT '留言用户ID（关联user.id）',
  `content` text NOT NULL COMMENT '留言内容',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`),
  KEY `idx_task` (`task_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务留言表';