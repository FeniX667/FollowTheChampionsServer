package followTheChampions.dao;

import followTheChampions.models.FavouritedTeam;
import followTheChampions.models.RegisteredDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FavouritedTeamRepository extends CrudRepository<FavouritedTeam, Long> {

    FavouritedTeam getById(Long id);

    List<FavouritedTeam> getByRegisteredDevice(RegisteredDevice registeredDevice);
}
