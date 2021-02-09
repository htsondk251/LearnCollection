package vn.techmaster.learncollection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.RowFilter.Entry;

import org.assertj.core.data.Offset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import vn.techmaster.learncollection.model.Person;
import vn.techmaster.learncollection.repository.PersonRepositoryInterface;

@SpringBootTest
class PersonRepositoryTest {

	@Autowired
	PersonRepositoryInterface personRepository;

	@Test
	public void getAll() {
		List<Person> people = personRepository.getAll();
		assertThat(people).hasSize(20);		
	}

	@Test	
	public void getSortedCities(){
		List<String> sortedCities = personRepository.getSortedCities();
		sortedCities.forEach(System.out::println);  //In theo tất các thành phố ra để kiểm tra xem có sắp xếp không
	/*
		Cách này viết dài
		assertThat(sortedCities).contains("Paris", "Dubai");
		assertThat(sortedCities).isSortedAccordingTo(Comparator.naturalOrder());*/

		//Cách này chain các điều kiện test với nhau ngắn gọn và đẹp hơn
		assertThat(sortedCities).isSortedAccordingTo(Comparator.naturalOrder()).hasSize(30)
		.contains("Berlin", "Budapest", "Buenos Aires", "Copenhagen", "Hanoi", "Jakarta","Mexico City","Zagreb");
	}

	@Test	
	public void getSortedJobs(){
		List<String> sortedJobs = personRepository.getSortedJobs();
		sortedJobs.forEach(System.out::println); 

		assertThat(sortedJobs).isSortedAccordingTo(Comparator.naturalOrder()).hasSize(17)
		.contains("Pole Dancer", "Bartender", "Developer", "Personal Trainer", "Soldier", "Teacher", "Taxi Driver", "Nurse", "Musician");

	}

	@Test
	public void groupPeopleByCity() {
		HashMap<String, List<Person>> groupPeople = personRepository.groupPeopleByCity();
		groupPeople.get("Budapest").forEach(person -> System.out.println(person.getFullname()));
		
		assertThat(groupPeople.get("Budapest").stream().map(Person::getFullname).collect(Collectors.toList()))
			.hasSize(4)
			.contains("Ardenia Hurn", "Jackie Wickenden", "Egor Fratczak", "Corty McCurtain");
	}

		
	@Test
	public void groupJobByCount() {
		Map<String, Long> groupJobs = personRepository.groupJobByCount();
		assertThat(groupJobs.keySet()).contains("Film Maker", "Nurse", "Teacher").size().isEqualTo(13);
	}

	@Test
	public void findTop5JobsTest() {
		Map<String, Long> top5Cities = personRepository.findTop5Jobs();
		top5Cities.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(top5Cities.keySet()).contains("Film Maker", "Nurse", "Project Manager", "Personal Trainer", "Bartender");
	}
	
	@Test
	public void findTop5CitiesTest() {
		Map<String, Long> top5Cities = personRepository.findTop5Cities();
		top5Cities.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(top5Cities.keySet()).contains("Canberra", "London", "Zagreb");
	}

	@Test
	public void sortPeopleByFullNameReversed() {
		List<Person> sortedPeople = personRepository.sortPeopleByFullNameReversed();
		sortedPeople.forEach(person -> System.out.println(person.getFullname()));
		assertThat(sortedPeople).isSortedAccordingTo(Comparator.comparing(Person::getFullname).reversed());
	}

	@Test
	public void findTopJobInCityTest() {
		Map<String, String> mapTopJob = personRepository.findTopJobInCity();
		mapTopJob.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(mapTopJob.get("Bejing")).isEqualTo("Soldier");
		assertThat(mapTopJob.get("Budapest")).isEqualTo("Developer");
		assertThat(mapTopJob.get("Montevideo")).isEqualTo("Project Manager");
	}
	
	@Test
	public void averageJobSalary() {
		Map<String, Double> mapJobSalary = personRepository.averageJobSalary();
		mapJobSalary.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(mapJobSalary.get("Developer")).isEqualTo(8486.0, Offset.offset(1.0));
		assertThat(mapJobSalary.get("Bartender")).isEqualTo(14142.0, Offset.offset(1.0));
		assertThat(mapJobSalary.get("Film Maker")).isEqualTo(9378.0, Offset.offset(1.0));
	}

	@Test
	public void top5HighestSalaryCities() {
		Map<String, Double> map5HighestSalaryCities = personRepository.top5HighestSalaryCities();
		map5HighestSalaryCities.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(map5HighestSalaryCities.keySet()).contains("Berlin", "Ottawa", "Copenhagen");
	}

	@Test
	public void averageJobAge() {
		Map<String, Double> mapAverageJobAge = personRepository.averageJobAge();
		mapAverageJobAge.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(mapAverageJobAge.get("Banker")).isEqualTo(50.0, Offset.offset(1.0));
		assertThat(mapAverageJobAge.get("Carpenter")).isEqualTo(41.0, Offset.offset(1.0));
	}

	@Test
	public void averageCityAge() {
		Map<String, Double> mapAverageCityAge = personRepository.averageCityAge();
		mapAverageCityAge.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		assertThat(mapAverageCityAge.get("Berlin")).isEqualTo(33.0, Offset.offset(1.0));
		assertThat(mapAverageCityAge.get("Caracas")).isEqualTo(51.0, Offset.offset(0.0));
	}

	@Test
	public void find5CitiesHaveMostSpecificJob() {
		// List<String> list5CitiesHaveMostSpecificJob = personRepository.find5CitiesHaveMostSpecificJob("Film Maker");
		assertThat(personRepository.find5CitiesHaveMostSpecificJob("Personal Trainer").contains("Berlin"));
		assertThat(personRepository.find5CitiesHaveMostSpecificJob("Soldier").contains("Bejing")).isTrue();
		assertThat(personRepository.find5CitiesHaveMostSpecificJob("Project Manager").contains("Montevideo")).isTrue();
	}
}
