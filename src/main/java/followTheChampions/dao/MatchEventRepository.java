package followTheChampions.dao;

import followTheChampions.models.MatchEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MatchEventRepository extends CrudRepository<MatchEvent, Long> {

    MatchEvent getById(Long id);
}
