package doc.num.projet.repository;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import doc.num.projet.model.Message;

@Repository
@Primary
public interface MessageRepository extends CrudRepository<Message, Long> {

    public List<Message> findAllByOrderById();

}