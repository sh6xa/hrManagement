package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
