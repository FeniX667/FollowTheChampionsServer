package followTheChampions.dao;

import followTheChampions.models.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {

    Competition getById(Long id);
}
