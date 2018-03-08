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
import com.blackducksoftware.sdk.protex.project.codetree.CharEncoding;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeApi;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNode;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNodeCommentInfo;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNodeRequest;
import com.blackducksoftware.sdk.protex.project.codetree.SourceFileInfoNode;

public class CodeTreeApiWeaved extends WeavedApi<CodeTreeApi> implements CodeTreeApi {

	public CodeTreeApiWeaved(final CodeTreeApi api) {
		super(api);
	}

	@Override
	public byte[] getFileContent(final String arg0,
			final String arg1, final CharEncoding arg2)
					throws SdkFault {
		return getApi().getFileContent(arg0, arg1, arg2);
	}

	@Override
	public String getFileOrFolderComment(final String arg0,
			final String arg1) throws SdkFault {
		return getApi().getFileOrFolderComment(arg0, arg1);
	}

	@Override
	public Boolean isCodeTreePathValid(final String arg0,
			final String arg1) throws SdkFault {
		return getApi().isCodeTreePathValid(arg0, arg1);
	}

	@Override
	public void updateFileOrFolderComment(final String arg0,
			final String arg1, final String arg2)
					throws SdkFault {
		getApi().updateFileOrFolderComment(arg0, arg1, arg2);
	}

	@Override
	public List<CodeTreeNode> getCodeTreeNodes(final String arg0, final String arg1, final CodeTreeNodeRequest arg2) throws SdkFault {
		return getApi().getCodeTreeNodes(arg0, arg1, arg2);
	}

	@Override
	public List<SourceFileInfoNode> getFileInfo(final String arg0, final String arg1, final Integer arg2, final Boolean arg3, final CharEncoding arg4) throws SdkFault {
		return getApi().getFileInfo(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Long getSkippedFileCount(final String arg0) throws SdkFault {
		return getApi().getSkippedFileCount(arg0);
	}

	@Override
	public List<CodeTreeNodeCommentInfo> getFileOrFolderComments(final String arg0, final String arg1,
			final CodeTreeNodeRequest arg2) throws SdkFault {
		return getApi().getFileOrFolderComments(arg0, arg1, arg2);
	}

	@Override
	public void updateFileOrFolderComments(final String arg0, final List<CodeTreeNodeCommentInfo> arg1)
			throws SdkFault {
		getApi().updateFileOrFolderComments(arg0, arg1);
	}

}
