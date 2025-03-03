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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.k.RoutesLoader;
import org.apache.camel.k.Source;
import org.apache.camel.k.support.URIResolver;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joor.Reflect;

public class JavaSourceRoutesLoader implements RoutesLoader {
    @Override
    public List<String> getSupportedLanguages() {
        return Collections.singletonList("java");
    }

    @Override
    public RouteBuilder load(CamelContext camelContext, Source source) throws Exception {
        try (InputStream is = URIResolver.resolve(camelContext, source)) {
            final String content = IOUtils.toString(is, StandardCharsets.UTF_8);
            final String name = determineQualifiedName(source, content);
            final Reflect compiled = Reflect.compile(name, content);

            RouteBuilder rb =  compiled.create().get();
            return rb;
        }
    }

    private static String determineQualifiedName(Source source, String content) throws Exception {
        String name = source.getName();
        name = StringUtils.removeEnd(name, ".java");

        Pattern pattern = Pattern.compile("^\\s*package\\s+([a-zA_Z_][\\.\\w]*)\\s*;.*");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            name = matcher.group(1) + "." + name;
        }

        return name;
    }
}
