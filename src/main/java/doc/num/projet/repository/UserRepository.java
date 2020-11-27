package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.User;



@Repository
@Primary
public interface UserRepository extends CrudRepository<User,Long>{
    
    public List<User> findAllByOrderById();
    public User findHeaderById(Long id);
}
