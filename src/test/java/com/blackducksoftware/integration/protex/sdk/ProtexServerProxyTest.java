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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ProxyServerType;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blackducksoftware.integration.protex.sdk.api.BomApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.CodeTreeApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.ComponentApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.CustomComponentManagementApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.DiscoveryApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.ExternalIdApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.FileComparisonApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.IdentificationApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.LicenseApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.LocalComponentApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.ObligationApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.PolicyApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.ProjectApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.ReportApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.RoleApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.SynchronizationApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.TemplateApiWeaved;
import com.blackducksoftware.integration.protex.sdk.api.UserApiWeaved;
import com.blackducksoftware.integration.protex.sdk.exceptions.ServerConfigException;
import com.blackducksoftware.integration.protex.sdk.factory.ProtexPageFilterFactory;
import com.blackducksoftware.integration.suite.sdk.logging.LogLevel;
import com.blackducksoftware.integration.suite.sdk.logging.SdkLogger;
import com.blackducksoftware.integration.suite.sdk.util.ProgrammedPasswordCallback;
import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.comparison.FileComparisonApi;
import com.blackducksoftware.sdk.protex.license.LicenseApi;
import com.blackducksoftware.sdk.protex.license.LicenseOriginType;
import com.blackducksoftware.sdk.protex.obligation.ObligationApi;
import com.blackducksoftware.sdk.protex.policy.PolicyApi;
import com.blackducksoftware.sdk.protex.policy.externalid.ExternalIdApi;
import com.blackducksoftware.sdk.protex.project.ProjectApi;
import com.blackducksoftware.sdk.protex.project.bom.BomApi;
import com.blackducksoftware.sdk.protex.project.codetree.CodeTreeApi;
import com.blackducksoftware.sdk.protex.project.codetree.discovery.DiscoveryApi;
import com.blackducksoftware.sdk.protex.project.codetree.identification.IdentificationApi;
import com.blackducksoftware.sdk.protex.project.localcomponent.LocalComponentApi;
import com.blackducksoftware.sdk.protex.project.template.TemplateApi;
import com.blackducksoftware.sdk.protex.report.ReportApi;
import com.blackducksoftware.sdk.protex.role.RoleApi;
import com.blackducksoftware.sdk.protex.synchronization.SynchronizationApi;
import com.blackducksoftware.sdk.protex.user.UserApi;

import junit.framework.Assert;

public class ProtexServerProxyTest {

	private static Properties testProperties;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void init() {
		testProperties = new Properties();
		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		final InputStream is = classLoader.getResourceAsStream("test.properties");

		try {
			testProperties.load(is);
		} catch (final IOException e) {
			System.err.println("reading test.properties failed!");
		}

	}

	public ProtexServerProxy getServerProxy() throws Exception {
		return new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"));
	}

	@Test
	public void testProtexServerProxyNullUrl() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy(null,
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"));

	}

