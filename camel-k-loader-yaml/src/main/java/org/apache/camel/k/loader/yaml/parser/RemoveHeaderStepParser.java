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
package org.apache.camel.k.loader.yaml.parser;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RemoveHeaderDefinition;

public class RemoveHeaderStepParser implements ProcessorStepParser {
    @Override
    public ProcessorDefinition<?> toProcessor(Context context) {
        return context.node(Definition.class);
    }

    public static final class Definition extends RemoveHeaderDefinition {
        @JsonAlias("name")
        public void setHeaderName(String headerName) {
            super.setHeaderName(headerName);
        }

        @JsonAlias("name")
        public String getHeaderName() {
            return super.getHeaderName();
        }
    }
}

