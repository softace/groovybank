package org.mbtest.groovybank

import groovy.json.JsonOutput
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*

class Client {
    static final String DEFAULT_BASE_URL = "http://localhost:2525";

    String baseUrl
    private mbtest = new RESTClient(baseUrl)

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
            return mbtest.get(path: '/').statusLine.statusCode == 200
        } catch (Exception) {
            return false
        }
    }

    Imposter http(port = 4545) {
        return new Imposter(client: this, port: port,
                protocol: 'http')
    }

    void createImposter(Imposter imposter) {
        def statusLine = mbtest.post(path: '/imposters', requestContentType: JSON,
                body: JsonOutput.toJson(imposter.buildRequest())).statusLine
        if(statusLine.statusCode != 201){
            throw new RuntimeException("Error: $statusLine")
        }
    }
}

