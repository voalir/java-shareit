package ru.practicum.shareit.request.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ItemRequestServiceImplTest {

    @Autowired
    ItemRequestService itemRequestService;

    @Test
    @Order(0)
    @Sql(value = {"/DropTables.sql", "/AddUsers.sql"})
    void addItemRequest() {
        ItemRequestDto itemRequestDto = getItemRequestDto();
        Optional<ItemRequestDto> createdRequest = Optional.of(
                itemRequestService.addItemRequest(1, itemRequestDto));
        Assertions.assertThat(createdRequest)
                .isPresent()
                .hasValueSatisfying(request -> {
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("description", itemRequestDto.getDescription());
                            Assertions.assertThat(request.getRequestorId()).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(request).hasFieldOrProperty("created");
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("items", itemRequestDto.getItems());
                        }
                );
        createdRequest = Optional.of(
                itemRequestService.addItemRequest(2, itemRequestDto));
        Assertions.assertThat(createdRequest)
                .isPresent()
                .hasValueSatisfying(request -> {
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("id", 2);
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("description", itemRequestDto.getDescription());
                            Assertions.assertThat(request.getRequestorId()).hasFieldOrPropertyWithValue("id", 2);
                            Assertions.assertThat(request).hasFieldOrProperty("created");
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("items", itemRequestDto.getItems());
                        }
                );
        createdRequest = Optional.of(
                itemRequestService.addItemRequest(2, itemRequestDto));
        Assertions.assertThat(createdRequest)
                .isPresent()
                .hasValueSatisfying(request -> {
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("id", 3);
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("description", itemRequestDto.getDescription());
                            Assertions.assertThat(request.getRequestorId()).hasFieldOrPropertyWithValue("id", 2);
                            Assertions.assertThat(request).hasFieldOrProperty("created");
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("items", itemRequestDto.getItems());
                        }
                );
        assertThrows(UserNotFoundException.class, () -> itemRequestService.addItemRequest(99, itemRequestDto));
    }

    @Test
    @Order(1)
    void getAllRequests() {
        Assertions.assertThat(itemRequestService.getAllRequests(2, 0, 10)).hasSize(1)
                .map(ItemRequestDto::getId).contains(1);
        Assertions.assertThat(itemRequestService.getAllRequests(1, 0, 10)).hasSize(2)
                .map(ItemRequestDto::getId).contains(2, 3);
        Assertions.assertThat(itemRequestService.getAllRequests(1, 0, 1)).hasSize(1);
        assertThrows(UserNotFoundException.class, () -> itemRequestService.getAllRequests(99, 0, 10));
    }

    @Test
    @Order(2)
    @Sql(value = {"/ItemRequestTestPrepare.sql"})
    void getRequestsByRequester() {
        Assertions.assertThat(itemRequestService.getRequests(1)).hasSize(1)
                .map(ItemRequestDto::getId).contains(1);
    }

    @Test
    @Order(3)
    void getRequestById() {
        Optional<ItemRequestDto> foundedRequest = Optional.of(itemRequestService.getRequest(1, 2));
        Assertions.assertThat(foundedRequest)
                .isPresent()
                .hasValueSatisfying(request -> {
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(request).hasFieldOrPropertyWithValue("description", "request");
                            Assertions.assertThat(request.getRequestorId()).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(request).hasFieldOrProperty("created");
                            Assertions.assertThat(request.getItems()).hasSize(1);
                            Assertions.assertThat(request.getItems().get(0)).hasFieldOrPropertyWithValue("id", 3);
                        }
                );
    }

    ItemRequestDto getItemRequestDto() {
        return new ItemRequestDto(null,
                "request",
                new UserDto(1, "user", "mail@mail.m"),
                null,
                new ArrayList<>());
    }
}