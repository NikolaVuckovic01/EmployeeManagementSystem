package com.example.demo11;

import java.util.Date;

public class Task {

    int id_task;
    int id_employee;
    String title;
    String description;
    Date deadline;
    public Task(int id_task, int id_employee, String title, String description, Date deadline) {
        this.id_task = id_task;
        this.id_employee = id_employee;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String title) {
        this.title = title;
    }



    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    @Override
    public String toString() {
        return this.title;
    }

   public Date returnDate(){
        return this.deadline;
   }
}
