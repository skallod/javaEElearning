package com.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import com.bookstore.service.UserSecurityService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String SALT = "rRkt4)leT7";

	@Autowired
	private Environment env;

	@Autowired
	private UserSecurityService userSecurityService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		//return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}

//	private static final String[] PUBLIC_MATCHERS = {
//			"/css/*",
//			"/js/*",
//			"/user/*"
//	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
		.httpBasic()
//		.and()
//			.rememberMe()
//			.key("salt")
//			.tokenValiditySeconds(30)
//		.and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
				//.antMatchers(PUBLIC_MATCHERS).permitAll()
				//применяется сверху вниз первый который смэтичлся
		.antMatchers(HttpMethod.POST,"/book/*").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/book/*").permitAll()
		//moved to nginx .antMatchers(HttpMethod.GET,"/image/book/*").permitAll()
		//.antMatchers(HttpMethod.GET,"/token","/checkSession","/user/logout").authenticated()
				.anyRequest().authenticated()
		//.and().logout().logoutUrl("/user/logout").invalidateHttpSession(true).clearAuthentication(true)//не заработало
        ;
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService)/*.passwordEncoder(passwordEncoder())*/;
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}