	@Test
	public void testProtexServerProxyNullUserName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"), null,
				testProperties.getProperty("TEST_PASSWORD"));
	}

	@Test
	public void testProtexServerProxyNullPassword() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"), null);
	}

	@Test
	public void testProtexServerProxyEmptyUrl() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy("", testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"));
	}

	@Test
	public void testProtexServerProxyEmptyUserName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"), "",
				testProperties.getProperty("TEST_PASSWORD"));
	}

	@Test
	public void testProtexServerProxyEmptyPassword() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"), null);
	}

	@Test
	public void testProtexServerProxyNullUrlWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy(null,
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 0L);
	}

	@Test
	public void testProtexServerProxyNullUserNameWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"), null,
				testProperties.getProperty("TEST_PASSWORD"), 0L);
	}

	@Test
	public void testProtexServerProxyNullPasswordWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"), null, 0L);
	}

	@Test
	public void testProtexServerProxyEmptyUrlWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy("", testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 0L);
	}

	@Test
	public void testProtexServerProxyEmptyUserNameWithTimeout()
			throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"), "",
				testProperties.getProperty("TEST_PASSWORD"), 0L);
	}

	@Test
	public void testProtexServerProxyEmptyPasswordWithTimeout()
			throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"), "", 0L);
	}

	@Test
	public void testProtexServerProxy() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"));
		assertEquals(Long.valueOf(300000L), server.getTimeout());
		assertEquals(
				new URL(testProperties.getProperty("TEST_PROTEX_SERVER_URL")),
				server.getBaseUrl());
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				server.getUsername());
		final Field remotePropsField = ProtexServerProxy.class
				.getDeclaredField("remoteProps");
		remotePropsField.setAccessible(true);
		final Map<String, Object> remoteProps = (Map<String, Object>) remotePropsField
				.get(server);
		final ProgrammedPasswordCallback passwordCallback = (ProgrammedPasswordCallback) remoteProps
				.get(WSHandlerConstants.PW_CALLBACK_REF);
		final Field passwordCacheField = ProgrammedPasswordCallback.class
				.getDeclaredField("passwordCache");
		passwordCacheField.setAccessible(true);
		final Map<String, String> passwordCache = (Map<String, String>) passwordCacheField
				.get(passwordCallback);
		final String password = passwordCache.get(testProperties
				.getProperty("TEST_USERNAME"));
		assertEquals(testProperties.getProperty("TEST_PASSWORD"), password);

		assertNotNull(remoteProps);
		assertTrue(remoteProps.containsKey(WSHandlerConstants.ACTION));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PASSWORD_TYPE));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.MUST_UNDERSTAND));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.USER));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PW_CALLBACK_REF));

		assertEquals(WSHandlerConstants.USERNAME_TOKEN,
				remoteProps.get(WSHandlerConstants.ACTION));
		assertEquals(WSConstants.PW_TEXT,
				remoteProps.get(WSHandlerConstants.PASSWORD_TYPE));
		assertEquals("false",
				remoteProps.get(WSHandlerConstants.MUST_UNDERSTAND));
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				remoteProps.get(WSHandlerConstants.USER));
	}

	@Test
	public void testProtexServerProxyWithTimeoutIndefinite() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 0L);
		assertEquals(Long.valueOf(0L), server.getTimeout());
		assertEquals(
				new URL(testProperties.getProperty("TEST_PROTEX_SERVER_URL")),
				server.getBaseUrl());
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				server.getUsername());

		final Field remotePropsField = ProtexServerProxy.class
				.getDeclaredField("remoteProps");
		remotePropsField.setAccessible(true);
		final Map<String, Object> remoteProps = (Map<String, Object>) remotePropsField
				.get(server);

		final ProgrammedPasswordCallback passwordCallback = (ProgrammedPasswordCallback) remoteProps
				.get(WSHandlerConstants.PW_CALLBACK_REF);
		final Field passwordCacheField = ProgrammedPasswordCallback.class
				.getDeclaredField("passwordCache");
		passwordCacheField.setAccessible(true);
		final Map<String, String> passwordCache = (Map<String, String>) passwordCacheField
				.get(passwordCallback);
		final String password = passwordCache.get(testProperties
				.getProperty("TEST_USERNAME"));
		assertEquals(testProperties.getProperty("TEST_PASSWORD"), password);

		assertNotNull(remoteProps);
		assertTrue(remoteProps.containsKey(WSHandlerConstants.ACTION));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PASSWORD_TYPE));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.MUST_UNDERSTAND));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.USER));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PW_CALLBACK_REF));

		assertEquals(WSHandlerConstants.USERNAME_TOKEN,
				remoteProps.get(WSHandlerConstants.ACTION));
		assertEquals(WSConstants.PW_TEXT,
				remoteProps.get(WSHandlerConstants.PASSWORD_TYPE));
		assertEquals("false",
				remoteProps.get(WSHandlerConstants.MUST_UNDERSTAND));
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				remoteProps.get(WSHandlerConstants.USER));
	}

	@Test
	public void testProtexServerProxyWithTimeout() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);
		assertEquals(Long.valueOf(300000L * 10), server.getTimeout());
		assertEquals(
				new URL(testProperties.getProperty("TEST_PROTEX_SERVER_URL")),
				server.getBaseUrl());
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				server.getUsername());

		final Field remotePropsField = ProtexServerProxy.class
				.getDeclaredField("remoteProps");
		remotePropsField.setAccessible(true);
		final Map<String, Object> remoteProps = (Map<String, Object>) remotePropsField
				.get(server);

		final ProgrammedPasswordCallback passwordCallback = (ProgrammedPasswordCallback) remoteProps
				.get(WSHandlerConstants.PW_CALLBACK_REF);
		final Field passwordCacheField = ProgrammedPasswordCallback.class
				.getDeclaredField("passwordCache");
		passwordCacheField.setAccessible(true);
		final Map<String, String> passwordCache = (Map<String, String>) passwordCacheField
				.get(passwordCallback);
		final String password = passwordCache.get(testProperties
				.getProperty("TEST_USERNAME"));
		assertEquals(testProperties.getProperty("TEST_PASSWORD"), password);

		assertNotNull(remoteProps);
		assertTrue(remoteProps.containsKey(WSHandlerConstants.ACTION));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PASSWORD_TYPE));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.MUST_UNDERSTAND));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.USER));
		assertTrue(remoteProps.containsKey(WSHandlerConstants.PW_CALLBACK_REF));

		assertEquals(WSHandlerConstants.USERNAME_TOKEN,
				remoteProps.get(WSHandlerConstants.ACTION));
		assertEquals(WSConstants.PW_TEXT,
				remoteProps.get(WSHandlerConstants.PASSWORD_TYPE));
		assertEquals("false",
				remoteProps.get(WSHandlerConstants.MUST_UNDERSTAND));
		assertEquals(testProperties.getProperty("TEST_USERNAME"),
				remoteProps.get(WSHandlerConstants.USER));
	}

	@Test
	public void testGetLicenseApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final LicenseApiWeaved api = (LicenseApiWeaved) server.getLicenseApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("licenseApi");
		remoteApisField.setAccessible(true);
		final LicenseApi licenseApi = ((ThreadLocal<LicenseApi>) remoteApisField
				.get(server)).get();
		assertNotNull(licenseApi);
		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());
		// should pull the api from the cached map
		final LicenseApiWeaved api2 = (LicenseApiWeaved) server.getLicenseApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetLicenseApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final LicenseApiWeaved api = (LicenseApiWeaved) server.getLicenseApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("licenseApi");
		remoteApisField.setAccessible(true);
		final LicenseApi licenseApi = ((ThreadLocal<LicenseApi>) remoteApisField
				.get(server)).get();
		assertNotNull(licenseApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final LicenseApiWeaved api2 = (LicenseApiWeaved) server.getLicenseApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetCustomComponentManagementApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final SdkLogger logger = new SdkLogger(this.getClass());
		logger.setLogLevel(LogLevel.TRACE);
		server.setLogger(logger);

		final CustomComponentManagementApiWeaved api = (CustomComponentManagementApiWeaved) server.getCustomComponentManagementApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("customComponentManagementApi");
		remoteApisField.setAccessible(true);
		final CustomComponentManagementApiWeaved CustomComponentApi = ((ThreadLocal<CustomComponentManagementApiWeaved>) remoteApisField
				.get(server)).get();
		assertNotNull(CustomComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final CustomComponentManagementApiWeaved api2 = (CustomComponentManagementApiWeaved) server.getCustomComponentManagementApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetCustomComponentManagementApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final CustomComponentManagementApiWeaved api = (CustomComponentManagementApiWeaved) server.getCustomComponentManagementApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("customComponentManagementApi");
		remoteApisField.setAccessible(true);
		final CustomComponentManagementApiWeaved CustomComponentApi = ((ThreadLocal<CustomComponentManagementApiWeaved>) remoteApisField
				.get(server)).get();
		assertNotNull(CustomComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final CustomComponentManagementApiWeaved api2 = (CustomComponentManagementApiWeaved) server.getCustomComponentManagementApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetComponentApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ComponentApiWeaved api = (ComponentApiWeaved) server.getComponentApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("componentApi");
		remoteApisField.setAccessible(true);
		final ComponentApiWeaved standardComponentApi = ((ThreadLocal<ComponentApiWeaved>) remoteApisField
				.get(server)).get();
		assertNotNull(standardComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ComponentApiWeaved api2 = (ComponentApiWeaved) server.getComponentApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetComponentApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ComponentApiWeaved api = (ComponentApiWeaved) server.getComponentApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("componentApi");
		remoteApisField.setAccessible(true);
		final ComponentApiWeaved standardComponentApi = ((ThreadLocal<ComponentApiWeaved>) remoteApisField
				.get(server)).get();
		assertNotNull(standardComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ComponentApiWeaved api2 = (ComponentApiWeaved) server.getComponentApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetLocalComponentApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final LocalComponentApiWeaved api = (LocalComponentApiWeaved) server.getLocalComponentApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("localComponentApi");
		remoteApisField.setAccessible(true);
		final LocalComponentApi localComponentApi = ((ThreadLocal<LocalComponentApi>) remoteApisField
				.get(server)).get();
		assertNotNull(localComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final LocalComponentApiWeaved api2 = (LocalComponentApiWeaved) server.getLocalComponentApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetLocalComponentApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final LocalComponentApiWeaved api = (LocalComponentApiWeaved) server.getLocalComponentApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("localComponentApi");
		remoteApisField.setAccessible(true);
		final LocalComponentApi localComponentApi = ((ThreadLocal<LocalComponentApi>) remoteApisField
				.get(server)).get();
		assertNotNull(localComponentApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final LocalComponentApiWeaved api2 = (LocalComponentApiWeaved) server.getLocalComponentApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetObligationApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ObligationApiWeaved api = (ObligationApiWeaved) server.getObligationApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("obligationApi");
		remoteApisField.setAccessible(true);
		final ObligationApi obligationApi = ((ThreadLocal<ObligationApi>) remoteApisField
				.get(server)).get();
		assertNotNull(obligationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ObligationApiWeaved api2 = (ObligationApiWeaved) server.getObligationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetObligationApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ObligationApiWeaved api = (ObligationApiWeaved) server.getObligationApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("obligationApi");
		remoteApisField.setAccessible(true);
		final ObligationApi obligationApi = ((ThreadLocal<ObligationApi>) remoteApisField
				.get(server)).get();
		assertNotNull(obligationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ObligationApiWeaved api2 = (ObligationApiWeaved) server.getObligationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetPolicyApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final PolicyApiWeaved api = (PolicyApiWeaved) server.getPolicyApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("policyApi");
		remoteApisField.setAccessible(true);
		final PolicyApi policyApi = ((ThreadLocal<PolicyApi>) remoteApisField
				.get(server)).get();
		assertNotNull(policyApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final PolicyApiWeaved api2 = (PolicyApiWeaved) server.getPolicyApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetPolicyApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final PolicyApiWeaved api = (PolicyApiWeaved) server.getPolicyApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("policyApi");
		remoteApisField.setAccessible(true);
		final PolicyApi policyApi = ((ThreadLocal<PolicyApi>) remoteApisField
				.get(server)).get();
		assertNotNull(policyApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final PolicyApiWeaved api2 = (PolicyApiWeaved) server.getPolicyApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetExternalIdApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ExternalIdApiWeaved api = (ExternalIdApiWeaved) server.getExternalIdApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("externalIdApi");
		remoteApisField.setAccessible(true);
		final ExternalIdApi externalIdApi = ((ThreadLocal<ExternalIdApi>) remoteApisField
				.get(server)).get();
		assertNotNull(externalIdApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ExternalIdApiWeaved api2 = (ExternalIdApiWeaved) server.getExternalIdApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetExternalIdApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ExternalIdApiWeaved api = (ExternalIdApiWeaved) server.getExternalIdApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("externalIdApi");
		remoteApisField.setAccessible(true);
		final ExternalIdApi externalIdApi = ((ThreadLocal<ExternalIdApi>) remoteApisField
				.get(server)).get();
		assertNotNull(externalIdApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ExternalIdApiWeaved api2 = (ExternalIdApiWeaved) server.getExternalIdApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetFileComparisonApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final FileComparisonApiWeaved api = (FileComparisonApiWeaved) server.getFileComparisonApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("fileComparisonApi");
		remoteApisField.setAccessible(true);
		final FileComparisonApi fileComparisonApi = ((ThreadLocal<FileComparisonApi>) remoteApisField
				.get(server)).get();
		assertNotNull(fileComparisonApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final FileComparisonApiWeaved api2 = (FileComparisonApiWeaved) server.getFileComparisonApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetFileComparisonApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final FileComparisonApiWeaved api = (FileComparisonApiWeaved) server.getFileComparisonApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("fileComparisonApi");
		remoteApisField.setAccessible(true);
		final FileComparisonApi fileComparisonApi = ((ThreadLocal<FileComparisonApi>) remoteApisField
				.get(server)).get();
		assertNotNull(fileComparisonApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final FileComparisonApiWeaved api2 = (FileComparisonApiWeaved) server.getFileComparisonApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetTemplateApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final TemplateApiWeaved api = (TemplateApiWeaved) server.getTemplateApi();
		final Field templateApisField = ProtexServerProxy.class.getDeclaredField("templateApi");
		templateApisField.setAccessible(true);
		final TemplateApi templateApi = ((ThreadLocal<TemplateApi>) templateApisField.get(server))
				.get();
		assertNotNull(templateApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final TemplateApiWeaved api2 = (TemplateApiWeaved) server.getTemplateApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetTemplateApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final TemplateApiWeaved api = (TemplateApiWeaved) server.getTemplateApi(111L);
		final Field templateApisField = ProtexServerProxy.class.getDeclaredField("templateApi");
		templateApisField.setAccessible(true);
		final TemplateApi templateApi = ((ThreadLocal<TemplateApi>) templateApisField.get(server))
				.get();
		assertNotNull(templateApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final TemplateApiWeaved api2 = (TemplateApiWeaved) server.getTemplateApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetSynchronizationApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final SynchronizationApiWeaved api = (SynchronizationApiWeaved) server.getSynchronizationApi();
		final Field synchronizationApiField = ProtexServerProxy.class.getDeclaredField("synchronizationApi");
		synchronizationApiField.setAccessible(true);
		final SynchronizationApi synchronizationApi = ((ThreadLocal<SynchronizationApi>) synchronizationApiField
				.get(server)).get();
		assertNotNull(synchronizationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final SynchronizationApiWeaved api2 = (SynchronizationApiWeaved) server.getSynchronizationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetSynchronizationApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final SynchronizationApiWeaved api = (SynchronizationApiWeaved) server.getSynchronizationApi(111L);
		final Field synchronizationApiField = ProtexServerProxy.class.getDeclaredField("synchronizationApi");
		synchronizationApiField.setAccessible(true);
		final SynchronizationApi synchronizationApi = ((ThreadLocal<SynchronizationApi>) synchronizationApiField
				.get(server))
				.get();
		assertNotNull(synchronizationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final SynchronizationApiWeaved api2 = (SynchronizationApiWeaved) server.getSynchronizationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetProjectApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ProjectApiWeaved api = (ProjectApiWeaved) server.getProjectApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("projectApi");
		remoteApisField.setAccessible(true);
		final ProjectApi projectApi = ((ThreadLocal<ProjectApi>) remoteApisField
				.get(server)).get();
		assertNotNull(projectApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ProjectApiWeaved api2 = (ProjectApiWeaved) server.getProjectApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetProjectApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ProjectApiWeaved api = (ProjectApiWeaved) server.getProjectApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("projectApi");
		remoteApisField.setAccessible(true);
		final ProjectApi projectApi = ((ThreadLocal<ProjectApi>) remoteApisField
				.get(server)).get();
		assertNotNull(projectApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ProjectApiWeaved api2 = (ProjectApiWeaved) server.getProjectApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());
		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetCodeTreeApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final CodeTreeApiWeaved api = (CodeTreeApiWeaved) server.getCodeTreeApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("codeTreeApi");
		remoteApisField.setAccessible(true);
		final CodeTreeApi codeTreeApi = ((ThreadLocal<CodeTreeApi>) remoteApisField
				.get(server)).get();
		assertNotNull(codeTreeApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final CodeTreeApiWeaved api2 = (CodeTreeApiWeaved) server.getCodeTreeApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetCodeTreeApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final CodeTreeApiWeaved api = (CodeTreeApiWeaved) server.getCodeTreeApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("codeTreeApi");
		remoteApisField.setAccessible(true);
		final CodeTreeApi codeTreeApi = ((ThreadLocal<CodeTreeApi>) remoteApisField
				.get(server)).get();
		assertNotNull(codeTreeApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final CodeTreeApiWeaved api2 = (CodeTreeApiWeaved) server.getCodeTreeApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetBomApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final BomApiWeaved api = (BomApiWeaved) server.getBomApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("bomApi");
		remoteApisField.setAccessible(true);
		final BomApi bomApi = ((ThreadLocal<BomApi>) remoteApisField.get(server))
				.get();
		assertNotNull(bomApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final BomApiWeaved api2 = (BomApiWeaved) server.getBomApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetBomApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final BomApiWeaved api = (BomApiWeaved) server.getBomApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("bomApi");
		remoteApisField.setAccessible(true);
		final BomApi bomApi = ((ThreadLocal<BomApi>) remoteApisField.get(server))
				.get();
		assertNotNull(bomApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final BomApiWeaved api2 = (BomApiWeaved) server.getBomApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetDiscoveryApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final DiscoveryApiWeaved api = (DiscoveryApiWeaved) server.getDiscoveryApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("discoveryApi");
		remoteApisField.setAccessible(true);
		final DiscoveryApi discoveryApi = ((ThreadLocal<DiscoveryApi>) remoteApisField
				.get(server)).get();
		assertNotNull(discoveryApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final DiscoveryApiWeaved api2 = (DiscoveryApiWeaved) server.getDiscoveryApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetDiscoveryApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final DiscoveryApiWeaved api = (DiscoveryApiWeaved) server.getDiscoveryApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("discoveryApi");
		remoteApisField.setAccessible(true);
		final DiscoveryApi discoveryApi = ((ThreadLocal<DiscoveryApi>) remoteApisField
				.get(server)).get();
		assertNotNull(discoveryApi);
		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final DiscoveryApiWeaved api2 = (DiscoveryApiWeaved) server.getDiscoveryApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetIdentificationApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final IdentificationApiWeaved api = (IdentificationApiWeaved) server.getIdentificationApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("identificationApi");
		remoteApisField.setAccessible(true);
		final IdentificationApi identificationApi = ((ThreadLocal<IdentificationApi>) remoteApisField
				.get(server)).get();
		assertNotNull(identificationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final IdentificationApiWeaved api2 = (IdentificationApiWeaved) server.getIdentificationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetIdentificationApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final IdentificationApiWeaved api = (IdentificationApiWeaved) server.getIdentificationApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("identificationApi");
		remoteApisField.setAccessible(true);
		final IdentificationApi identificationApi = ((ThreadLocal<IdentificationApi>) remoteApisField
				.get(server)).get();
		assertNotNull(identificationApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final IdentificationApiWeaved api2 = (IdentificationApiWeaved) server.getIdentificationApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetReportApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ReportApiWeaved api = (ReportApiWeaved) server.getReportApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("reportApi");
		remoteApisField.setAccessible(true);
		final ReportApi reportApi = ((ThreadLocal<ReportApi>) remoteApisField
				.get(server)).get();
		assertNotNull(reportApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ReportApiWeaved api2 = (ReportApiWeaved) server.getReportApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetReportApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final ReportApiWeaved api = (ReportApiWeaved) server.getReportApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("reportApi");
		remoteApisField.setAccessible(true);
		final ReportApi reportApi = ((ThreadLocal<ReportApi>) remoteApisField
				.get(server)).get();
		assertNotNull(reportApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final ReportApiWeaved api2 = (ReportApiWeaved) server.getReportApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetRoleApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final RoleApiWeaved api = (RoleApiWeaved) server.getRoleApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("roleApi");
		remoteApisField.setAccessible(true);
		final RoleApi roleApi = ((ThreadLocal<RoleApi>) remoteApisField.get(server))
				.get();
		assertNotNull(roleApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());
		// should pull the api from the cached map
		final RoleApiWeaved api2 = (RoleApiWeaved) server.getRoleApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetRoleApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final RoleApiWeaved api = (RoleApiWeaved) server.getRoleApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("roleApi");
		remoteApisField.setAccessible(true);
		final RoleApi roleApi = ((ThreadLocal<RoleApi>) remoteApisField.get(server))
				.get();
		assertNotNull(roleApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final RoleApiWeaved api2 = (RoleApiWeaved) server.getRoleApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetUserApi() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final UserApiWeaved api = (UserApiWeaved) server.getUserApi();
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("userApi");
		remoteApisField.setAccessible(true);
		final UserApi userApi = ((ThreadLocal<UserApi>) remoteApisField.get(server))
				.get();
		assertNotNull(userApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L, http.getClient().getReceiveTimeout());
		assertEquals(300000L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final UserApiWeaved api2 = (UserApiWeaved) server.getUserApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L, http2.getClient().getReceiveTimeout());
		assertEquals(300000L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}

	@Test
	public void testGetUserApiTimeout() throws Exception {
		final ProtexServerProxy server = getServerProxy();
		final UserApiWeaved api = (UserApiWeaved) server.getUserApi(111L);
		final Field remoteApisField = ProtexServerProxy.class
				.getDeclaredField("userApi");
		remoteApisField.setAccessible(true);
		final UserApi userApi = ((ThreadLocal<UserApi>) remoteApisField.get(server))
				.get();
		assertNotNull(userApi);

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(111L, http.getClient().getReceiveTimeout());
		assertEquals(111L, http.getClient().getConnectionTimeout());

		// should pull the api from the cached map
		final UserApiWeaved api2 = (UserApiWeaved) server.getUserApi(234L);
		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(111L, http2.getClient().getReceiveTimeout());
		assertEquals(111L, http2.getClient().getConnectionTimeout());

		assertEquals(http, http2);
		assertEquals(client, client2);
	}


	@Test
	public void testProtexServerProxyWithProxy() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);

		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, false);

		final Field proxyServerField = ProtexServerProxy.class
				.getDeclaredField("proxyServer");
		proxyServerField.setAccessible(true);
		final String proxyServer = (String) proxyServerField.get(server);
		assertEquals(testProperties.getProperty("TEST_PROXY_SERVER"),
				proxyServer);

		final Field proxyPortField = ProtexServerProxy.class
				.getDeclaredField("proxyPort");
		proxyPortField.setAccessible(true);
		final int port = (Integer) proxyPortField.get(server);
		assertEquals(
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				Integer.valueOf(port));
		final Field proxyTypeField = ProtexServerProxy.class
				.getDeclaredField("proxyType");
		proxyTypeField.setAccessible(true);
		final ProxyServerType proxyTypeValue = (ProxyServerType) proxyTypeField
				.get(server);
		assertEquals(ProxyServerType.HTTP, proxyTypeValue);
	}

	@Test
	public void testProtexServerProxyWithProxyUpdateApis() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);
		final UserApiWeaved api = (UserApiWeaved) server.getUserApi();

		final Client client = ClientProxy.getClient(api.getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertEquals(300000L * 10, http.getClient().getReceiveTimeout());
		assertEquals(300000L * 10, http.getClient().getConnectionTimeout());
		assertNull(http.getClient().getProxyServer());

		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, true);

		final UserApiWeaved api2 = (UserApiWeaved) server.getUserApi();

		final Client client2 = ClientProxy.getClient(api2.getApi());
		final HTTPConduit http2 = (HTTPConduit) client2.getConduit();
		assertEquals(300000L * 10, http2.getClient().getReceiveTimeout());
		assertEquals(300000L * 10, http2.getClient().getConnectionTimeout());
		assertEquals(testProperties.getProperty("TEST_PROXY_SERVER"), http2
				.getClient().getProxyServer());
		assertEquals(
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				Integer.valueOf(http2.getClient().getProxyServerPort()));
		assertEquals(proxyType, http2.getClient().getProxyServerType());

		final Field proxyServerField = ProtexServerProxy.class
				.getDeclaredField("proxyServer");
		proxyServerField.setAccessible(true);
		final String proxyServer = (String) proxyServerField.get(server);
		assertEquals(testProperties.getProperty("TEST_PROXY_SERVER"),
				proxyServer);

		final Field proxyPortField = ProtexServerProxy.class
				.getDeclaredField("proxyPort");
		proxyPortField.setAccessible(true);
		final int port = (Integer) proxyPortField.get(server);
		assertEquals(
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				Integer.valueOf(port));

		final Field proxyTypeField = ProtexServerProxy.class
				.getDeclaredField("proxyType");
		proxyTypeField.setAccessible(true);
		final ProxyServerType proxyTypeValue = (ProxyServerType) proxyTypeField
				.get(server);
		assertEquals(ProxyServerType.HTTP, proxyTypeValue);

		final RoleApiWeaved api3 = (RoleApiWeaved) server.getRoleApi(111L);

		final Client client3 = ClientProxy.getClient(api3.getApi());
		final HTTPConduit http3 = (HTTPConduit) client3.getConduit();
		assertEquals(111L, http3.getClient().getReceiveTimeout());
		assertEquals(111L, http3.getClient().getConnectionTimeout());
		assertEquals(testProperties.getProperty("TEST_PROXY_SERVER"), http3
				.getClient().getProxyServer());
		assertEquals(
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				Integer.valueOf(http3.getClient().getProxyServerPort()));
		assertEquals(proxyType, http3.getClient().getProxyServerType());

	}

	@Test
	public void testProtexServerProxyWithProxyNullProxyName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception
		.expectMessage("Can not set the proxy with an empty proxy name.");

		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);

		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(null,
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, false);

	}

	@Test
	public void testProtexServerProxyWithProxyEmptyProxyName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception
		.expectMessage("Can not set the proxy with an empty proxy name.");

		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);

		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer("",
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, false);

	}

	@Test
	public void testProtexServerProxyWithProxyInvalidPort() throws Exception {
		exception.expect(RuntimeException.class);
		exception
		.expectMessage("Need to provide the port of the proxy server.");

		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);

		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				0, proxyType, false);
	}

	@Test
	public void testProtexServerProxyWithProxyNullProxyType() throws Exception {
		exception.expect(RuntimeException.class);
		exception
		.expectMessage("Can not set the proxy without knowing the proxy type. Ex: http, socks, etc.");

		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				null, false);

	}

	@Test
	public void testProtexServerProxyWithDebugLogging() throws Exception {
		final TestLogger logger = new TestLogger();
		logger.setLogLevel(LogLevel.DEBUG);
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);
		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, true);
		final Client client = ClientProxy.getClient(((UserApiWeaved) server.getUserApi()).getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertTrue(!logger.getTestOutput().isEmpty());
		assertTrue(logger.getTestExceptions().isEmpty());
		assertTrue(logger.getTestOutput().contains(
				"Using " + ProxyServerType.HTTP + " proxy: "
						+ testProperties.getProperty("TEST_PROXY_SERVER") + ":"
						+ testProperties.getProperty("TEST_PROXY_PORT")));
		assertTrue(logger.getTestOutput().contains(
				"set proxy server for service: "
						+ http.getTarget().getAddress().getValue()));
		assertTrue("Expected 2, got " + logger.getTestOutput().size(), logger.getTestOutput().size() == 2);
	}

	@Test
	public void testProtexServerProxyWithTraceLogging() throws Exception {
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 1000);
		logger.setLogLevel(LogLevel.TRACE);
		server.setLogger(logger);
		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, true);
		try {
			server.getUserApi().getUserAccountsUrl();
		} catch (final SdkFault f) {
			throw f;
		}
		final Client client = ClientProxy.getClient(((UserApiWeaved) server.getUserApi()).getApi());
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		assertTrue(!logger.getTestOutput().isEmpty());
		assertTrue(logger.getTestExceptions().isEmpty());
		assertTrue(logger.getTestOutput().contains(
				"Using " + ProxyServerType.HTTP + " proxy: "
						+ testProperties.getProperty("TEST_PROXY_SERVER") + ":"
						+ testProperties.getProperty("TEST_PROXY_PORT")));
		assertTrue(logger.getTestOutput().contains(
				"set proxy server for service: "
						+ http.getTarget().getAddress().getValue()));
		assertTrue(logger.printTestOutput() + "\n  Expected > 3, got " + logger.getTestOutput().size(), logger.getTestOutput().size() > 3);
		assertTrue("Log (expect \"Retrieving Api of class\": " + logger.getTestOutput().get(1), logger.getTestOutput().get(1)
				.contains("Retrieving Api of class"));
		assertTrue(logger.getTestOutput().get(3).contains("Executing method"));
		assertTrue(logger.printTestOutput()
				.contains("Execution time of method"));
	}

	@Test
	public void testProtexServerProxyWithTraceLoggingAndContextLoading()
			throws Exception {
		final TestLogger logger = new TestLogger();
		logger.setLogLevel(LogLevel.TRACE);
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 1000);
		server.setLogger(logger);
		server.setUseContextClassLoader(true);
		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));
		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, true);
		try {
			server.getUserApi().getUserAccountsUrl();
		} catch (final SdkFault f) {
			throw f;
		} finally {
			server.setUseContextClassLoader(false);
		}

		assertTrue(!logger.getTestOutput().isEmpty());
		assertTrue(logger.getTestExceptions().isEmpty());
		assertTrue(logger.printTestOutput() + "\n Expected > 4, got " + logger.getTestOutput().size(), logger.getTestOutput().size() > 4);
		assertTrue(logger.printTestOutput(), logger.printTestOutput().contains("useContextClassloader ..."));
	}

	@Test
	public void testProtexServerProxyWithLoggingDebuggingOff() throws Exception {
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);
		final ProxyServerType proxyType = ProxyServerType.fromValue(testProperties
				.getProperty("TEST_PROXY_TYPE"));

		server.setProxyServer(testProperties.getProperty("TEST_PROXY_SERVER"),
				Integer.valueOf(testProperties.getProperty("TEST_PROXY_PORT")),
				proxyType, true);
		assertTrue("Expected empty, got " + logger.getTestOutput().size(), logger.getTestOutput().isEmpty());
		assertTrue(logger.getTestExceptions().isEmpty());
	}

	@Test
	public void testGetApiInvalidServerUrl() throws Exception {
		exception.expect(MalformedURLException.class);
		exception.expectMessage("no protocol: NOTAVALID/URL");
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy("NOTAVALID/URL",
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);
		server.getUserApi();
	}

	@Test
	public void testGetApiUnreachableServer() throws Exception {
		exception.expect(RuntimeException.class);
		exception
		.expectMessage("java.net.UnknownHostException: THISSERVERSHOULDNOTEXIST");
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy(
				"http://THISSERVERSHOULDNOTEXIST",
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);

		try {
			server.getUserApi().getUserAccountsUrl();
		} catch (final SdkFault f) {
			throw f;
		}
	}

	@Test(expected = SdkFault.class)
	public void testSdkFaultWrapper() throws Exception {
		final TestLogger logger = new TestLogger();
		logger.setLogLevel(LogLevel.DEBUG);
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);
		try {
			final List<LicenseOriginType> licenseTypes = Arrays
					.asList(LicenseOriginType.values());
			// PROJECT_LOCAL is not an allowed value for filterByLicenseType parameter --> SDKFault thrown
			server.getLicenseApi().getLicenses(
					licenseTypes,
					ProtexPageFilterFactory.createLicenseInfoPageFilter(0,
							Integer.MAX_VALUE, false));
		} catch (final SdkFault e) {
			// somehow the order of certain attributes is swapped between my client
			// and the Jenkins-CI, so make partial text asserts.
			assertTrue(
					"starts with: " + logger.printTestOutput(),
					logger.printTestOutput()
					.startsWith(
							"SDKFAULT {Method : List com.blackducksoftware.integration.protex.sdk.api.LicenseApiWeaved.getLicenses(List, LicenseInfoPageFilter) :: Input Parameters "));
			assertTrue(
					"ends with: " + logger.printTestOutput(),
					logger.printTestOutput()
					.endsWith(
							" does not support the value \"PROJECT_LOCAL\" for the argument \"filterByLicenseType\".}"));
			throw e;
		}
		Assert.assertTrue("Should throw execption", false);
	}

	@Test
	public void testGetApiInvalidUserIncludedPrefix() throws Exception {
		exception.expect(ServerConfigException.class);
		exception.expectMessage("Do not include '/protex' in the Server Url.");
		new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL")
				+ "/protex",
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300000L * 10);
	}

	@Test
	public void testSingleThreadApiCalls() throws Exception {
		// FIXME - I'm not quite sure what needs testing here - all other api calls are single threaded anyway - may be
		// this was before we made the class loader switching standard?
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"),
				300000L * 10);
		server.setLogger(logger);
		final long startNano = System.nanoTime();
		long collectiveTimes = 0L;
		try {
			final long start1 = System.nanoTime();
			server.getUserApi().getUserByEmail(
					testProperties.getProperty("TEST_USERNAME"));
			collectiveTimes += System.nanoTime() - start1;
		} catch (final SdkFault f) {
			throw f;
		}
		try {
			final long start2 = System.nanoTime();
			server.getUserApi().getUserByEmail(
					testProperties.getProperty("TEST_USERNAME"));
			collectiveTimes += System.nanoTime() - start2;
		} catch (final SdkFault f) {
			throw f;
		}
		try {
			final long start3 = System.nanoTime();
			server.getUserApi().getUserByEmail(testProperties.getProperty("TEST_USERNAME"));
			collectiveTimes += System.nanoTime() - start3;
		} catch (final SdkFault f) {
			throw f;
		}
		try {
			final long start4 = System.nanoTime();
			server.getUserApi().getUserByEmail(testProperties.getProperty("TEST_USERNAME"));
			collectiveTimes += System.nanoTime() - start4;
		} catch (final SdkFault f) {
			throw f;
		}

		final long endNano = System.nanoTime() - startNano;

		assertTrue(endNano >= collectiveTimes);
	}

	@Test
	public void testMultiThreadApiCalls() throws Exception {
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy(
				testProperties.getProperty("TEST_PROTEX_SERVER_URL"),
				testProperties.getProperty("TEST_USERNAME"),
				testProperties.getProperty("TEST_PASSWORD"), 300L * 10);
		server.setLogger(logger);
		// Should be thread safe
		final ArrayList<TestThread> threads = new ArrayList<TestThread>();
		threads.add(new TestThread(server, "1111"));
		threads.add(new TestThread(server, "2222"));
		threads.add(new TestThread(server, "3333"));
		threads.add(new TestThread(server, "4444"));

		final long startNano = System.nanoTime();
		for (final TestThread thread : threads) {
			while (thread.running) {
				Thread.sleep(100);
			}
		}
		final long durationNano = System.nanoTime() - startNano;
		System.out.println("Total running time of multithreaded api calls : "
				+ durationNano / 1000 + " \u03BCs");
		long collectiveTimes = 0L;
		for (final TestThread thread : threads) {
			collectiveTimes += thread.executionTime;
		}
		System.out
		.println("Sum of the running times of each threaded api call : "
				+ collectiveTimes / 1000 + " \u03BCs");
		assertTrue("durationNano (" + (durationNano / 1000) + " \u03BCs"
				+ ") >= collective Times (" + (collectiveTimes / 1000)
				+ " \u03BCs)", durationNano < collectiveTimes);
	}

	class TestThread implements Runnable {
		private final String threadName;

		private final ProtexServerProxy server;

		public long executionTime;

		public boolean running = true;

		TestThread(final ProtexServerProxy server, final String threadName) {
			this.server = server;
			this.threadName = threadName;
			System.out.println("New thread : " + threadName);
			final Thread thread = new Thread(this);
			thread.start();
		}

		@Override
		public void run() {
			System.out.println("Running thread : " + threadName);
			final long startNano = System.nanoTime();
			try {
				UserApi originalApi = null;
				try {
					for (int i = 0; i < 300; i++) {
						Thread.sleep(10);
						final UserApi userApi = server.getUserApi();
						if (originalApi == null) {
							originalApi = userApi;
							System.out.println("Thread " + threadName
									+ " UserApi= " + userApi);
						} else {
							// objects should be identical per thread
							assertTrue(userApi == originalApi);
						}

						userApi.getUserByEmail(testProperties
								.getProperty("TEST_USERNAME"));
					}
				} catch (final SdkFault e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage(), e);
				} catch (final ServerConfigException e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage(), e);
				} catch (final InterruptedException e) {
					System.err.println("[WARNING] interrupted" + e.getMessage());
				} catch (final Throwable t) {
					System.err.println("[WARNING]" + t.getMessage());
					t.printStackTrace();
					throw new RuntimeException(t.getMessage(), t);
				}
			} finally {
				executionTime = System.nanoTime() - startNano;
				running = false;
				System.out.println("Thread " + threadName + " exiting.");
			}
		}
	}

}
