package vn.techmaster.learncollection.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import vn.techmaster.learncollection.model.Person;
import vn.techmaster.learncollection.model.SearchRequest;
import vn.techmaster.learncollection.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SearchController {
    @Autowired private PersonService personService;

    @GetMapping(value="/search")
    public String showSearchForm(Model model, HttpServletRequest request) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "search";
    }
    
    @PostMapping("/search")
    public String handleSearch(SearchRequest searchRequest, Model model, HttpServletRequest request) {
        List<Person> people = personService.searchPerson(searchRequest.getTerm(), 5, 0);
        model.addAttribute("people", people);

        return "searchResult";
    }

    @GetMapping("/search/index")
    public String reindexFullText(Model model, HttpServletRequest request) {
        personService.reindexFullTextSearch();

        return "done";
    }
}
