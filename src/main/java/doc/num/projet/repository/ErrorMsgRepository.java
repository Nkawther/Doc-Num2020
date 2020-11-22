package doc.num.projet.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.ErrorMsg;
import java.util.List;

@Repository
@Primary
public interface ErrorMsgRepository extends CrudRepository<ErrorMsg, Long> {

    public List<ErrorMsg> findAllByOrderById();
}
