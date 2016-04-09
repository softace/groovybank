package org.mbtest.groovybank


class Imposter {

    Client client
    String protocol
    int port
    List<Map> responses = []
    private Map requestMap

    Imposter responds(response) {
        this.responses << response
        return this
    }

    void end() {
        requestMap = buildRequest()
        client.createImposter(this)
    }

    def buildRequest() {
        [port    : port,
         protocol: "http",
         stubs   : [[responses: responses.collect {
             [is: it]
         }]]]
    }
}
