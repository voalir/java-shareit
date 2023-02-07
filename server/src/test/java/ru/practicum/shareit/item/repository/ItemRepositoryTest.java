package ru.practicum.shareit.item.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@DataJpaTest
@Sql(value = {"/DropTables.sql", "/JpaRepositoryPrepare.sql"})
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void findItems() {
        List<Item> items = itemRepository.findItems("item", PageRequest.of(0, 5));
        Assertions.assertThat(items).hasSize(2);
    }
}