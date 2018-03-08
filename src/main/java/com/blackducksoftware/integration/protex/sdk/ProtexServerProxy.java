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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.ProxyServerType;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

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
import com.blackducksoftware.integration.suite.sdk.logging.ConnectionTimeTranslator;
import com.blackducksoftware.integration.suite.sdk.logging.IntLogger;
import com.blackducksoftware.integration.suite.sdk.logging.SdkLogger;
import com.blackducksoftware.integration.suite.sdk.util.ProgrammedLicenseCheckEncryptedPasswordCallback;
import com.blackducksoftware.integration.suite.sdk.util.ProgrammedLicenseCheckPasswordCallback;
import com.blackducksoftware.sdk.protex.comparison.FileComparisonApi;
import com.blackducksoftware.sdk.protex.component.ComponentApi;
import com.blackducksoftware.sdk.protex.component.custom.CustomComponentManagementApi;
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

public class ProtexServerProxy {

	public static final String ENCRYPTED_PASSWORD_FILE = "com.blackducksoftware.integration.sdk.password.encryptedFile";

	public static final Long DEFAULT_TIMEOUT = 300L * 1000;

	private static final String PROTEX_SDK_BASE_URI = "/protex-sdk";

	private static final String PROTEX_SDK_VERSION = "/v7_0";

	private final Map<String, Object> remoteProps = new HashMap<String, Object>();

	private final URL baseUrl;

	private final String userName;

	private final Long timeout;

	private IntLogger logger;

	@SuppressWarnings("unused")
	// monitored and used by Aspect around this class
	private boolean useContextClassLoader = false;

	private String proxyServer = null;

	private int proxyPort = -1;

	private String proxyUsername = null;

	private String proxyPassword = null;

	private ProxyServerType proxyType = null;

	private final ThreadLocal<UserApiWeaved> userApi = new ThreadLocal<UserApiWeaved>();

	private final ThreadLocal<RoleApiWeaved> roleApi = new ThreadLocal<RoleApiWeaved>();

	private final ThreadLocal<ReportApiWeaved> reportApi = new ThreadLocal<ReportApiWeaved>();

	private final ThreadLocal<IdentificationApiWeaved> identificationApi = new ThreadLocal<IdentificationApiWeaved>();

	private final ThreadLocal<DiscoveryApiWeaved> discoveryApi = new ThreadLocal<DiscoveryApiWeaved>();

	private final ThreadLocal<BomApiWeaved> bomApi = new ThreadLocal<BomApiWeaved>();

	private final ThreadLocal<CodeTreeApiWeaved> codeTreeApi = new ThreadLocal<CodeTreeApiWeaved>();

	private final ThreadLocal<LocalComponentApiWeaved> localComponentApi = new ThreadLocal<LocalComponentApiWeaved>();

	private final ThreadLocal<LicenseApiWeaved> licenseApi = new ThreadLocal<LicenseApiWeaved>();

	private final ThreadLocal<PolicyApiWeaved> policyApi = new ThreadLocal<PolicyApiWeaved>();

	private final ThreadLocal<ExternalIdApiWeaved> externalIdApi = new ThreadLocal<ExternalIdApiWeaved>();

	private final ThreadLocal<FileComparisonApiWeaved> fileComparisonApi = new ThreadLocal<FileComparisonApiWeaved>();

	private final ThreadLocal<ProjectApiWeaved> projectApi = new ThreadLocal<ProjectApiWeaved>();

	private final ThreadLocal<ObligationApiWeaved> obligationApi = new ThreadLocal<ObligationApiWeaved>();

	private final ThreadLocal<CustomComponentManagementApiWeaved> customComponentManagementApi = new ThreadLocal<CustomComponentManagementApiWeaved>();

	private final ThreadLocal<ComponentApiWeaved> componentApi = new ThreadLocal<ComponentApiWeaved>();

	private final ThreadLocal<TemplateApiWeaved> templateApi = new ThreadLocal<TemplateApiWeaved>();

	private final ThreadLocal<SynchronizationApiWeaved> synchronizationApi = new ThreadLocal<SynchronizationApiWeaved>();

	/**
	 *
	 * @param baseUrl
	 *            The Protex server URL
	 * @param userName
	 *            The User's Protex UserName
	 * @param password
	 *            Optional: The User's Protex password, If not provided then the
	 *            ENCRYPTED_PASSWORD_FILE property is checked for the encrypted
	 *            file location
	 * @param loggerInt
	 *            Optional: only provide if you want to log messages this way
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws ServerConfigException
	 */
	public ProtexServerProxy(final String baseUrl, final String userName, final String password)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException, ServerConfigException {
		this(baseUrl, userName, password, DEFAULT_TIMEOUT);
	}

