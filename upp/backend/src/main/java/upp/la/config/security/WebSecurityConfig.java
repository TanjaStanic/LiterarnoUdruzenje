package upp.la.config.security;

import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.spin.SpringBootSpinProcessEnginePlugin;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import upp.la.config.security.AuthUserDetailsService;
import upp.la.config.security.JwtTokenFilter;
import upp.la.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired UserRepository userRepository;

  @Autowired
  JwtTokenFilter jwtTokenFilter;

  @Lazy @Autowired private AuthUserDetailsService authUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
  }
  
  @Bean
  public RestTemplate restTemplate() {
      return new RestTemplate();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  // If all else fails - turn off security by uncommenting these 3 lines
    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/**");
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();

    // Set session management to stateless
    http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

    // Set unauthorized requests exception handler
    http =
        http.exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                })
            .and();

    // Set permissions on endpoints
    http.authorizeRequests()
        // Our public endpoints
        .antMatchers("/camunda/**")
        .permitAll()
        .antMatchers("/auth/**")
        .permitAll()
        .antMatchers("/registration/**")
        .permitAll()
        .antMatchers("/files/**")
        .permitAll()
        .antMatchers("/review/**")
        .permitAll()
        .antMatchers("/camunda-welcome/**")
        .permitAll()
        .antMatchers("/rap/**")
        .permitAll()
        .antMatchers("/book/**")
        .permitAll()
        .antMatchers("/engine-rest/**")
        .permitAll()
        .antMatchers("/rest/**")
        .permitAll()
		.antMatchers("/plagiarism/**")
            .permitAll()
        // Our private endpoints
        .anyRequest()
        .authenticated();

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @ConditionalOnClass(SpinProcessEnginePlugin.class)
  @Configuration
  static class SpinConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "spinProcessEnginePlugin")
    public static ProcessEnginePlugin spinProcessEnginePlugin() {
      return new SpringBootSpinProcessEnginePlugin();
    }

  }
}
