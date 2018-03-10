# 架构
* 本项目采用的是目前十分流行的SSH （Spring， Struts， Hibernate）架构，严格遵循模型-视图-控制器，即MVC的设计模式。
* 利用Spring实现了IOC模式，实现了类之间的解耦
* 其中利用Struts框架及其标签库实现了控制器和视图，而模型则分为三层，分别为业务(Service)层（即业务模型）、数据访问(DAO)层（即数据访问模型）和持久层(由Hibernate框架实现)
×　利用 Hibernate 实现的 OR 映射，解决了代码中大量的sql语句的问题，同时数据的持久化也交由Hibernate自动来做

# 功能
1. 管理员：
* 对图书，用户，订单的增删查改
* 对销售数据获取GUI的分析图表，包括对特定用户，对特定图书，对特定图书种类的分析图表 （利用了开源JS图表工具 chart.js）

2. 用户
* 登陆，记住密码，登出，注册（其中登陆后产生的数据存放在了session中，记住密码的功能通过cookie的机制实现）
* 用户个人主页除了注册时输入的基本个人信息以外，还可以上传一张头像，以及编辑一段富文本文字作文个人简介，这部分都是用MongoDB存储
* 个人主页的增删改查
* 搜索图书，查看图书的详细信息，这部分运用的是AJAX，异步的更新页面中的数据
* 选定某图书，指定数量加入购物车， 其中图书飞入购物车的动画效果使用了开源脚本fly.js
* 在购物车内对某本书的数量做修改
* 在购物车内选定图书加入新的订单中，提交订单

3. 其他
* 管理员和普通用户在同一界面上登陆，只有管理员账号可以登入控制台

# 数据库
* MySQL
* MongoDB

# 部署
* File->Import->Git->Projects from Git->Next->Clone URI->[https://github.com/IrvingW/BookStore](https://github.com/IrvingW/BookStore)->User/Password
* Right Click->Properties->Project Facets->Convert to faceted form
1. 左边Project Facet->选择Dynamic Web Module, Java, JavaScript
2. 右边Runtimes->选择Apache Tomcat v8.0
3. Apply->OK
* Right Click->Configure->Convert to Maven Project
* 若出现timeout问题，双击Tomcat v8.0 Servers->将启动时间限制放大，右边，选择timeouts->Start设置为1000
* Right Click->Run as->Maven install->BUILD SUCCESS
* 运行MySQL建库脚本，这里会插入一个管理员账号，name是root， 密码是mysql,管理员账号可以登入控制台页面
* 打开MongoDB服务
* Right Click->Run as->Run on Server
* 访问[http://localhost:8080/BookStore/](http://localhost:8080/BookStore/)
