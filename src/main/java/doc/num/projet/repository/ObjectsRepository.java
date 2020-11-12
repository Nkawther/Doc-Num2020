package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Objects;

@Repository
@Primary
public interface ObjectsRepository extends CrudRepository<Objects, String> {

    public List<Objects> findAllByOrderByObjectName();

}