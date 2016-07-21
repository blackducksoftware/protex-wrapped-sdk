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
import com.blackducksoftware.sdk.protex.common.FileComparisonRepository;
import com.blackducksoftware.sdk.protex.comparison.ComparisonType;
import com.blackducksoftware.sdk.protex.comparison.FileComparisonApi;
import com.blackducksoftware.sdk.protex.comparison.ProtexFile;
import com.blackducksoftware.sdk.protex.comparison.RelatedSnippets;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.CodeMatchDiscovery;

public class FileComparisonApiWeaved extends WeavedApi<FileComparisonApi> implements FileComparisonApi {

	public FileComparisonApiWeaved(final FileComparisonApi api) {
		super(api);
	}

	@Override
	public List<RelatedSnippets> getFileDifferences(final ProtexFile arg0,
			final ProtexFile arg1) throws SdkFault {
		return getApi().getFileDifferences(arg0, arg1);
	}

	@Override
	public List<RelatedSnippets> getFileSimilarities(final ProtexFile arg0,
			final ProtexFile arg1) throws SdkFault {
		return getApi().getFileSimilarities(arg0, arg1);
	}

	@Override
	public String getFileUrl(final ProtexFile arg0) throws SdkFault {
		return getApi().getFileUrl(arg0);
	}

	@Override
	public FileComparisonRepository getFileComparisonRepository(final String arg0) throws SdkFault {
		return getApi().getFileComparisonRepository(arg0);
	}

	@Override
	public void setFileComparisonRepository(final String arg0, final FileComparisonRepository arg1) throws SdkFault {
		getApi().setFileComparisonRepository(arg0, arg1);
	}

	@Override
	public List<RelatedSnippets> compareFiles(final String arg0, final CodeMatchDiscovery arg1,
			final ComparisonType arg2) throws SdkFault {
		return getApi().compareFiles(arg0, arg1, arg2);
	}

}
