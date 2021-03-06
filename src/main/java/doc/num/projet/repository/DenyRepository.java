package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Deny;

@Repository
@Primary
public interface DenyRepository extends CrudRepository<Deny, Long> {

    public List<Deny> findAllByOrderById();
}
