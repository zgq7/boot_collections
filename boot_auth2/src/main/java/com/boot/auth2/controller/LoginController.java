package com.boot.auth2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/5/26 9:47
 **/
@RestController
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private static String authorizationRequestBaseUri = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Resource
	private ClientRegistrationRepository clientRegistrationRepository;
	@Resource
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping(value = "/auth2/login")
	public Map<String, Object> login(@RequestBody Map<String, String> dto) {

		String username = "username";
		String password = "password";
		HashMap<String, Object> result = new HashMap<>();
		if (dto.get(username).equals("admin") && dto.get(password).equals("admin")) {
			// 登录成功

			result.put("code", 200);
		} else {
			result.put("code", 300);
		}
		return result;
	}


}
