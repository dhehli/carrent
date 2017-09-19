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
import ch.zbw.carrent.Klasse;
import ch.zbw.carrent.KlasseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class KlasseRestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private KlasseRepository klasseRep;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private List<Klasse> klasseList = new ArrayList<>();
    
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

       this.klasseRep.deleteAllInBatch();
        this.klasseList.add(klasseRep.save(new Klasse("Sport", 320)));
        this.klasseList.add(klasseRep.save(new Klasse("Luxus", 300)));
    }
    @Test
    public void getAllKlassen() throws Exception {
        mockMvc.perform(get("/restAPI/klasse/klassen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(this.klasseList.get(0).getId())))
                .andExpect(jsonPath("$[0].klassenName", is("Sport")))
                .andExpect(jsonPath("$[0].tagesgebuehr", is("320")))
		        .andExpect(jsonPath("$[1].id", is(this.klasseList.get(1).getId())))
		        .andExpect(jsonPath("$[1].klassenName", is("Luxus")))
		        .andExpect(jsonPath("$[1].tagesgebuehr", is("300")));
    }
    @Test
    public void createKlasse() throws Exception {
        String klasseJson = json(new Klasse(
               "Kombi", 200));

        this.mockMvc.perform(post("/klasse")
                .contentType(contentType)
                .content(klasseJson))
                .andExpect(status().isCreated());
    }
    @Test
    public void deleteKlasse() throws Exception {
        Klasse klasse = klasseRep.findByKlassenName("Luxus");
    	this.mockMvc.perform(delete("/klasse/" + klasse.getId())
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
