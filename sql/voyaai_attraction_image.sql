-- 增量脚本：在已有业务数据库执行，不重建景点表，不删除已有数据。
CREATE TABLE IF NOT EXISTS voya_ai_attraction_image (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  attraction_id BIGINT NOT NULL COMMENT '景点ID',
  image_url VARCHAR(500) NOT NULL COMMENT '图片访问路径',
  sort INT NOT NULL DEFAULT 0 COMMENT '展示顺序，越小越靠前',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_attraction_image_sort (attraction_id, sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='景点图片';
