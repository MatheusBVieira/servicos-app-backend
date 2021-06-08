package br.com.servicos.servicosApi.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_PRESTADOR = "PRESTADOR";

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/usuario").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/cidades").permitAll()
		.antMatchers(HttpMethod.GET, "/cidades/*").permitAll()
		.antMatchers("/cidades").hasRole(ROLE_ADMIN)
		.antMatchers("/cidades/*").hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.GET, "/estados").permitAll()
		.antMatchers(HttpMethod.GET, "/estados/*").permitAll()
		.antMatchers("/estados").hasRole(ROLE_ADMIN)
		.antMatchers("/estados/*").hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.GET, "/categorias").permitAll()
		.antMatchers(HttpMethod.GET, "/categorias/*").permitAll()
		.antMatchers("/categorias").hasRole(ROLE_ADMIN)
		.antMatchers("/categorias/*").hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.GET, "/servicos").permitAll()
		.antMatchers(HttpMethod.GET, "/servicos/*").permitAll()
		.antMatchers("/servicos").hasRole(ROLE_PRESTADOR)
		.antMatchers("/servicos/*").hasRole(ROLE_PRESTADOR)
		.antMatchers(HttpMethod.GET, "/avaliacao").permitAll()
		.antMatchers(HttpMethod.GET, "/avaliacao/*").permitAll()
		.antMatchers(HttpMethod.GET, "/midia/*").permitAll()
		.anyRequest().authenticated().and().cors()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	//Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
	
}
