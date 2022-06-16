package com.netty.rpc.server;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.server.core.NettyServer;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * RPC Server
 *
 * @author luxiaoxun
 */
public class RpcServer extends NettyServer implements ApplicationContextAware, InitializingBean, DisposableBean {
    public RpcServer(String serverAddress, String registryAddress) {
        super(serverAddress, registryAddress);
    }

    /**
     * 将 NettyRpcService 注解的服务加入到 父类的serviceMap中
     * @param ctx
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // 拿到所有被 NettyRpcService 注解的Bean
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(NettyRpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                // 获取注解 属性的值
                NettyRpcService nettyRpcService = serviceBean.getClass().getAnnotation(NettyRpcService.class);
                String interfaceName = nettyRpcService.value().getName();
                String version = nettyRpcService.version();
//                添加服务
                super.addService(interfaceName, version, serviceBean);
            }
        }
    }

    /**
     * 初始化Bean 的时候 启动 NettyServer
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.start();
    }

    /**
     * 销毁Bean 的时候，stop
     */
    @Override
    public void destroy() {
        super.stop();
    }
}
