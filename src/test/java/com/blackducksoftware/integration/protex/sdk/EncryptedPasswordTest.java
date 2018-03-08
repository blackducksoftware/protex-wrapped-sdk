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
package com.blackducksoftware.integration.protex.sdk;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EncryptedPasswordTest {

	private static final String TEST_WORKSPACE = "./testWorkspace/EncryptedPasswords/";


	private final TestLogger logger = new TestLogger();

	@Rule
	public ExpectedException exception = ExpectedException.none();


	@After
	public void testLogCleanup() {
		logger.getTestOutput().clear();
		logger.getTestExceptions().clear();
		System.clearProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE);
	}

	@Test
	public void testEncryptedPassFileNotExisting() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("The password file does not exist at");
		System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "fileDoesNotExist");
		new ProtexServerProxy("https://www.google.com", "testUser", null);

	}

	@Test
	public void testEncryptedPassFileExistingEmpty() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("There is no matching UserName and Password in the password file.");
		System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "existingEmptyFile");
		new ProtexServerProxy("https://www.google.com", "testUser", null);

	}


	@Test
	public void testEncryptedPassFileExistingNonEncryptedPasswords() throws Exception {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Security processing failed");
		System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "nonEncryptedPasswordFile");
		final ProtexServerProxy server = new ProtexServerProxy("https://www.google.com",
				"testUser",
				null);
		server.setLogger(logger);
		server.getUserApi().getUserByEmail("testUser");
	}
}
