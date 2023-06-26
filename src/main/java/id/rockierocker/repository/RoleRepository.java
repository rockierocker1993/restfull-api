package id.rockierocker.repository;

import id.rockierocker.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findFirstByName(String name);

    List<Role> findByNameIn(String[] names);
}
