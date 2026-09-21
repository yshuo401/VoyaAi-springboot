-- 评论表。表已存在时不会覆盖现有结构，字段与项目总 DDL 保持一致。
CREATE TABLE IF NOT EXISTS voya_ai_comment (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    user_id     BIGINT        NOT NULL                COMMENT '用户ID',
    target_type VARCHAR(30)   NOT NULL                COMMENT '目标类型：attraction/guide',
    target_id   BIGINT        NOT NULL                COMMENT '目标ID',
    parent_id   BIGINT        DEFAULT 0               COMMENT '父评论ID，0表示一级评论',
    content     VARCHAR(1000) NOT NULL                COMMENT '评论内容',
    like_count  BIGINT        DEFAULT 0               COMMENT '点赞量',
    status      CHAR(1)       DEFAULT '0'             COMMENT '状态：0正常 1隐藏',
    create_time DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    del_flag    CHAR(1)       DEFAULT '0'             COMMENT '删除标志：0存在 1删除',
    PRIMARY KEY (id),
    KEY idx_comment_user_id (user_id),
    KEY idx_comment_target (target_type, target_id),
    KEY idx_comment_parent_id (parent_id),
    KEY idx_comment_status (status),
    KEY idx_comment_create_time (create_time),
    KEY idx_comment_del_flag (del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';
