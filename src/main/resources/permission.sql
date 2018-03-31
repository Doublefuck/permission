create table sys_dept(
	dept_id int not null primary key comment '部门id',
	name varchar(20) not null default '' comment '部门名称',
	parent_id int not null default 0 comment '上级部门id',
	level varchar(200) not null default '' comment '部门层级',
	seq int not null default 0 comment '部门在当前层级下的顺序，由小到大',
	remark varchar(200) default '' comment '备注',
	operator varchar(20) not null comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次更新操作者的IP'
) comment='部门表';

ALTER TABLE sys_dept MODIFY dept_id int(3) AUTO_INCREMENT;
ALTER TABLE sys_dept AUTO_INCREMENT = 100;

SELECT count(1) FROM sys_dept;
INSERT INTO sys_dept VALUES (null, 'zs', 0, 0, 1, 'xxx', 'zs', now(), now());
DELETE FROM sys_dept WHERE dept_id IN (100,101);

ALTER TABLE sys_dept AUTO_INCREMENT = 100000;
DELETE FROM sys_user WHERE user_id = 100000;

create table sys_user(
	user_id int not null primary key comment '用户id，主键自增，以100000起始',
	username varchar(20) not null default '' comment '用户名',
	telephone varchar(13) not null default '' comment '手机号码',
	email varchar(20) not null default '' comment '邮箱',
	password varchar(40) not null comment '加密后的密码',
	dept_id int not null default 0 comment '用户所在部门的id',
	status int not null default 1 comment '用户状态，1代表正常，0代表冻结状态，2代表已被删除',
	remark varchar(200) not null default '' comment '备注',
	operator varchar(20) not null default '' comment '最后一次的操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次更新操作者的IP'
) comment='用户表';

insert INTO sys_user VALUES (null, 'zs', '11111', '1','1',1,1,'11111','xxx', now(), '111');
SELECT * FROM sys_user;

ALTER TABLE sys_acl_module MODIFY acl_module_id int(3) AUTO_INCREMENT;
ALTER TABLE sys_acl_module AUTO_INCREMENT = 100;

create table sys_acl_module(
	acl_module_id int not null primary key comment '权限模块id，自增，100起始',
	name varchar(20) not null default '' comment '权限模块名称',
	parent_id int not null default 0 comment '上级权限id',
	level varchar(200) not null default '' comment '权限模块层级',
	seq int not null default 0 comment '权限模块在当前层级下的顺序，由小到大',
	status int not null default 1 comment '用户状态，1代表正常，0代表冻结状态',
	remark varchar(200) default '' comment '备注',
	operator varchar(20) not null comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP'
)comment='权限模块表';

ALTER TABLE sys_acl MODIFY acl_id int AUTO_INCREMENT;
ALTER TABLE sys_acl AUTO_INCREMENT = 10;

create table sys_acl(
	acl_id int not null primary key comment '权限id，自增，10开始',
	code varchar(20) not null default '' comment '权限码',
	name varchar(20) not null default '' comment '权限名称',
	acl_module_id int not null default 0 comment '权限所属的权限模块id',
	url varchar(100) not null default '' comment '请求的url，可以填写正则',
	type int not null default 1 comment '权限类型，1：菜单，2：按钮，3：其他',
	status int not null default 1 comment '权限状态，1：正常，0：冻结',
	seq int not null default 0 comment '权限在当前模块下的顺序，由小到大',
	remark varchar(200) default '' comment '备注',
	operator varchar(20) not null comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP'
)comment='权限表';

ALTER TABLE sys_role MODIFY role_id int AUTO_INCREMENT;
ALTER TABLE sys_role AUTO_INCREMENT = 1;

create table sys_role(
	role_id int not null primary key comment '角色id',
	name varchar(20) not null comment '角色名称',
	type int not null default 1 comment '角色类型,1管理员，2其他',
	status int not null default 1 comment '权限状态，1：正常，0：冻结',
	remark varchar(200) default '' comment '备注',
	operator varchar(20) not null comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP'
)comment='角色表';
select * from sys_role;
ALTER TABLE sys_role_user MODIFY role_user_id int AUTO_INCREMENT;
ALTER TABLE sys_role_user AUTO_INCREMENT = 1;

create table sys_role_user(
	role_user_id int not null primary key comment '用户角色id',
	role_id int not null comment '角色id',
	user_id int not null comment '用户id', 
	operator varchar(20) not null default '' comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP'
)comment='角色用户关联表';

ALTER TABLE sys_role_acl MODIFY role_acl_id int AUTO_INCREMENT;
ALTER TABLE sys_role_acl AUTO_INCREMENT = 1;
SELECT * FROM sys_role_acl;
create table sys_role_acl(
	role_acl_id int not null primary key auto_increment comment '角色权限id',
	role_id int not null comment '角色id',
	acl_id int not null comment '权限id', 
	operator varchar(20) not null default '' comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP'
)comment='角色权限关联表';

ALTER TABLE sys_log MODIFY log_id int AUTO_INCREMENT;
ALTER TABLE sys_log AUTO_INCREMENT = 1;

create table sys_log(
	log_id int not null primary key,
	type int not null default 0 comment '权限更新类型，1部门，2用户，3权限模块，4权限，5角色，6角色用户关系，7角色权限关系',
	target_id int not null comment '基于type结果指定的对象id，比如用户权限、权限、角色等表的主键',
	old_value text comment '旧值',
	new_value text comment '新值',
	operator varchar(20) not null default '' comment '操作人员',
	operator_time datetime default NOW() comment '最后一次操作时间',
	operator_ip varchar(20) not null default '' comment '最后一次操作者的IP',
	status int not null comment '当前是否进行过复原操作，0没有，1复原过'
)comment='权限相关更新记录表';

select * from sys_dept;
alter table sys_user ADD COLUMN type int(2);
DELETE FROM sys_dept;
SELECT count(1) from sys_user where telephone = '1111111111';
INSERT INTO sys_user VALUES (null, '张三', '13555796632', '10001@qq.com', '12345678', 1, 1 ,'张三备注1', 'admin', now(), now());
select * FROM sys_log;
select * from sys_acl_module;

/*
	每个表都有自己的主键
	字段尽量定义为not null
	尽量为每一个字段添加备注
	数据库字典统一小写，单词之间使用下划线
	使用innoDB存储引擎
	尽量使用varchar
*/

CREATE DATABASE security;