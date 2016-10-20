package org.jason.lazytire.porter.bean;

/**
 * Created by Jason.Xia on 16/10/20.
 */
public class ConnectConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String original;
    private String target;

    public ConnectConfig() {
    }

    public ConnectConfig(String host, String username, String password, String original, String target) {
        this.host = host;
        this.port = 22;
        this.username = username;
        this.password = password;
        this.original = original;
        this.target = target;
    }

    public ConnectConfig(String host, int port, String username, String password, String original, String target) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.original = original;
        this.target = target;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
