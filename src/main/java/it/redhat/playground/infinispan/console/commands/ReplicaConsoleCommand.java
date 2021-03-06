/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.infinispan.console.commands;

import it.redhat.playground.infinispan.console.support.IllegalParametersException;
import org.infinispan.Cache;
import it.redhat.playground.infinispan.InfinispanHelper;
import it.redhat.playground.infinispan.configuration.PlaygroundConfiguration;
import it.redhat.playground.infinispan.console.UI;
import it.redhat.playground.infinispan.domain.Value;

import java.util.Iterator;
import java.util.Set;

public class ReplicaConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "replica";
    private final Cache<Long, Value> cache;

    public ReplicaConsoleCommand(PlaygroundConfiguration conf) {
        this.cache = conf.getCache();
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        Set<String> replicas = InfinispanHelper.replicaValuesFromKeys(cache);
        for (String key : replicas) {
            console.println(key);
        }
        console.println("Cache Size: " + cache.size() + "\n");
        console.println("Replica Size: " + replicas.size() + "\n");
        return true;
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME);
        console.println("\t\tList all local values for which this node is a replica.");
    }
}
