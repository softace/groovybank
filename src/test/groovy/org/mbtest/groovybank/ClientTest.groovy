package org.mbtest.groovybank

import spock.lang.Specification


class ClientTest extends Specification {
    Process mountebank

    def "Can verify Mountebank is running"() {
        given:
        mounteBankIsRunning()
        when:
        Client client = new Client()
        then:
        client.isMountebankRunning()
        cleanup:
        stopMountebank()

    }

    def "Can verify Mountebank is not running"() {
        when:
        Client client = new Client()
        then:
        !client.isMountebankRunning()
    }

    def "Can create imposter"() {
        given:
        mounteBankIsRunning()
        Client stub = new Client()
        when:
        stub.http(4545).responds(statusCode: 200, headers: ['Content-Type': "application/xml"], body: "Hello").end()
        then:
        new URL('http://localhost:4545/').getText() == 'Hello'
        cleanup:
        stopMountebank()
    }

    private void mounteBankIsRunning() {
        mountebank = ['mb', 'start'].execute()
        sleep 1000 // TODO: Could be done nicer based on stdout matching /now taking orders/
    }

    private void stopMountebank() {
        mountebank.waitForOrKill(1)
    }

}
