/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.k.loader.groovy

import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.k.Source
import org.apache.camel.k.support.RuntimeSupport
import org.apache.camel.model.ToDefinition
import spock.lang.Specification

class LoaderTest extends Specification {

    def "load route from classpath"() {
        given:
            def context = new DefaultCamelContext()
            def source = Source.create("classpath:routes.groovy")

        when:
            def loader = RuntimeSupport.loaderFor(context, source)
            def builder = loader.load(context, source)

        then:
            loader instanceof GroovyRoutesLoader
            builder != null

            builder.setContext(context)
            builder.configure()

            def routes = builder.routeCollection.routes

            routes.size() == 1
            routes[0].outputs[0] instanceof ToDefinition
            routes[0].input.endpointUri == 'timer:tick'
    }
}
