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

import java.util.Date;
import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.common.BomRefreshMode;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNode;
import com.blackducksoftware.sdk.protex.project.codetree.identification.AuditedEvent;
import com.blackducksoftware.sdk.protex.project.codetree.identification.CodeMatchExclusion;
import com.blackducksoftware.sdk.protex.project.codetree.identification.CodeMatchExclusionRequest;
import com.blackducksoftware.sdk.protex.project.codetree.identification.CodeMatchIdentification;
import com.blackducksoftware.sdk.protex.project.codetree.identification.CodeMatchIdentificationRequest;
import com.blackducksoftware.sdk.protex.project.codetree.identification.CodeTreeIdentificationInfo;
import com.blackducksoftware.sdk.protex.project.codetree.identification.DeclaredIdentification;
import com.blackducksoftware.sdk.protex.project.codetree.identification.DependencyIdentification;
import com.blackducksoftware.sdk.protex.project.codetree.identification.DependencyIdentificationRequest;
import com.blackducksoftware.sdk.protex.project.codetree.identification.Identification;
import com.blackducksoftware.sdk.protex.project.codetree.identification.IdentificationApi;
import com.blackducksoftware.sdk.protex.project.codetree.identification.IdentificationRequest;
import com.blackducksoftware.sdk.protex.project.codetree.identification.RapidIdentificationRefreshMode;
import com.blackducksoftware.sdk.protex.project.codetree.identification.StringSearchIdentification;
import com.blackducksoftware.sdk.protex.project.codetree.identification.StringSearchIdentificationRequest;

public class IdentificationApiWeaved extends WeavedApi<IdentificationApi> implements IdentificationApi {

    public IdentificationApiWeaved(final IdentificationApi api) {
        super(api);
    }

    @Override
    public void addCodeMatchExclusion(final String arg0, final String arg1, final CodeMatchExclusionRequest arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().addCodeMatchExclusion(arg0, arg1, arg2, arg3);
    }

    @Override
    public void addCodeMatchIdentification(final String arg0, final String arg1, final CodeMatchIdentificationRequest arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().addCodeMatchIdentification(arg0, arg1, arg2, arg3);
    }

    @Override
    public void addDependencyIdentification(final String arg0, final String arg1, final DependencyIdentificationRequest arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().addDependencyIdentification(arg0, arg1, arg2, arg3);
    }

    @Override
    public void addStringSearchIdentification(final String arg0, final String arg1, final StringSearchIdentificationRequest arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().addStringSearchIdentification(arg0, arg1, arg2, arg3);
    }

    @Override
    public void copyIdentifications(final String arg0, final String arg1, final String arg2, final String arg3, final Boolean arg4, final Boolean arg5, final BomRefreshMode arg6) throws SdkFault {
        getApi().copyIdentifications(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    @Override
    public List<AuditedEvent> getAuditTrail(final String arg0, final Date arg1, final Date arg2) throws SdkFault {

        return getApi().getAuditTrail(arg0, arg1, arg2);
    }

    @Override
    public String getIdentifyBomURL(final String arg0, final String arg1) throws SdkFault {

        return getApi().getIdentifyBomURL(arg0, arg1);
    }

    @Override
    public String getIdentifyCodeMatchesURL(final String arg0, final String arg1) throws SdkFault {

        return getApi().getIdentifyCodeMatchesURL(arg0, arg1);
    }

    @Override
    public String getIdentifyDependenciesURL(final String arg0, final String arg1) throws SdkFault {

        return getApi().getIdentifyDependenciesURL(arg0, arg1);
    }

    @Override
    public String getIdentifyStringSearchesURL(final String arg0, final String arg1) throws SdkFault {

        return getApi().getIdentifyStringSearchesURL(arg0, arg1);
    }

    @Override
    public Long learnIdentification(final String arg0, final Identification arg1) throws SdkFault {

        return getApi().learnIdentification(arg0, arg1);
    }

    @Override
    public void removeCodeMatchExclusion(final String arg0, final CodeMatchExclusion arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().removeCodeMatchExclusion(arg0, arg1, arg2);
    }

    @Override
    public void removeCodeMatchIdentification(final String arg0, final CodeMatchIdentification arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().removeCodeMatchIdentification(arg0, arg1, arg2);
    }

    @Override
    public void removeDeclaredIdentification(final String arg0, final DeclaredIdentification arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().removeDeclaredIdentification(arg0, arg1, arg2);
    }

    @Override
    public void removeDependencyIdentification(final String arg0, final DependencyIdentification arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().removeDependencyIdentification(arg0, arg1, arg2);
    }

    @Override
    public void removeStringSearchIdentification(final String arg0, final StringSearchIdentification arg1, final BomRefreshMode arg2) throws SdkFault {
        getApi().removeStringSearchIdentification(arg0, arg1, arg2);
    }

    @Override
    public void runRapidId(final String arg0, final String arg1, final RapidIdentificationRefreshMode arg2) throws SdkFault {
        getApi().runRapidId(arg0, arg1, arg2);
    }

    @Override
    public void addDeclaredIdentification(final String arg0, final String arg1, final IdentificationRequest arg2, final BomRefreshMode arg3) throws SdkFault {
        getApi().addDeclaredIdentification(arg0, arg1, arg2, arg3);
    }

    @Override
    public List<CodeTreeIdentificationInfo> getAppliedIdentifications(final String arg0, final List<CodeTreeNode> arg1) throws SdkFault {
        return getApi().getAppliedIdentifications(arg0, arg1);
    }

    @Override
    public List<CodeTreeIdentificationInfo> getEffectiveIdentifications(final String arg0, final List<CodeTreeNode> arg1) throws SdkFault {
        return getApi().getEffectiveIdentifications(arg0, arg1);
    }

    @Override
    public void reevaluateRapidIDMatches(final String arg0, final String arg1, final RapidIdentificationRefreshMode arg2) throws SdkFault {
        getApi().reevaluateRapidIDMatches(arg0, arg1, arg2);
    }

    @Override
    public void convertRapidIdentificationsToManual(final String arg0, final RapidIdentificationRefreshMode arg1) throws SdkFault {
        getApi().convertRapidIdentificationsToManual(arg0, arg1);
    }

    @Override
    public void copyEffectiveIdentifications(final String arg0, final String arg1, final String arg2, final String arg3, final Boolean arg4, final Boolean arg5, final BomRefreshMode arg6) throws SdkFault {
        getApi().copyEffectiveIdentifications(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

}
