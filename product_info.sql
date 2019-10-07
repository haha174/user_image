create table user_info(
    product_id     int,
    product_type_id   int,
    product_name   varchar(256),
	product_description    varchar(256),
	price  decimal(18,4),
    num	int,
	mechart_id    int,
	product_url   varchar(256),
	brand         varchar(50),
	cre_date       datetime comment '创建时间',
	cre_user       int comment ' 创建用户',
	upd_date       datetime comment '修改时间',
	upd_user       int  comment '修改用户'
 );
