-- 意见反馈表。表已存在时不会覆盖现有结构，字段与项目总 DDL 保持一致。
CREATE TABLE IF NOT EXISTS voya_ai_feedback (
    id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id       BIGINT        DEFAULT NULL              COMMENT '用户ID，匿名反馈时为NULL',
    content       VARCHAR(1000) NOT NULL                  COMMENT '反馈内容',
    contact       VARCHAR(100)  DEFAULT NULL              COMMENT '联系方式',
    images        VARCHAR(2000) DEFAULT NULL              COMMENT '图片路径，逗号分隔',
    status        CHAR(1)       DEFAULT '0'               COMMENT '状态：0待处理 1已处理 2已忽略',
    handle_by     VARCHAR(64)   DEFAULT NULL              COMMENT '处理人',
    handle_time   DATETIME      DEFAULT NULL              COMMENT '处理时间',
    handle_remark VARCHAR(500)  DEFAULT NULL              COMMENT '处理备注',
    create_time   DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    del_flag      CHAR(1)       DEFAULT '0'               COMMENT '删除标志：0存在 1删除',
    PRIMARY KEY (id),
    KEY idx_feedback_user_id (user_id),
    KEY idx_feedback_status (status),
    KEY idx_feedback_create_time (create_time),
    KEY idx_feedback_del_flag (del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='意见反馈表';
