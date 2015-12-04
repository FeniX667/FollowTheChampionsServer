package followTheChampions.dao;

import followTheChampions.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FavouritedMatchRepository extends CrudRepository<FavouritedMatch, Long> {

    FavouritedMatch getById(Long id);

    List<FavouritedMatch> getByRegisteredDevice(RegisteredDevice registeredDevice);

    @Query("SELECT fm "
            + "FROM FavouritedMatch fm "
            + "WHERE "
            + "fm.registeredDevice = (:device) AND "
            + "fm.match =  (:match)")
    List<FavouritedMatch> getByDeviceTokenTeamId(@Param("device")RegisteredDevice device, @Param("match")Match match);
}
