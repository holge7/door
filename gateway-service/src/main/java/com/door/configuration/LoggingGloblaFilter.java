package com.door.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import jakarta.servlet.http.HttpServletRequest;

import javax.print.event.PrintJobAttributeEvent;

import org.slf4j.Logger;

import reactor.core.publisher.Mono;

@Component
public class LoggingGloblaFilter implements GlobalFilter {
	final Logger logger = LoggerFactory.getLogger(LoggingGloblaFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("=====================================");
		logger.info("Global Pre Filter executed");
		logger.info("=====================================");
		ServerHttpRequest request = exchange.getRequest();
	
    	if (!request.getHeaders().containsKey("Authorization")) {
    		
    		return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
    	}
		

		return chain.filter(exchange);
	}
	
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
    	
        ServerHttpResponse response = exchange.getResponse();
	
        response.setStatusCode(httpStatus);
	

	
        return response.setComplete();
	
    }
   
	private String parseJwt2(HttpServletRequest request) {
		// Get header for auth
		String headerAuth = request.getHeader("Authorization");
		
		// Check if it have text and is of type Bearer
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length()); // substring of 7 becouse it is "|B|e|a|r|e|r| | ......."
		}
		
		return null;
	}
}
