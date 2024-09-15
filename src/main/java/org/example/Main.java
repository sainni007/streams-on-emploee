package org.example;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LoadData loadData = new LoadData();
        List<Employee> employeeList = loadData.loadEmployeeData();
        //1.how many male and female employees are there in organization?
        //employeeCountByGender(employeeList);
        //2.print the name of all the departments in the organization
        //namesOfAllDepts(employeeList);
        //3.what is the avg age of male and female employees?
        //avgAgeByGender(employeeList);
        //4.details of the highest paid employee in the organization?
        //highestPaidEmployee(employeeList);
        //5.names of all the employees who have joined after 2015
        //after2015(employeeList);
        //6.count the no.of employees in each department
        //noOfEmployeesByDept(employeeList);
        //7.what is the avg salary of each department?
        //avgSalaryOfEachDept(employeeList);
        //8.youngest male employee in the product development department?
        //Optional<Employee> youngEmployee = youngestMaleEmployee(employeeList);
        //System.out.println(youngEmployee.orElseThrow(() -> new RuntimeException("employee not found")));
        //youngEmployee.ifPresentOrElse(employee -> System.out.println(employee.toString()), () -> System.out.println("no value"));
        //9.who has the most working experience in the organization?
        //getMostExperiencedEmployee(employeeList);
        //10.how many male and female employees in the Sales and marketing team
        //noOfMaleAndFemaleEmployees(employeeList);
        //11.what is the avg salary of male and female employees?
        //avgSalOfMaleAndFemaleEmployees(employeeList);
        //12.list down the names of all employees in each dept
        //namesOfEmployees(employeeList);
        //13.what is the avg sal and total sal of whole organization
        //summary(employeeList);
        //14.separate employees younger than 25 and older than 25
        //separate(employeeList);
       //15.oldest employee
       old(employeeList) ;
    }

    private static void old(List<Employee> employeeList) {
        Optional<Employee> oldestEmployeeWrapper = employeeList.stream().max(Comparator.comparingInt(Employee::getAge));

        Employee oldestEmployee = oldestEmployeeWrapper.get();

        System.out.println("Name : "+oldestEmployee.getName());

        System.out.println("Age : "+oldestEmployee.getAge());

        System.out.println("Department : "+oldestEmployee.getDepartment());
    }

    private static void separate(List<Employee> employeeList) {
        Map<Boolean, List<Employee>> result = employeeList.stream().collect(Collectors.partitioningBy(e -> e.getAge() > 25));
    }

    private static  void summary(List<Employee> employeeList) {
        double average = employeeList.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
        System.out.println(average);

    }

    private static void namesOfEmployees(List<Employee> employeeList) {
        Map<String, List<Employee>> result = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        Set<Map.Entry<String, List<Employee>>> entrySet = result.entrySet();

        for(Map.Entry<String, List<Employee>> entry : entrySet) {
            System.out.println("---------------------");
            System.out.println(entry.getKey());
            System.out.println("---------------------");
            List<Employee> val = entry.getValue();
            for(Employee emp : val) {
                System.out.println(emp.getName());
            }
        }

    }

    private static void avgSalOfMaleAndFemaleEmployees(List<Employee> employeeList) {
        Map<String, Double> res = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(res);
    }

    private static void noOfMaleAndFemaleEmployees(List<Employee> employeeList) {
        Map<String, Long> result = employeeList.stream().filter(e -> e.getDepartment().equalsIgnoreCase("Sales And marketing"))
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(result);
    }

    private static void getMostExperiencedEmployee(List<Employee> employeeList) {
        Optional<Employee> result = employeeList.stream().min(Comparator.comparingInt(Employee::getYearOfJoining));
        System.out.println(result);
    }

    private static Optional<Employee> youngestMaleEmployee(List<Employee> employeeList) {
        /*return employeeList.stream()
                .filter(e -> Objects.equals(e.getDepartment(), "Product Development") && Objects.equals(e.getGender(), "Male"))
                .min(Comparator.comparingInt(Employee::getAge));*/
        return Optional.empty();
    }

    private static void avgSalaryOfEachDept(List<Employee> employeeList) {
        Map<String, Double> result = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(result);
    }

    private static void noOfEmployeesByDept(List<Employee> employeeList) {
        /*Map<String, Long> result = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        //System.out.println(result);
        Set<Map.Entry<String, Long>> entrySet = result.entrySet();
        for(Map.Entry<String, Long> entry : entrySet) {
            System.out.println(entry.getKey()+"     "+entry.getValue());
        }*/
        Map<String, List<Employee>> result = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        Set<Map.Entry<String, List<Employee>>> entrySet = result.entrySet();
        for(Map.Entry<String, List<Employee>> entry : entrySet) {
            System.out.println(entry.getKey());
            System.out.println("--------------------------");
            for(Employee employee : entry.getValue()) {
                System.out.println(employee.getName());
            }
            System.out.println("--------------------------");
        }

    }

    private static void after2015(List<Employee> employeeList) {
         employeeList.stream().filter(employee -> employee.getYearOfJoining() > 2015 )
                .map(Employee::getName).forEach(System.out::println);

    }

    private static void highestPaidEmployee(List<Employee> employeeList) {
            Optional.ofNullable(employeeList)  // Handle if employeeList is null
                    .map(list -> list.stream()
                            .max(Comparator.comparingDouble(Employee::getSalary)))
                    .ifPresentOrElse(
                            employee -> System.out.println(employee.toString()),
                            () -> System.out.println("No employee found")
                    );
            //System.out.println(employee.toString());
    }

    private static void avgAgeByGender(List<Employee> employeeList) {
        Map<String, Double> avgAgeOfMaleAndFemaleEmployees
                = employeeList.stream().collect(Collectors
                .groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        System.out.println(avgAgeOfMaleAndFemaleEmployees);
    }

    private static void namesOfAllDepts(List<Employee> employeeList) {
        employeeList.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);
    }

    private static void employeeCountByGender(List<Employee> employeeList) {
        Map<String, Long> noOfMaleAndFemaleEmployees
                = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(noOfMaleAndFemaleEmployees);
    }

}