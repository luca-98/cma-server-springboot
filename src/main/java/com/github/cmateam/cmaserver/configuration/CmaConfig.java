package com.github.cmateam.cmaserver.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Tham chiếu config từ file application.yml
@ConfigurationProperties(prefix = "cma")
public class CmaConfig {
	private String gatewayServerUrl;
	private String tokenSecretKey;
	private String tokenHeaderString;
	private String tokenPrefix;
	private Long tokenExpiration;
	private String templatePatients;

	public String getGatewayServerUrl() {
		return this.gatewayServerUrl;
	}

	public void setGatewayServerUrl(String gatewayServerUrl) {
		this.gatewayServerUrl = gatewayServerUrl;
	}

	public String getTokenSecretKey() {
		return tokenSecretKey;
	}

	public void setTokenSecretKey(String tokenSecretKey) {
		this.tokenSecretKey = tokenSecretKey;
	}

	public String getTokenHeaderString() {
		return tokenHeaderString;
	}

	public void setTokenHeaderString(String tokenHeaderString) {
		this.tokenHeaderString = tokenHeaderString;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public Long getTokenExpiration() {
		return this.tokenExpiration;
	}

	public void setTokenExpiration(Long tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	public String getTemplatePatients() {
		return templatePatients;
	}

	public void setTemplatePatients(String templatePatients) {
		this.templatePatients = templatePatients;
	}

}
