package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Catalogue;

@Repository
@Primary
public interface CatalogueRepository extends CrudRepository<Catalogue, Long> {

    public List<Catalogue> findAllByOrderById();

}