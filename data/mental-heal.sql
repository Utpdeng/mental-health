/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.26 : Database - mental-heal
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mental-heal` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mental-heal`;

/*Table structure for table `appointments` */

DROP TABLE IF EXISTS `appointments`;

CREATE TABLE `appointments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '预约ID',
  `user_id` bigint NOT NULL COMMENT '预约用户ID',
  `doctor_id` bigint NOT NULL COMMENT '预约心理医生ID',
  `schedule_time` datetime NOT NULL COMMENT '预约开始时间',
  `status` enum('pending','confirmed','completed','cancelled') DEFAULT 'pending' COMMENT '预约状态',
  `payment_status` enum('unpaid','paid','refunded') DEFAULT 'unpaid' COMMENT '支付状态',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `form` enum('graphic','video','other') DEFAULT 'graphic' COMMENT '咨询形式',
  `content` varchar(255) DEFAULT NULL COMMENT '主要内容',
  `time` varchar(20) DEFAULT NULL COMMENT '预约时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `appointments` */

insert  into `appointments`(`id`,`user_id`,`doctor_id`,`schedule_time`,`status`,`payment_status`,`created_at`,`updated_at`,`form`,`content`,`time`) values (1,3,2,'2025-01-18 21:57:10','confirmed','unpaid','2025-01-18 21:57:40','2025-01-27 11:26:04','graphic','工作压力大，出现焦虑症状，影响睡眠','60'),(2,3,2,'2025-01-19 21:57:10','confirmed','unpaid','2025-01-18 22:54:49','2025-01-31 18:30:26','video','最近感到焦虑，睡眠质量差','60');

/*Table structure for table `assessment_results` */

DROP TABLE IF EXISTS `assessment_results`;

CREATE TABLE `assessment_results` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '测评结果ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `assessment_id` bigint NOT NULL COMMENT '测评ID',
  `results` json DEFAULT NULL COMMENT '测评结果（JSON格式）',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `assessment_results` */

/*Table structure for table `assessments` */

DROP TABLE IF EXISTS `assessments`;

CREATE TABLE `assessments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '测评ID',
  `title` varchar(200) NOT NULL COMMENT '测评标题',
  `description` text COMMENT '测评说明',
  `questions` json DEFAULT NULL COMMENT '测评问题（JSON格式）',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `assessments` */

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` bigint NOT NULL COMMENT '关联帖子ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `content` text NOT NULL COMMENT '评论内容',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comments` */

/*Table structure for table `consultations` */

DROP TABLE IF EXISTS `consultations`;

CREATE TABLE `consultations` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '咨询记录ID',
  `appointment_id` bigint NOT NULL COMMENT '关联预约ID',
  `doctor_id` bigint NOT NULL COMMENT '心理医生ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `chat_log` text COMMENT '聊天记录或视频记录',
  `feedback` text COMMENT '用户反馈（评分、评价等）',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '咨询开始时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `consultations` */

insert  into `consultations`(`id`,`appointment_id`,`doctor_id`,`user_id`,`chat_log`,`feedback`,`created_at`,`updated_at`) values (1,1,2,3,NULL,'猜的真准','2025-01-18 21:58:54','2025-01-19 17:46:37'),(2,2,2,3,NULL,'猜的真准啊','2025-01-19 17:26:17','2025-01-27 17:44:45');

/*Table structure for table `contents` */

DROP TABLE IF EXISTS `contents`;

CREATE TABLE `contents` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `type` enum('article','video','audio','test') NOT NULL COMMENT '内容类型',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `author_id` bigint DEFAULT NULL COMMENT '作者ID（用户或心理医生）',
  `content` text COMMENT '内容详情或路径（如视频URL）',
  `metadata` json DEFAULT NULL COMMENT '附加元数据（如标签）',
  `status` enum('pending','approved','rejected') DEFAULT 'pending' COMMENT '内容状态',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `contents` */

/*Table structure for table `posts` */

DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '发帖用户ID',
  `title` varchar(200) DEFAULT NULL COMMENT '帖子标题',
  `content` text NOT NULL COMMENT '帖子内容',
  `category` varchar(50) DEFAULT NULL COMMENT '帖子分类',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `posts` */

/*Table structure for table `psychologists` */

DROP TABLE IF EXISTS `psychologists`;

