/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.core.filter;

import io.servicecomb.core.CseContext;
import io.servicecomb.core.Endpoint;
import io.servicecomb.core.Invocation;
import io.servicecomb.core.Transport;
import io.servicecomb.serviceregistry.api.registry.MicroserviceInstance;
import io.servicecomb.serviceregistry.discovery.AbstractEndpointDiscoveryFilter;
import io.servicecomb.serviceregistry.discovery.DiscoveryContext;
import io.servicecomb.serviceregistry.discovery.DiscoveryTreeNode;

public class EndpointDiscoveryFilter extends AbstractEndpointDiscoveryFilter {
  @Override
  public int getOrder() {
    return (int) Short.MAX_VALUE;
  }

  @Override
  protected String findTransportName(DiscoveryContext context, DiscoveryTreeNode parent) {
    Invocation invocation = context.getInputParameters();
    return invocation.getConfigTransportName();
  }

  @Override
  protected Object createEndpoint(String transportName, String endpoint, MicroserviceInstance instance) {
    Transport transport = CseContext.getInstance().getTransportManager().findTransport(transportName);
    if (transport == null) {
      return null;
    }

    return new Endpoint(transport, endpoint, instance);
  }
}
