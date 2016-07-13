package com.gomeplus.meipro.web.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "serviceapi")
public class JedisProperties {

	private List<String> version = new ArrayList<String>();

	public List<String> getVersion() {
		return this.version;
	}

}
