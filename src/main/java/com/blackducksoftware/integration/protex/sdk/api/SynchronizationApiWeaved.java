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
import com.blackducksoftware.sdk.protex.synchronization.SynchronizationApi;
import com.blackducksoftware.sdk.protex.synchronization.SynchronizedServerInfo;

public class SynchronizationApiWeaved extends WeavedApi<SynchronizationApi> implements SynchronizationApi {

	public SynchronizationApiWeaved(final SynchronizationApi api) {
		super(api);
	}

	@Override
	public boolean isPrimary() throws SdkFault {
		return getApi().isPrimary();
	}

	@Override
	public List<SynchronizedServerInfo> getSecondaryServers() throws SdkFault {
		return getApi().getSecondaryServers();
	}

	@Override
	public boolean isSecondary() throws SdkFault {
		return getApi().isSecondary();
	}

}
