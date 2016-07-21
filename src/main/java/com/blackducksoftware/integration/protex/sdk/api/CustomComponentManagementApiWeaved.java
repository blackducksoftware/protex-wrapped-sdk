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
import com.blackducksoftware.sdk.protex.common.AnalysisStatus;
import com.blackducksoftware.sdk.protex.common.ComponentKey;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPattern;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternPageFilter;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternRequest;
import com.blackducksoftware.sdk.protex.common.PatternOriginType;
import com.blackducksoftware.sdk.protex.component.custom.CustomComponentManagementApi;
import com.blackducksoftware.sdk.protex.component.custom.CustomComponentSettings;

public class CustomComponentManagementApiWeaved extends WeavedApi<CustomComponentManagementApi> implements CustomComponentManagementApi {

    public CustomComponentManagementApiWeaved(CustomComponentManagementApi api) {
        super(api);
    }

    @Override
    public String addFileDiscoveryPattern(ComponentKey arg0, FileDiscoveryPatternRequest arg1) throws SdkFault {
        return getApi().addFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public void cancelCodeprinting(ComponentKey arg0) throws SdkFault {
        getApi().cancelCodeprinting(arg0);
    }

    @Override
    public AnalysisStatus getCodeprintingStatus(ComponentKey arg0) throws SdkFault {
        return getApi().getCodeprintingStatus(arg0);
    }

    @Override
    public CustomComponentSettings getCustomComponentSetting(ComponentKey arg0) throws SdkFault {
        return getApi().getCustomComponentSetting(arg0);
    }

    @Override
    public List<CustomComponentSettings> getCustomComponentSettings(List<ComponentKey> arg0) throws SdkFault {
        return getApi().getCustomComponentSettings(arg0);
    }

    @Override
    public List<FileDiscoveryPattern> getFileDiscoveryPatterns(ComponentKey arg0, List<PatternOriginType> arg1, FileDiscoveryPatternPageFilter arg2)
            throws SdkFault {
        return getApi().getFileDiscoveryPatterns(arg0, arg1, arg2);
    }

    @Override
    public void removeFileDiscoveryPattern(ComponentKey arg0, String arg1) throws SdkFault {
        getApi().removeFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public void resetFileDiscoveryPattern(ComponentKey arg0, String arg1) throws SdkFault {
        getApi().resetFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public void startCodeprinting(ComponentKey arg0, Boolean arg1) throws SdkFault {
        getApi().startCodeprinting(arg0, arg1);
    }

    @Override
    public void updateCustomComponentSettings(List<CustomComponentSettings> arg0) throws SdkFault {
        getApi().updateCustomComponentSettings(arg0);
    }

    @Override
    public void updateFileDiscoveryPattern(ComponentKey arg0, String arg1, FileDiscoveryPatternRequest arg2) throws SdkFault {
        getApi().updateFileDiscoveryPattern(arg0, arg1, arg2);
    }

}
