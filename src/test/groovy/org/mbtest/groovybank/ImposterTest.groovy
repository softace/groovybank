package org.mbtest.groovybank

import groovy.json.JsonSlurper
import spock.lang.Specification

class ImposterTest extends Specification {
    def "buildRequest"() {
        given:

        def sut = new Imposter(client: new Client(), protocol: 'http', port: 4545)
        sut.responds([statusCode: 400, headers: ['Content-Type': "application/xml"], body: "Hello"])
        when:
        def request = sut.buildRequest()
        then:
        request == new JsonSlurper().parseText('''
{
    "port": 4545,
    "protocol": "http",
    "stubs": [
        {
            "responses": [
                {
                    "is": {
                        "statusCode": 400,
                        "headers": {
                            "Content-Type": "application/xml"
                        },
                        "body": "Hello"
                    }
                }
            ]
        }
    ]
}
''')
    }
}
