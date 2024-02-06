package com.giftandgo.fileprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldProcessFileSuccessfully() throws Exception {
        String content = """
                18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
                3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
                1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3""";
        //language=JSON
        String expectedContent = "[\n" +
                "  {\n" +
                "    \"name\": \"John Smith\",\n" +
                "    \"transport\": \"Rides A Bike\",\n" +
                "    \"topSpeed\": 12.1\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Mike Smith\",\n" +
                "    \"transport\": \"Drives an SUV\",\n" +
                "    \"topSpeed\": 95.5\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Jenny Walters\",\n" +
                "    \"transport\": \"Rides A Scooter\",\n" +
                "    \"topSpeed\": 15.3\n" +
                "  }\n" +
                "]\n";
        mockMvc.perform(MockMvcRequestBuilders.post("/files/process")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedContent));
    }
}
