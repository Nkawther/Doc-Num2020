package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.CatalogueRequest;

@Repository
@Primary
public interface CatalogueRequestRepository extends CrudRepository<CatalogueRequest, Long> {

    public List<CatalogueRequest> findAllByOrderById();

}