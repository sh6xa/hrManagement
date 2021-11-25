package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {

}
