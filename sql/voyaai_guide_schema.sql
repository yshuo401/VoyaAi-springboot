-- VoyaAi 攻略模块增量脚本。
-- 适用于已经执行基础数据库脚本的环境；只创建缺失表，不删除现有数据。
USE `voya_ai`;

CREATE TABLE IF NOT EXISTS `voya_ai_guide` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `city_id` BIGINT NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `cover_image` VARCHAR(500) DEFAULT NULL,
  `summary` VARCHAR(500) DEFAULT NULL,
  `content` LONGTEXT,
  `guide_type` VARCHAR(50) DEFAULT NULL,
  `days` INT DEFAULT 1,
  `budget_min` DECIMAL(10,2) DEFAULT 0.00,
  `budget_max` DECIMAL(10,2) DEFAULT 0.00,
  `publish_status` CHAR(1) DEFAULT '0',
  `publish_time` DATETIME DEFAULT NULL,
  `view_count` BIGINT DEFAULT 0,
  `like_count` BIGINT DEFAULT 0,
  `favorite_count` BIGINT DEFAULT 0,
  `comment_count` BIGINT DEFAULT 0,
  `sort` INT DEFAULT 0,
  `create_by` VARCHAR(64) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_by` VARCHAR(64) DEFAULT NULL,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` CHAR(1) DEFAULT '0',
  `remark` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_guide_city_id` (`city_id`),
  KEY `idx_guide_publish_status` (`publish_status`),
  KEY `idx_guide_type` (`guide_type`),
  KEY `idx_guide_sort` (`sort`),
  KEY `idx_guide_create_time` (`create_time`),
  KEY `idx_guide_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='旅游攻略表';

CREATE TABLE IF NOT EXISTS `voya_ai_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `sort` INT DEFAULT 0,
  `status` CHAR(1) DEFAULT '0',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` CHAR(1) DEFAULT '0',
  `remark` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_name_type` (`name`,`type`),
  KEY `idx_tag_type` (`type`),
  KEY `idx_tag_status` (`status`),
  KEY `idx_tag_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='攻略标签表';

CREATE TABLE IF NOT EXISTS `voya_ai_guide_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `guide_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_guide_tag` (`guide_id`,`tag_id`),
  KEY `idx_guide_tag_guide_id` (`guide_id`),
  KEY `idx_guide_tag_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='攻略标签关联表';
