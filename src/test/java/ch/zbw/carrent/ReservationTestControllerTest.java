package ch.zbw.carrent;

import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
      // kunde = this.kundeRep.findByName("")
       
       
 
     //  this.resList.add(resRep.save(new Reservation(10)("Kunde1", "Hans", "kundenstrasse1", 9000, "St. Gallen", sachB)));
    }
}
