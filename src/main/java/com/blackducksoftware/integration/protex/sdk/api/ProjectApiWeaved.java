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

import java.util.Date;
import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.common.AnalysisDatabaseOptions;
import com.blackducksoftware.sdk.protex.common.AnalysisStatus;
import com.blackducksoftware.sdk.protex.common.CaptureOptions;
import com.blackducksoftware.sdk.protex.common.CodeLabelOption;
import com.blackducksoftware.sdk.protex.common.ComponentInfo;
import com.blackducksoftware.sdk.protex.common.ComponentKey;
import com.blackducksoftware.sdk.protex.common.ComponentPageFilter;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPattern;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternPageFilter;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternRequest;
import com.blackducksoftware.sdk.protex.common.ForcibleIntegerOption;
import com.blackducksoftware.sdk.protex.common.GetBehavior;
import com.blackducksoftware.sdk.protex.common.PatternOriginType;
import com.blackducksoftware.sdk.protex.common.RapidIdConfiguration;
import com.blackducksoftware.sdk.protex.common.RapidIdConfigurationRequest;
import com.blackducksoftware.sdk.protex.common.StringSearchPattern;
import com.blackducksoftware.sdk.protex.common.StringSearchPatternRequest;
import com.blackducksoftware.sdk.protex.license.License;
import com.blackducksoftware.sdk.protex.license.LicenseCategory;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligation;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationPageFilter;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationRequest;
import com.blackducksoftware.sdk.protex.obligation.ObligationCategory;
import com.blackducksoftware.sdk.protex.project.CloneOption;
import com.blackducksoftware.sdk.protex.project.Project;
import com.blackducksoftware.sdk.protex.project.ProjectApi;
import com.blackducksoftware.sdk.protex.project.ProjectInfo;
import com.blackducksoftware.sdk.protex.project.ProjectInfoPageFilter;
import com.blackducksoftware.sdk.protex.project.ProjectPageFilter;
import com.blackducksoftware.sdk.protex.project.ProjectRequest;
import com.blackducksoftware.sdk.protex.project.ProjectScanLog;
import com.blackducksoftware.sdk.protex.project.RapidIdentificationMode;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeNode;
import com.blackducksoftware.sdk.protex.user.User;

public class ProjectApiWeaved extends WeavedApi<ProjectApi> implements ProjectApi {

    public ProjectApiWeaved(final ProjectApi api) {
        super(api);
    }

