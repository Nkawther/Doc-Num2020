package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Auth;

@Repository
@Primary
public interface AuthRepository extends CrudRepository<Auth, Long> {

    public List<Auth> findAllByOrderById();
}
