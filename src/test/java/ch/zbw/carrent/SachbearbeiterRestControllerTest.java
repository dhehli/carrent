package ch.zbw.carrent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.zbw.carrent.Application;
import ch.zbw.carrent.Sachbearbeiter;
import ch.zbw.carrent.SachbearbeiterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SachbearbeiterRestControllerTest {
	  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));

	    private MockMvc mockMvc;
	    private HttpMessageConverter mappingJackson2HttpMessageConverter;
	    
	    @Autowired
	    private SachbearbeiterRepository sachRep;
	    
	    @Autowired
	    private WebApplicationContext webApplicationContext;
	    
	    private List<Sachbearbeiter> sachList = new ArrayList<>();
	    
	    @Autowired
	    void setConverters(HttpMessageConverter<?>[] converters) {

	        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
	            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
	            .findAny()
	            .orElse(null);

	        assertNotNull("the JSON message converter must not be null",
	                this.mappingJackson2HttpMessageConverter);
	    }
	    
	    @Before
	    public void setup() throws Exception {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	       this.sachRep.deleteAllInBatch();
	        this.sachList.add(sachRep.save(new Sachbearbeiter("Sachb1", "Mars")));
	        this.sachList.add(sachRep.save(new Sachbearbeiter("Sachb2", "Pluto")));
	    }
	    
	    @Test
	    public void getAllSachbearbeiter() throws Exception {
	        mockMvc.perform(get("restAPI/sachbearbeiter/sachbearbeiterList"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType))
	                .andExpect(jsonPath("$[0].id", is(this.sachList.get(0).getId())))
	                .andExpect(jsonPath("$[0].name", is("Sachb1")))
	                .andExpect(jsonPath("$[0].vorName", is("Mars")))
			        .andExpect(jsonPath("$[1].id", is(this.sachList.get(1).getId())))
			        .andExpect(jsonPath("$[1].name", is("Sachb2")))
			        .andExpect(jsonPath("$[1].vorName", is("Pluto")));
	    }
	    @Test
	    public void createSachbearbeiter() throws Exception {
	        String sachBJson = json(new Sachbearbeiter(
	               "Sachb3", "Jupiter"));

	        this.mockMvc.perform(post("restAPI/sachbearbeiter")
	                .contentType(contentType)
	                .content(sachBJson))
	                .andExpect(status().isCreated());
	    }
	    @Test
	    public void deleteSachbearbeiter() throws Exception {
	        Sachbearbeiter sach = sachRep.findByName("Sachb1");
	    	this.mockMvc.perform(delete("restAPI/sachbearbeiter/" + sach.getId())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	    protected String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.mappingJackson2HttpMessageConverter.write(
	                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }

}
