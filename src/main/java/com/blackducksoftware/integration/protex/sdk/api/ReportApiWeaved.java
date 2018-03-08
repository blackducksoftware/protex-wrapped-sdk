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
import com.blackducksoftware.sdk.protex.report.AuditHistoryReportRequest;
import com.blackducksoftware.sdk.protex.report.Report;
import com.blackducksoftware.sdk.protex.report.ReportApi;
import com.blackducksoftware.sdk.protex.report.ReportFormat;
import com.blackducksoftware.sdk.protex.report.ReportTemplate;
import com.blackducksoftware.sdk.protex.report.ReportTemplateRequest;
import com.blackducksoftware.sdk.protex.report.SpdxReportConfiguration;
import com.blackducksoftware.sdk.protex.report.SpdxReportFormat;

public class ReportApiWeaved extends WeavedApi<ReportApi> implements ReportApi {

	public ReportApiWeaved(final ReportApi api) {
		super(api);
	}

	@Override
	public String createReportTemplate(final ReportTemplateRequest arg0) throws SdkFault {
		return getApi().createReportTemplate(arg0);
	}

	@Override
	public void deleteReportTemplate(final String arg0) throws SdkFault {
		getApi().deleteReportTemplate(arg0);

	}

	@Override
	public Report generateSpdxReport(final String arg0, final SpdxReportConfiguration arg1, final SpdxReportFormat arg2) throws SdkFault {

		return getApi().generateSpdxReport(arg0, arg1, arg2);
	}

	@Override
	public ReportTemplate getReportTemplateById(final String arg0) throws SdkFault {

		return getApi().getReportTemplateById(arg0);
	}

	@Override
	public List<ReportTemplate> suggestReportTemplates(final String arg0) throws SdkFault {

		return getApi().suggestReportTemplates(arg0);
	}

	@Override
	public Report generateAdHocProjectReport(final String arg0, final ReportTemplateRequest arg1, final ReportFormat arg2, final boolean arg3) throws SdkFault {
		return getApi().generateAdHocProjectReport(arg0, arg1, arg2, arg3);
	}

	@Override
	public Report generateProjectReport(final String arg0, final String arg1, final ReportFormat arg2, final boolean arg3) throws SdkFault {
		return getApi().generateProjectReport(arg0, arg1, arg2, arg3);
	}

	@Override
	public ReportTemplate getReportTemplateByTitle(final String arg0) throws SdkFault {
		return getApi().getReportTemplateByTitle(arg0);
	}

	@Override
	public void updateReportTemplate(final String arg0, final ReportTemplateRequest arg1) throws SdkFault {
		getApi().updateReportTemplate(arg0, arg1);
	}

	@Override
	public ReportTemplate getDefaultReportTemplate() throws SdkFault {
		return getApi().getDefaultReportTemplate();
	}

	@Override
	public void setDefaultReportTemplate(final String arg0) throws SdkFault {
		getApi().setDefaultReportTemplate(arg0);
	}

	@Override
	public Report generateProjectReportFromDefaultTemplate(final String arg0, final ReportFormat arg1,
			final boolean arg2) throws SdkFault {
		return getApi().generateProjectReportFromDefaultTemplate(arg0, arg1, arg2);
	}

	@Override
	public Report generateAuditHistoryReport(final AuditHistoryReportRequest arg0) throws SdkFault {
		return getApi().generateAuditHistoryReport(arg0);
	}

}
