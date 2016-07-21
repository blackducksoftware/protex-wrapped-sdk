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
import com.blackducksoftware.sdk.protex.common.StringSearchPatternOriginType;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNode;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.AnalysisCodeTreeInfo;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.AnalysisEnvironmentInfo;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.AnalysisInfo;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.CodeMatchDiscovery;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.CodeMatchType;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.DependencyDiscovery;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.DiscoveryApi;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.DiscoverySourceInfo;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.DiscoveryType;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.FileDiscoveryPatternDiscovery;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.StringSearchDiscovery;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.StringSearchDiscoveryWithMatches;

public class DiscoveryApiWeaved extends WeavedApi<DiscoveryApi> implements DiscoveryApi {

	public DiscoveryApiWeaved(final DiscoveryApi api) {
		super(api);
	}

	@Override
	public List<DiscoveryType> getDiscoveredDiscoveryTypes(final String arg0) throws SdkFault {

		return getApi().getDiscoveredDiscoveryTypes(arg0);
	}

	@Override
	public AnalysisCodeTreeInfo getLastAnalysisCodeTreeInfo(final String arg0) throws SdkFault {

		return getApi().getLastAnalysisCodeTreeInfo(arg0);
	}

	@Override
	public AnalysisEnvironmentInfo getLastAnalysisEnvironmentInfo(final String arg0) throws SdkFault {

		return getApi().getLastAnalysisEnvironmentInfo(arg0);
	}

	@Override
	public AnalysisInfo getLastAnalysisInfo(final String arg0) throws SdkFault {

		return getApi().getLastAnalysisInfo(arg0);
	}

	@Override
	public StringSearchDiscoveryWithMatches getStringSearchMatches(final String arg0,
			final StringSearchDiscovery arg1,
			final Integer arg2) throws SdkFault {

		return getApi().getStringSearchMatches(arg0, arg1, arg2);
	}

	@Override
	public List<CodeMatchDiscovery> getCodeMatchDiscoveries(final String arg0, final List<CodeTreeNode> arg1, final List<CodeMatchType> arg2) throws SdkFault {
		return getApi().getCodeMatchDiscoveries(arg0, arg1, arg2);
	}

	@Override
	public List<DependencyDiscovery> getDependencyDiscoveries(final String arg0, final List<CodeTreeNode> arg1) throws SdkFault {
		return getApi().getDependencyDiscoveries(arg0, arg1);
	}

	@Override
	public List<FileDiscoveryPatternDiscovery> getFileDiscoveryPatternDiscoveries(final String arg0, final List<CodeTreeNode> arg1) throws SdkFault {
		return getApi().getFileDiscoveryPatternDiscoveries(arg0, arg1);
	}

	@Override
	public List<StringSearchDiscovery> getStringSearchDiscoveries(final String arg0, final List<CodeTreeNode> arg1, final List<StringSearchPatternOriginType> arg2)
			throws SdkFault {
		return getApi().getStringSearchDiscoveries(arg0, arg1, arg2);
	}

	@Override
	public List<DiscoverySourceInfo> getCodeMatchSourceInfo(final String arg0,
			final List<CodeTreeNode> arg1,
			final List<CodeMatchType> arg2) throws SdkFault {
		return getApi().getCodeMatchSourceInfo(arg0, arg1, arg2);
	}

	@Override
	public void deleteRejectedDiscoveries(final String arg0) throws SdkFault {
		getApi().deleteRejectedDiscoveries(arg0);
	}

}
