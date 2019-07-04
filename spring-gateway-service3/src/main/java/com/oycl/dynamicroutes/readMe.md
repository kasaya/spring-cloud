思路，通过spring bus 刷新route：
  -  1- 扩展消息总线，首先定义新的endpoint, 其中 @WriteOperation 是设定此方法为写入方法，在写入方法里触发自定义刷新事件

 ```
    @Endpoint(id = "refresh-gateway")
    public class DynamicRouteBusEndpoint extends AbstractBusEndpoint{
    
        public DynamicRouteBusEndpoint(ApplicationEventPublisher context, String appId) {
            super(context, appId);
        }
    
        @WriteOperation
        public void notifyChanged(){
            this.publish(new RefreshRoutesApplicationEvent(this,getInstanceId()));
        }
    }

 ```

  - 2- RemoteApplicationEvent定义被传输的消息，继承RemoteApplicationEvent实现自定义刷新事件，将事件的目的服务destinationService指定为“gateway-server”，即你的gateway服务
  ```
    public class RefreshRoutesApplicationEvent extends RemoteApplicationEvent {
        public RefreshRoutesApplicationEvent(Object source, String orginService){
            //在RemoteApplicationEvent中定义了主要的三个通用属性事件的来源originService、事件的目的服务destinationService和随机生成的全局id。
            super(source, orginService, "gateway-server");
        }
    }

  ```
  - 3- 继承 ApplicationListener来实现监听自定义刷新事件的监听器，消息总线返回后会执行这里。
  ```
    @Component
    public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesApplicationEvent> {
    
        
        @Autowired
        private FileRouteDefinitionRepository fileRouteDefinitionRepository;
    
    
        @Override
        public void onApplicationEvent(RefreshRoutesApplicationEvent event) {
            fileRouteDefinitionRepository.initRoute();
            System.out.println("RefreshRoutesListener.onApplicationEvent");
        }
    
    }

  ```
  - 4- 使用@RemoteApplicationEventScan 注册自定义事件。
       @RemoteApplicationEventScan注解可以非常方便的注册一个自定义的RemoteApplicationEvent。 
       自定义一个事件类型，然后使用@RemoteApplicationEventScan注解让Spring 启动的时候可以扫描到这个事件类：
   ```
      @EnableDiscoveryClient
      @SpringBootApplication
      @RemoteApplicationEventScan(basePackages = "com.oycl.dynamicroutes")
      public class SpringGatewayServiceApplication { ... }

   ```