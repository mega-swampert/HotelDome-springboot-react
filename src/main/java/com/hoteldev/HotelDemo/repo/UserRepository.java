package com.hoteldev.HotelDemo.repo;

import com.hoteldev.HotelDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.DocFlavor;
import java.util.Optional;

/**
 * ClassName: UserRepository
 * Package: com.hoteldev.HotelDemo.repo
 * Description:
 *
 * @Author MegaSwampert
 * @Create 5/09/2024 1:19 pm
 * @Version 1.0
 */

/*JpaRepository provides by JPA
* User is the entity type
* Long is the type of entity's primary key.
*
* JPA uses derived query method, SQL will be created automatically
*
* Optional: it indicates a value may or may not exist, avoiding the
* potential NullPointerException problem.*/

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
