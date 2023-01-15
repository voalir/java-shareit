package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "start_date")
    Date start;
    @Column(name = "end_date")
    Date end;
    @JoinColumn(name = "item_id")
    @ManyToOne
    Item item;
    @JoinColumn(name = "booker_id")
    @ManyToOne
    User booker;
    @Enumerated(EnumType.STRING)
    BookingStatus status;
}
