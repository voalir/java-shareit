package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Collection<Item> findByOwner_id(Integer userId);

    @Query("select it from Item as it where it.available = true and " +
            "(lower(it.name) like CONCAT('%', lower(?1), '%') " +
            "or lower(it.description) like CONCAT('%', lower(?1), '%')) ")
    List<Item> findItems(String text);
}
