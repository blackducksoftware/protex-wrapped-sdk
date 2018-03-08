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
package com.blackducksoftware.integration.protex.sdk.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.sdk.protex.common.ComponentColumn;
import com.blackducksoftware.sdk.protex.common.ComponentPageFilter;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternColumn;
import com.blackducksoftware.sdk.protex.common.FileDiscoveryPatternPageFilter;
import com.blackducksoftware.sdk.protex.common.LearnedIdentificationColumn;
import com.blackducksoftware.sdk.protex.common.LearnedIdentificationPageFilter;
import com.blackducksoftware.sdk.protex.common.StringSearchPatternColumn;
import com.blackducksoftware.sdk.protex.common.StringSearchPatternPageFilter;
import com.blackducksoftware.sdk.protex.license.LicenseInfoColumn;
import com.blackducksoftware.sdk.protex.license.LicenseInfoPageFilter;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationColumn;
import com.blackducksoftware.sdk.protex.obligation.AssignedObligationPageFilter;
import com.blackducksoftware.sdk.protex.obligation.ObligationColumn;
import com.blackducksoftware.sdk.protex.obligation.ObligationPageFilter;
import com.blackducksoftware.sdk.protex.project.ProjectColumn;
import com.blackducksoftware.sdk.protex.project.ProjectInfoColumn;
import com.blackducksoftware.sdk.protex.project.ProjectInfoPageFilter;
import com.blackducksoftware.sdk.protex.project.ProjectPageFilter;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentColumn;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentPageFilter;
import com.blackducksoftware.sdk.protex.user.UserColumn;
import com.blackducksoftware.sdk.protex.user.UserPageFilter;

public class ProtexPageFilterFactoryTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCreateAssignedObligationPageFilter() throws Exception {
		final AssignedObligationPageFilter filter = ProtexPageFilterFactory.createAssignedObligationPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateAssignedObligationPageFilterWithColumn() throws Exception {
		final AssignedObligationPageFilter filter = ProtexPageFilterFactory.createAssignedObligationPageFilter(0, 13, false, AssignedObligationColumn.OBLIGATION_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(AssignedObligationColumn.OBLIGATION_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateComponentPageFilter() throws Exception {
		final ComponentPageFilter filter = ProtexPageFilterFactory.createComponentPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateComponentPageFilterWithColumn() throws Exception {
		final ComponentPageFilter filter = ProtexPageFilterFactory.createComponentPageFilter(0, 13, false,
				ComponentColumn.NAME);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(ComponentColumn.NAME, filter.getSortedColumn());
	}

	@Test
	public void testCreateFileDiscoveryPatternPageFilter() throws Exception {
		final FileDiscoveryPatternPageFilter filter = ProtexPageFilterFactory.createFileDiscoveryPatternPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateFileDiscoveryPatternPageFilterWithColumn() throws Exception {
		final FileDiscoveryPatternPageFilter filter = ProtexPageFilterFactory
				.createFileDiscoveryPatternPageFilter(0, 13, false, FileDiscoveryPatternColumn.FILE_TYPE);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(FileDiscoveryPatternColumn.FILE_TYPE, filter.getSortedColumn());
	}

	@Test
	public void testCreateLearnedIdentificationPageFilter() throws Exception {
		final LearnedIdentificationPageFilter filter = ProtexPageFilterFactory.createLearnedIdentificationPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateLearnedIdentificationPageFilterWithColumn() throws Exception {
		final LearnedIdentificationPageFilter filter = ProtexPageFilterFactory.createLearnedIdentificationPageFilter(0, 13, false,
				LearnedIdentificationColumn.COMPONENT_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(LearnedIdentificationColumn.COMPONENT_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateLicenseInfoPageFilter() throws Exception {
		final LicenseInfoPageFilter filter = ProtexPageFilterFactory.createLicenseInfoPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateLicenseInfoPageFilterWithColumn() throws Exception {
		final LicenseInfoPageFilter filter = ProtexPageFilterFactory.createLicenseInfoPageFilter(0, 13, false, LicenseInfoColumn.LICENSE_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(LicenseInfoColumn.LICENSE_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateLocalComponentPageFilter() throws Exception {
		final LocalComponentPageFilter filter = ProtexPageFilterFactory.createLocalComponentPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateLocalComponentPageFilterWithColumn() throws Exception {
		final LocalComponentPageFilter filter = ProtexPageFilterFactory.createLocalComponentPageFilter(0, 13, false, LocalComponentColumn.COMPONENT_NAME);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(LocalComponentColumn.COMPONENT_NAME, filter.getSortedColumn());
	}

	@Test
	public void testCreateObligationPageFilter() throws Exception {
		final ObligationPageFilter filter = ProtexPageFilterFactory.createObligationPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateObligationPageFilterWithColumn() throws Exception {
		final ObligationPageFilter filter = ProtexPageFilterFactory.createObligationPageFilter(0, 13, false, ObligationColumn.OBLIGATION_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(ObligationColumn.OBLIGATION_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateProjectInfoPageFilter() throws Exception {
		final ProjectInfoPageFilter filter = ProtexPageFilterFactory.createProjectInfoPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateProjectInfoPageFilterWithColumn() throws Exception {
		final ProjectInfoPageFilter filter = ProtexPageFilterFactory.createProjectInfoPageFilter(0, 13, false, ProjectInfoColumn.PROJECT_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(ProjectInfoColumn.PROJECT_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateProjectPageFilter() throws Exception {
		final ProjectPageFilter filter = ProtexPageFilterFactory.createProjectPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateProjectPageFilterWithColumn() throws Exception {
		final ProjectPageFilter filter = ProtexPageFilterFactory.createProjectPageFilter(0, 13, false, ProjectColumn.PROJECT_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(ProjectColumn.PROJECT_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateStringSearchPatternPageFilter() throws Exception {
		final StringSearchPatternPageFilter filter = ProtexPageFilterFactory.createStringSearchPatternPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateStringSearchPatternPageFilterWithColumn() throws Exception {
		final StringSearchPatternPageFilter filter = ProtexPageFilterFactory.createStringSearchPatternPageFilter(0, 13, false,
				StringSearchPatternColumn.PATTERN_ID);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(StringSearchPatternColumn.PATTERN_ID, filter.getSortedColumn());
	}

	@Test
	public void testCreateUserPageFilter() throws Exception {
		final UserPageFilter filter = ProtexPageFilterFactory.createUserPageFilter(0, 13, false);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertNull(filter.getSortedColumn());
	}

	@Test
	public void testCreateUserPageFilterWithColumn() throws Exception {
		final UserPageFilter filter = ProtexPageFilterFactory.createUserPageFilter(0, 13, false, UserColumn.EMAIL);

		assertEquals(Integer.valueOf(0), filter.getFirstRowIndex());
		assertEquals(Integer.valueOf(13), filter.getLastRowIndex());
		assertTrue(!filter.isSortAscending());
		assertEquals(UserColumn.EMAIL, filter.getSortedColumn());
	}

}
