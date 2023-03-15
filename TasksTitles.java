package com.example.demo11;

import java.util.Date;

public class TasksTitles {

    String first_name;
    String email;
    String title;
    Date deadline;
    public TasksTitles(String first_name, String email, String title,Date deadline) {
        this.first_name = first_name;
        this.email = email;
        this.title = title;
        this.deadline=deadline;
    }


    public String getFirst_name() {
        return first_name;
    }
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline=deadline;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
