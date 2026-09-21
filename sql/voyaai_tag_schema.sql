-- 标签表补充审计字段，与项目其他业务表保持一致。脚本可重复执行。
SET @db = DATABASE();
SET @has_create_by = (SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'voya_ai_tag' AND COLUMN_NAME = 'create_by');
SET @sql = IF(@has_create_by = 0,
    'ALTER TABLE voya_ai_tag ADD COLUMN `create_by` VARCHAR(64) DEFAULT NULL COMMENT ''创建人'' AFTER `remark`',
    'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_update_by = (SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'voya_ai_tag' AND COLUMN_NAME = 'update_by');
SET @sql = IF(@has_update_by = 0,
    'ALTER TABLE voya_ai_tag ADD COLUMN `update_by` VARCHAR(64) DEFAULT NULL COMMENT ''修改人'' AFTER `create_by`',
    'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
