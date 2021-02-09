package vn.techmaster.learncollection.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.learncollection.model.Person;
import vn.techmaster.learncollection.model.SearchRequest;
import vn.techmaster.learncollection.repository.PersonRepository;
import vn.techmaster.learncollection.service.PersonService;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired private PersonService personService;
    // private PersonRepository personRepository;

    @GetMapping
    public String getHome(Model model) {
        // List<Person> people = personService.listAll();
        // Page<Person> page = personService.pageZero();
        // int currentPage = 1;
        // model.addAttribute("currentPage", currentPage);
        // model.addAttribute("people", page.getContent());
        // model.addAttribute("totalPages", page.getTotalPages());
        // model.addAttribute("totalItems", page.getTotalElements());
        
        // return "home";
        return viewPage(model, 1, "id", "asc");
    }

    
    @GetMapping("page/{pageNum}")
    public String viewPage(Model model, 
            @PathVariable("pageNum") int pageNum,
            @Param("sortField") String sortField,
            @Param("sortDir") String sortDir) {
            //SearchRequest searchRequest) {

        Page<Person> page = personService.listAllPagingAndSorting(pageNum, sortField, sortDir);
        List<Person> people = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalNumber", page.getTotalElements());
        model.addAttribute("people", people);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "index";
    }

    // @GetMapping("/getAll")
    // public String getAll(Model model) {
    //     List<Person> listPerson = personRepository.getAll();
    //     model.addAttribute("list", listPerson);
    //     return "listPerson";
    // }

    // @GetMapping("/sortPeopleByFullNameReversed")
    // public String sortPeopleByFullNameReversed(Model model) {
    //     List<Person> listPerson = personRepository.sortPeopleByFullNameReversed();
    //     model.addAttribute("list", listPerson);
    //     return "listPerson";
    // }

    // @GetMapping("/getSortedJobs")
    // public String getSortedJobs(Model model) {
    //     List<String> listJobs = personRepository.getSortedJobs();
    //     model.addAttribute("list", listJobs);
    //     return "listString";
    // }

    // @GetMapping("/getSortedCities")
    // public String getSortedCities(Model model) {
    //     List<String> listCities = personRepository.getSortedCities();
    //     model.addAttribute("list", listCities);
    //     return "listString";
    // }
    
    // @GetMapping("/groupPeopleByCity")
    // public String groupPeopleByCity(Model model) {
    //     HashMap<String, List<Person>> mapPeopleByCity = personRepository.groupPeopleByCity();
    //     model.addAttribute("map", mapPeopleByCity);
    //     return "mapStringList";
    // }

    // @GetMapping("/groupJobByCount")
    // public String groupJobByCount(Model model) {
    //     HashMap<String, Long> mapJobByCount = personRepository.groupJobByCount();
    //     model.addAttribute("map", mapJobByCount);
    //     return "mapStringLong";
    // }

    // @GetMapping("/findTop5Jobs")
    // public String findTop5Jobs(Model model) {
    //     HashMap<String, Long> mapTop5Jobs = personRepository.findTop5Jobs();
    //     model.addAttribute("map", mapTop5Jobs);
    //     return "mapStringLong";
    // }

    // @GetMapping("/findTop5Cities")
    // public String findTop5Cities(Model model) {
    //     HashMap<String, Long> mapTop5Cities = personRepository.findTop5Cities();
    //     model.addAttribute("map", mapTop5Cities);
    //     return "mapStringLong";
    // }

    // @GetMapping("/findTopJobInCity")
    // public String findTopJobInCity(Model model) {
    //     HashMap<String, String> mapTopJobInCity = personRepository.findTopJobInCity();
    //     model.addAttribute("map", mapTopJobInCity);
    //     return "mapStringLong";
    // }
    
    // @GetMapping("/averageJobSalary")
    // public String averageJobSalary(Model model) {
    //     HashMap<String, Double> mapTopJobInCity = personRepository.averageJobSalary();
    //     model.addAttribute("map", mapTopJobInCity);
    //     return "mapStringDouble";
    // }

    // @GetMapping("/top5HighestSalaryCities")
    // public String top5HighestSalaryCities(Model model) {
    //     HashMap<String, Double> mapTop5HighestSalaryCities = personRepository.top5HighestSalaryCities();
    //     model.addAttribute("map", mapTop5HighestSalaryCities);
    //     return "mapStringDouble";
    // }

    // @GetMapping("/averageJobAge")
    // public String averageJobAge(Model model) {
    //     HashMap<String, Double> mapAverageJobAge = personRepository.averageJobAge();
    //     model.addAttribute("map", mapAverageJobAge);
    //     return "mapStringDouble";
    // }

    // @GetMapping("/averageCityAge")
    // public String averageCityAge(Model model) {
    //     HashMap<String, Double> mapAverageCityAge = personRepository.averageCityAge();
    //     model.addAttribute("map", mapAverageCityAge);
    //     return "mapStringDouble";
    // }

    // @GetMapping("/find5CitiesHaveMostSpecificJob")
    // public String find5CitiesHaveMostSpecificJobGet(Model model) {
    //     return "specificJob";
    // }

    // @PostMapping("/find5CitiesHaveMostSpecificJob")
    // public String find5CitiesHaveMostSpecificJobPost(@RequestParam("job") String job, Model model) {
    //     List<String> list5CitiesHaveMostSpecificJob = personRepository.find5CitiesHaveMostSpecificJob(job);
    //     model.addAttribute("job", job);
    //     model.addAttribute("list", list5CitiesHaveMostSpecificJob);
    //     return "listString";
    // }
}
