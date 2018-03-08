/**
 * Suite SDK Integration library for Protex API
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
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
 */
package com.blackducksoftware.integration.protex.sdk.api;

import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.common.GetBehavior;
import com.blackducksoftware.sdk.protex.license.GlobalLicense;
import com.blackducksoftware.sdk.protex.license.GlobalLicenseRequest;
import com.blackducksoftware.sdk.protex.license.LicenseApi;
import com.blackducksoftware.sdk.protex.license.LicenseInfo;
import com.blackducksoftware.sdk.protex.license.LicenseInfoPageFilter;
import com.blackducksoftware.sdk.protex.license.LicenseOriginType;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligation;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationRequest;

public class LicenseApiWeaved extends WeavedApi<LicenseApi> implements LicenseApi {

    public LicenseApiWeaved(LicenseApi api) {
        super(api);
    }

    @Override
    public String addLicenseObligation(String arg0,
            AssignedObligationRequest arg1) throws SdkFault {
        return getApi().addLicenseObligation(arg0, arg1);
    }

    @Override
    public String addLicenseObligationUsingReference(String arg0,
            String arg1, Boolean arg2,
            Boolean arg3) throws SdkFault {
        return getApi().addLicenseObligationUsingReference(arg0, arg1, arg2, arg3);
    }

    @Override
    public String cloneLicense(String arg0,
            String arg1) throws SdkFault {
        return getApi().cloneLicense(arg0, arg1);
    }

    @Override
    public String createLicense(GlobalLicenseRequest arg0) throws SdkFault {
        return getApi().createLicense(arg0);
    }

    @Override
    public void deleteLicense(String arg0) throws SdkFault {
        getApi().deleteLicense(arg0);
    }

    @Override
    public GlobalLicense getLicenseById(String arg0) throws SdkFault {
        return getApi().getLicenseById(arg0);
    }

    @Override
    public GlobalLicense getLicenseByName(String arg0) throws SdkFault {
        return getApi().getLicenseByName(arg0);
    }

    @Override
    public List<AssignedObligation> getLicenseObligations(String arg0) throws SdkFault {
        return getApi().getLicenseObligations(arg0);
    }

    @Override
    public List<LicenseInfo> getLicenses(List<LicenseOriginType> arg0,
            LicenseInfoPageFilter arg1) throws SdkFault {
        return getApi().getLicenses(arg0, arg1);
    }

    @Override
    public void removeLicenseObligation(String arg0,
            String arg1) throws SdkFault {
        getApi().removeLicenseObligation(arg0, arg1);
    }

    @Override
    public void resetLicense(String arg0) throws SdkFault {
        getApi().resetLicense(arg0);
    }

    @Override
    public List<LicenseInfo> suggestLicenses(String arg0,
            List<LicenseOriginType> arg1,
            LicenseInfoPageFilter arg2) throws SdkFault {
        return getApi().suggestLicenses(arg0, arg1, arg2);
    }

    @Override
    public void addTag(String arg0, String arg1, String arg2) throws SdkFault {
        getApi().addTag(arg0, arg1, arg2);
    }

    @Override
    public List<GlobalLicense> getLicensesById(List<String> arg0, GetBehavior arg1) throws SdkFault {
        return getApi().getLicensesById(arg0, arg1);
    }

    @Override
    public List<GlobalLicense> getTaggedLicenses(String arg0, String arg1) throws SdkFault {
        return getApi().getTaggedLicenses(arg0, arg1);
    }

    @Override
    public List<String> getTags(String arg0, String arg1) throws SdkFault {
        return getApi().getTags(arg0, arg1);
    }

    @Override
    public void removeTag(String arg0, String arg1, String arg2) throws SdkFault {
        getApi().removeTag(arg0, arg1, arg2);
    }

    @Override
    public void updateLicense(String arg0, GlobalLicenseRequest arg1) throws SdkFault {
        getApi().updateLicense(arg0, arg1);
    }

    @Override
    public void updateLicenseObligation(String arg0, String arg1, AssignedObligationRequest arg2) throws SdkFault {
        getApi().updateLicenseObligation(arg0, arg1, arg2);
    }

}
