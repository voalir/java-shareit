package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name", length = 50, nullable = false)
    String name;
    @Column(name = "description", nullable = false)
    String description;
    @Column(name = "is_available")
    Boolean available;
    @JoinColumn(name = "owner_id")
    @ManyToOne
    User owner;
    @JoinColumn(name = "request_id")
    @ManyToOne
    ItemRequest request;
}
