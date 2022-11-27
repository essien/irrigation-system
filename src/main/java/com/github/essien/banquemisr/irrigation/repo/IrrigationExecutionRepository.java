package com.github.essien.banquemisr.irrigation.repo;

import com.github.essien.banquemisr.irrigation.entity.IrrigationExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Repository
public interface IrrigationExecutionRepository extends JpaRepository<IrrigationExecution, Long> {
}
