CREATE TABLE user_db (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      user_name VARCHAR(255) NOT NULL,
                      nick_name VARCHAR(255) NOT NULL,
                      workspace_id VARCHAR(255) NOT NULL,
                      gmt_creat DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      gmt_update DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE id_management_db (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  head VARCHAR(255) NOT NULL,
                                  value BIGINT NOT NULL,
                                  gmt_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  gmt_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE deepseek_db (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',  -- 自增主键
                             workspace_id VARCHAR(255) NOT NULL COMMENT '工作区ID',  -- 工作区ID，字符串类型
                             message TEXT NOT NULL COMMENT '消息内容',               -- 消息内容，文本类型
                             gmt_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  -- 创建时间，默认当前时间
                             gmt_update DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'  -- 更新时间，默认当前时间，更新时自动刷新
);

CREATE TABLE user_chat_db (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                              user_id VARCHAR(255) NOT NULL COMMENT '用户id',
                              friend_id VARCHAR(255) NOT NULL COMMENT '好友id',
                              unread BIGINT DEFAULT 0 COMMENT '未读消息数',
                              status BIGINT DEFAULT 0 COMMENT '是否删除（0: 未删除, 1: 已删除）',
                              workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间',
                              UNIQUE KEY unique_user_friend_workspace (user_id, friend_id, workspace_id) COMMENT '唯一约束，防止重复记录'
) COMMENT='用户好友列表表';

CREATE TABLE chat_detail_db (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                                user_id VARCHAR(255) NOT NULL COMMENT '用户id',
                                friend_id VARCHAR(255) NOT NULL COMMENT '好友id',
                                content_user VARCHAR(255) NOT NULL COMMENT '发该消息的人',
                                content TEXT NOT NULL COMMENT '聊天内容',
                                time VARCHAR(255) NOT NULL COMMENT '发送时间',
                                type VARCHAR(255) NOT NULL COMMENT '消息类型',
                                workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间'
) COMMENT='用户聊天详情表';