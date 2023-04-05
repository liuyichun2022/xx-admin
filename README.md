## 概述
针对ToB的业务，搭建通用管理后台框架，基于RBCA权限控制模型，权限认证体系。这个其实是一个比较成熟的解决方案体系，
对应优秀的框架也很多，但是可能集成的东西比较多，我这边整理的可能比较符合自己的业务场景，有类似场景的同学，欢迎借鉴， 如有错误也欢迎指正。

## 功能概述
主要功能
1. 后端管理系统，权限，菜单角色管理
![image](https://user-images.githubusercontent.com/16557117/229948046-0b6d1cb7-c24b-4b2b-9a04-8e7a565f8768.png)

2. 简单的内容管理服务
![image](https://user-images.githubusercontent.com/16557117/229948188-fc1a4098-a7fb-4b4c-8d5f-fe305bdb1c6a.png)

3. UeditorPlus + 秀米编辑器, 实现了秀米图片或者视频文件转储
![image](https://user-images.githubusercontent.com/16557117/229948287-c055e01a-f900-4f5e-b865-4f8b2eadef42.png)
![image](https://user-images.githubusercontent.com/16557117/229948325-f2867732-828b-4c5c-b9ff-68a3224a964e.png)



项目演示地址：
http://1.13.22.158:8000/#/login

账号密码: admin/admin

## 技术选型
| 技术                   | 版本    | 说明              |
| ---------------------- | ------- |-----------------|
| SpringBoot             | 2.7.0   | 脚手架            |
| SpringSecurity         | 5.7.1   | 认证和授权框架         |
| MyBatis-Plus           | 3.5.1   | MyBatis增强工具     |
| Swagger-UI             | 3.0.0   | 文档生产工具          |
| Redis                  | 5.0     | 分布式缓存           |
| Docker                 | 18.09.0 | 应用容器引擎          |
| Druid                  | 1.2.9   | 数据库连接池          |
| Hutool                 | 5.8.0   | Java工具类库        |
| JWT                    | 0.9.1   | JWT登录支持         |
| Lombok                 | 1.18.24 | 简化对象封装工具        |
