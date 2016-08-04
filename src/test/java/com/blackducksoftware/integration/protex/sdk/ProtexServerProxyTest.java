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
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
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
import com.blackducksoftware.integration.suite.sdk.logging.LogLevel;
import com.blackducksoftware.integration.suite.sdk.logging.SdkLogger;
import com.blackducksoftware.integration.suite.sdk.util.ProgrammedPasswordCallback;
import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.comparison.FileComparisonApi;
import com.blackducksoftware.sdk.protex.license.LicenseApi;
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

public class ProtexServerProxyTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();


	public ProtexServerProxy getServerProxy() throws Exception {
		return new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", "TEST_PASSWORD");
	}

	@Test
	public void testProtexServerProxyNullUrl() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy(null,
				"TEST_USERNAME", "TEST_PASSWORD");

	}

	@Test
	public void testProtexServerProxyNullUserName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				"https://www.google.com", null, "TEST_PASSWORD");
	}

	@Test
	public void testProtexServerProxyNullPassword() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", null);
	}

	@Test
	public void testProtexServerProxyEmptyUrl() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy("", "TEST_USERNAME", "TEST_PASSWORD");
	}

	@Test
	public void testProtexServerProxyEmptyUserName() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				"https://www.google.com", "", "TEST_PASSWORD");
	}

	@Test
	public void testProtexServerProxyEmptyPassword() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", null);
	}

	@Test
	public void testProtexServerProxyNullUrlWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy(null,
				"TEST_USERNAME", "TEST_PASSWORD", 0L);
	}

	@Test
	public void testProtexServerProxyNullUserNameWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				"https://www.google.com", null, "TEST_PASSWORD", 0L);
	}

	@Test
	public void testProtexServerProxyNullPasswordWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", null, 0L);
	}

	@Test
	public void testProtexServerProxyEmptyUrlWithTimeout() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Server Url.");
		new ProtexServerProxy("", "TEST_USERNAME", "TEST_PASSWORD", 0L);
	}

	@Test
	public void testProtexServerProxyEmptyUserNameWithTimeout()
			throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Username.");
		new ProtexServerProxy(
				"https://www.google.com", "", "TEST_PASSWORD", 0L);
	}

	@Test
	public void testProtexServerProxyEmptyPasswordWithTimeout()
			throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Did not provide a valid Protex Password.");
		new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", "", 0L);
	}

	@Test
	public void testProtexServerProxy() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", "TEST_PASSWORD");
		assertEquals(Long.valueOf(300000L), server.getTimeout());
		assertEquals(
				new URL("https://www.google.com"),
				server.getBaseUrl());
		assertEquals("TEST_USERNAME",
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
		final String password = passwordCache.get("TEST_USERNAME");
		assertEquals("TEST_PASSWORD", password);

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
		assertEquals("TEST_USERNAME",
				remoteProps.get(WSHandlerConstants.USER));
	}

	@Test
	public void testProtexServerProxyWithTimeoutIndefinite() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", "TEST_PASSWORD", 0L);
		assertEquals(Long.valueOf(0L), server.getTimeout());
		assertEquals(
				new URL("https://www.google.com"),
				server.getBaseUrl());
		assertEquals("TEST_USERNAME",
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
		final String password = passwordCache.get("TEST_USERNAME");
		assertEquals("TEST_PASSWORD", password);

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
		assertEquals("TEST_USERNAME",
				remoteProps.get(WSHandlerConstants.USER));
	}

	@Test
	public void testProtexServerProxyWithTimeout() throws Exception {
		final ProtexServerProxy server = new ProtexServerProxy(
				"https://www.google.com", "TEST_USERNAME", "TEST_PASSWORD", 300000L * 10);
		assertEquals(Long.valueOf(300000L * 10), server.getTimeout());
		assertEquals(
				new URL("https://www.google.com"),
				server.getBaseUrl());
		assertEquals("TEST_USERNAME",
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
		final String password = passwordCache.get("TEST_USERNAME");
		assertEquals("TEST_PASSWORD", password);

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
		assertEquals("TEST_USERNAME",
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
	public void testGetApiInvalidServerUrl() throws Exception {
		exception.expect(MalformedURLException.class);
		exception.expectMessage("no protocol: NOTAVALID/URL");
		final TestLogger logger = new TestLogger();
		final ProtexServerProxy server = new ProtexServerProxy("NOTAVALID/URL",
				"TEST_USERNAME", "TEST_PASSWORD",
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
				"TEST_USERNAME", "TEST_PASSWORD",
				300000L * 10);
		server.setLogger(logger);

		try {
			server.getUserApi().getUserAccountsUrl();
		} catch (final SdkFault f) {
			throw f;
		}
	}

	@Test
	public void testGetApiInvalidUserIncludedPrefix() throws Exception {
		exception.expect(ServerConfigException.class);
		exception.expectMessage("Do not include '/protex' in the Server Url.");
		new ProtexServerProxy(
				"https://www.google.com"
						+ "/protex",
						"TEST_USERNAME", "TEST_PASSWORD", 300000L * 10);
	}

}
