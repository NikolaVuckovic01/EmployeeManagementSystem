package com.example.demo11;

public class Employee {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String date_employment;
    private double salary;
    private int end_tasks;
    private int id_task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_employment() {
        return date_employment;
    }

    public void setDate_employment(String date_employment) {
        this.date_employment = date_employment;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getEnd_tasks() {
        return end_tasks;
    }

    public void setEnd_tasks(int end_tasks) {
        this.end_tasks = end_tasks;
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }
    public Employee(int id, String first_name, String last_name, String email, String date_employment, double salary, int end_tasks, int id_task) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.date_employment = date_employment;
        this.salary = salary;
        this.end_tasks = end_tasks;
        this.id_task = id_task;
    }
}
