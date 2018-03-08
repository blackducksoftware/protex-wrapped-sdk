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

public class ProtexPageFilterFactory {

	private ProtexPageFilterFactory() {

	}

	/**
	 * Will return an AssignedObligationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            AssignedObligationColumn - The column you want to have the assigned obligations sorted by.
	 * @return AssignedObligationPageFilter
	 */
	public static AssignedObligationPageFilter createAssignedObligationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending,
			final AssignedObligationColumn sortedColumn) {
		final AssignedObligationPageFilter pf = new AssignedObligationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an AssignedObligationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return AssignedObligationPageFilter
	 */
	public static AssignedObligationPageFilter createAssignedObligationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final AssignedObligationPageFilter pf = new AssignedObligationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an ComponentPageFilter using the parameters provided to
	 * create it.
	 * 
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            ComponentColumn - The column you want to have the components
	 *            sorted by.
	 * @return ComponentPageFilter
	 */
	public static ComponentPageFilter createComponentPageFilter(final int firstIndex, final int lastIndex,
			final boolean sortAscending, final ComponentColumn sortedColumn) {
		final ComponentPageFilter pf = new ComponentPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an ComponentPageFilter using the parameters provided to
	 * create it.
	 * 
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return ComponentPageFilter
	 */
	public static ComponentPageFilter createComponentPageFilter(final int firstIndex, final int lastIndex,
			final boolean sortAscending) {
		final ComponentPageFilter pf = new ComponentPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an FileDiscoveryPatternPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            FileDiscoveryPatternColumn - The column you want to have the file discovery patterns sorted by.
	 * @return AttributePageFilter
	 */
	public static FileDiscoveryPatternPageFilter createFileDiscoveryPatternPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending,
			final FileDiscoveryPatternColumn sortedColumn) {
		final FileDiscoveryPatternPageFilter pf = new FileDiscoveryPatternPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an FileDiscoveryPatternPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return FileDiscoveryPatternPageFilter
	 */
	public static FileDiscoveryPatternPageFilter createFileDiscoveryPatternPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final FileDiscoveryPatternPageFilter pf = new FileDiscoveryPatternPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an LearnedIdentificationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            LearnedIdentificationColumn - The column you want to have the learned identifications sorted by.
	 * @return LearnedIdentificationPageFilter
	 */
	public static LearnedIdentificationPageFilter createLearnedIdentificationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending,
			final LearnedIdentificationColumn sortedColumn) {
		final LearnedIdentificationPageFilter pf = new LearnedIdentificationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an LearnedIdentificationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return LearnedIdentificationPageFilter
	 */
	public static LearnedIdentificationPageFilter createLearnedIdentificationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final LearnedIdentificationPageFilter pf = new LearnedIdentificationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an LicenseInfoPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            LicenseInfoColumn - The column you want to have the license info sorted by.
	 * @return LicenseInfoPageFilter
	 */
	public static LicenseInfoPageFilter createLicenseInfoPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending, final LicenseInfoColumn sortedColumn) {
		final LicenseInfoPageFilter pf = new LicenseInfoPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an LicenseInfoPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return LicenseInfoPageFilter
	 */
	public static LicenseInfoPageFilter createLicenseInfoPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final LicenseInfoPageFilter pf = new LicenseInfoPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an LocalComponentPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            LocalComponentColumn - The column you want to have the local components sorted by.
	 * @return LocalComponentPageFilter
	 */
	public static LocalComponentPageFilter createLocalComponentPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending,
			final LocalComponentColumn sortedColumn) {
		final LocalComponentPageFilter pf = new LocalComponentPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an LocalComponentPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return LocalComponentPageFilter
	 */
	public static LocalComponentPageFilter createLocalComponentPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final LocalComponentPageFilter pf = new LocalComponentPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an ObligationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            ObligationColumn - The column you want to have the obligations sorted by.
	 * @return ObligationPageFilter
	 */
	public static ObligationPageFilter createObligationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending, final ObligationColumn sortedColumn) {
		final ObligationPageFilter pf = new ObligationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an ObligationPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return ObligationPageFilter
	 */
	public static ObligationPageFilter createObligationPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final ObligationPageFilter pf = new ObligationPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an ProjectInfoPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            ProjectInfoColumn - The column you want to have the project info sorted by.
	 * @return ProjectInfoPageFilter
	 */
	public static ProjectInfoPageFilter createProjectInfoPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending, final ProjectInfoColumn sortedColumn) {
		final ProjectInfoPageFilter pf = new ProjectInfoPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an ProjectInfoPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return ProjectInfoPageFilter
	 */
	public static ProjectInfoPageFilter createProjectInfoPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final ProjectInfoPageFilter pf = new ProjectInfoPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an ProjectPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            ProjectColumn - The column you want to have the projects sorted by.
	 * @return ProjectPageFilter
	 */
	public static ProjectPageFilter createProjectPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending, final ProjectColumn sortedColumn) {
		final ProjectPageFilter pf = new ProjectPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an ProjectPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return ProjectPageFilter
	 */
	public static ProjectPageFilter createProjectPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final ProjectPageFilter pf = new ProjectPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an StringSearchPatternPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            StringSearchPatternColumn - The column you want to have the string search patterns sorted by.
	 * @return StringSearchPatternPageFilter
	 */
	public static StringSearchPatternPageFilter createStringSearchPatternPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending,
			final StringSearchPatternColumn sortedColumn) {
		final StringSearchPatternPageFilter pf = new StringSearchPatternPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an StringSearchPatternPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return StringSearchPatternPageFilter
	 */
	public static StringSearchPatternPageFilter createStringSearchPatternPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final StringSearchPatternPageFilter pf = new StringSearchPatternPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}

	/**
	 * Will return an UserPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @param sortedColumn
	 *            UserColumn - The column you want to have the users sorted by.
	 * @return UserPageFilter
	 */
	public static UserPageFilter createUserPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending, final UserColumn sortedColumn) {
		final UserPageFilter pf = new UserPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		pf.setSortedColumn(sortedColumn);
		return pf;
	}

	/**
	 * Will return an UserPageFilter using the parameters provided to create it.
	 *
	 * @param firstIndex
	 *            Integer - the index of the first row you want.
	 * @param lastIndex
	 *            Integer - the index of the last row you want.
	 * @param sortAscending
	 *            Boolean - should the sorting be ascending
	 * @return UserPageFilter
	 */
	public static UserPageFilter createUserPageFilter(final int firstIndex, final int lastIndex, final boolean sortAscending) {
		final UserPageFilter pf = new UserPageFilter();
		pf.setFirstRowIndex(firstIndex);
		pf.setLastRowIndex(lastIndex);
		pf.setSortAscending(sortAscending);
		return pf;
	}
}
