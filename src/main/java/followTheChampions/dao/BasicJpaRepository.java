package followTheChampions.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface BasicJpaRepository<T, ID extends Serializable> extends Repository<T, ID> {

    T findOne(ID id);

    boolean exists(ID id);

    List<T> findAll();

    List<T> findAll(Iterable<ID> ids);

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> S save(S entity);

    <S extends T> List<S> save(Iterable<S> entities);

    T saveAndFlush(T entity);

    void flush();
}