CREATE TABLE `psychologists` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `specialty` varchar(100) DEFAULT NULL COMMENT '专长领域',
  `introduction` text COMMENT '个人简介',
  `qualification` text COMMENT '资质证书',
  `work_years` int DEFAULT NULL COMMENT '从业年限',
  `consultation_fee` decimal(10,2) DEFAULT NULL COMMENT '咨询费用/小时',
  `employment_status` enum('active','leave','resigned') NOT NULL DEFAULT 'active' COMMENT 'active:在职, leave:休假, resigned:离职',
  `employment_date` date DEFAULT NULL COMMENT '入职日期',
  `resignation_date` date DEFAULT NULL COMMENT '离职日期',
  `leave_start_date` date DEFAULT NULL COMMENT '休假开始日期',
  `leave_end_date` date DEFAULT NULL COMMENT '休假结束日期',
  `leave_reason` varchar(255) DEFAULT NULL COMMENT '休假原因',
  `max_appointments_per_day` int DEFAULT '8' COMMENT '每日最大预约数',
  `deleted_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_employment_status` (`employment_status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `psychologists` */

insert  into `psychologists`(`id`,`user_id`,`title`,`specialty`,`introduction`,`qualification`,`work_years`,`consultation_fee`,`employment_status`,`employment_date`,`resignation_date`,`leave_start_date`,`leave_end_date`,`leave_reason`,`max_appointments_per_day`,`deleted_at`,`created_at`,`updated_at`) values (1,2,'专家','猜','Experienced clinical psychologist with over 10 years of experience.','PhD in Psychology',10,'150.00','active','2012-01-01','2025-01-14',NULL,NULL,NULL,5,NULL,'2025-01-14 21:46:08','2025-01-26 15:45:56');

/*Table structure for table `system_logs` */

DROP TABLE IF EXISTS `system_logs`;

CREATE TABLE `system_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint DEFAULT NULL COMMENT '关联用户ID（如果适用）',
  `action` varchar(100) NOT NULL COMMENT '操作类型',
  `details` json DEFAULT NULL COMMENT '操作详情',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `system_logs` */

/*Table structure for table `user_profiles` */

DROP TABLE IF EXISTS `user_profiles`;

CREATE TABLE `user_profiles` (
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `bio` text COMMENT '用户简介',
  `gender` enum('male','female','other') DEFAULT NULL COMMENT '性别',
  `birthdate` date DEFAULT NULL COMMENT '出生日期',
  `location` varchar(100) DEFAULT NULL COMMENT '所在城市',
  `preferences` varchar(255) DEFAULT NULL COMMENT '用户偏好设置',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_profiles` */

insert  into `user_profiles`(`user_id`,`nickname`,`avatar_url`,`bio`,`gender`,`birthdate`,`location`,`preferences`,`created_at`,`updated_at`) values (1,'管理员','https://www.xiguama.com/content/templates/default/static/images/randem/pic1.jpg',NULL,'male','2025-01-14','china','和平精英','2025-01-14 21:43:56','2025-02-03 14:07:03'),(2,'张三','https://www.xiguama.com/content/templates/default/static/images/randem/pic3.jpg',NULL,'male','2025-01-16','china','王者荣耀','2025-01-16 22:49:36','2025-02-03 14:06:46'),(3,'李四','https://www.xiguama.com/content/templates/default/static/images/randem/pic2.jpg',NULL,'male','2025-01-16','china','英雄联盟','2025-01-16 22:55:42','2025-01-27 11:25:28');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户唯一ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码（加密存储）',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '用户手机号',
  `role` enum('user','doctor','admin') NOT NULL COMMENT '用户角色',
  `status` enum('active','inactive','banned') DEFAULT 'active' COMMENT '用户状态',
  `created_at` datetime DEFAULT NULL COMMENT '注册时间',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx_username` (`username`),
  UNIQUE KEY `unique_idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`email`,`phone`,`role`,`status`,`created_at`,`updated_at`) values (1,'admin','123456','admin@123.com','13888888888','admin','active','2025-01-14 21:41:09','2025-01-14 21:41:11'),(2,'zs','123456','zs@123.com','13888888888','doctor','active','2025-01-14 21:42:01','2025-01-14 21:42:04'),(3,'ls','123456','ls@123.com','13888888888','user','active','2025-01-14 21:42:35','2025-01-14 21:42:37'),(4,'john_doe','secure_password','john@example.com','13888888888','user','active','2025-01-23 19:57:20','2025-01-23 20:00:30'),(5,'marry','123456','maryy@123.com','13888888888','user','active','2025-01-23 20:01:53','2025-01-31 21:52:20');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
