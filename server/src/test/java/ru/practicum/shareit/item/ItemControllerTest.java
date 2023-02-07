package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ItemService itemService;

    @Test
    void addItem() throws Exception {
        int userId = 1;
        when(itemService.addItem(ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenReturn(getItemDto("name item"));
        String content = mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getItemDto("name item")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(getItemDto("name item")), content);
    }

    @Test
    void pathItem() throws Exception {
        int userId = 1;
        int itemId = 1;
        ItemDto itemDto = getItemDto("name item");
        Mockito.when(itemService.updateItem(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.any()))
                .thenReturn(itemDto);
        String content = mockMvc.perform(MockMvcRequestBuilders.patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(itemDto), content);
    }

    @Test
    void getItem() throws Exception {
        int itemId = 1;
        int userId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(itemService).getItem(itemId, userId);
    }

    @Test
    void getItemNotFound() throws Exception {
        int itemId = 99;
        int userId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", itemId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(itemService, Mockito.never()).getItem(itemId, userId);
    }

    @Test
    void getItemsByOwner() throws Exception {
        int userId = 1;
        int from = 1;
        int size = 10;
        mockMvc.perform(MockMvcRequestBuilders.get("/items?from={from}&size={size}", from, size)
                        .header("X-Sharer-User-Id", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(itemService).getItemsByOwner(userId, 1, 10);
    }

    @Test
    void searchItems() throws Exception {
        String text = "search text";
        mockMvc.perform(MockMvcRequestBuilders.get("/items/search?text={text}", text))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(itemService).foundItems(text, 0, 10);
    }

    @Test
    void addComment() throws Exception {
        int userId = 1;
        int itemId = 1;
        CommentDto commentDto = new CommentDto(1, "comment text", "user");
        Mockito.when(itemService.addComment(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenReturn(commentDto);
        String content = mockMvc.perform(MockMvcRequestBuilders.post("/items/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(commentDto), content);
    }

    ItemDto getItemDto(String name) {
        return new ItemDto(
                1,
                name,
                "description",
                false,
                null,
                null,
                null,
                null
        );
    }
}