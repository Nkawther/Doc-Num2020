package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Accept;

@Repository
@Primary
public interface AcceptRepository extends CrudRepository<Accept, Long> {

    public List<Accept> findAllByOrderById();
}
