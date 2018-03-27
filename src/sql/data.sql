-- ------------
-- 输入用户数据
-- ------------
INSERT INTO user(username, password, nick_name, user_type) VALUES ('buyer','37254660e226ea65ce6f1efd54233424','buyer',0),
                                                                    ('seller','981c57a5cfb0f868e064904b8745766f','seller',1);



-- ------------
-- 输入商品数据
-- ------------
INSERT INTO product(seller_id, price, title, image, summary, content) VALUES
  (2,11.11,'testTitle1','/image/1.jpg','testSummary1','testContent1'),
  (2,22.22,'testTitle2','/image/2.jpg','testSummary2','testContent2'),
  (2,33.33,'testTitle3','/image/3.jpg','testSummary3','testContent3'),
  (2,44.44,'testTitle4','/image/4.jpg','testSummary4','testContent4'),
  (2,55.55,'testTitle5','/image/5.jpg','testSummary5','testContent5'),
  (2,66.66,'testTitle6','/image/6.jpg','testSummary6','testContent6'),
  (2,77.77,'testTitle7','/image/7.jpg','testSummary7','testContent7'),
  (2,88.88,'testTitle8','/image/8.jpg','testSummary8','testContent8'),
  (2,99.99,'testTitle9','/image/9.jpg','testSummary9','testContent9');