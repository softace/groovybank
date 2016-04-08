package org.mbtest.groovybank

import spock.lang.Specification


class ClientTest extends Specification {
    Process mountebank

    def "Can create client when mountebank is running"() {
        given:
        mounteBankIsRunning()
        when:
        Client client = new Client()
        then:
        client.isMountebankRunning()
        cleanup:
        stopMountebank()

    }

    def "Client constructor throws when mountebank is not running"() {
        when:
        Client client = new Client()
        then:
        !client.isMountebankRunning()
    }

    private void mounteBankIsRunning() {
        println 'starting'
        mountebank = ['mb', 'start'].execute()
        sleep 1000
//
//        def reader = new BufferedReader(new InputStreamReader(mountebank.getInputStream()))
//        def line
//        while ((line = reader.readLine()) != null) {
//            if (line =~ /now taking orders/){
//                break
//            }
//        }
        println "started  $mountebank"
    }

    private void stopMountebank() {
        println 'stoping'
        mountebank.waitForOrKill(1)
        println 'stoped'
    }

}
