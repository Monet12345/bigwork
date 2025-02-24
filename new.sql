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
                                  gmt_creat DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  gmt_update DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
                              gmt_update DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  -- 更新时间，默认当前时间，更新时自动刷新
                              UNIQUE KEY unique_user_friend_workspace (user_id, friend_id, workspace_id) COMMENT '唯一约束，防止重复记录'
) COMMENT='用户好友列表表';

CREATE TABLE chat_detail_db (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                                user_id VARCHAR(255) NOT NULL COMMENT '用户id',
                                friend_id VARCHAR(255) NOT NULL COMMENT '好友id',
                                content_user VARCHAR(255) NOT NULL COMMENT '发该消息的人',
                                content TEXT NOT NULL COMMENT '聊天内容',
                                gmt_update  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
                                type VARCHAR(255) NOT NULL COMMENT '消息类型',
                                workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间'
) COMMENT='用户聊天详情表';

CREATE TABLE resource_data_read_db (
                                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，唯一标识每条记录',
                                       resource_data_id VARCHAR(255) NOT NULL COMMENT '资源数据ID，关联其他表的唯一标识',
                                       name VARCHAR(255) NOT NULL COMMENT '资源名称或表名',
                                       data TEXT NOT NULL COMMENT '资源数据内容，存储具体的数据信息',
                                       creat_user_id VARCHAR(255) NOT NULL COMMENT '创建人ID，标识创建该记录的用户',
                                       update_user_id VARCHAR(255) NOT NULL COMMENT '最新修改人ID，标识最后修改该记录的用户',
                                       gmt_creat DATETIME NOT NULL COMMENT '记录创建时间',
                                       gmt_update DATETIME NOT NULL COMMENT '记录最后更新时间',
                                       workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间ID，标识该记录所属的工作空间',
                                       iteration VARCHAR(255) NOT NULL COMMENT '版本号，标识当前记录的版本',
                                       exist INT NOT NULL DEFAULT 1 COMMENT '记录是否存在，1表示存在，0表示已删除',
                                       INDEX idx_resource_data_id (resource_data_id) COMMENT '资源数据ID索引，用于加速查询',
                                       INDEX idx_workspace_id (workspace_id) COMMENT '工作空间ID索引，用于加速查询'
) COMMENT='资源数据读取表，存储资源的基本信息和数据内容';

CREATE TABLE resource_data_write_db (
                                        id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，唯一标识每条记录',
                                        resource_data_id VARCHAR(255) NOT NULL COMMENT '资源数据ID，关联其他表的唯一标识',
                                        name VARCHAR(255) NOT NULL COMMENT '资源名称或表名',
                                        data TEXT NOT NULL COMMENT '资源数据内容，存储具体的数据信息',
                                        creat_user_id VARCHAR(255) NOT NULL COMMENT '创建人ID，标识创建该记录的用户',
                                        update_user_id VARCHAR(255) NOT NULL COMMENT '最新修改人ID，标识最后修改该记录的用户',
                                        gmt_creat DATETIME NOT NULL COMMENT '记录创建时间',
                                        gmt_update DATETIME NOT NULL COMMENT '记录最后更新时间',
                                        workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间ID，标识该记录所属的工作空间',
                                        iteration VARCHAR(255) NOT NULL COMMENT '版本号，标识当前记录的版本',
                                        exist INT NOT NULL DEFAULT 1 COMMENT '记录是否存在，1表示存在，0表示已删除',
                                        INDEX idx_resource_data_id (resource_data_id) COMMENT '资源数据ID索引，用于加速查询',
                                        INDEX idx_workspace_id (workspace_id) COMMENT '工作空间ID索引，用于加速查询'
) COMMENT='资源数据写入表，是resource_data_read_db表的复制，用于写入操作';

CREATE TABLE resource_iteration_data_read_db (
                                                 id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，唯一标识每条记录',
                                                 resource_data_id VARCHAR(255) NOT NULL COMMENT '资源数据ID，外键关联resource_data_read_db表的resource_data_id字段',
                                                 iteration VARCHAR(255) NOT NULL COMMENT '当前展示的版本号',
                                                 parent VARCHAR(255) NOT NULL COMMENT '父节点',
                                                 workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间ID，标识该记录所属的工作空间',
                                                 update_user_id VARCHAR(255) NOT NULL COMMENT '版本创建人ID',
                                                 gmt_creat DATETIME NOT NULL COMMENT '版本创建时间',
                                                 gmt_update DATETIME NOT NULL COMMENT '版本更新时间',
                                                     FOREIGN KEY (resource_data_id) REFERENCES resource_data_read_db(resource_data_id),
                                                 INDEX idx_resource_data_id (resource_data_id) COMMENT '资源数据ID索引，用于加速查询'
) COMMENT='资源版本数据读取表，存储资源的版本信息';

CREATE TABLE resource_iteration_data_write_db (
                                                  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，唯一标识每条记录',
                                                  resource_data_id VARCHAR(255) NOT NULL COMMENT '资源数据ID，外键关联resource_data_read_db表的resource_data_id字段',
                                                  iteration VARCHAR(255) NOT NULL COMMENT '当前展示的版本号',
                                                  parent VARCHAR(255) NOT NULL COMMENT '父节点',
                                                  workspace_id VARCHAR(255) NOT NULL COMMENT '工作空间ID，标识该记录所属的工作空间',
                                                  update_user_id VARCHAR(255) NOT NULL COMMENT '版本创建人ID',
                                                  gmt_creat DATETIME NOT NULL COMMENT '版本创建时间',
                                                  gmt_update DATETIME NOT NULL COMMENT '版本更新时间',
                                                  FOREIGN KEY (resource_data_id) REFERENCES resource_data_read_db(resource_data_id),
                                                  INDEX idx_resource_data_id (resource_data_id) COMMENT '资源数据ID索引，用于加速查询'
) COMMENT='资源版本数据读取表，存储资源的版本信息,写表';



