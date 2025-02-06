//package ru.yandex.practicum.controller;
//
//
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//class UserControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Test
//    void shouldReturnHtmlWithUsers() throws Exception {
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("users"))
//                .andExpect(model().attributeExists("users"))
//                .andExpect(xpath("//table/tbody/tr").nodeCount(2))
//                .andExpect(xpath("//table/tbody/tr[1]/td[2]").string("Иван"))
//                .andExpect(xpath("//table/tbody/tr[2]/td[2]").string("Мария"));
//    }
//
//}