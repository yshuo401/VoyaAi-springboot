-- 行程四张表。表已存在时不会覆盖现有结构，字段与项目总 DDL 保持一致。
CREATE TABLE IF NOT EXISTS voya_ai_trip (
    id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '行程ID',
    user_id       BIGINT        NOT NULL                  COMMENT '所属用户',
    city_id       BIGINT        NOT NULL                  COMMENT '目的地城市',
    title         VARCHAR(200)  NOT NULL                  COMMENT '行程标题',
    cover_image   VARCHAR(500)  DEFAULT NULL              COMMENT '封面',
    start_date    DATE          DEFAULT NULL              COMMENT '开始日期',
    end_date      DATE          DEFAULT NULL              COMMENT '结束日期',
    people_count  INT           DEFAULT 1                 COMMENT '人数',
    budget        DECIMAL(10,2) DEFAULT 0.00              COMMENT '预算',
    travel_type   VARCHAR(50)   DEFAULT NULL              COMMENT '出行类型',
    description   VARCHAR(1000) DEFAULT NULL              COMMENT '描述',
    source        VARCHAR(30)   DEFAULT 'USER'            COMMENT '来源：USER/AI/ADMIN',
    status        CHAR(1)       DEFAULT '0'               COMMENT '状态：0正常 1停用',
    create_time   DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    del_flag      CHAR(1)       DEFAULT '0'               COMMENT '删除标志：0存在 1删除',
    PRIMARY KEY (id),
    KEY idx_trip_user_id (user_id),
    KEY idx_trip_city_id (city_id),
    KEY idx_trip_start_date (start_date),
    KEY idx_trip_source (source),
    KEY idx_trip_status (status),
    KEY idx_trip_create_time (create_time),
    KEY idx_trip_del_flag (del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='行程表';

CREATE TABLE IF NOT EXISTS voya_ai_trip_day (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    trip_id     BIGINT        NOT NULL                  COMMENT '行程ID',
    day_number  INT           NOT NULL                  COMMENT '第几天',
    date        DATE          DEFAULT NULL              COMMENT '日期',
    title       VARCHAR(200)  DEFAULT NULL              COMMENT '标题',
    description VARCHAR(1000) DEFAULT NULL              COMMENT '描述',
    create_time DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    del_flag    CHAR(1)       DEFAULT '0'               COMMENT '删除标志：0存在 1删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_trip_day (trip_id, day_number),
    KEY idx_trip_day_trip_id (trip_id),
    KEY idx_trip_day_del_flag (del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='行程日表';

CREATE TABLE IF NOT EXISTS voya_ai_trip_item (
    id             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    trip_day_id    BIGINT        NOT NULL                  COMMENT '行程日ID',
    attraction_id  BIGINT        DEFAULT NULL              COMMENT '景点ID，可空',
    item_type      VARCHAR(30)   NOT NULL                  COMMENT '类型：1景点 2餐饮 3酒店 4交通 5购物 6其他',
    title          VARCHAR(200)  NOT NULL                  COMMENT '标题',
    start_time     VARCHAR(20)   DEFAULT NULL              COMMENT '开始时间',
    end_time       VARCHAR(20)   DEFAULT NULL              COMMENT '结束时间',
    address        VARCHAR(500)  DEFAULT NULL              COMMENT '地址',
    description    VARCHAR(1000) DEFAULT NULL              COMMENT '描述',
    estimated_cost DECIMAL(10,2) DEFAULT 0.00              COMMENT '预估费用',
    sort           INT           DEFAULT 0                 COMMENT '排序',
    create_time    DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    del_flag       CHAR(1)       DEFAULT '0'               COMMENT '删除标志：0存在 1删除',
    PRIMARY KEY (id),
    KEY idx_trip_item_day_id (trip_day_id),
    KEY idx_trip_item_attraction_id (attraction_id),
    KEY idx_trip_item_type (item_type),
    KEY idx_trip_item_sort (sort),
    KEY idx_trip_item_del_flag (del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='行程项表';

CREATE TABLE IF NOT EXISTS voya_ai_trip_share (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    trip_id     BIGINT       NOT NULL                  COMMENT '行程ID',
    user_id     BIGINT       NOT NULL                  COMMENT '分享人',
    share_code  VARCHAR(64)  NOT NULL                  COMMENT '分享码',
    share_token VARCHAR(128) NOT NULL                  COMMENT '访问令牌',
    expire_time DATETIME     DEFAULT NULL              COMMENT '过期时间',
    view_count  BIGINT       DEFAULT 0                 COMMENT '访问量',
    status      CHAR(1)      DEFAULT '0'               COMMENT '状态：0有效 1失效',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_trip_share_code (share_code),
    UNIQUE KEY uk_trip_share_token (share_token),
    KEY idx_trip_share_trip_id (trip_id),
    KEY idx_trip_share_user_id (user_id),
    KEY idx_trip_share_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='行程分享表';
