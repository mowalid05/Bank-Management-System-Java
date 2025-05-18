/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;
import Classes.Employee;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author LENOVO
 */
public class BranchMismatchException extends RuntimeException {
    private final ArrayList<Employee> mismatchedEmployees;

    public BranchMismatchException(ArrayList<Employee> mismatchedEmployees) {
        super("Employees in wrong branch: " + mismatchedEmployees.stream()
              .map(e -> e.getFirstName() + " " + e.getLastName())
              .collect(Collectors.joining(", ")));
        this.mismatchedEmployees = mismatchedEmployees;
    }

    public ArrayList<Employee> getMismatchedEmployees() {
        return mismatchedEmployees;
    }
}