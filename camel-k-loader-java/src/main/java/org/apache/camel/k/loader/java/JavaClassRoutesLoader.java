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
package org.apache.camel.k.loader.java;

import java.util.Collections;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.k.RoutesLoader;
import org.apache.camel.k.Source;
import org.apache.commons.lang3.StringUtils;

public class JavaClassRoutesLoader implements RoutesLoader {
    @Override
    public List<String> getSupportedLanguages() {
        return Collections.singletonList("class");
    }

    @Override
    public RouteBuilder load(CamelContext camelContext, Source source) throws Exception {
        String name = source.getName();
        name = StringUtils.removeEnd(name, ".class");

        Class<?> type = Class.forName(name);

        if (!RouteBuilder.class.isAssignableFrom(type)) {
            throw new IllegalStateException("The class provided (" + source.getLocation() + ") is not a org.apache.camel.builder.RouteBuilder");
        }

        return (RouteBuilder)type.newInstance();
    }
}