    @Override
    public String addFileDiscoveryPattern(final String arg0, final FileDiscoveryPatternRequest arg1) throws SdkFault {
        return getApi().addFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public String addProjectObligation(final String arg0, final AssignedObligationRequest arg1) throws SdkFault {
        return getApi().addProjectObligation(arg0, arg1);
    }

    @Override
    public String addProjectObligationUsingReference(final String arg0, final String arg1, final Boolean arg2, final Boolean arg3) throws SdkFault {
        return getApi().addProjectObligationUsingReference(arg0, arg1, arg2, arg3);
    }

    @Override
    public void addProjectUser(final String arg0, final String arg1) throws SdkFault {
        getApi().addProjectUser(arg0, arg1);
    }

    @Override
    public Long addRapidIdConfiguration(final String arg0, final RapidIdConfigurationRequest arg1) throws SdkFault {

        return getApi().addRapidIdConfiguration(arg0, arg1);
    }

    @Override
    public String addStringSearchPattern(final String arg0, final StringSearchPatternRequest arg1) throws SdkFault {

        return getApi().addStringSearchPattern(arg0, arg1);
    }

    @Override
    public void associateRapidIdConfiguration(final String arg0, final String arg1, final Long arg2) throws SdkFault {
        getApi().associateRapidIdConfiguration(arg0, arg1, arg2);
    }

    @Override
    public void cancelAnalysis(final String arg0) throws SdkFault {
        getApi().cancelAnalysis(arg0);
    }

    @Override
    public String createProject(final ProjectRequest arg0, final LicenseCategory arg1) throws SdkFault {

        return getApi().createProject(arg0, arg1);
    }

    @Override
    public void deleteProject(final String arg0) throws SdkFault {
        getApi().deleteProject(arg0);
    }

    @Override
    public void deleteUploadedSourceCode(final String arg0) throws SdkFault {
        getApi().deleteUploadedSourceCode(arg0);
    }

    @Override
    public void dissociateRapidIdConfiguration(final String arg0, final String arg1, final Long arg2) throws SdkFault {
        getApi().dissociateRapidIdConfiguration(arg0, arg1, arg2);
    }

    @Override
    public AnalysisDatabaseOptions getAnalysisDatabaseOptions(final String arg0) throws SdkFault {

        return getApi().getAnalysisDatabaseOptions(arg0);
    }

    @Override
    public AnalysisStatus getAnalysisStatus(final String arg0) throws SdkFault {

        return getApi().getAnalysisStatus(arg0);
    }

    @Override
    public CaptureOptions getCaptureOptions(final String arg0) throws SdkFault {

        return getApi().getCaptureOptions(arg0);
    }

    @Override
    public CodeLabelOption getCodeLabelOption(final String arg0) throws SdkFault {

        return getApi().getCodeLabelOption(arg0);
    }

    @Override
    public FileDiscoveryPattern getFileDiscoveryPatternById(final String arg0, final String arg1) throws SdkFault {

        return getApi().getFileDiscoveryPatternById(arg0, arg1);
    }

    @Override
    public List<FileDiscoveryPattern> getFileDiscoveryPatterns(final String arg0, final List<PatternOriginType> arg1, final FileDiscoveryPatternPageFilter arg2) throws SdkFault {

        return getApi().getFileDiscoveryPatterns(arg0, arg1, arg2);
    }

    @Override
    public License getLicenseById(final String arg0, final String arg1) throws SdkFault {

        return getApi().getLicenseById(arg0, arg1);
    }

    @Override
    public Project getProjectById(final String arg0) throws SdkFault {

        return getApi().getProjectById(arg0);
    }

    @Override
    public Project getProjectByName(final String arg0) throws SdkFault {

        return getApi().getProjectByName(arg0);
    }

    @Override
    public String getProjectManageUrl(final String arg0) throws SdkFault {

        return getApi().getProjectManageUrl(arg0);
    }

    @Override
    public List<AssignedObligation> getProjectObligations(final String arg0, final AssignedObligationPageFilter arg1) throws SdkFault {

        return getApi().getProjectObligations(arg0, arg1);
    }

    @Override
    public List<User> getProjectUsers(final String arg0) throws SdkFault {

        return getApi().getProjectUsers(arg0);
    }

    @Override
    public List<Project> getProjects(final ProjectPageFilter arg0) throws SdkFault {

        return getApi().getProjects(arg0);
    }

    @Override
    public List<ProjectInfo> getProjectsByUser(final String arg0) throws SdkFault {

        return getApi().getProjectsByUser(arg0);
    }

    @Override
    public List<String> getRapidIdConfigurationAssociations(final String arg0, final Long arg1) throws SdkFault {

        return getApi().getRapidIdConfigurationAssociations(arg0, arg1);
    }

    @Override
    public RapidIdConfiguration getRapidIdConfigurationById(final String arg0, final Long arg1) throws SdkFault {

        return getApi().getRapidIdConfigurationById(arg0, arg1);
    }

    @Override
    public RapidIdConfiguration getRapidIdConfigurationByName(final String arg0, final String arg1) throws SdkFault {

        return getApi().getRapidIdConfigurationByName(arg0, arg1);
    }

    @Override
    public List<RapidIdConfiguration> getRapidIdConfigurations(final String arg0) throws SdkFault {

        return getApi().getRapidIdConfigurations(arg0);
    }

    @Override
    public RapidIdentificationMode getRapidIdentificationMode(final String arg0) throws SdkFault {

        return getApi().getRapidIdentificationMode(arg0);
    }

    @Override
    public StringSearchPattern getStringSearchPatternById(final String arg0, final String arg1) throws SdkFault {

        return getApi().getStringSearchPatternById(arg0, arg1);
    }

    @Override
    public StringSearchPattern getStringSearchPatternByName(final String arg0, final String arg1) throws SdkFault {

        return getApi().getStringSearchPatternByName(arg0, arg1);
    }

    @Override
    public List<StringSearchPattern> getStringSearchPatterns(final String arg0) throws SdkFault {

        return getApi().getStringSearchPatterns(arg0);
    }

    @Override
    public void removeFileDiscoveryPattern(final String arg0, final String arg1) throws SdkFault {
        getApi().removeFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public void removeProjectObligation(final String arg0, final String arg1) throws SdkFault {
        getApi().removeProjectObligation(arg0, arg1);
    }

    @Override
    public void removeProjectUser(final String arg0, final String arg1) throws SdkFault {
        getApi().removeProjectUser(arg0, arg1);
    }

    @Override
    public void removeRapidIdConfiguration(final String arg0, final Long arg1) throws SdkFault {
        getApi().removeRapidIdConfiguration(arg0, arg1);
    }

    @Override
    public void removeStringSearchPattern(final String arg0, final String arg1) throws SdkFault {
        getApi().removeStringSearchPattern(arg0, arg1);
    }

    @Override
    public void resetFileDiscoveryPattern(final String arg0, final String arg1) throws SdkFault {
        getApi().resetFileDiscoveryPattern(arg0, arg1);
    }

    @Override
    public void startAnalysis(final String arg0, final Boolean arg1) throws SdkFault {
        getApi().startAnalysis(arg0, arg1);
    }

    @Override
    public List<ProjectInfo> suggestProjects(final String arg0, final ProjectInfoPageFilter arg1) throws SdkFault {

        return getApi().suggestProjects(arg0, arg1);
    }

    @Override
    public void updateAnalysisDatabaseOptions(final String arg0, final AnalysisDatabaseOptions arg1) throws SdkFault {
        getApi().updateAnalysisDatabaseOptions(arg0, arg1);
    }

    @Override
    public void updateCaptureOptions(final String arg0, final CaptureOptions arg1) throws SdkFault {
        getApi().updateCaptureOptions(arg0, arg1);
    }

    @Override
    public void updateCodeLabelOption(final String arg0, final CodeLabelOption arg1) throws SdkFault {
        getApi().updateCodeLabelOption(arg0, arg1);
    }

    @Override
    public void updateRapidIdentificationMode(final String arg0, final RapidIdentificationMode arg1) throws SdkFault {
        getApi().updateRapidIdentificationMode(arg0, arg1);
    }

    @Override
    public void addProjectToFavorites(final String arg0, final String arg1) throws SdkFault {
        getApi().addProjectToFavorites(arg0, arg1);
    }

    @Override
    public void addTag(final String arg0, final String arg1, final String arg2) throws SdkFault {
        getApi().addTag(arg0, arg1, arg2);
    }

    @Override
    public String cloneProject(final String arg0, final String arg1, final List<CloneOption> arg2, final List<ObligationCategory> arg3) throws SdkFault {
        return getApi().cloneProject(arg0, arg1, arg2, arg3);
    }

    @Override
    public List<ProjectScanLog> getAnalysisLogs(final String arg0, final Date arg1, final Date arg2) throws SdkFault {
        return getApi().getAnalysisLogs(arg0, arg1, arg2);
    }

    @Override
    public ComponentInfo getComponentByKey(final String arg0, final ComponentKey arg1) throws SdkFault {
        return getApi().getComponentByKey(arg0, arg1);
    }

    @Override
    public List<Project> getFavoriteProjects(final String arg0) throws SdkFault {
        return getApi().getFavoriteProjects(arg0);
    }

    @Override
    public List<CodeTreeNode> getFilesOmittedFromRapidId(final String arg0, final List<CodeTreeNode> arg1) throws SdkFault {
        return getApi().getFilesOmittedFromRapidId(arg0, arg1);
    }

    @Override
    public List<Project> getProjectsById(final List<String> arg0, final GetBehavior arg1) throws SdkFault {
        return getApi().getProjectsById(arg0, arg1);
    }

    @Override
    public List<Project> getTaggedProjects(final String arg0, final String arg1) throws SdkFault {
        return getApi().getTaggedProjects(arg0, arg1);
    }

    @Override
    public List<String> getTags(final String arg0, final String arg1) throws SdkFault {
        return getApi().getTags(arg0, arg1);
    }

    @Override
    public void removeProjectFromFavorites(final String arg0, final String arg1) throws SdkFault {
        getApi().removeProjectFromFavorites(arg0, arg1);
    }

    @Override
    public void removeTag(final String arg0, final String arg1, final String arg2) throws SdkFault {
        getApi().removeTag(arg0, arg1, arg2);
    }

    @Override
    public void setOmitFromRapidId(final String arg0, final List<CodeTreeNode> arg1, final Boolean arg2) throws SdkFault {
        getApi().setOmitFromRapidId(arg0, arg1, arg2);
    }

    @Override
    public List<ComponentInfo> suggestComponents(final String arg0, final String arg1, final ComponentPageFilter arg2) throws SdkFault {
        return getApi().suggestComponents(arg0, arg1, arg2);
    }

    @Override
    public void updateFileDiscoveryPattern(final String arg0, final String arg1, final FileDiscoveryPatternRequest arg2) throws SdkFault {
        getApi().updateFileDiscoveryPattern(arg0, arg1, arg2);
    }

    @Override
    public void updateProject(final String arg0, final ProjectRequest arg1) throws SdkFault {
        getApi().updateProject(arg0, arg1);
    }

    @Override
    public void updateProjectObligation(final String arg0, final String arg1, final AssignedObligationRequest arg2) throws SdkFault {
        getApi().updateProjectObligation(arg0, arg1, arg2);
    }

    @Override
    public void updateRapidIdConfiguration(final String arg0, final Long arg1, final RapidIdConfigurationRequest arg2) throws SdkFault {
        getApi().updateRapidIdConfiguration(arg0, arg1, arg2);
    }

    @Override
    public void updateStringSearchPattern(final String arg0, final String arg1, final StringSearchPatternRequest arg2) throws SdkFault {
        getApi().updateStringSearchPattern(arg0, arg1, arg2);
    }

    @Override
    public String createProjectFromTemplate(final String arg0, final ProjectRequest arg1) throws SdkFault {
        return getApi().createProjectFromTemplate(arg0, arg1);
    }

    @Override
    public ForcibleIntegerOption getMinimumComponentRankCaptureOption(final String arg0) throws SdkFault {
        return getApi().getMinimumComponentRankCaptureOption(arg0);
    }

    @Override
    public void updateMinimumComponentRankCaptureOption(final String arg0, final ForcibleIntegerOption arg1) throws SdkFault {
        getApi().updateMinimumComponentRankCaptureOption(arg0, arg1);
    }

}
