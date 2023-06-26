package id.rockierocker.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "oauth_access_token")
@Where(clause = "deleted is null")
public class AccessToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String token;

    private String username;

    private String clientId;

    @Column(columnDefinition = "TEXT")
    private String details;


}
