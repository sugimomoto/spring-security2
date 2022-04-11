package sugimomoto.springsecurity2;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sugimomoto.springsecurity2.model.SiteUser;
import sugimomoto.springsecurity2.repository.SiteUserRepository;
import sugimomoto.springsecurity2.service.UserDetailsServiceImpl;
import sugimomoto.springsecurity2.util.Role;

@SpringBootTest
@Transactional
class SpringSecurity2ApplicationTests {

	@Autowired
	SiteUserRepository repository;

	@Autowired
	UserDetailsServiceImpl service;

	@Test
	@DisplayName("ユーザ名が存在する時、ユーザ詳細を取得することを期待します。")
	void whenUsernameExists_expectToGetUserDetails() {

		var user = new SiteUser();
		user.setUsername("Harada");
		user.setPassword("password");
		user.setEmail("harada@example.com");
		user.setRole(Role.USER.name());
		repository.save(user);

		var actual = service.loadUserByUsername("Harada");
		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	@DisplayName("ユーザ名が存在しない時、例外をスローします。")
	void whenUsernameDoesNotExist_throwException(){
		assertThrows(UsernameNotFoundException.class,
		() -> service.loadUserByUsername("Takeda"));
	}

}
