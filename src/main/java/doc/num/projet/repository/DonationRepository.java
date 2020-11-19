package doc.num.projet.repository;


import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Donation;

@Repository
@Primary
public interface DonationRepository extends CrudRepository<Donation,Long>{
    
    public List<Donation> findAllByOrderById();

}
