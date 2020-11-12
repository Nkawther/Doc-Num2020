package doc.num.projet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import doc.num.projet.model.Deny;

public interface DenyRepository extends CrudRepository<Deny, Long> {

    public List<Deny> findAllByOrderById();
}
