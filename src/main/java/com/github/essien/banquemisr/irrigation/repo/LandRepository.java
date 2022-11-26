package com.github.essien.banquemisr.irrigation.repo;

import com.github.essien.banquemisr.irrigation.entity.Land;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Repository
public interface LandRepository extends JpaRepository<Land, Long> {

    Optional<Land> findByKey(String key);
}
