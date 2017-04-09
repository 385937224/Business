# Tax
服务系统


本系统的开发工环境：
●	系统开发平台：Eclipse。
●	数据库管理系统软件：MySQL。
●	运行平台：Windows 7。
●	Web服务器：Tomcat 6.0。
本系统是基于MVC模式，采用struts2+Spring+hibernate3的SSH框架进行搭建的服务系统
●	显示层：html标签、jstl标签、js、jquery、ajax、EL表达式。
●	控制层：使用Struts 2技术开发。
●	业务逻辑层：使用Spring技术进行业务处理。
●	数据访问层：使用Hibernate进行数据库访问和操作。


2017.4.10
本系统目前完成了用户模块的所有功能。


![用户模块列表页面](https://github.com/385937224/Tax/raw/master/imges4md/1123123.png)
该页面是用户列表页面，所具有的功能：
1.显示用户：列出数据库中所有用户。
2.跳转新增用户页面。
3.跳转编辑用户页面。
4.删除用户。
5.批量删除用户。
6.导出所有用户到Excel表中。
7.把Excel表中的用户数据批量导入。

6、7功能是通过POI组件操作Excel完成。
	对于POI组件，只是使用起部分功能，并未深入研究。在导出功能方面算比较完善。但是把Excel表中的数据批量导入的时候，目前不具备对Excel表中的数据内容、格式等进行校验的功能。

	
![新增户页面](https://github.com/385937224/Tax/raw/master/imges4md/1.png)	
该页面是新增用户页面：
利用js实现校验功能：用户的新增时，对用户名、帐号、密码进行非空校验、帐号的唯一性校验、用户名内容的校验。

1.当点击保存时，对用户名、帐号、密码这三项进行了效验。这三项必须填写，不然保存失败。

2.用户名利用正则表达式，简单的限制了用户名的内容：只能是数字或字符。
![用户名校验](https://github.com/385937224/Tax/raw/master/imges4md/2.png)

3.帐号的唯一性校验利用ajax技术。发送的请求使用同步执行，因为ajax请求异步执行在保存校验时会有bug。
![帐号校验](https://github.com/385937224/Tax/raw/master/imges4md/3.png)


![新增户页面](https://github.com/385937224/Tax/raw/master/imges4md/4.png)
该页面是编辑页面:
对用户的数据进行了回显。以及帐号的唯一性校验、用户名内容的校验。

1.在编辑页面中帐号的唯一性校验要排除当前编辑的帐号，不然出现无法保存。
2.对当前编辑用户的数据进行了回显。
3.头像图片上传功能。
	
	