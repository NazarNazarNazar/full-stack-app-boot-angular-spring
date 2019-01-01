package com.example.learning.repository;

import com.example.learning.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

    RoomEntity findRoomEntityById(Long id);
}
