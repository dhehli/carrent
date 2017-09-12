package ch.zbw.carrent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import ch.zbw.carrent.Auto;
import ch.zbw.carrent.AutoRepository;
import ch.zbw.carrent.Klasse;
import ch.zbw.carrent.KlasseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AutoRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private AutoRepository autoRep;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private KlasseRepository klasseRep;
    
    private List<Auto> autoList = new ArrayList<>();
    private Klasse klasse;
    
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

        this.autoRep.deleteAllInBatch();
        this.klasseRep.deleteAllInBatch();

        this.klasse = klasseRep.save(new Klasse("Luxus", 300));
        this.autoList.add(autoRep.save(new Auto("Mercedes", "CL500", klasse)));
        this.autoList.add(autoRep.save(new Auto("Audi", "A8", klasse)));
    }
    
    @Test
    public void getAllAutos() throws Exception {
        mockMvc.perform(get("/autos/auto"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(this.autoList.get(0).getId())))
                .andExpect(jsonPath("$[0].marke", is("Mercedes")))
                .andExpect(jsonPath("$[0].typ", is("CL500")))
		        .andExpect(jsonPath("$[1].id", is(this.autoList.get(1).getId())))
		        .andExpect(jsonPath("$[1].marke", is("Mercedes")))
		        .andExpect(jsonPath("$[1].typ", is("SL500")));
    }
    @Test
    public void createAuto() throws Exception {
        String autoJson = json(new Auto("BMW", "I8", klasse));

        this.mockMvc.perform(post("/auto/" + klasse.getId())
                .contentType(contentType)
                .content(autoJson))
                .andExpect(status().isCreated());
    }
    @Test
    public void deleteAuto() throws Exception {
        Auto auto = autoRep.findByMarke("Audi");
    	this.mockMvc.perform(delete("/auto/" + auto.getId())
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
