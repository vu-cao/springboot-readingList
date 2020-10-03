package cao.vu.readinglist;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.nullValue;

import cao.vu.readinglist.model.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value =
{ "/application.properties" })
class ReadingListApplicationTests
{
	@Autowired
	private MockMvc mockMvc;
	@Value("${server.port}")
	private String port;

	@Test
	void contextLoads()
	{
	}

	@Test
	public void testUnAuthentication() throws Exception
	{
		mockMvc.perform(get("http://localhost:" + port + "/reading")).andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "http://localhost:" + port + "/reading/login"));
	}

	@Test
	@WithUserDetails(value="vu")
	public void testAuthentication() throws Exception
	{
		final Reader expectedReader = new Reader();
		expectedReader.setPassword("password");
		expectedReader.setUsername("vu");
		expectedReader.setFullname("Vu Cao");
		mockMvc.perform(get("http://localhost:" + port + "/reading/list")).andExpect(status().isOk())
			.andExpect(view().name("list")).andExpect(model().attribute("books", nullValue()))
			.andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)));
	}

	@Test
	public void testReaderBooks()
	{

	}
}
