package followTheChampions.dao;

import followTheChampions.models.FavouritedMatch;
import followTheChampions.models.FavouritedTeam;
import followTheChampions.models.RegisteredDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FavouritedMatchRepository extends CrudRepository<FavouritedMatch, Long> {

    FavouritedMatch getById(Long id);

    List<FavouritedMatch> getByRegisteredDevice(RegisteredDevice registeredDevice);
}
