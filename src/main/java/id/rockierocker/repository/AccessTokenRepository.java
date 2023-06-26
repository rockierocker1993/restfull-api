package id.rockierocker.repository;

import id.rockierocker.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends PagingAndSortingRepository<AccessToken, Long>, JpaSpecificationExecutor<AccessToken> {

    Optional<AccessToken> findFirstByUsername(String username);

    Optional<AccessToken> findFirstByToken(String token);

}
