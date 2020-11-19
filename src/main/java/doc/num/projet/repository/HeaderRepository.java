package doc.num.projet.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Header;

@Repository
@Primary
public interface HeaderRepository extends CrudRepository<Header, Long> {
    public List<Header> findAllByOrderById();
}
