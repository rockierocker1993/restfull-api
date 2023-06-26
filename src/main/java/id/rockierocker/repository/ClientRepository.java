package id.rockierocker.repository;

import id.rockierocker.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findFirstByClientId(String clientId);

}
