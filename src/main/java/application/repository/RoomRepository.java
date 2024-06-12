package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.RoomDTO;

@Repository
public interface RoomRepository extends JpaRepository<RoomDTO, Long> {

}
