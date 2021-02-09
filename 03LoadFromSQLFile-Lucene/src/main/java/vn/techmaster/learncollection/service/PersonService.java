package vn.techmaster.learncollection.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.CacheMode;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.techmaster.learncollection.model.Person;
import vn.techmaster.learncollection.repository.PersonRepository;

@Service
public class PersonService {
    private static final int PAGESIZE = 10;
    @Autowired PersonRepository personRepository;
    @PersistenceContext EntityManager em;

    // public List<Person> listAll() {
    //     // return personRepository.findAll();
    //     Pageable pageable = PageRequest.of(0, PAGESIZE);
    //     Page<Person> page = personRepository.findAll(pageable);
    //     List<Person> people = page.getContent();

    //     return people;
    // }

    // public Page<Person> pageZero() {
    //     Pageable pageable = PageRequest.of(0, PAGESIZE);

    //     return personRepository.findAll(pageable);
    // }

    // public Page<Person> listAll(int pageNum) {
    //     Sort sort = Sort.by("fullname").ascending();
    //     Pageable pageable = PageRequest.of(pageNum - 1, PAGESIZE, sort);
    //     return personRepository.findAll(pageable);
    // }

    public Page<Person> listAllPagingAndSorting(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, PAGESIZE, 
            sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return personRepository.findAll(pageable);
    }

    public List<Person> searchPerson(String terms, int limit, int offset) {
        return Search.session(em)
            .search(Person.class)
            .where(f -> f.match()
                .fields("fullname", "job")
                .matching(terms))
            .fetchHits(offset, limit);
    }

    public void reindexFullTextSearch() {
        SearchSession searchSession = Search.session(em);

        MassIndexer indexer = searchSession.massIndexer(Person.class).dropAndCreateSchemaOnStart(true)
            .typesToIndexInParallel(2)
            .batchSizeToLoadObjects(10)
            .idFetchSize(200)
            .threadsToLoadObjects(5)
            .cacheMode(CacheMode.IGNORE);

        indexer.start();
    }
}
