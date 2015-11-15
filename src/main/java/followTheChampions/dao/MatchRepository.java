package followTheChampions.dao;

import followTheChampions.models.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MatchRepository extends CrudRepository<Match, Long> {

    Match getById(Long id);
}
