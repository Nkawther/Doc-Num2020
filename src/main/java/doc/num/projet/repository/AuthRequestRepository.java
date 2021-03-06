package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.AuthRequest;

@Repository
@Primary
public interface AuthRequestRepository extends CrudRepository<AuthRequest, Long> {

    public List<AuthRequest> findAllByOrderById();

}