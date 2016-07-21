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
import com.blackducksoftware.sdk.protex.common.GetBehavior;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationPageFilter;
import com.blackducksoftware.sdk.protex.obligation.Obligation;
import com.blackducksoftware.sdk.protex.obligation.ObligationApi;
import com.blackducksoftware.sdk.protex.obligation.ObligationCategory;
import com.blackducksoftware.sdk.protex.obligation.ObligationCategoryRequest;
import com.blackducksoftware.sdk.protex.obligation.ObligationPageFilter;
import com.blackducksoftware.sdk.protex.obligation.ObligationRequest;
import com.blackducksoftware.sdk.protex.obligation.ObligationsAssigned;
import com.blackducksoftware.sdk.protex.obligation.ProtexElementWithObligations;

public class ObligationApiWeaved extends WeavedApi<ObligationApi> implements ObligationApi {

    public ObligationApiWeaved(ObligationApi api) {
        super(api);
    }

    @Override
    public String createObligation(ObligationRequest arg0) throws SdkFault {
        return getApi().createObligation(arg0);
    }

    @Override
    public String createObligationCategory(ObligationCategoryRequest arg0) throws SdkFault {

        return getApi().createObligationCategory(arg0);
    }

    @Override
    public void deleteObligation(String arg0) throws SdkFault {
        getApi().deleteObligation(arg0);
    }

    @Override
    public void deleteObligationCategory(String arg0) throws SdkFault {
        getApi().deleteObligationCategory(arg0);
    }

    @Override
    public Obligation getObligationById(String arg0) throws SdkFault {

        return getApi().getObligationById(arg0);
    }

    @Override
    public Obligation getObligationByName(String arg0) throws SdkFault {

        return getApi().getObligationByName(arg0);
    }

    @Override
    public ObligationCategory getObligationCategoryById(String arg0) throws SdkFault {

        return getApi().getObligationCategoryById(arg0);
    }

    @Override
    public ObligationCategory getObligationCategoryByName(String arg0) throws SdkFault {

        return getApi().getObligationCategoryByName(arg0);
    }

    @Override
    public List<ObligationCategory> suggestObligationCategories(String arg0) throws SdkFault {

        return getApi().suggestObligationCategories(arg0);
    }

    @Override
    public List<Obligation> suggestObligations(String arg0,
            ObligationPageFilter arg1) throws SdkFault {

        return getApi().suggestObligations(arg0, arg1);
    }

    @Override
    public ObligationsAssigned getAssignedObligationsForElement(ProtexElementWithObligations arg0, AssignedObligationPageFilter arg1) throws SdkFault {
        return getApi().getAssignedObligationsForElement(arg0, arg1);
    }

    @Override
    public List<ObligationsAssigned> getAssignedObligationsForElements(List<ProtexElementWithObligations> arg0, AssignedObligationPageFilter arg1,
            GetBehavior arg2) throws SdkFault {
        return getApi().getAssignedObligationsForElements(arg0, arg1, arg2);
    }

    @Override
    public void updateObligation(String arg0, ObligationRequest arg1) throws SdkFault {
        getApi().updateObligation(arg0, arg1);
    }

    @Override
    public void updateObligationCategory(String arg0, ObligationCategoryRequest arg1) throws SdkFault {
        getApi().updateObligationCategory(arg0, arg1);
    }

}
