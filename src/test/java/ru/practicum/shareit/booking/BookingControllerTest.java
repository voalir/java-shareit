package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookingService bookingService;

    @Test
    void addBooking() throws Exception {
        when(bookingService.addBooking(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(getBookingDto());
        String content = mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getBookingDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(getBookingDto()), content);
    }

    @Test
    void addBookingBad() throws Exception {
        BookingDto bookingDto = getBookingDto();
        bookingDto.setEnd(LocalDateTime.now().minusDays(3));
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Mockito.verify(bookingService, Mockito.never()).addBooking(ArgumentMatchers.any(), ArgumentMatchers.anyInt());
    }

    @Test
    void getBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}", 1)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingById(1, 1);
    }

    @Test
    void confirmBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/{bookingId}?approved={approved}", 1, true)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).confirmBooking(1, 1, true);
    }

    @Test
    void getBookingByOwner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingByOwner(1, "ALL", 0, 10);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner?state={state}", "WAITING")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingByOwner(1, "WAITING", 0, 10);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner?from={from}&size={size}", -1, 0)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isBadRequest());

        Mockito.verify(bookingService, Mockito.never()).getBookingByOwner(1, "ALL", -1, 0);
    }

    @Test
    void getBookingByBooker() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingByBooker(1, "ALL", 0, 10);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings?state={state}", "WAITING")
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingByBooker(1, "WAITING", 0, 10);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings?from={from}&size={size}", -1, 0)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isBadRequest());

        Mockito.verify(bookingService, Mockito.never()).getBookingByBooker(1, "ALL", -1, 0);
    }

    BookingDto getBookingDto() {
        return new BookingDto(1,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                1,
                null,
                new UserDto(1, "name", "mail"),
                BookingStatus.WAITING);
    }
}