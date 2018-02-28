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

import java.util.Date;
import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.common.BomRefreshMode;
import com.blackducksoftware.sdk.protex.common.ComponentKey;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligation;
import com.blackducksoftware.sdk.protex.project.bom.BomApi;
import com.blackducksoftware.sdk.protex.project.bom.BomComponent;
import com.blackducksoftware.sdk.protex.project.bom.BomComponentRelationship;
import com.blackducksoftware.sdk.protex.project.bom.BomComponentRequest;
import com.blackducksoftware.sdk.protex.project.bom.BomLicenseInfo;
import com.blackducksoftware.sdk.protex.project.bom.BomProgressStatus;
import com.blackducksoftware.sdk.protex.project.bom.BomShippingCodeInfo;
import com.blackducksoftware.sdk.protex.project.bom.LicenseViolation;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNode;
import com.blackducksoftware.sdk.protex.project.codetree.identification.IdentificationMode;
import com.blackducksoftware.sdk.protex.project.codetree.identification.IdentificationType;

public class BomApiWeaved extends WeavedApi<BomApi> implements BomApi {

    public BomApiWeaved(final BomApi api) {
        super(api);
    }

    @Override
    public void addBomComponent(final String arg0, final BomComponentRequest arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().addBomComponent(arg0, arg1, arg2);
    }

    @Override
    public Integer getBomComponentCount(final String arg0) throws SdkFault {
        return getApi().getBomComponentCount(arg0);
    }

    @Override
    public List<BomComponent> getBomComponents(final String arg0) throws SdkFault {
        return getApi().getBomComponents(arg0);
    }

    @Override
    public List<BomComponent> getBomComponentsForPath(final String arg0, final String arg1) throws SdkFault {
        return getApi().getBomComponentsForPath(arg0, arg1);
    }

    @Override
    public Integer getBomLicenseCount(final String arg0) throws SdkFault {
        return getApi().getBomLicenseCount(arg0);
    }

    @Override
    public List<BomLicenseInfo> getBomLicenseInfo(final String arg0) throws SdkFault {
        return getApi().getBomLicenseInfo(arg0);
    }

    @Override
    public BomShippingCodeInfo getBomShippingCodeInfo(final String arg0) throws SdkFault {
        return getApi().getBomShippingCodeInfo(arg0);
    }

    @Override
    public String getIdentifyBomUrl(final String arg0, final String arg1) throws SdkFault {
        return getApi().getIdentifyBomUrl(arg0, arg1);
    }

    @Override
    public Date getLastBomRefreshFinishDate(final String arg0) throws SdkFault {
        return getApi().getLastBomRefreshFinishDate(arg0);
    }

    @Override
    public BomProgressStatus getRefreshBomProgress(final String arg0) throws SdkFault {
        return getApi().getRefreshBomProgress(arg0);
    }

    @Override
    public void refreshBom(final String arg0, final Boolean arg1, final Boolean arg2) throws SdkFault {
        getApi().refreshBom(arg0, arg1, arg2);
    }

    @Override
    public String repairBomAfterCrash(final String arg0) throws SdkFault {
        return getApi().repairBomAfterCrash(arg0);
    }

    @Override
    public List<BomComponentRelationship> getBomComponentChildren(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getBomComponentChildren(arg0, arg1);
    }

    @Override
    public List<LicenseViolation> getBomComponentLicenseViolatingAttributes(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getBomComponentLicenseViolatingAttributes(arg0, arg1);
    }

    @Override
    public List<AssignedObligation> getBomComponentObligations(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getBomComponentObligations(arg0, arg1);
    }

    @Override
    public List<BomComponentRelationship> getBomComponentParents(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getBomComponentParents(arg0, arg1);
    }

    @Override
    public String getComponentComment(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getComponentComment(arg0, arg1);
    }

    @Override
    public void removeBomComponent(final String arg0, final ComponentKey arg1, final Boolean arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().removeBomComponent(arg0, arg1, arg2, arg3);
    }

    @Override
    public void setBomComponentObligationsStatus(final String arg0, final ComponentKey arg1, final List<String> arg2, final Boolean arg3, final BomRefreshMode arg4) throws SdkFault {
        getApi().setBomComponentObligationsStatus(arg0, arg1, arg2, arg3, arg4);
    }

    @Override
    public void setComponentComment(final String arg0, final ComponentKey arg1, final String arg2) throws SdkFault {
        getApi().setComponentComment(arg0, arg1, arg2);
    }

    @Override
    public void updateBomComponent(final String arg0, final BomComponentRequest arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().updateBomComponent(arg0, arg1, arg2);
    }

    @Override
    public List<CodeTreeNode> getFilesIdentifiedTo(final String arg0, final ComponentKey arg1, final List<IdentificationType> arg2, final List<IdentificationMode> arg3) throws SdkFault {
        return getApi().getFilesIdentifiedTo(arg0, arg1, arg2, arg3);
    }

}
