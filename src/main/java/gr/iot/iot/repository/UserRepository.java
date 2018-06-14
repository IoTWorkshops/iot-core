package gr.iot.iot.repository;

import gr.iot.iot.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByUuid(String uuid);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmail(String email);

    @Cacheable("user_findAllByUsernameLike")
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(concat('%', concat(?1, '%')))")
    List<User> findAllByUsernameLike(Pageable pageable, String username);

}
