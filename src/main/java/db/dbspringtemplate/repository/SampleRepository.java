package db.dbspringtemplate.repository;

import db.dbspringtemplate.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {

    Optional<Sample> findByName(String name);

    @Query("select x.id from #{#entityName} x")
    List<Long> getAllIds();

}
