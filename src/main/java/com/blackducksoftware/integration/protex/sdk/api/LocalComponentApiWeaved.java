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
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponent;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentApi;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentPageFilter;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentRequest;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalLicenseRequest;

public class LocalComponentApiWeaved extends WeavedApi<LocalComponentApi> implements LocalComponentApi {

    public LocalComponentApiWeaved(LocalComponentApi api) {
        super(api);
    }

    @Override
    public LocalComponent getLocalComponentByName(String arg0,
            String arg1) throws SdkFault {
        return getApi().getLocalComponentByName(arg0, arg1);
    }

    @Override
    public List<LocalComponent> getLocalComponents(String arg0,
            LocalComponentPageFilter arg1) throws SdkFault {
        return getApi().getLocalComponents(arg0, arg1);
    }

    @Override
    public ComponentKey createLocalComponent(String arg0, LocalComponentRequest arg1, LocalLicenseRequest arg2) throws SdkFault {
        return getApi().createLocalComponent(arg0, arg1, arg2);
    }

    @Override
    public LocalComponent getLocalComponentByKey(String arg0, ComponentKey arg1) throws SdkFault {
        return getApi().getLocalComponentByKey(arg0, arg1);
    }

}
