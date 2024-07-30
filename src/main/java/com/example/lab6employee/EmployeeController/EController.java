package com.example.lab6employee.EmployeeController;

import com.example.lab6employee.EmployeeModel.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EController {
    ArrayList<Employee> employees = new ArrayList<Employee>();


    @GetMapping("/get")
    public ResponseEntity getAllEmployee() {
        if (employees.isEmpty()) {
            return ResponseEntity.status(200).body("No employees");
        }else {
            return ResponseEntity.ok(employees);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }else {
            employees.add(employee);
            return ResponseEntity.status(201).body("Employee added successfully");
        }
    }

    @PutMapping("/update/employee/{index}")
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee, Errors errors, @PathVariable int index) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }else {
            employees.set(index, employee);
            return ResponseEntity.status(201).body("Employee updated successfully");
        }
    }

    @DeleteMapping("/delete/employee/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index){
        if (index>employees.size()) {
            return ResponseEntity.status(404).body("Employee not found");
        }else {
            employees.remove(index);
            return ResponseEntity.status(204).body("Employee deleted successfully");
        }
    }

    @GetMapping("/search/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position){
       ArrayList<Employee> searchedEmployees = new ArrayList<Employee>();
        if (employees.isEmpty()) {
            return ResponseEntity.status(404).body("No employees");
        }else {
            //NOTE:
            //i did not check because wean add new employee i check their
            //position (supervisor or coordinator).
            for (Employee employee : employees) {
                if(employee.getPosition().equalsIgnoreCase(position)){
                    searchedEmployees.add(employee);
                }
            }
            return ResponseEntity.status(200).body(searchedEmployees);
        }

    }
    @GetMapping("/age")
    public ResponseEntity getEmployeeAge(){
       if (employees.isEmpty()) {
          return ResponseEntity.status(404).body("No employees");
       }else {

           int highest = 0;
           int lowest = 100;
           int[] getvalues = new int[2];
           for (int i = 0; i < employees.size(); i++) {
               if (employees.get(i).getAge() >= highest) {
                   highest = employees.get(i).getAge();
               }
               if (employees.get(i).getAge() <= lowest) {
                   lowest = employees.get(i).getAge();
               }
           }
           return ResponseEntity.status(200).body("the highest age is " + highest +" and lowest age is " + lowest+" and the range is "+(highest-lowest));
       }
    }

    @PutMapping("/update/leave/{index}")
    public ResponseEntity updateEmployeeLeave(@PathVariable int index) {
        if (employees.isEmpty()) {
            return ResponseEntity.status(404).body("No employees");
        }else {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.contains(employees.get(index))){
                    if (!employees.get(index).isOnLeave()){
                        if (employees.get(index).getAnnualLeave()>0){
                            employees.get(index).setOnLeave(true);
                            employees.get(index).setAnnualLeave(employees.get(index).getAnnualLeave()-1);
                            return ResponseEntity.status(204).body("Employee: "+employees.get(index).getName()+" leave successfully");

                        }else {
                            return ResponseEntity.status(404).body("you do not have any Annual Leave");
                        }

                    }else {
                        return ResponseEntity.status(200).body("You have Leave");
                    }
                }
            }
        }
        return ResponseEntity.status(404).body("employee not found try again later");
    }

    @GetMapping("/get/no/annual")
    public ResponseEntity getEmployeeNoAnnual(){
        ArrayList<Employee> NoAnnualLeave = new ArrayList<>();
        if (employees.isEmpty()) {
            return ResponseEntity.status(404).body("No employees");
        }else {
            for (Employee employee : employees) {
                if (employee.getAnnualLeave()==0){
                    NoAnnualLeave.add(employee);
                }
            }
            return ResponseEntity.status(200).body(NoAnnualLeave);
        }
    }


    @PutMapping("/update/promote/{postion}/{eindex}")
    public ResponseEntity updateEmployeePromote(@PathVariable String postion,@PathVariable int eindex) {
        if (eindex > employees.size()) {
            return ResponseEntity.status(404).body("check employees index");
        } else {

            if (employees.isEmpty()) {
                return ResponseEntity.status(404).body("No employees");
            } else {
                if (postion.equalsIgnoreCase("supervisor")) {
                    for (int i = 0; i < employees.size(); i++) {
                        if (employees.contains(employees.get(eindex))) {
                            if (employees.get(eindex).getAge() >= 30) {
                                if (!employees.get(eindex).isOnLeave()) {
                                    employees.get(eindex).setPosition(postion);
                                    return ResponseEntity.status(204).body("Employee promoted successfully");
                                }else {
                                    return ResponseEntity.status(404).body("The Employee  On Leave");
                                }
                            }else {
                                return ResponseEntity.status(404).body("the age most be > 30");
                            }
                        }
                    }
                } else {
                    return ResponseEntity.status(200).body("You must be a supervisor");
                }
            }
            return ResponseEntity.status(404).body("employee not found try again later");
        }
    }
}
