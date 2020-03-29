## 码匠社区

## 资料
https://spring.io/

[github授权](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

[spring 数据库支持](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)
[整合mybatis](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
[okhttp]()
[lombok](https://projectlombok.org/features/all)
[MyBatis Generator](http://mybatis.org/generator/index.html)
[Jquery](https://api.jquery.com/)
[Html5-localStorage](https://www.runoob.com/jsref/prop-win-localstorage.html)
## 工具
[JSON在线解析工具](https://jsoneditoronline.org/)
[apipost]
## 遇到问题
P7 下拉列表不显示 先引入jquery


## 脚本
创建user表
```sql 
create table user(
  id bigint auto_increment primary key,
  name varchar(50),
  account_id varchar(50),
  token char(36),
  gmt_create bigint,
  gmt_modified bigint
)
```

创建question表
```sql
create table question
(
    id bigint primary key auto_increment,
	title varchar(50) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator bigint null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
    tag varchar(256) null
);
-- 添加头像字段
alter table user
	add avatar_url varchar(100) null;
-- 添加头像字段
alter table user
	add avatar_url varchar(100) null;
```
创建回复表
```sql
create table comment
(
  id bigint not null auto_increment,
  parent_id bigint not null comment '所回复问题的id',
  type int not null comment '区分一级回复和二级回复',
  content text not null ,
  commentator bigint not null comment '评论人id',
  gmt_create bigint not null,
  gmt_modified bigint null,
  like_count int default 0 null,
  constraint comment_pk
    primary key (id)
);


```
**快捷键**
shift+F9  只改html
cmd+ alt + o 去除无用的引入 
shift + f6 选中要替换的值
cmd + alt +v 自动抽取出变量
在html文件中 ctrl+w （可以连续按 不断选中上一层）

P18 20分钟提到 数据库验证登录的缺陷 可以用redis改进            

ctrl+shift+F
ctrl+F12 
ctrl+shift + U 大小写转换
调试时： alt+F8 调试时执行选中的方法


### 学习体会
updateByExample需要将表的条件全部给出，比如一个一个表有三个字段，就必须给三个字段给他，不给会设为null，
而updateByExampleSelective不同，当某一实体类的属性为null时，mybatis会使用动态sql过滤掉，不更新该字段：




--mybatis自动生成的命令
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate


TO-DO LIST
+ 修复主页描述信息获取不到的问题
+ P35 CustomizeErrorController没有做


----
P36
实现阅读数展示，原来的实现存在并发问题
```java
        Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setViewCount(question.getViewCount()+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, example);
```
改进方法：在数据库字段上做累加操作，不要获取数据库字段成为变量再操作