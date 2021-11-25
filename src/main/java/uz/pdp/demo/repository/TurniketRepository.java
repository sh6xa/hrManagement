package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Turniket;
import uz.pdp.demo.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface TurniketRepository extends JpaRepository<Turniket, UUID> {
    Optional<Turniket> findByNumber(String number);
    Optional<Turniket> findAllByOwner(User owner);
}
