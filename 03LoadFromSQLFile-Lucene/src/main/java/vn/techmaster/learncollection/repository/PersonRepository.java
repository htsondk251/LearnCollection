package vn.techmaster.learncollection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.learncollection.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
