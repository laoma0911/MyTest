package com.medlinker.dentist.provider.util;

import org.apache.curator.framework.CuratorFramework;

public final class SessionCuratorClient {
    
    private static ThreadLocal<CuratorFramework> curatorClient = new ThreadLocal<CuratorFramework>();
    
    private SessionCuratorClient() {
    }
    
    public static CuratorFramework getCuratorClient() {
        return curatorClient.get();
    }
    
    public static void setCuratorClient(final CuratorFramework client) {
        curatorClient.set(client);
    }
    
    public static void clear() {
        curatorClient.remove();
    }
}
