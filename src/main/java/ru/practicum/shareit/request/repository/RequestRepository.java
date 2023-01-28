package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Integer> {
    Collection<ItemRequest> findAllByRequestorIdNotOrderByCreatedDesc(Integer requestorId, Pageable pageable);

    Collection<ItemRequest> findAllByRequestorIdOrderByCreatedDesc(Integer userId);
}
