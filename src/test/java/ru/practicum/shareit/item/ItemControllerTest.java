package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemControllerTest {

    static Validator validator;
    @Autowired
    ItemController itemController;
    @Autowired
    UserService userService;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().usingContext().getValidator();

    }

    @Test
    void addItem() {
        UserDto userDto = new UserDto(null, "name", "vAddOwner@v.v");
        Integer ownerId = userService.addUser(userDto).getId();
        ItemDto itemDto = new ItemDto(null, "name", "desc", true, null);
        assertEquals(0, validator.validate(itemDto).size());
        ItemDto addedItemDto = itemController.addItem(ownerId, itemDto);
        itemDto.setId(addedItemDto.getId());
        assertEquals(itemDto, addedItemDto);
        assertEquals(1, validator.validate(
                new ItemDto(null, "", "desc", true, null)).size());
        assertEquals(1, validator.validate(
                new ItemDto(null, null, "desc", true, null)).size());
        assertEquals(1, validator.validate(
                new ItemDto(null, "name", "", true, null)).size());
        assertEquals(1, validator.validate(
                new ItemDto(null, "name", null, true, null)).size());
    }

    @Test
    void pathItem() {
        UserDto userDto = new UserDto(null, "name", "vPathOwner@v.v");
        Integer ownerId = userService.addUser(userDto).getId();
        ItemDto itemDto = new ItemDto(null, "name", "desc", true, null);
        ItemDto addedItemDto = itemController.addItem(ownerId, itemDto);
        addedItemDto.setDescription("updated");
        ItemDto updatedItem = itemController.pathItem(userDto.getId(), addedItemDto.getId(), addedItemDto);
        assertEquals(addedItemDto, updatedItem);
    }

    @Test
    void getItem() {
        UserDto userDto = new UserDto(null, "name", "vGetOwner@v.v");
        Integer ownerId = userService.addUser(userDto).getId();
        ItemDto itemDto = new ItemDto(null, "name", "desc", true, null);
        ItemDto addedItemDto = itemController.addItem(ownerId, itemDto);
        assertEquals(addedItemDto, itemController.getItem(addedItemDto.getId(), 1));
    }

    @Test
    void getItemsByOwner() {
        UserDto userDto = new UserDto(null, "name", "vGetByOwner@v.v");
        Integer ownerId = userService.addUser(userDto).getId();
        ItemDto itemDto = new ItemDto(null, "name", "desc", true, null);
        ItemDto addedItemDto = itemController.addItem(ownerId, itemDto);
        assertEquals(1, itemController.getItemsByOwner(userDto.getId()).size());
        assertEquals(addedItemDto, itemController.getItemsByOwner(userDto.getId()).get(0));
    }

    @Test
    void searchItems() {
        UserDto userDto = new UserDto(null, "name", "vSearchOwner@v.v");
        Integer ownerId = userService.addUser(userDto).getId();
        ItemDto itemDto = new ItemDto(null, "name", "search string", true, null);
        ItemDto addedItemDto = itemController.addItem(ownerId, itemDto);
        assertEquals(1, itemController.searchItems("sEaRcH").size());
        assertEquals(addedItemDto, itemController.searchItems("sEaRcH").get(0));
    }
}