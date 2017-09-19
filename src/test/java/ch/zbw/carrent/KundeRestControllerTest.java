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
import ch.zbw.carrent.Kunde;
import ch.zbw.carrent.KundeRepository;
import ch.zbw.carrent.Sachbearbeiter;
import ch.zbw.carrent.SachbearbeiterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class KundeRestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private KundeRepository kundeRep;
    
    @Autowired
    private SachbearbeiterRepository sachRep;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private List<Kunde> kundeList = new ArrayList<>();
    private Sachbearbeiter sachB;
    
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
       this.kundeRep.deleteAllInBatch();
       sachB = this.sachRep.save(new Sachbearbeiter("SachbTest", "Reto"));
       
 
       this.kundeList.add(kundeRep.save(new Kunde("Kunde1", "Hans", "kundenstrasse1", 9000, "St. Gallen", sachB)));
    }
    @Test
    public void getAllKunden() throws Exception {
        mockMvc.perform(get("/restAPI/kunde/kunden"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].kundenId", is(this.kundeList.get(0).getKundenId())))
                .andExpect(jsonPath("$[0].name", is("Kunde1")))
                .andExpect(jsonPath("$[0].strasse", is("kundenstrasse1")))
                .andExpect(jsonPath("$[0].plz", is(9000)))
                .andExpect(jsonPath("$[0].ort", is("St. Gallen")))
                ;
    }
    @Test
    public void createKunde() throws Exception {
        String kundeJson = json(new Kunde("Kunde2", "Peter", "kundenstrasse2", 9000, "St.Gallen", sachB));

        this.mockMvc.perform(post("/restAPI/kunde/" + sachB.getId())
                .contentType(contentType)
                .content(kundeJson))
                .andExpect(status().isCreated());
    }
    @Test
    public void deleteKunde() throws Exception {
        Kunde kunde = kundeRep.findByName("Kunde1");
    	this.mockMvc.perform(delete("/restAPI/kunde/" + kunde.getKundenId())
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
