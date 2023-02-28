package com.laiketui.root.utils.tool;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 网络节点是否可用
 * @author wangxian
 */
public class NetworkUtils {
    /**
     * 节点是否在线
     * @param host
     * @param timeOut
     * @return
     */
    public static boolean isHostReachable(String host, Integer timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 节点指定端口是否开启了
     * @param host
     * @param port
     * @return
     */
    public static boolean isHostConnectable(String host, int port,int timeOut) {
        Socket socket = new Socket();
        try {
            socket.setSoTimeout(timeOut);
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
