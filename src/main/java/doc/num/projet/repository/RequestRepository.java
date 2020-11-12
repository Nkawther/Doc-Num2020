package doc.num.projet.repository;



import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Request;

@Repository
@Primary
public interface RequestRepository extends CrudRepository<Request,Long>{
    
    public List<Request> findAllByOrderById();

}
