<div align="center">
	<img src="https://image.xiaoxiaofeng.site/blog/2024/04/15/xxf-20240415111508.svg?xxfjava">
    <h2>
        基于SpringBoot+Vue3开发的轻量级快速开发脚手架
    </h3>
	<p align="center">
        <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
            <img src="https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417134430.png?xxfjava" alt="jdk-8" />
        </a>
		<a href="https://v3.vuejs.org/" target="_blank">
			<img src="https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417134539.png?xxfjava" alt="vue">
		</a>
		<a href="https://github.com/hack-feng/maple-boot/blob/main/LICENSE" target="_blank">
		  <img src="https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417134340.png?xxfjava" alt="license">
		</a>
	</p>
</div>

## 🍁项目简介

一个通用的前、后端项目模板

一个快速开发管理系统的项目

一个可以生成SpringBoot+Vue代码的项目

一个持续迭代的开源项目

一个程序员的心血合集

度过严寒，终有春日；挺过酷暑，必有丰收。

🚀🚀🚀 项目体验地址[https://maple.xiaoxiaofeng.com/](https://maple.xiaoxiaofeng.com/)

## 🚀项目架构

**JDK17版本戳链接** [https://gitee.com/hack-feng/maple-boot-17](https://gitee.com/hack-feng/maple-boot-17)

**后端项目**

项目主要使用SpringBoot + Mybatis Plus开发，代码生成模块参考[若依](https://gitee.com/y_project/RuoYi)框架进行改造。

* JDK8
* SpringBoot  v:2.7.12
* Mybatis Plus  v:3.5.2
* Lombok
* Mysql  v:8.0+
* Redis
* ...

**前端项目**

前端基于开源项目[vue-next-admin](https://gitee.com/lyt-top/vue-next-admin)框架进行开发改造。

* vue3.x
* CompositionAPI setup 语法糖
* typescript
* vite
* element plus
* vue-router-next
* pinia

## 🎨项目功能

| 功能名称 |                           功能简介                           | 功能状态 |
| :------: | :----------------------------------------------------------: | :------: |
| 用户管理 |                       管理平台用户管理                        |    ✔     |
| 角色管理 |                       管理平台角色管理、数据权限               |    ✔     |
| 菜单管理 |                       管理平台菜单管理、按钮权限               |    ✔     |
| 部门管理 |                      树表结构生成实验田                       |    ✔     |
| 字典管理 |                     一对多模式生成实验田                      |    ✔     |
| 系统配置 |                        单表生成实验田                        |    ✔     |
| 代码生成 | 生成SpringBoot+Vue代码。单表生成、树表结构生成、一对多模式生成   |    ✔     |
| 后续规划 | 增加可配置化网站功能，网站展示，文章发布，类似于[笑小枫](https://www.xiaoxiaofeng.com)网站 |    进行中     |
| 更多功能 | 本项目只做最基础的功能，后续功能化集成会陆续开源其它项目，欢迎大家持续关注[笑小枫](https://www.xiaoxiaofeng.com) |    ❤     |

## 功能欣赏

登录页

![image-20240417141408120](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141408.png?xxfjava)

首页

![image-20240417141854786](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141855.png?xxfjava)

用户管理

![image-20240417141432864](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141432.png?xxfjava)

菜单管理

![image-20240417141502304](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141502.png?xxfjava)

字典编辑页面

![image-20240417141537529](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141537.png?xxfjava)

生成工具-生成代码配置

![image-20240417141641747](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141641.png?xxfjava)

生成工具-生成代码预览

![image-20240417141614037](https://image.xiaoxiaofeng.site/blog/2024/04/17/xxf-20240417141614.png?xxfjava)

## 项目结构

服务模块定义

~~~ABAP
maple-admin-system             ------------ 模块名称
├─src
│  └─main
│      ├─java
│      │  └─com.maple.system   ------------ 模块目录
│      │     ├─bean          ------------ 实体类
│      │     ├─mapper        ------------ mapper类
│      │     ├─service       ------------ service接口类
│      │     │  └─impl       ------------ service接口实现类
│      │     └─vo
│      │        ├─model      ------------ 对外实体Model类
│      │        └─query      ------------ 请求参数对象
│      └─resources
│          └─mapper          ------------ mapper的xml文件
└─pom.xml                    ------------ maven配置文件
~~~

Rest接口模块定义

~~~ABAP
maple-admin-rest                   ----------- 模块名称
├─src
│  └─main
│      ├─java
│      │  └─com.maple.rest         ----------- 模块目录
│      │     ├─aop                 ----------- AOP切面配置
│      │     ├─config              ----------- 启动项配置
│      │     ├─controller          ----------- Controller接口
│      │     │  ├─common           ----------- 通用接口
│      │     │  └─manage           ----------- 管理模块接口
│      │     │     ├─system        ----------- 管理模块-系统设置接口
│      │     │     ├─tool          ----------- 管理模块-工具类接口
│      │     │     └─usc           ----------- 管理模块-系统用户接口
│      │     ├─job                 ----------- 定时任务
│      │     └─Application.java    ----------- 项目启动类
│      └─resources                 ----------- 资源目录
│          └─WEB-INF
│              └─resources
└─pom.xml                          ----------- Maven配置文件
~~~

前端模块定义

~~~ABAP
maple-web
├─public             ---------- 公开文件
└─src                           
    ├─api            ---------- 接口地址
    │  └─system      ---------- 系统管理接口
    ├─assets         ---------- 应用程序使用的静态资源文件
    ├─components     ---------- 应用程序的公共组件
    ├─directive      ---------- Vue的自定义指令
    ├─i18n           ---------- 放置国际化多语言配置文件
    ├─layout         ---------- 项目的布局.vue模板
    ├─router         ---------- 应用程序的路由配置
    ├─stores         ---------- 应用程序的状态管理工具
    ├─theme          ---------- 应用程序的主题配置
    ├─types          ---------- 数据类型
    ├─utils          ---------- 自己封装的一些全局性的js功能文件
    └─views          ---------- 应用程序的页面
~~~