	/**
	 *
	 * @param baseUrl
	 *            The Protex server URL
	 * @param userName
	 *            The User's Protex UserName
	 * @param password
	 *            Optional: The User's Protex password, If not provided then the
	 *            ENCRYPTED_PASSWORD_FILE property is checked for the encrypted
	 *            file location
	 * @param loggerInt
	 *            Optional: only provide if you want to log messages this way
	 * @param timeout
	 *            Long, connection timeout in MILLISECONDS
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws ServerConfigException
	 */
	public ProtexServerProxy(final String baseUrl, final String userName, final String password,
			final long timeout) throws IOException, InvalidKeyException,
	NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException,
	ServerConfigException {
		if (StringUtils.isEmpty(baseUrl)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Server Url.");
		}
		if (StringUtils.isEmpty(userName)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Username.");
		}
		setLogger(new SdkLogger(ProtexServerProxy.class));
		this.userName = userName;
		final String encryptedPasswordsFilePath = System
				.getProperty(ENCRYPTED_PASSWORD_FILE);

		if (StringUtils.isEmpty(password)
				&& StringUtils.isEmpty(encryptedPasswordsFilePath)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Password.");
		} else if (!StringUtils.isEmpty(encryptedPasswordsFilePath)) {
			final File encrPassFile = new File(encryptedPasswordsFilePath);
			if (!encrPassFile.exists()) {
				throw new IllegalArgumentException(
						"The password file does not exist at : "
								+ encryptedPasswordsFilePath);
			}
			String encPassword = "";

			final Properties userNamePasswordProps = new Properties();
			InputStream is = null;
			try {
				is = new FileInputStream(encrPassFile);
				try {
					userNamePasswordProps.load(is);
				} catch (final IOException e) {
					logger.error("Reading Encrypted password file failed!", e);
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
			encPassword = userNamePasswordProps.getProperty(userName);
			if (StringUtils.isEmpty(encPassword)) {
				throw new IllegalArgumentException(
						"There is no matching UserName and Password in the password file.");
			}
			// TODO use decrypted password in the init, OR might be possible in
			// the ProgrammedPasswordCallback??
			initConnectionProperties(userName, encPassword, true);
		} else {
			initConnectionProperties(userName, password, false);
		}
		this.timeout = Long.valueOf(timeout);
		this.baseUrl = new URL(baseUrl);
		if (!StringUtils.isEmpty(this.baseUrl.getPath())) {
			// This should catch any mistakes made by the User when providing
			// the Server Url
			throw new ServerConfigException("Do not include '"
					+ this.baseUrl.getPath() + "' in the Server Url.");
		}
		if (StringUtils.isEmpty(this.baseUrl.getHost())) {
			// This should catch any mistakes made by the User when providing
			// the Server Url
			throw new ServerConfigException("There was no Host name provided.");
		}

	}

	/**
	 *
	 * @param baseUrl
	 *            The Protex server URL
	 * @param userName
	 *            The User's Protex UserName
	 * @param encryptedPassword
	 *            The User's Protex encrypted password
	 * @param timeout
	 *            Long, connection timeout in MILLISECONDS
	 * @param isPasswordEncrypted
	 *            true if the password is encrypted
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws ServerConfigException
	 */
	public ProtexServerProxy(final String baseUrl, final String userName, final String encryptedPassword,
			final long timeout, final boolean isPasswordEncrypted) throws IOException, InvalidKeyException,
	NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException,
	ServerConfigException {
		if (StringUtils.isEmpty(baseUrl)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Server Url.");
		}
		if (StringUtils.isEmpty(userName)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Username.");
		}
		if (StringUtils.isEmpty(encryptedPassword)) {
			throw new IllegalArgumentException(
					"Did not provide a valid Protex Password.");
		}
		setLogger(new SdkLogger(ProtexServerProxy.class));
		this.userName = userName;

		initConnectionProperties(userName, encryptedPassword, isPasswordEncrypted);

		this.timeout = Long.valueOf(timeout);
		this.baseUrl = new URL(baseUrl);
		if (!StringUtils.isEmpty(this.baseUrl.getPath())) {
			// This should catch any mistakes made by the User when providing
			// the Server Url
			throw new ServerConfigException("Do not include '"
					+ this.baseUrl.getPath() + "' in the Server Url.");
		}
		if (StringUtils.isEmpty(this.baseUrl.getHost())) {
			// This should catch any mistakes made by the User when providing
			// the Server Url
			throw new ServerConfigException("There was no Host name provided.");
		}

	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(final IntLogger logger) {
		this.logger = logger;
	}

	/**
	 * @return the logger
	 */
	public IntLogger getLogger() {
		return logger;
	}

	/**
	 * Set to true if you run inside a managed container, like a web app
	 * container (i.e. tomcat, etc.)
	 *
	 * CXF does attempt to load certain resources in the
	 * Thread-contextClassLoader and that can lead to ClassDefNotFound
	 * exceptions, because this class is loaded in a different class loader then
	 * some of the CXF classes.
	 *
	 * @param useContextClassLoader
	 */
	public void setUseContextClassLoader(final boolean useContextClassLoader) {
		this.useContextClassLoader = useContextClassLoader;
	}

	public URL getBaseUrl() {
		return baseUrl;
	}

	public String getUsername() {
		return userName;
	}

	public Long getTimeout() {
		return timeout;
	}

	/**
	 * Set the Proxy server values for all get*Api() method calls following
	 *
	 * @param server
	 *            - The name of the proxy server, i.e. proxy.example.com
	 * @param port
	 *            - The port of the proxy server, i.e. 3128
	 * @param type
	 *            - The type of proxy server. i.e. HTTP or Socks
	 * @param updateExistingApis
	 *            - Update any cached Api's that have already been retrieved
	 * @param proxyUser
	 *            - The Username to connect to the proxy server
	 * @param proxyPass
	 *            - The Password to connect to the proxy server
	 *
	 */
	public void setProxyServer(final String server, final int port, final ProxyServerType type,
			final boolean updateExistingApis, final String proxyUser, final String proxyPass) {
		if (StringUtils.isEmpty(server)) {
			throw new IllegalArgumentException(
					"Can not set the proxy with an empty proxy name.");
		}
		if (port == 0) {
			throw new IllegalArgumentException(
					"Need to provide the port of the proxy server.");
		}
		if (type == null) {
			throw new IllegalArgumentException(
					"Can not set the proxy without knowing the proxy type. Ex: http, socks, etc.");
		}
		proxyServer = server;
		proxyPort = port;
		proxyType = type;
		proxyUsername = proxyUser;
		proxyPassword = proxyPass;

		logger.debug("Using " + proxyType + " proxy: " + proxyServer + ":"
				+ proxyPort);

		if (!StringUtils.isEmpty(proxyUsername)) {
			logger.debug("Proxy User : " + proxyUsername);
		}

		if (updateExistingApis) {
			if (userApi.get() != null) {
				setProxyServer(userApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (roleApi.get() != null) {
				setProxyServer(roleApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (reportApi.get() != null) {
				setProxyServer(reportApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (identificationApi.get() != null) {
				setProxyServer(identificationApi.get().getApi(), server, port,
						type, proxyUser, proxyPass);
			}
			if (discoveryApi.get() != null) {
				setProxyServer(discoveryApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (bomApi.get() != null) {
				setProxyServer(bomApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (codeTreeApi.get() != null) {
				setProxyServer(codeTreeApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (localComponentApi.get() != null) {
				setProxyServer(localComponentApi.get().getApi(), server, port,
						type, proxyUser, proxyPass);
			}
			if (licenseApi.get() != null) {
				setProxyServer(licenseApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (policyApi.get() != null) {
				setProxyServer(policyApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (externalIdApi.get() != null) {
				setProxyServer(externalIdApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (fileComparisonApi.get() != null) {
				setProxyServer(fileComparisonApi.get().getApi(), server, port,
						type, proxyUser, proxyPass);
			}
			if (projectApi.get() != null) {
				setProxyServer(projectApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (obligationApi.get() != null) {
				setProxyServer(obligationApi.get().getApi(), server, port, type, proxyUser, proxyPass);
			}
			if (customComponentManagementApi.get() != null) {
				setProxyServer(customComponentManagementApi.get(), server, port, type, proxyUser, proxyPass);
			}
			if (componentApi.get() != null) {
				setProxyServer(componentApi.get(), server, port, type, proxyUser, proxyPass);
			}
			if (templateApi.get() != null) {
				setProxyServer(templateApi.get(), server, port, type, proxyUser, proxyPass);
			}
			if (synchronizationApi.get() != null) {
				setProxyServer(synchronizationApi.get(), server, port, type, proxyUser, proxyPass);
			}
		}
	}

	/**
	 * Set the Proxy server values for all get*Api() method calls following
	 *
	 * @param server
	 *            - The name of the proxy server, i.e. proxy.example.com
	 * @param port
	 *            - The port of the proxy server, i.e. 3128
	 * @param type
	 *            - The type of proxy server. i.e. HTTP or Socks
	 * @param updateExistingApis
	 *            - Update any cached Api's that have already been retrieved
	 *
	 */
	public void setProxyServer(final String server, final int port, final ProxyServerType type,
			final boolean updateExistingApis) {
		this.setProxyServer(server, port, type, updateExistingApis, null, null);
	}

	/**
	 * Set the Proxy server values for a service
	 *
	 * @param serviceApi
	 *            - The object representing the serviceApi
	 * @param server
	 *            - The name of the proxy server, i.e. proxy.example.com
	 * @param port
	 *            - The port of the proxy server, i.e. 3128
	 * @param type
	 *            - The type of proxy server. i.e. HTTP or Socks
	 *
	 */
	private <T> T setProxyServer(final T serviceApi, final String server, final int port,
			final ProxyServerType type, final String proxyUsername, final String proxyPassword) {
		final Client client = ClientProxy.getClient(serviceApi);
		/* set timeout */
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		logger.debug("set proxy server for service: "
				+ http.getTarget().getAddress().getValue());

		if (http.getClient() != null) {
			final HTTPClientPolicy clientPolicy = http.getClient();
			clientPolicy.setProxyServer(server);
			clientPolicy.setProxyServerPort(port);
			clientPolicy.setProxyServerType(type);
			http.setClient(clientPolicy);
		} else {
			// in case the client policy was never set
			final HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
			clientPolicy.setProxyServer(server);
			clientPolicy.setProxyServerPort(port);
			clientPolicy.setProxyServerType(type);
			http.setClient(clientPolicy);
		}

		if (!StringUtils.isEmpty(proxyUsername) && !StringUtils.isEmpty(proxyPassword)) {
			if (http.getProxyAuthorization() != null) {
				final ProxyAuthorizationPolicy proxyAuth = http.getProxyAuthorization();
				proxyAuth.setUserName(proxyUsername);
				proxyAuth.setPassword(proxyPassword);
				http.setProxyAuthorization(proxyAuth);
			} else {
				// in case the client policy was never set
				final ProxyAuthorizationPolicy proxyAuth = new ProxyAuthorizationPolicy();
				proxyAuth.setUserName(proxyUsername);
				proxyAuth.setPassword(proxyPassword);
				http.setProxyAuthorization(proxyAuth);
			}
		}

		return serviceApi;
	}

	/**
	 * This gets the Api and adds it to the cache map. Once it is cached the
	 * timeout of that Api cannot be changed.
	 *
	 */
	protected <T> T getApi(final Class<T> apiClass, final String stubName)
			throws ServerConfigException {
		return getApi(apiClass, stubName, getTimeout());
	}

	/**
	 * This gets the Api and adds it to the cache map. Once it is cached the
	 * timeout of that Api cannot be changed.
	 *
	 */
	protected <T> T getApi(final Class<T> apiClass, final String stubName, final long timeout)
			throws ServerConfigException {
		logger.trace("Retrieving Api of class : "
				+ apiClass.getName()
				+ ", with stubName : "
				+ stubName
				+ ", and with timeout : "
				+ ConnectionTimeTranslator
				.getLoggableConnectionTimeout(timeout));

		final String endpointRef = getEndpointRef(stubName);

		final T api = setConnectionTimeout(
				instrumentService(getRemoteProxy(apiClass, endpointRef)),
				timeout);
		if (proxyType != null) {
			setProxyServer(api, proxyServer, proxyPort, proxyType, proxyUsername, proxyPassword);
		}
		return api;

	}

	private String getEndpointRef(final String stubName) throws ServerConfigException {
		try {

			final URL endpoint = new URL(getBaseUrl(), PROTEX_SDK_BASE_URI
					+ PROTEX_SDK_VERSION + stubName);
			return endpoint.toExternalForm();
		} catch (final MalformedURLException e) {
			throw new ServerConfigException("Invalid URL parameters", e);
		}
	}

	private <T> T getRemoteProxy(final Class<T> serviceClass, final String serviceUrl) {
		final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceClass);
		factory.setAddress(serviceUrl);
		return (T) factory.create();
	}

	/**
	 * Initialize the Connection Properties, such as user and password, for CXF
	 *
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 *
	 */
	private void initConnectionProperties(final String userName, final String password,
			final boolean isEncryptedPassword) throws InvalidKeyException,
	NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException {
		remoteProps.put(WSHandlerConstants.ACTION,
				WSHandlerConstants.USERNAME_TOKEN);
		remoteProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		remoteProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
		// Specify our userName
		remoteProps.put(WSHandlerConstants.USER, userName);
		// Callback used to retrieve password for given user.
		if (isEncryptedPassword) {
			final ProgrammedLicenseCheckEncryptedPasswordCallback callback = new ProgrammedLicenseCheckEncryptedPasswordCallback();
			callback.addUserNameAndPassword(userName, password);
			remoteProps.put(WSHandlerConstants.PW_CALLBACK_REF, callback);
		} else {
			final ProgrammedLicenseCheckPasswordCallback callback = new ProgrammedLicenseCheckPasswordCallback();
			callback.addUserNameAndPassword(userName, password);
			remoteProps.put(WSHandlerConstants.PW_CALLBACK_REF, callback);
		}
	}

	/**
	 * Instrument the service port object with authentication information and
	 * the appropriate handlers
	 *
	 * @param serviceApi
	 *            The object representing the serviceApiPort
	 */
	private <T> T instrumentService(final T serviceApi) {

		final Client client = ClientProxy.getClient(serviceApi);
		final Endpoint cxfEndpoint = client.getEndpoint();

		final WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(remoteProps);
		cxfEndpoint.getOutInterceptors().add(wssOut);

		return serviceApi;
	}

	/**
	 * Set the Http timeout value for a service port
	 *
	 * @param serviceApi
	 *            The object representing the serviceApiPort
	 *
	 * @param timeout
	 *            Optional http timeout in milliseconds, if INDEFINITE_TIMEOUT
	 *            (zero) the timeout is indefinite, if null the default timeout
	 *            is used
	 *
	 */
	private <T> T setConnectionTimeout(final T serviceApi, final long timeout) {
		final Client client = ClientProxy.getClient(serviceApi);
		final HTTPConduit http = (HTTPConduit) client.getConduit();
		// Set incoming and outgoing timeout values.
		if (http.getClient() != null) {
			final HTTPClientPolicy clientPolicy = http.getClient();
			clientPolicy.setConnectionTimeout(timeout);
			clientPolicy.setReceiveTimeout(timeout);
			http.setClient(clientPolicy);
		} else {
			// in case the client policy was never set
			final HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
			clientPolicy.setConnectionTimeout(timeout);
			clientPolicy.setReceiveTimeout(timeout);
			http.setClient(clientPolicy);
		}

		return serviceApi;
	}

	// Use this if ever logging password
	// private String getPasswordMask(String password) {
	// char[] mask = new char[password.length()];
	// Arrays.fill(mask, '*');
	// return new String(mask);
	// }

	public LicenseApi getLicenseApi() throws ServerConfigException {
		if (licenseApi.get() != null) {
			return licenseApi.get();
		} else {
			final LicenseApi api = getApi(LicenseApi.class, "/license"); //$NON-NLS-1$
			final LicenseApiWeaved weaved = new LicenseApiWeaved(api);
			licenseApi.set(weaved);
			return weaved;
		}
	}

	public LicenseApi getLicenseApi(final long timeout) throws ServerConfigException {
		if (licenseApi.get() != null) {
			return licenseApi.get();
		} else {
			final LicenseApi api = getApi(LicenseApi.class, "/license", timeout); //$NON-NLS-1$
			final LicenseApiWeaved weaved = new LicenseApiWeaved(api);
			licenseApi.set(weaved);
			return weaved;
		}
	}

	public CustomComponentManagementApi getCustomComponentManagementApi() throws ServerConfigException {
		if (customComponentManagementApi.get() != null) {
			return customComponentManagementApi.get();
		} else {
			final CustomComponentManagementApi api = getApi(CustomComponentManagementApi.class, "/customcomponentmanagement"); //$NON-NLS-1$
			final CustomComponentManagementApiWeaved weaved = new CustomComponentManagementApiWeaved(api);
			customComponentManagementApi.set(weaved);
			return weaved;
		}
	}

	public CustomComponentManagementApi getCustomComponentManagementApi(final long timeout) throws ServerConfigException {
		if (customComponentManagementApi.get() != null) {
			return customComponentManagementApi.get();
		} else {
			final CustomComponentManagementApi api = getApi(CustomComponentManagementApi.class, "/customcomponentmanagement", timeout); //$NON-NLS-1$
			final CustomComponentManagementApiWeaved weaved = new CustomComponentManagementApiWeaved(api);
			customComponentManagementApi.set(weaved);
			return weaved;
		}
	}

	public ComponentApi getComponentApi() throws ServerConfigException {
		if (componentApi.get() != null) {
			return componentApi.get();
		} else {
			final ComponentApi api = getApi(ComponentApi.class, "/component"); //$NON-NLS-1$
			final ComponentApiWeaved weaved = new ComponentApiWeaved(api);
			componentApi.set(weaved);
			return weaved;
		}
	}

	public ComponentApi getComponentApi(final long timeout) throws ServerConfigException {
		if (componentApi.get() != null) {
			return componentApi.get();
		} else {
			final ComponentApi api = getApi(ComponentApi.class, "/component", timeout); //$NON-NLS-1$
			final ComponentApiWeaved weaved = new ComponentApiWeaved(api);
			componentApi.set(weaved);
			return weaved;
		}
	}

	public LocalComponentApi getLocalComponentApi()
			throws ServerConfigException {
		if (localComponentApi.get() != null) {
			return localComponentApi.get();
		} else {
			final LocalComponentApi api = getApi(LocalComponentApi.class, "/localcomponent"); //$NON-NLS-1$
			final LocalComponentApiWeaved weaved = new LocalComponentApiWeaved(api);
			localComponentApi.set(weaved);
			return weaved;
		}
	}

	public LocalComponentApi getLocalComponentApi(final long timeout)
			throws ServerConfigException {
		if (localComponentApi.get() != null) {
			return localComponentApi.get();
		} else {
			final LocalComponentApi api = getApi(LocalComponentApi.class, "/localcomponent", timeout); //$NON-NLS-1$
			final LocalComponentApiWeaved weaved = new LocalComponentApiWeaved(api);
			localComponentApi.set(weaved);
			return weaved;
		}
	}

	public ObligationApi getObligationApi() throws ServerConfigException {
		if (obligationApi.get() != null) {
			return obligationApi.get();
		} else {
			final ObligationApi api = getApi(ObligationApi.class, "/obligation"); //$NON-NLS-1$
			final ObligationApiWeaved weaved = new ObligationApiWeaved(api);
			obligationApi.set(weaved);
			return weaved;
		}
	}

	public ObligationApi getObligationApi(final long timeout)
			throws ServerConfigException {
		if (obligationApi.get() != null) {
			return obligationApi.get();
		} else {
			final ObligationApi api = getApi(ObligationApi.class, "/obligation", timeout); //$NON-NLS-1$
			final ObligationApiWeaved weaved = new ObligationApiWeaved(api);
			obligationApi.set(weaved);
			return weaved;
		}
	}

	public PolicyApi getPolicyApi() throws ServerConfigException {
		if (policyApi.get() != null) {
			return policyApi.get();
		} else {
			final PolicyApi api = getApi(PolicyApi.class, "/policy"); //$NON-NLS-1$
			final PolicyApiWeaved weaved = new PolicyApiWeaved(api);
			policyApi.set(weaved);
			return weaved;
		}
	}

	public PolicyApi getPolicyApi(final long timeout) throws ServerConfigException {
		if (policyApi.get() != null) {
			return policyApi.get();
		} else {
			final PolicyApi api = getApi(PolicyApi.class, "/policy", timeout); //$NON-NLS-1$
			final PolicyApiWeaved weaved = new PolicyApiWeaved(api);
			policyApi.set(weaved);
			return weaved;
		}
	}

	public ExternalIdApi getExternalIdApi() throws ServerConfigException {
		if (externalIdApi.get() != null) {
			return externalIdApi.get();
		} else {
			final ExternalIdApi api = getApi(ExternalIdApi.class, "/externalid"); //$NON-NLS-1$
			final ExternalIdApiWeaved weaved = new ExternalIdApiWeaved(api);
			externalIdApi.set(weaved);
			return weaved;
		}
	}

	public ExternalIdApi getExternalIdApi(final long timeout)
			throws ServerConfigException {
		if (externalIdApi.get() != null) {
			return externalIdApi.get();
		} else {
			final ExternalIdApi api = getApi(ExternalIdApi.class, "/externalid", timeout); //$NON-NLS-1$
			final ExternalIdApiWeaved weaved = new ExternalIdApiWeaved(api);
			externalIdApi.set(weaved);
			return weaved;
		}
	}

	public FileComparisonApi getFileComparisonApi()
			throws ServerConfigException {
		if (fileComparisonApi.get() != null) {
			return fileComparisonApi.get();
		} else {
			final FileComparisonApi api = getApi(FileComparisonApi.class, "/filecomparison"); //$NON-NLS-1$
			final FileComparisonApiWeaved weaved = new FileComparisonApiWeaved(api);
			fileComparisonApi.set(weaved);
			return weaved;
		}
	}

	public FileComparisonApi getFileComparisonApi(final long timeout)
			throws ServerConfigException {
		if (fileComparisonApi.get() != null) {
			return fileComparisonApi.get();
		} else {
			final FileComparisonApi api = getApi(FileComparisonApi.class, "/filecomparison", timeout); //$NON-NLS-1$
			final FileComparisonApiWeaved weaved = new FileComparisonApiWeaved(api);
			fileComparisonApi.set(weaved);
			return weaved;
		}
	}

	public ProjectApi getProjectApi() throws ServerConfigException {
		if (projectApi.get() != null) {
			return projectApi.get();
		} else {
			final ProjectApi api = getApi(ProjectApi.class, "/project"); //$NON-NLS-1$
			final ProjectApiWeaved weaved = new ProjectApiWeaved(api);
			projectApi.set(weaved);
			return weaved;
		}
	}

	public ProjectApi getProjectApi(final long timeout) throws ServerConfigException {
		if (projectApi.get() != null) {
			return projectApi.get();
		} else {
			final ProjectApi api = getApi(ProjectApi.class, "/project", timeout); //$NON-NLS-1$
			final ProjectApiWeaved weaved = new ProjectApiWeaved(api);
			projectApi.set(weaved);
			return weaved;
		}
	}

	public TemplateApi getTemplateApi() throws ServerConfigException {
		if (templateApi.get() != null) {
			return templateApi.get();
		} else {
			final TemplateApi api = getApi(TemplateApi.class, "/template", timeout); //$NON-NLS-1$
			final TemplateApiWeaved weaved = new TemplateApiWeaved(api);
			templateApi.set(weaved);
			return weaved;
		}
	}

	public TemplateApi getTemplateApi(final long timeout) throws ServerConfigException {
		if (templateApi.get() != null) {
			return templateApi.get();
		} else {
			final TemplateApi api = getApi(TemplateApi.class, "/template", timeout); //$NON-NLS-1$
			final TemplateApiWeaved weaved = new TemplateApiWeaved(api);
			templateApi.set(weaved);
			return weaved;
		}
	}

	public SynchronizationApi getSynchronizationApi() throws ServerConfigException {
		if (synchronizationApi.get() != null) {
			return synchronizationApi.get();
		} else {
			final SynchronizationApi api = getApi(SynchronizationApi.class, "/synchronization", timeout); //$NON-NLS-1$
			final SynchronizationApiWeaved weaved = new SynchronizationApiWeaved(api);
			synchronizationApi.set(weaved);
			return weaved;
		}
	}

	public SynchronizationApi getSynchronizationApi(final long timeout) throws ServerConfigException {
		if (synchronizationApi.get() != null) {
			return synchronizationApi.get();
		} else {
			final SynchronizationApi api = getApi(SynchronizationApi.class, "/synchronization", timeout); //$NON-NLS-1$
			final SynchronizationApiWeaved weaved = new SynchronizationApiWeaved(api);
			synchronizationApi.set(weaved);
			return weaved;
		}
	}

	public CodeTreeApi getCodeTreeApi() throws ServerConfigException {
		if (codeTreeApi.get() != null) {
			return codeTreeApi.get();
		} else {
			final CodeTreeApi api = getApi(CodeTreeApi.class, "/codetree"); //$NON-NLS-1$
			final CodeTreeApiWeaved weaved = new CodeTreeApiWeaved(api);
			codeTreeApi.set(weaved);
			return weaved;
		}
	}

	public CodeTreeApi getCodeTreeApi(final long timeout)
			throws ServerConfigException {
		if (codeTreeApi.get() != null) {
			return codeTreeApi.get();
		} else {
			final CodeTreeApi api = getApi(CodeTreeApi.class, "/codetree", timeout); //$NON-NLS-1$
			final CodeTreeApiWeaved weaved = new CodeTreeApiWeaved(api);
			codeTreeApi.set(weaved);
			return weaved;
		}
	}

	public BomApi getBomApi() throws ServerConfigException {
		if (bomApi.get() != null) {
			return bomApi.get();
		} else {
			final BomApi api = getApi(BomApi.class, "/bom"); //$NON-NLS-1$
			final BomApiWeaved weaved = new BomApiWeaved(api);
			bomApi.set(weaved);
			return weaved;
		}
	}

	public BomApi getBomApi(final long timeout) throws ServerConfigException {
		if (bomApi.get() != null) {
			return bomApi.get();
		} else {
			final BomApi api = getApi(BomApi.class, "/bom", timeout); //$NON-NLS-1$
			final BomApiWeaved weaved = new BomApiWeaved(api);
			bomApi.set(weaved);
			return weaved;
		}
	}

	public DiscoveryApi getDiscoveryApi() throws ServerConfigException {
		if (discoveryApi.get() != null) {
			return discoveryApi.get();
		} else {
			final DiscoveryApi api = getApi(DiscoveryApi.class, "/discovery"); //$NON-NLS-1$
			final DiscoveryApiWeaved weaved = new DiscoveryApiWeaved(api);
			discoveryApi.set(weaved);
			return weaved;
		}
	}

	public DiscoveryApi getDiscoveryApi(final long timeout)
			throws ServerConfigException {
		if (discoveryApi.get() != null) {
			return discoveryApi.get();
		} else {
			final DiscoveryApi api = getApi(DiscoveryApi.class, "/discovery", timeout); //$NON-NLS-1$
			final DiscoveryApiWeaved weaved = new DiscoveryApiWeaved(api);
			discoveryApi.set(weaved);
			return weaved;
		}
	}

	public IdentificationApi getIdentificationApi()
			throws ServerConfigException {
		if (identificationApi.get() != null) {
			return identificationApi.get();
		} else {
			final IdentificationApi api = getApi(IdentificationApi.class, "/identification"); //$NON-NLS-1$
			final IdentificationApiWeaved weaved = new IdentificationApiWeaved(api);
			identificationApi.set(weaved);
			return weaved;
		}
	}

	public IdentificationApi getIdentificationApi(final long timeout)
			throws ServerConfigException {
		if (identificationApi.get() != null) {
			return identificationApi.get();
		} else {
			final IdentificationApi api = getApi(IdentificationApi.class, "/identification", timeout); //$NON-NLS-1$
			final IdentificationApiWeaved weaved = new IdentificationApiWeaved(api);
			identificationApi.set(weaved);
			return weaved;
		}
	}

	public ReportApi getReportApi() throws ServerConfigException {
		if (reportApi.get() != null) {
			return reportApi.get();
		} else {
			final ReportApi api = getApi(ReportApi.class, "/report"); //$NON-NLS-1$
			final ReportApiWeaved weaved = new ReportApiWeaved(api);
			reportApi.set(weaved);
			return weaved;
		}
	}

	public ReportApi getReportApi(final long timeout) throws ServerConfigException {
		if (reportApi.get() != null) {
			return reportApi.get();
		} else {
			final ReportApi api = getApi(ReportApi.class, "/report", timeout); //$NON-NLS-1$
			final ReportApiWeaved weaved = new ReportApiWeaved(api);
			reportApi.set(weaved);
			return weaved;
		}
	}

	public RoleApi getRoleApi() throws ServerConfigException {
		if (roleApi.get() != null) {
			return roleApi.get();
		} else {
			final RoleApi api = getApi(RoleApi.class, "/role"); //$NON-NLS-1$
			final RoleApiWeaved weaved = new RoleApiWeaved(api);
			roleApi.set(weaved);
			return weaved;
		}
	}

	public RoleApi getRoleApi(final long timeout) throws ServerConfigException {
		if (roleApi.get() != null) {
			return roleApi.get();
		} else {
			final RoleApi api = getApi(RoleApi.class, "/role", timeout); //$NON-NLS-1$
			final RoleApiWeaved weaved = new RoleApiWeaved(api);
			roleApi.set(weaved);
			return weaved;
		}
	}

	public UserApi getUserApi() throws ServerConfigException {
		if (userApi.get() != null) {
			final UserApi api = userApi.get();
			return api;
		} else {
			final UserApi api = getApi(UserApi.class, "/user"); //$NON-NLS-1$
			final UserApiWeaved weaved = new UserApiWeaved(api);
			userApi.set(weaved);
			return weaved;
		}
	}

	public UserApi getUserApi(final long timeout) throws ServerConfigException {
		if (userApi.get() != null) {
			return userApi.get();
		} else {
			final UserApi api = getApi(UserApi.class, "/user", timeout); //$NON-NLS-1$
			final UserApiWeaved weaved = new UserApiWeaved(api);
			userApi.set(weaved);
			return weaved;
		}
	}

}
