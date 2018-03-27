-- -----
-- 用户表
-- -----
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
  id BIGINT NOT NULL auto_increment COMMENT '设置主键自增',
  username VARCHAR(100) NOT NULL COMMENT '用户名',
  password varchar(100) NOT NULL COMMENT '密码 md5加密',
  nick_name VARCHAR(100) NOT NULL COMMENT '用户昵称',
  user_type BOOL DEFAULT TRUE COMMENT '角色',-- 0表示buyer买家;  1表示seller卖家
  PRIMARY KEY  (id)
)CHARSET=utf8 ENGINE=InnoDB;
CREATE UNIQUE INDEX idx_s_users_username ON user(username);

-- ---------
-- ticket 表
-- ---------
DROP TABLE IF EXISTS ticket;
CREATE TABLE IF NOT EXISTS ticket(
  id BIGINT NOT NULL auto_increment COMMENT '设置主键自增',
  user_id BIGINT NOT NULL COMMENT '用户id',
  ticket varchar(128) NOT NULL COMMENT 'ticket',
  expired DATETIME COMMENT '过期时间',
  status INT NULL DEFAULT 0 COMMENT '状态，登出改为1',
  PRIMARY KEY  (id),
  UNIQUE INDEX ticket_UNIQUE (`ticket` ASC)
)CHARSET=utf8 ENGINE=InnoDB;


-- -----
-- 商品表
-- -----
DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product (
  id BIGINT NOT NULL auto_increment COMMENT '设置主键自增',
  seller_id BIGINT NOT NULL COMMENT '卖家ID',
  price DOUBLE DEFAULT NULL COMMENT '当前价格',
  title VARCHAR(80) NOT NULL COMMENT '标题',
  image varchar(128) NOT NULL COMMENT '图片url',
  summary varchar(140) NOT NULL COMMENT '摘要',
  content varchar(1000) NOT NULL COMMENT '正文',
  PRIMARY KEY  (id)
)CHARSET=utf8 ENGINE=InnoDB;
CREATE UNIQUE INDEX product_seller_id ON product(seller_id);

-- ------
-- 交易表
-- ------
DROP TABLE IF EXISTS transaction;
CREATE TABLE IF NOT EXISTS transaction (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '设置主键自增',
  product_id int(11) DEFAULT NULL COMMENT '产品id',
  buyer_id int(11) DEFAULT NULL COMMENT '购买用户id',
  buy_price double DEFAULT NULL COMMENT '购买价格',
  num int(11) DEFAULT NULL COMMENT '购买数量',
  buy_time DATETIME COMMENT '购买时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX transaction_product_id ON transaction(product_id);

-- ------
-- 购物车
-- ------
DROP TABLE IF EXISTS cart;
CREATE TABLE IF NOT EXISTS cart(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  buyer_id BIGINT NOT NULL COMMENT '购买用户id',
  content_id BIGINT NOT NULL COMMENT '产品id',
  num INT(11) NOT NULL COMMENT '购买数量',
  PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;
