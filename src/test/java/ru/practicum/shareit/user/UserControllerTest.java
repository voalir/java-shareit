package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    static Validator validator;
    @Autowired
    UserController userController;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().usingContext().getValidator();
    }

    @Test
    void adduser() {
        UserDto userDto = new UserDto(null, "name", "v");
        assertEquals(1, validator.validate(userDto).size(), "Неверный адрес электронной почты");
        userDto = new UserDto(null, "name", "v@v.v");
        UserDto addedUser = userController.addUser(userDto);
        assertEquals(userDto.getEmail(), addedUser.getEmail());
        assertEquals(userDto.getName(), addedUser.getName());
        assertThrows(DataIntegrityViolationException.class, () ->
                userController.addUser(new UserDto(null, "name", "v@v.v")));
    }

    @Test
    void pathUser() {
        UserDto userDto = new UserDto(null, "name", "vForPath@v.v");
        UserDto userDtoDuplicate = new UserDto(null, "name", "vForPathDuplicate@v.v");
        UserDto addedUser = userController.addUser(userDto);
        userController.addUser(userDtoDuplicate);
        addedUser.setName("updated name");
        UserDto updatedUser = userController.pathUser(addedUser.getId(), addedUser);
        assertEquals(addedUser, updatedUser);
        addedUser.setEmail("vForPathUpdated@v.v");
        updatedUser = userController.pathUser(addedUser.getId(), addedUser);
        assertEquals(addedUser, updatedUser);
        addedUser.setEmail("vForPathDuplicate@v.v");
        assertThrows(DataIntegrityViolationException.class, () -> userController.pathUser(addedUser.getId(), addedUser));
    }

    @Test
    void getUser() {
        UserDto userDto = new UserDto(null, "name", "vForGet@v.v");
        UserDto addedUser = userController.addUser(userDto);
        UserDto receivedUser = userController.getUser(addedUser.getId());
        assertEquals(addedUser, receivedUser);
    }

    @Test
    void getAllUsers() {
        int countBefore = userController.getAllUsers().size();
        UserDto userDto = new UserDto(null, "name", "vForGetAll@v.v");
        userController.addUser(userDto);
        assertEquals(countBefore + 1, userController.getAllUsers().size());
    }

    @Test
    void deleteUser() {
        int countBefore = userController.getAllUsers().size();
        UserDto userDto = new UserDto(null, "name", "vForDelete@v.v");
        UserDto addedUser = userController.addUser(userDto);
        assertEquals(countBefore + 1, userController.getAllUsers().size());
        userController.deleteUser(addedUser.getId());
        assertEquals(countBefore, userController.getAllUsers().size());
    }
}