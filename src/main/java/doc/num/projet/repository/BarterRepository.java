package doc.num.projet.repository;



import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Barter;

@Repository
@Primary
public interface BarterRepository extends CrudRepository<Barter,Long>{
    
    public List<Barter> findAllByOrderById();

}