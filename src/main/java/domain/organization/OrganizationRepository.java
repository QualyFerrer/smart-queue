package domain.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByCnpj(String cnpj);

    java.util.List<Organization> findByActive(Boolean active);
}
