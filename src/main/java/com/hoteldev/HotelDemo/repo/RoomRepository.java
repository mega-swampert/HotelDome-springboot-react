package com.hoteldev.HotelDemo.repo;

import com.hoteldev.HotelDemo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * ClassName: RoomRepository
 * Package: com.hoteldev.HotelDemo.repo
 * Description:
 *
 * @Author MegaSwampert
 * @Create 5/09/2024 1:20 pm
 * @Version 1.0
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /*Custom JPQL query
    *
    * updates and deletions require the use of the @Modifying
    * update and delete involve modifications to the database and
    * need to be performed in a transaction. Add @Transactional at service layer method*/

    @Query("SELECT DISTINCT  r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN(SELECT bk.room.id FROM Booking bk WHERE" +
            "(bk.checkInDate<= :checkOutDate) AND (bk.checkOutDate>= :checkInDate))")
    List<Room> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    @Query("SELECT r From Room r WHERE r.id NOT IN (SELECT b.room.id FROM  Booking b) ")
    List<Room> getAllAvailableRooms();
}
