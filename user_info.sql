create table user_info(
    user_id     int,
    user_name   varchar(256),
    user_pass   varchar(256),
	user_sex    varchar(4),
	user_phone  varchar(32),
    user_email	varchar(128),
	user_age    int,
	user_type   varchar(16),
	crt_t       datetime comment '创建时间',
	crt_u       int comment '创建用户',
	upt_t       datetime comment '修改时间',
	upt_u       int  comment '修改用户'
 );
