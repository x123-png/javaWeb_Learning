## 内容

### Database 
用mysql讲了关系型数据库的相关知识
+ 关系型数据库
+ 数据库的产品
+ SQL语句语法
+ 讲了MySQL服务器的安装
+ MySQL客户端的使用


### JDBC
如何使用Java来连接和操作MySQL数据库
+ 数据库的驱动 Mysql Connector Jar 下载
+ JDBC的基本步骤
  + 创建连接 Connection
  + 创建的语句 Statement
  + 执行数据库查询 SQL（select）
  + 返回ResultSet数据集，获取数据
  + 执行数据库的修改的 SQL（insert， update， delete）

### DBCP
如何使用数据库的连接池来高效地访问数据库
+ Apache的DBCP2的开发库来构建数据库连接池
+ 如何将连接池包装成一个数据库的服务类 DatabaseService
  + 完成初始化，创建连接池
  + 从 BasicDataSource 获取数据库的连接。 
  + 在 DatabaseService 提供了两类接口
    + 一类Query接口（增加了一个队ResultSet对象的访问者对象）
    + 一类Execute接口（insert， update， delete）
  + 如何将DatabaseService的生命周期融入到我们的JavaWeb APP的生命周期里？
    + 通过实现一个可以**监控WebApp**生命周期的对象来控制DatabaseService的生命周期。
    + 通过实现一个继承了ServletContextListener接口的对象来完成对WebApp生命周期的监控。
    + 将初始化好的DatabaseService对象放入到WebApp的上下文中，供其它对象获取并调用数据库访问接口。