package com.netty.rpc.client.proxy;

import com.netty.rpc.client.connect.ConnectionManager;
import com.netty.rpc.client.handler.RpcClientHandler;
import com.netty.rpc.client.handler.RpcFuture;
import com.netty.rpc.codec.RpcRequest;
import com.netty.rpc.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 发起RPC的代理对象
 * 继承 InvocationHandler实现invoke方法
 * Created by luxiaoxun on 2016-03-16.
 */
public class ObjectProxy<T, P> implements InvocationHandler, RpcService<T, P, SerializableFunction<T>> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectProxy.class);
    private Class<T> clazz;
    private String version;

    public ObjectProxy(Class<T> clazz, String version) {
        this.clazz = clazz;
        this.version = version;
    }

    /**
     * 代理对象执行的方法：发送 数据
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
//        构造 RPC的请求
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        request.setVersion(version);
        // Debug
        if (logger.isDebugEnabled()) {
            logger.debug(method.getDeclaringClass().getName());
            logger.debug(method.getName());
            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                logger.debug(method.getParameterTypes()[i].getName());
            }
            for (int i = 0; i < args.length; ++i) {
                logger.debug(args[i].toString());
            }
        }

        String serviceKey = ServiceUtil.makeServiceKey(method.getDeclaringClass().getName(), version);
        RpcClientHandler handler = ConnectionManager.getInstance().chooseHandler(serviceKey);
        // sendRequest 阻塞方法
        RpcFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture.get();
    }

    /**
     * @param funcName
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public RpcFuture call(String funcName, Object... args) throws Exception {
        String serviceKey = ServiceUtil.makeServiceKey(this.clazz.getName(), version);
        RpcClientHandler handler = ConnectionManager.getInstance().chooseHandler(serviceKey);
        RpcRequest request = createRequest(this.clazz.getName(), funcName, args);
        return handler.sendRequest(request);
    }

    /**
     * @param tSerializableFunction
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public RpcFuture call(SerializableFunction<T> tSerializableFunction, Object... args) throws Exception {
        String serviceKey = ServiceUtil.makeServiceKey(this.clazz.getName(), version);
        RpcClientHandler handler = ConnectionManager.getInstance().chooseHandler(serviceKey);
        RpcRequest request = createRequest(this.clazz.getName(), tSerializableFunction.getName(), args);
        return handler.sendRequest(request);
    }

    private RpcRequest createRequest(String className, String methodName, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);
        request.setVersion(version);
        Class[] parameterTypes = new Class[args.length];
        // Get the right class type
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = getClassType(args[i]);
        }
        request.setParameterTypes(parameterTypes);

        // Debug
        if (logger.isDebugEnabled()) {
            logger.debug(className);
            logger.debug(methodName);
            for (int i = 0; i < parameterTypes.length; ++i) {
                logger.debug(parameterTypes[i].getName());
            }
            for (int i = 0; i < args.length; ++i) {
                logger.debug(args[i].toString());
            }
        }

        return request;
    }

    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
//        String typeName = classType.getName();
//        switch (typeName) {
//            case "java.lang.Integer":
//                return Integer.TYPE;
//            case "java.lang.Long":
//                return Long.TYPE;
//            case "java.lang.Float":
//                return Float.TYPE;
//            case "java.lang.Double":
//                return Double.TYPE;
//            case "java.lang.Character":
//                return Character.TYPE;
//            case "java.lang.Boolean":
//                return Boolean.TYPE;
//            case "java.lang.Short":
//                return Short.TYPE;
//            case "java.lang.Byte":
//                return Byte.TYPE;
//        }
        return classType;
    }

}
