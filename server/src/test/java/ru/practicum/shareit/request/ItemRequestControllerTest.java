package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ItemRequestService itemRequestService;

    @Test
    void addRequest() throws Exception {
        ItemRequestDto itemRequestDto = getItemRequestDto();
        when(itemRequestService.addItemRequest(anyInt(), any())).thenReturn(itemRequestDto);
        String content = mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(itemRequestDto), content);
    }

    @Test
    void getRequestsByRequester() throws Exception {
        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(itemRequestService).getRequests(1);
    }

    @Test
    void getRequestById() throws Exception {
        mockMvc.perform(get("/requests/{requestId}", 1)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(itemRequestService).getRequest(1, 1);
    }

    @Test
    void getAllRequests() throws Exception {
        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(itemRequestService).getAllRequests(1, 0, 10);
    }

    @Test
    void getAllRequestsByPage() throws Exception {
        mockMvc.perform(get("/requests/all?from={from}&size={size}", 5, 5)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(itemRequestService).getAllRequests(1, 5, 5);
    }

    ItemRequestDto getItemRequestDto() {
        return new ItemRequestDto(null,
                "request",
                new UserDto(1, "user", "mail@mail.m"),
                null,
                new ArrayList<>());
    }
}