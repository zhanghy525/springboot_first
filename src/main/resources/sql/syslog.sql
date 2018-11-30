CREATE TABLE SYS_LOG (
  ID int(20) primary key auto_increment NOT NULL ,
  USERNAME VARCHAR(50) NULL comment '用户名',
  OPERATION VARCHAR(50) NULL comment '用户操作',
  TIME numeric(11) NULL comment '响应时间',
  METHOD VARCHAR(200) NULL comment '请求方法',
  PARAMS VARCHAR(500) NULL comment '请求参数',
  IP VARCHAR(64) NULL comment 'IP地址',
  CREATE_TIME DATE NULL comment '创建时间'
);