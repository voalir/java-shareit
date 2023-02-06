package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwnerId(Integer userId, Pageable pageable);

    @Query("select it from Item as it where it.available = true and " +
            "(lower(it.name) like CONCAT('%', lower(?1), '%') " +
            "or lower(it.description) like CONCAT('%', lower(?1), '%')) ")
    List<Item> findItems(String text, Pageable pageable);

    List<Item> findByRequestId(Integer id);

    List<Item> findAllByRequestIdIn(List<Integer> id);
}
