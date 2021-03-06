/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.orca.deploymentmonitor;

import com.netflix.spinnaker.config.MonitoredDeployConfigurationProperties;
import com.netflix.spinnaker.orca.deploymentmonitor.models.DeploymentMonitorDefinition;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DeploymentMonitorCapabilities {
  private MonitoredDeployConfigurationProperties monitoredDeployConfigurationProperties;

  public DeploymentMonitorCapabilities(
      Optional<MonitoredDeployConfigurationProperties> monitoredDeployConfigurationProperties) {
    this.monitoredDeployConfigurationProperties =
        monitoredDeployConfigurationProperties.orElse(null);
  }

  public List<DeploymentMonitorDefinition> getDeploymentMonitors() {
    if (monitoredDeployConfigurationProperties == null) {
      return Collections.emptyList();
    }

    List<com.netflix.spinnaker.config.DeploymentMonitorDefinition> registeredMonitors =
        monitoredDeployConfigurationProperties.getDeploymentMonitors();
    if (registeredMonitors == null) {
      return Collections.emptyList();
    }

    return registeredMonitors.stream()
        .map(DeploymentMonitorDefinition::new)
        .collect(Collectors.toList());
  }
}
