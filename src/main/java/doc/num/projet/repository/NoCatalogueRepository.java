package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.NoCatalogue;

@Repository
@Primary
public interface NoCatalogueRepository extends CrudRepository<NoCatalogue, Long> {

    public List<NoCatalogue> findAllByOrderById();

}