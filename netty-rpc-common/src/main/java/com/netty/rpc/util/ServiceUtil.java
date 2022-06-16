package com.netty.rpc.util;

public class ServiceUtil {
    public static final String SERVICE_CONCAT_TOKEN = "#";

    /**
     *
     * @param interfaceName 接口
     * @param version 版本号
     * @return 接口名+版本号
     */
    public static String makeServiceKey(String interfaceName, String version) {
        String serviceKey = interfaceName;
        if (version != null && version.trim().length() > 0) {
            serviceKey += SERVICE_CONCAT_TOKEN.concat(version);
        }
        return serviceKey;
    }
}
