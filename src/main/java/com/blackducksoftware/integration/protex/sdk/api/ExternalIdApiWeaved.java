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

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.policy.externalid.ExternalIdApi;
import com.blackducksoftware.sdk.protex.policy.externalid.ExternalIdMapping;
import com.blackducksoftware.sdk.protex.policy.externalid.ExternalNamespace;
import com.blackducksoftware.sdk.protex.policy.externalid.ExternalNamespaceRequest;
import com.blackducksoftware.sdk.protex.policy.externalid.ProtexObjectKey;
import com.blackducksoftware.sdk.protex.policy.externalid.ProtexObjectType;

public class ExternalIdApiWeaved extends WeavedApi<ExternalIdApi> implements ExternalIdApi {

	public ExternalIdApiWeaved(final ExternalIdApi api) {
		super(api);
	}

	@Override
	public void createExternalIdMapping(final String arg0,
			final ExternalIdMapping arg1) throws SdkFault {
		getApi().createExternalIdMapping(arg0, arg1);
	}

	@Override
	public void createExternalNamespace(final ExternalNamespaceRequest arg0) throws SdkFault {
		getApi().createExternalNamespace(arg0);
	}

	@Override
	public void deleteExternalIdMapping(final String arg0,
			final String arg1, final ProtexObjectType arg2)
					throws SdkFault {
		getApi().deleteExternalIdMapping(arg0, arg1, arg2);
	}

	@Override
	public void deleteExternalNamespace(final String arg0) throws SdkFault {
		getApi().deleteExternalNamespace(arg0);
	}

	@Override
	public ExternalNamespace getExternalNamespace(final String arg0) throws SdkFault {
		return getApi().getExternalNamespace(arg0);
	}

	@Override
	public ProtexObjectKey getObjectIdByExternalId(final String arg0,
			final String arg1, final ProtexObjectType arg2)
					throws SdkFault {
		return getApi().getObjectIdByExternalId(arg0, arg1, arg2);
	}

	@Override
	public void updateExternalNamespace(final ExternalNamespaceRequest arg0) throws SdkFault {
		getApi().updateExternalNamespace(arg0);
	}

	@Override
	public ProtexObjectKey getObjectAndVersionIdByExternalId(final String arg0, final String arg1,
			final ProtexObjectType arg2) throws SdkFault {
		return getApi().getObjectAndVersionIdByExternalId(arg0, arg1, arg2);
	}

}
