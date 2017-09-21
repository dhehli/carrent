package ch.zbw.carrent;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import ch.zbw.carrent.Auto;
import ch.zbw.carrent.AutoRepository;
import ch.zbw.carrent.Kunde;
import ch.zbw.carrent.KundeRepository;
import ch.zbw.carrent.Reservation;
import ch.zbw.carrent.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ReservationTestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private ReservationRepository resRep;
    
    @Autowired
    private KundeRepository kundeRep;
    
    @Autowired
    private AutoRepository autoRep;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private List<Reservation> resList = new ArrayList<>();
    private Kunde kunde;
    private Auto auto;
    
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
       this.resRep.deleteAllInBatch();
       auto = this.autoRep.findByMarke("Mercedes");
       kunde = this.kundeRep.findByName("Kunde1");
 
       this.resList.add(resRep.save(new Reservation(10, kunde, auto)));
    }
    
    @Test
    public void createReservation() throws Exception {
        String resJson = json(new Reservation(15, kunde, auto));

        this.mockMvc.perform(post("/restAPI/reservation/" + auto.getId() + "/" + kunde.getKundenId())
                .contentType(contentType)
                .content(resJson)
                )
                .andExpect(status().isCreated());
    }
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
