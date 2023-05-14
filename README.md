# README

## application.yml配置

安全原因，已经删除了里面的yml配置文件

以下为yml配置

```java
server:
  port: 8083
spring:
  data:
    mongodb:
      uri: mongodb://localhost/fklast
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/fklast?serverTimezone=UTC
      username: "root"
      password: "root"
  profiles:
    active: dev
  mail:
    username: 邮箱
    password: 你的秘钥
    default-encoding: UTF-8
    host: smtp.qq.com
  servlet:
    multipart:
      #      单个文件大小
      max-file-size: 1500MB
      #      总上传文件大小
      max-request-size: 3000MB
      #      文件达到多少时进行磁盘写入
      file-size-threshold: 4000MB
      enabled: false
  redis:
    host: localhost
    port: 6379
#    password: "1236987415q"
  cache:
    type: redis
    redis:
      time-to-live: 5m
      #      是否开启前缀
      use-key-prefix: true
      key-prefix: sms_

mybatis-plus:
  global-config:
    db-config:
      table-prefix: fk_
      id-type: auto
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: false

#公私钥配置
rsa:
  key:
    pubKeyFile: E:\\fuckingkeys\\id_key_rsa.pub
    priKeyFile: E:\\fuckingkeys\\id_key_rsa



fromMail:
  sender: 邮箱
logging:
  level:
    root: warn
pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
```

## 计算机组成原理培训课程学习平台的设计

### 软件简介

​	本系统是基于SpringBoot框架开发的计算机组成原理培训学习平台。实现了发布视频、发布习题、管理员审核、视频播放、习题练习、视频进度的保存和学习进度保存等功能。尤其是实现了视频进度保存和个人学习进度的保存，可以让学生们明确的知道自己学到具体哪一步，并能更好的针对自己的学习展开下一步计划。系统达到了看视频学习，并且做习题练习的目的，可以拓宽学生们学习计算机组成原理课程的渠道，多一种学习方式。学生对较难的知识点有充足的时间可以学习，也能提升学生学习本课程的自主性和积极性。

### 总体设计

#### 总体功能

系统的主要体现在于在线视频的学习和习题练习，主要功能包括习题组模块、视频模块。

##### 视频模块包括：

1) 上传视频模块：提供一个视频上传的接口，给用户上传，在上传成功之后会自动截取视频封面。
2) 查询视频模块：可以让用户查询具体的视频信息。
3) 视频播放量记录：可以记录视频的播放次数。
4) 视频进度保存：在用户登录的状态下，可以保存用户当前观看的视频进度，方便下次观看时直接跳到对应位置。

##### 习题模块主要包括：

1) 上传习题：用户可以自行制作选择题、判断题和主观题，题目数量和难度由用户自主掌握，内有保存功能。
2) 查找习题：可以让用户按习题标题查询习题。
3) 已完成的习题记录：在个人主页中，可以提供一个已完成的习题记录列表，里面是已经完成过的习题。

主要功能结构如图2-1所示：

![系统模块](./img/%E7%B3%BB%E7%BB%9F%E6%A8%A1%E5%9D%97.jpg)

### 环境部署

#### 1、 开发环境部署

在线下载JDK，版本17。在DOS窗口输入”java -version“然后点击回车键，出现图3-1所示的信息表示运行环境部署成功。

![jdk](./img/jdk-1684044690019-13.jpg)

图3-1 开发环境部署

#### 2、 配置Java的环境变量

在DOS窗口输入 “javac”然后点击回车键，出现图3-2所示的信息表示开发环境变量配置成功。

![javac](./javac-1684044699600-15.png)

图3-2 开发环境配置

 

#### 3、 配置Node环境变量

 在DOS窗口输入 “node”然后点击回车键，出现图3-3所示的信息表示开发环境变量配置成功。



![node](./node.png)



#### 4、 启动MongoDB数据库

在对应数据库路径下的文件夹中开启DOS窗口并且输入“mongod --dbpath=..\data\db”后，出现如图3-4所示信息表示开启成功




![clip_image002](./clip_image002.jpg)

图 3‑4 启动MongoDB数据库

#### 5、启动前端静态资源访问端口

配置文件app.js内容如下。在对应路径下启动DOS窗口并且输入“node app.js”回车之后，出现如图3-5所示信息表示开启成功

const express = require("express");

const app = express();

const PORT = 3005;

 

//开放静态资源以供访问

app.use(express.static('static'));

 

app.listen(PORT, () => {

 console.log(`running at port ${PORT}...`);

})

![image-20230514134311092](./image-20230514134311092-1684044728271-19.png)

图 3-5 启动前端静态资源端口

