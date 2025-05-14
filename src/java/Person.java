
import java.io.Serializable;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */


// Person superclass (abstract)
public abstract class Person implements Serializable{
    protected String firstName;
    protected String lastName;
    protected LocalDate bdate;
    protected Gender gender;

    public Person(String firstName, String lastName, LocalDate bdate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bdate = bdate;
        this.gender = gender;
    }
    public Person(){}


    
}
