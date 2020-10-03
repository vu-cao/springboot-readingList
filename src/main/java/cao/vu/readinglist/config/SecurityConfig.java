package cao.vu.readinglist.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import cao.vu.readinglist.dao.ReaderDao;
import cao.vu.readinglist.model.Reader;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	private ReaderDao readerDao;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(getUserDetailsService()).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
				.authorizeRequests()
				.antMatchers("/reading", "/reading/list").access("hasRole('READER')")
				.antMatchers("/reading/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/reading/login").defaultSuccessUrl("/reading/list", true)
				.failureUrl("/reading/login?error=true").and().csrf().disable();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException {
				final Optional<Reader> reader = readerDao.findById(username);

				if (reader.isPresent()) {
					final Reader r = reader.get();
					LOGGER.debug(r.getFullname());
					LOGGER.debug(r.getUsername());
					LOGGER.debug(r.getPassword());
					LOGGER.debug(r.getAuthorities().iterator().next().getAuthority());
					return r;
				}
				throw new UsernameNotFoundException("User '" + username + "' not found.");
			}
		};
	}
}
