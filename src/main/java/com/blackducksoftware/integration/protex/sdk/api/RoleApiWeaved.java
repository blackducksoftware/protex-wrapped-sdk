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
import com.blackducksoftware.sdk.protex.role.Role;
import com.blackducksoftware.sdk.protex.role.RoleApi;
import com.blackducksoftware.sdk.protex.role.RoleName;
import com.blackducksoftware.sdk.protex.role.UserRoleInfo;
import com.blackducksoftware.sdk.protex.role.UserRoleInfoPageFilter;

public class RoleApiWeaved extends WeavedApi<RoleApi> implements RoleApi {

	public RoleApiWeaved(final RoleApi api) {
		super(api);
	}

	@Override
	public Role getRoleByName(final RoleName arg0) throws SdkFault {
		return getApi().getRoleByName(arg0);
	}

	@Override
	public void addUserRoles(final String arg0, final List<String> arg1) throws SdkFault {
		getApi().addUserRoles(arg0, arg1);
	}

	@Override
	public List<UserRoleInfo> getUserRoleInfos(final UserRoleInfoPageFilter arg0) throws SdkFault {
		return getApi().getUserRoleInfos(arg0);
	}

	@Override
	public List<UserRoleInfo> getUserRoles(final String arg0) throws SdkFault {
		return getApi().getUserRoles(arg0);
	}

	@Override
	public void removeUserRoles(final String arg0, final List<String> arg1) throws SdkFault {
		getApi().removeUserRoles(arg0, arg1);
	}

}
