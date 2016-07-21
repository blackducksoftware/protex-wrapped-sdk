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
package com.blackducksoftware.integration.protex.sdk;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.cxf.common.util.StringUtils;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.integration.suite.sdk.util.ProgrammedLicenseCheckEncryptedPasswordCallback;

public class EncryptedPasswordTest {

    private static final String TEST_WORKSPACE = "./testWorkspace/EncryptedPasswords/";

    private static Properties testProperties;

    private TestLogger logger = new TestLogger();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void init() {

        testProperties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("test.properties");
        try {
            testProperties.load(is);
        } catch (IOException e) {
            System.err.println("reading test.properties failed!");
        }

    }

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
        new ProtexServerProxy(testProperties.getProperty("TEST_PROTEX_SERVER_URL"), testProperties.getProperty("TEST_USERNAME"), null);

    }

    @Test
    public void testEncryptedPassFileExistingEmpty() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There is no matching UserName and Password in the password file.");
        System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "existingEmptyFile");
        new ProtexServerProxy(testProperties.getProperty("TEST_PROTEX_SERVER_URL"), testProperties.getProperty("TEST_USERNAME"), null);

    }

    @Test
    public void testEncryptedPassFileExistingEncryptedPasswords() throws Exception {
        System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "encryptedPasswordFile");
        ProtexServerProxy server = new ProtexServerProxy(testProperties.getProperty("TEST_PROTEX_SERVER_URL"), testProperties.getProperty("TEST_USERNAME"),
                null);
        server.setLogger(logger);

        Field remotePropsField = ProtexServerProxy.class.getDeclaredField("remoteProps");
        remotePropsField.setAccessible(true);
        Map<String, Object> remoteProps = (Map<String, Object>) remotePropsField.get(server);

        ProgrammedLicenseCheckEncryptedPasswordCallback passwordCallback = (ProgrammedLicenseCheckEncryptedPasswordCallback) remoteProps
                .get(WSHandlerConstants.PW_CALLBACK_REF);
        Field passwordCacheField = passwordCallback.getClass().getSuperclass().getDeclaredField("passwordCache");
        passwordCacheField.setAccessible(true);
        Map<String, String> passwordCache = (Map<String, String>) passwordCacheField.get(passwordCallback);
        String password = passwordCache.get(testProperties.getProperty("TEST_USERNAME"));
        assertTrue(!StringUtils.isEmpty(password));

        server.getUserApi().getUserByEmail(testProperties.getProperty("TEST_USERNAME"));

    }

    @Test
    public void testEncryptedPassFileExistingNonEncryptedPasswords() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Security processing failed");
        System.setProperty(ProtexServerProxy.ENCRYPTED_PASSWORD_FILE, TEST_WORKSPACE + "nonEncryptedPasswordFile");
        ProtexServerProxy server = new ProtexServerProxy(testProperties.getProperty("TEST_PROTEX_SERVER_URL"), testProperties.getProperty("TEST_USERNAME"),
                null);
        server.setLogger(logger);
        server.getUserApi().getUserByEmail(testProperties.getProperty("TEST_USERNAME"));
    }
}
