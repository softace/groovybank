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

    private void mounteBankIsRunning() {
        mountebank = ['mb', 'start'].execute()
        sleep 1000 //TODO: Could be done nicer based on stdout matching /now taking orders/
    }

    private void stopMountebank() {
        mountebank.waitForOrKill(1)
    }

}
