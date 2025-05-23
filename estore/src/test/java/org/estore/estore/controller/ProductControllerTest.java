package org.estore.estore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCanAddProduct() throws Exception {
        buildFormFields();

        mockMvc.perform(multipart("/api/v1/product/")
                        .part(new MockPart("image", ), new MockPart("video", ))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                .formFields(buildFormFields())
        ).andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    private static MultiValueMap<String, String> buildFormFields() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "iPhone");
        params.add("price", BigDecimal.valueOf(1000).toString());
        params.add("description", "Iphone 16 Pro Max");
        params.add("quantity", "10");
        return params;
    }

}
