package org.mbtest.groovybank

import groovyx.net.http.RESTClient
/**
 * Created by jarl on 08-04-16.
 */
class Client {
    static final String DEFAULT_BASE_URL = "http://localhost:2525";

    String baseUrl;

    Client() {
        this(DEFAULT_BASE_URL);
    }

    Client(String baseUrl) {
        this.baseUrl = baseUrl
    }

    Client(String host, int port) {
        this.baseUrl = "http://$host:$port"
    }

    boolean isMountebankRunning() {
        try {
            def mbtest = new RESTClient(baseUrl)
            return mbtest.get(path: '/').statusLine.statusCode == 200
        } catch (Exception) {
            return false
        }
    }
}
