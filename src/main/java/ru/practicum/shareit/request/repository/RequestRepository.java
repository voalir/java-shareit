package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Integer> {
    List<ItemRequest> findAllByRequestorIdNotOrderByCreatedDesc(Integer requestorId, Pageable pageable);

    List<ItemRequest> findAllByRequestorIdOrderByCreatedDesc(Integer userId);
}
