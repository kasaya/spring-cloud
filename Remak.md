###已实现：

 - com.oycl.config server
 - zuul 路由+ 路由端跨域处理
 - spring retry 框架接入
 - maven 分环境打包
 - spring bus + Rabbitmq 自动更新配置
 
 ````
  spring bus 局部刷新url
  http://localhost:9093/actuator/bus-refresh/zuul-com.oycl.service:9095
 ````
 关于spring bus的配置。只需要在client端添加bus依赖+mq的配置就可以实现（不需要在eureka上配置bus依赖）
 ```
 42. Addressing an Instance
 Each instance of the application has a com.oycl.service ID, whose value can be set with spring.cloud.bus.id and whose value is expected to be a colon-separated list of identifiers, in order from least specific to most specific. The default value is constructed from the environment as a combination of the spring.application.name and server.port (or spring.application.index, if set). The default value of the ID is constructed in the form of app:index:id, where:
 
 app is the vcap.application.name, if it exists, or spring.application.name
 index is the vcap.application.instance_index, if it exists, spring.application.index, local.server.port, server.port, or 0 (in that order).
 id is the vcap.application.instance_id, if it exists, or a random value.
 The HTTP endpoints accept a “destination” path parameter, such as /bus-refresh/customers:9000, where destination is a com.oycl.service ID. If the ID is owned by an instance on the bus, it processes the message, and all other instances ignore it.

 ```
 - 异步config请求
 - Spring Security
 - JWT token
###待实现：

 - spring common 的调研
 - mq 服务
 
 - 分布式事物处理
 - 服务监控
 - 服务高可用
 - 分布式任务调度
 - 服务降级 服务限流
 - spring boot自定义启动器
 - 灰度发布