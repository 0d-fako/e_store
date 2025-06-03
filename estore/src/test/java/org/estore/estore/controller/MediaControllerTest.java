package org.estore.estore.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCanGetMedia() throws Exception {
        String blobId = "J9cXuJjM3O71evQWejrzLCeSqYEjqDLErCLeAtHuL3I";
        mockMvc.perform(get("/api/v1/media")
                .queryParam("blobId", blobId))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}
