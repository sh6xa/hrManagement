package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.SalaryTaken;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.enums.Month;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalaryTakenRepository extends JpaRepository<SalaryTaken, UUID> {
    Optional<SalaryTaken> findByOwnerAndPeriod(User owner, Month period);
    List<SalaryTaken> findAllByOwner(User user);
    List<SalaryTaken> findAllByPeriod(Month period);
}
