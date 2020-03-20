## 码匠社区

## 资料
https://spring.io/

[github授权](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

[spring 数据库支持](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)
[整合mybatis](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
## 工具

## 遇到问题
P7 下拉列表不显示 先引入jquery


## 脚本
创建user表
```sql 
create table user(
  id int auto_increment primary key,
  name varchar(50),
  account_id varchar(50),
  token char(36),
  gmt_create bigint,
  gmt_modified bigint
)
```