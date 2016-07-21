/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.blackducksoftware.integration.protex.sdk.api;

import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.common.ComponentKey;
import com.blackducksoftware.sdk.protex.common.ComponentPageFilter;
import com.blackducksoftware.sdk.protex.common.DownloadHashType;
import com.blackducksoftware.sdk.protex.component.Component;
import com.blackducksoftware.sdk.protex.component.ComponentApi;
import com.blackducksoftware.sdk.protex.component.ComponentRequest;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligation;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationRequest;

public class ComponentApiWeaved extends WeavedApi<ComponentApi> implements ComponentApi {

    public ComponentApiWeaved(ComponentApi api) {
        super(api);
    }

    @Override
    public String addComponentObligation(String arg0, AssignedObligationRequest arg1) throws SdkFault {
        return getApi().addComponentObligation(arg0, arg1);
    }

    @Override
    public String addComponentObligationUsingReference(String arg0, String arg1, Boolean arg2, Boolean arg3) throws SdkFault {
        return getApi().addComponentObligationUsingReference(arg0, arg1, arg2, arg3);
    }

    @Override
    public void addTag(String arg0, String arg1, String arg2) throws SdkFault {
        getApi().addTag(arg0, arg1, arg2);
    }

    @Override
    public List<ComponentKey> checkComponentsExist(List<ComponentKey> arg0) throws SdkFault {
        return getApi().checkComponentsExist(arg0);
    }

    @Override
    public ComponentKey createComponent(ComponentRequest arg0) throws SdkFault {
        return getApi().createComponent(arg0);
    }

    @Override
    public void deleteComponent(ComponentKey arg0) throws SdkFault {
        getApi().deleteComponent(arg0);
    }

    @Override
    public Component getComponentByKey(ComponentKey arg0) throws SdkFault {
        return getApi().getComponentByKey(arg0);
    }

    @Override
    public List<AssignedObligation> getComponentObligations(String arg0) throws SdkFault {
        return getApi().getComponentObligations(arg0);
    }

    @Override
    public List<Component> getComponentVersions(String arg0) throws SdkFault {
        return getApi().getComponentVersions(arg0);
    }

    @Override
    public List<Component> getComponents(ComponentPageFilter arg0) throws SdkFault {
        return getApi().getComponents(arg0);
    }

    @Override
    public List<Component> getComponentsByKey(List<ComponentKey> arg0) throws SdkFault {
        return getApi().getComponentsByKey(arg0);
    }

    @Override
    public List<Component> getComponentsByName(String arg0, String arg1) throws SdkFault {
        return getApi().getComponentsByName(arg0, arg1);
    }

    @Override
    public List<Component> getTaggedComponents(String arg0, String arg1) throws SdkFault {
        return getApi().getTaggedComponents(arg0, arg1);
    }

    @Override
    public List<String> getTags(String arg0, String arg1) throws SdkFault {
        return getApi().getTags(arg0, arg1);
    }

    @Override
    public void removeComponentObligation(String arg0, String arg1) throws SdkFault {
        getApi().removeComponentObligation(arg0, arg1);
    }

    @Override
    public void removeTag(String arg0, String arg1, String arg2) throws SdkFault {
        getApi().removeTag(arg0, arg1, arg2);
    }

    @Override
    public void resetComponent(ComponentKey arg0) throws SdkFault {
        getApi().resetComponent(arg0);
    }

    @Override
    public List<Component> suggestComponents(String arg0, ComponentPageFilter arg1) throws SdkFault {
        return getApi().suggestComponents(arg0, arg1);
    }

    @Override
    public void updateComponent(ComponentKey arg0, ComponentRequest arg1) throws SdkFault {
        getApi().updateComponent(arg0, arg1);
    }

    @Override
    public void updateComponentObligation(String arg0, String arg1, AssignedObligationRequest arg2) throws SdkFault {
        getApi().updateComponentObligation(arg0, arg1, arg2);
    }

    @Override
    public List<Component> getComponentsByHash(DownloadHashType arg0,
            String arg1) throws SdkFault {
        return getApi().getComponentsByHash(arg0, arg1);
    }

    @Override
    public List<String> getHashesByComponent(DownloadHashType arg0,
            ComponentKey arg1) throws SdkFault {
        return getApi().getHashesByComponent(arg0, arg1);
    }

}
