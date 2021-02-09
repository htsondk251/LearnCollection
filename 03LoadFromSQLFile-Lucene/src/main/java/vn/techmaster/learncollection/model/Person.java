package vn.techmaster.learncollection.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Entity(name = "person")
@Table(name = "person")
@Setter
@Getter
@Indexed
public class Person {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @FullTextField
  private String fullname;
  @FullTextField
  private String job;
  private String gender;
  private String city;
  private int salary;
  @Temporal(TemporalType.DATE)
  private Date birthday;

  @Transient
  private int age;
  public int getAge(){
    Date safeDate = new Date(birthday.getTime());
    LocalDate birthDayInLocalDate = safeDate.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
    return Period.between(birthDayInLocalDate, LocalDate.now()).getYears();
  }

  @Override
  public String toString() {
    return "Person [city=" + city + ", fullname=" + fullname + ", job=" + job + "]";
  }

  
}
