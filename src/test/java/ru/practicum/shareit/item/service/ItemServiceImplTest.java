package ru.practicum.shareit.item.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.CommentCreateException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.user.exception.UserAccessException;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @Test
    @Order(0)
    @Sql(value = {"/ItemTestPrepare.sql"})
    void addItem() {
        ItemDto itemDto = getItemDto();
        Optional<ItemDto> createdItemDto = Optional.of(itemService.addItem(1, itemDto));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("name", itemDto.getName());
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("description", itemDto.getDescription());
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("available", itemDto.getAvailable());
                });
        assertThrows(UserNotFoundException.class, () -> itemService.addItem(100, itemDto));
        itemDto.setRequestId(99);
        assertThrows(ItemRequestNotFoundException.class, () -> itemService.addItem(1, itemDto));
    }

    @Test
    @Order(1)
    void updateItemAvailable() {
        ItemDto itemDto = new ItemDto(null, null, null, true, null, null, null, null);
        Optional<ItemDto> createdItemDto = Optional.of(itemService.updateItem(1, 1, itemDto));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dto -> Assertions.assertThat(dto).hasFieldOrPropertyWithValue("available", itemDto.getAvailable()));
    }

    @Test
    @Order(2)
    void updateItemName() {
        ItemDto itemDto = new ItemDto(null, "new name", null, null, null, null, null, null);
        Optional<ItemDto> createdItemDto = Optional.of(itemService.updateItem(1, 1, itemDto));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dto -> Assertions.assertThat(dto).hasFieldOrPropertyWithValue("name", itemDto.getName()));
    }

    @Test
    @Order(3)
    void updateItemDescription() {
        ItemDto itemDto = new ItemDto(null, null, "new description", null, null, null, null, null);
        Optional<ItemDto> createdItemDto = Optional.of(itemService.updateItem(1, 1, itemDto));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dto -> Assertions.assertThat(dto).hasFieldOrPropertyWithValue("description", itemDto.getDescription()));
    }

    @Test
    @Order(4)
    void updateItemNotCorrectId() {
        ItemDto itemDto = new ItemDto(null, "new name", null, null, null, null, null, null);
        assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(1, 99, itemDto));
    }

    @Test
    @Order(5)
    void updateItemNotCorrectUser() {
        ItemDto itemDto = new ItemDto(null, "new name", null, null, null, null, null, null);
        assertThrows(UserAccessException.class, () -> itemService.updateItem(2, 1, itemDto));
    }

    @Test
    @Order(6)
    void getItem() {
        Optional<ItemDto> createdItemDto = Optional.of(itemService.getItem(1, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("name", "new name");
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("description", "new description");
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("available", true);
                });
        assertThrows(ItemNotFoundException.class, () -> itemService.getItem(99, 1));
    }

    @Test
    @Order(7)
    void getRawItem() {
        Optional<Item> createdItem = Optional.of(itemService.getRawItem(1));
        Assertions.assertThat(createdItem)
                .isPresent()
                .hasValueSatisfying(item -> {
                    Assertions.assertThat(item).hasFieldOrPropertyWithValue("id", 1);
                    Assertions.assertThat(item).hasFieldOrPropertyWithValue("name", "new name");
                    Assertions.assertThat(item).hasFieldOrPropertyWithValue("description", "new description");
                    Assertions.assertThat(item).hasFieldOrPropertyWithValue("available", true);
                });
    }

    @Test
    @Order(8)
    void getItemsByOwner() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(itemService.getItemsByOwner(1, 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    @Test
    @Order(9)
    void getItemsByOwnerWithoutItems() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(itemService.getItemsByOwner(2, 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(0));
    }

    @Test
    @Order(10)
    void foundItems() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(
                itemService.foundItems("desc", 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    @Test
    @Order(11)
    void foundItemsLowerCase() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(
                itemService.foundItems("Desc", 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    @Test
    @Order(12)
    void foundItemsNoItem() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(
                itemService.foundItems("foo", 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(0));
    }

    @Test
    @Order(13)
    void foundItemsByName() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(
                itemService.foundItems("name", 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    @Test
    @Order(13)
    void foundItemsByEmptyString() {
        Optional<List<ItemDto>> createdItemDto = Optional.of(
                itemService.foundItems("", 0, 1));
        Assertions.assertThat(createdItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(0));
    }

    @Test
    @Order(14)
    @Sql(value = {"/ItemTestPrepareBooking.sql"})
    void addComment() {
        CommentDto commentDto = new CommentDto(null, "comment", null);


        Optional<CommentDto> createdCommentDto = Optional.of(itemService.addComment(2, 1, commentDto));
        Assertions.assertThat(createdCommentDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("text", commentDto.getText());
                    Assertions.assertThat(dto).hasFieldOrPropertyWithValue("authorName", "user2");
                });

        assertThrows(UserNotFoundException.class, () -> itemService.addComment(100, 1, commentDto));
        assertThrows(ItemNotFoundException.class, () -> itemService.addComment(2, 100, commentDto));
        assertThrows(CommentCreateException.class, () -> itemService.addComment(1, 1, commentDto));
    }

    @Test
    @Order(15)
    void getItemByRequest() {
        ItemDto itemDto = getItemDto();
        itemDto.setRequestId(1);
        itemService.addItem(1, itemDto);
        Optional<List<ItemDto>> foundItemDto = Optional.of(itemService.getItemByRequest(1));
        Assertions.assertThat(foundItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    @Test
    @Order(16)
    void getItemsByRequest() {
        itemService.getItemsByRequest(List.of(1));
        Optional<List<ItemDto>> foundItemDto = Optional.of(itemService.getItemByRequest(1));
        Assertions.assertThat(foundItemDto)
                .isPresent()
                .hasValueSatisfying(dtos -> Assertions.assertThat(dtos).hasSize(1));
    }

    ItemDto getItemDto() {
        return new ItemDto(
                1,
                "name item",
                "description",
                false,
                null,
                null,
                null,
                null
        );
    }
}