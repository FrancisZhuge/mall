-- -----
-- 用户表
-- -----
DROP TABLE IF EXISTS s_user;
CREATE TABLE IF NOT EXISTS s_user (
  id BIGINT NOT NULL auto_increment COMMENT '设置主键自增',
  username VARCHAR(32) NOT NULL COMMENT '用户名',
  password varchar(32) NOT NULL COMMENT '密码',
  role BOOL DEFAULT TRUE COMMENT '角色',-- 0表示buyer;  1表示seller
  PRIMARY KEY  (id)
)CHARSET=utf8 ENGINE=InnoDB;
CREATE UNIQUE INDEX idx_s_users_username ON s_user(username);

-- -----
-- 商品表
-- -----
DROP TABLE IF EXISTS s_goods;
CREATE TABLE IF NOT EXISTS s_goods (
  id BIGINT NOT NULL auto_increment COMMENT '设置主键自增',
  title VARCHAR(80) NOT NULL COMMENT '标题',
  pic_url varchar(128) NOT NULL COMMENT '图片url',
  abstract varchar(140) NOT NULL COMMENT '摘要',
  text varchar(1000) NOT NULL COMMENT '正文',
  PRIMARY KEY  (id)
)CHARSET=utf8 ENGINE=InnoDB;
