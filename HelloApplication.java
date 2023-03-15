package com.example.demo11;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.shape.Box;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import javafx.geometry.*;
public class HelloApplication extends Application {
    public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage primaryStage) throws SQLException {
        Label lblHeading = new Label("Employees");
        lblHeading.setFont(new Font("Arial", 20));
        TableView<Employee> table = new TableView<Employee>();
        Button addEmployee=new Button("Add employee");
        addEmployee.setMaxWidth(750);
        Button deleteEmployee=new Button("Delete employee");
        deleteEmployee.setMaxWidth(750);
        Button editEmployee=new Button("Edit employee");
        editEmployee.setMaxWidth(750);
        Button addTaskToEmployee=new Button("Add task to employee");
        addTaskToEmployee.setMaxWidth(750);
        Button topEmployees=new Button("Top 5 employees");
        topEmployees.setMaxWidth(750);
        topEmployees.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage topEmployees=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                topEmployees.setHeight(400);
                topEmployees.setWidth(300);
                topEmployees.setScene(scene);
                topEmployees.setTitle("Top employees");

                ArrayList<Integer> endTasks=new ArrayList<Integer>();
                ArrayList<String> names=new ArrayList<String>();
                try {
                    DbConnection db=new DbConnection();
                    db.myRs=db.myStmt.executeQuery("select endTasks, first_name from company.employee " +
                            "order by endTasks desc");
                    while (db.myRs.next()){
                        endTasks.add(db.myRs.getInt(1));
                        names.add(db.myRs.getString(2));

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
                        new PieChart.Data(names.get(0), endTasks.get(0)),
                        new PieChart.Data(names.get(1), endTasks.get(1)),
                        new PieChart.Data(names.get(2), endTasks.get(2)),
                        new PieChart.Data(names.get(3), endTasks.get(3)),
                        new PieChart.Data(names.get(4), endTasks.get(4))
                );

                PieChart pieChart = new PieChart(valueList);
                pieChart.setTitle("Top 5 employees");
                pieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
                    Tooltip toolTip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), toolTip);
                });

                vBox.getChildren().setAll(pieChart);
                topEmployees.show();
            }
        });




        // ADD EMPLOYEE BUTTON
        addEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addEmployeeStage=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                addEmployeeStage.setHeight(400);
                addEmployeeStage.setWidth(300);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.setTitle("Add employee");

                Label name=new Label("Enter employee first name");
                TextField nameField=new TextField();

                Label lastName=new Label("Enter employee last name");
                TextField lastNameField=new TextField();

                Label email=new Label("Enter employee email");
                TextField emailField=new TextField();

                Label dateEmployment=new Label("Enter date employmnet");
                DatePicker datePicker=new DatePicker();

                Label salary=new Label("Enter employee salary");
                TextField salaryField=new TextField();

                Button createEmployee=new Button("Create employee");
                createEmployee.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(nameField.getText().equals("")  || lastNameField.getText().equals("") || email.getText().equals("")
                                || salaryField.getText().equals("")
                        ){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Enter all fields!");
                            alert.showAndWait();
                        }
                        else {
                            try {
                                String name=nameField.getText();
                                Date date= Date.valueOf(datePicker.getValue());
                                String lname=lastNameField.getText();
                                String email=emailField.getText();
                                double salary= Double.parseDouble(salaryField.getText());
                                DbConnection dbConnection=new DbConnection();
                                dbConnection.preparedStatement=dbConnection.connection.prepareStatement(
                                        " insert into company.employee (first_name,last_name,email,date_employment,salary,endTasks,id_task)"
                                        + " values (?,?,?,?,?,?,?)"
                                );
                                dbConnection.preparedStatement.setString(1,name);
                                dbConnection.preparedStatement.setString(2,lname);
                                dbConnection.preparedStatement.setString(3,email);
                                dbConnection.preparedStatement.setDate(4,date);
                                dbConnection.preparedStatement.setDouble(5,salary);
                                dbConnection.preparedStatement.setInt(6,0);
                                dbConnection.preparedStatement.setInt(7,0);

                                dbConnection.preparedStatement.executeUpdate();

                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Recorded successfully");
                                alert.showAndWait();

                                nameField.setText("");
                                lastNameField.setText("");
                                emailField.setText("");
                                salaryField.setText("");

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                vBox.setSpacing(10);
                vBox.setPadding(new Insets(10, 10, 10, 10));
                vBox.getChildren().addAll(name,nameField,lastName,lastNameField,email,emailField,dateEmployment,datePicker,salary,salaryField,createEmployee);
                addEmployeeStage.show();
            }
        });




        //EDIT EMPLOYEE BUTTON
        editEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage editEmployeeStage=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                editEmployeeStage.setHeight(400);
                editEmployeeStage.setWidth(700);
                editEmployeeStage.setScene(scene);
                editEmployeeStage.setTitle("Edit employee");

                Label firstName=new Label("Enter new first name");
                TextField firstNametf=new TextField();
                firstNametf.setMaxWidth(300);
                firstNametf.setLayoutY(50);

                Label lastName=new Label("Enter new last name");
                TextField lastNametf=new TextField();
                lastNametf.setMaxWidth(300);


                Label enterEmail=new Label("Enter old employee email");
                TextField email=new TextField();
                email.setMaxWidth(300);

                Label enterEmailNew=new Label("Enter new employee email");
                TextField emailNew=new TextField();
                emailNew.setMaxWidth(300);

                Label enterSalary=new Label("Enter new employee salary");
                TextField salary=new TextField();
                salary.setMaxWidth(300);

                Button btnSave=new Button("Save information");
                btnSave.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        if(firstNametf.getText().equals("")  || lastNametf.getText().equals("") || email.getText().equals("")
                                || salary.getText().equals("") || emailNew.getText().equals("")
                        ){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Enter all fields!");
                            alert.showAndWait();
                        }
                        else {
                            try {
                                int id=0;
                                String name=firstNametf.getText();
                                String lname=lastNametf.getText();
                                String emailNewS=emailNew.getText();
                                String emailOld=email.getText();
                                double salaryD= Double.parseDouble(salary.getText());
                                DbConnection dbConnection=new DbConnection();
                                dbConnection.myRs=dbConnection.myStmt.executeQuery("select id_employee from company.employee where email='"+emailOld+"'");
                                while (dbConnection.myRs.next()){
                                    id=dbConnection.myRs.getInt(1);
                                }


                                dbConnection.preparedStatement=dbConnection.connection.prepareStatement(
                                        " update company.employee set first_name=?,last_name=?, email=?, salary=? where id_employee=?"
                                );
                                dbConnection.preparedStatement.setString(1,name);
                                dbConnection.preparedStatement.setString(2,lname);
                                dbConnection.preparedStatement.setString(3,emailNewS);
                                dbConnection.preparedStatement.setDouble(4,salaryD);
                                dbConnection.preparedStatement.setDouble(5,id);

                                dbConnection.preparedStatement.executeUpdate();

                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Recorded successfully");
                                alert.showAndWait();

                                firstNametf.setText("");
                                lastNametf.setText("");
                                email.setText("");
                                salary.setText("");

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                vBox.getChildren().addAll(enterEmail,email,enterEmailNew,emailNew,enterSalary,salary,firstName,firstNametf,lastName,lastNametf,btnSave);

                editEmployeeStage.show();
            }
        });




        //ADD TASK BUTTON
        addTaskToEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addTaskToEmployeeStage=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                addTaskToEmployeeStage.setHeight(400);
                addTaskToEmployeeStage.setWidth(700);
                addTaskToEmployeeStage.setScene(scene);
                addTaskToEmployeeStage.setTitle("Edit employee");
                addTaskToEmployeeStage.show();

                Label enterEmail=new Label("Enter email of employee");
                TextField emailField=new TextField();
                emailField.setMaxWidth(300);
                Label choiceTask=new Label("Choice new employee task");
                ChoiceBox choiceBox=new ChoiceBox<>();


                try {
                    choiceBox.setItems(loadTaskTitle());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Button addTask=new Button("Add task to employee");
                addTask.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            int id_employee=0;
                            int id_task = 0;
                            String title= ((Task) choiceBox.getValue()).title;
                            String description="";
                            java.util.Date utilDate = new java.util.Date();
                            java.sql.Date sqlDate=null;

                            DbConnection db=new DbConnection();

                            db.myRs=db.myStmt.executeQuery("select distinct deadline from company.task where title='"+title+"'");
                            while(db.myRs.next()){
                                System.out.println(db.myRs.getDate(1));
                                utilDate=db.myRs.getDate(1);
                               sqlDate = new java.sql.Date(utilDate.getTime());
                            }


                            db.myRs=db.myStmt.executeQuery("select id_employee from company.employee where email='"+emailField.getText()+"'");
                            while(db.myRs.next()){
                                id_employee=db.myRs.getInt(1);
                            }
                            db.myRs=db.myStmt.executeQuery("select id_task from company.task where title='"+title+"'");
                            while(db.myRs.next()){
                                id_task=db.myRs.getInt(1);
                            }
                            db.myRs=db.myStmt.executeQuery("select description from company.task where title='"+title+"'");
                            while(db.myRs.next()){
                                description=db.myRs.getString(1);
                            }


                            db.preparedStatement=db.connection.prepareStatement(
                                    " insert into company.task (id_task,id_employee,title,description,deadline)"
                                            + " values (?,?,?,?,?)"
                            );


                            db.preparedStatement.setInt(1,id_task);
                            db.preparedStatement.setInt(2,id_employee);
                            db.preparedStatement.setString(3,title);
                            db.preparedStatement.setString(4,description);
                            db.preparedStatement.setDate(5,sqlDate);

                            db.preparedStatement.executeUpdate();

                            DbConnection db2=new DbConnection();


                            db2.preparedStatement=db2.connection.prepareStatement(
                                    " update company.employee set id_task=? where id_employee=?"

                            );
                            db2.preparedStatement.setInt(1,id_task);
                            db2.preparedStatement.setInt(2,id_employee);

                            db2.preparedStatement.executeUpdate();

                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Recorded successfully");
                            alert.showAndWait();


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                vBox.getChildren().addAll(enterEmail,emailField,choiceTask,choiceBox,addTask);
            }
        });




        //DELETE EMPLOYEE
        deleteEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage deleteEmployeeStage=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                deleteEmployeeStage.setHeight(400);
                deleteEmployeeStage.setWidth(700);
                deleteEmployeeStage.setScene(scene);
                deleteEmployeeStage.setTitle("Delete employee");
                deleteEmployeeStage.show();

                Label deleteEmployee=new Label("Enter email address of employee to delete");
                TextField deleteEmployeeTf=new TextField();
                deleteEmployeeTf.setMaxWidth(300);
                Button delete=new Button("Delete employe");

                delete.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(deleteEmployeeTf.getText().equals("")){
                            Alert alert=new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Enter email!");
                            alert.showAndWait();
                        }
                        else{
                            try {
                                DbConnection db=new DbConnection();
                                db.myStmt.executeUpdate("delete from company.employee where email='"+deleteEmployeeTf.getText()+"'");


                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Employee deleted");
                                alert.showAndWait();
                                deleteEmployeeTf.setText("");

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
                vBox.getChildren().setAll(deleteEmployee,deleteEmployeeTf,delete);

            }
        });

        table.setItems(loadData());
        TableColumn<Employee, Integer> colId = new TableColumn("ID");
        colId.setMinWidth(50);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> colFirstName = new TableColumn("First name");
        colFirstName.setMinWidth(150);
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        TableColumn<Employee, String> lastName = new TableColumn("Last name");
        lastName.setMinWidth(100);
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("last_name"));

        TableColumn<Employee, String> email = new TableColumn("Email");
        email.setMinWidth(100);
        email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));

        TableColumn<Employee, String> dataEmployment = new TableColumn("Date emloyment");
        dataEmployment.setMinWidth(100);
        dataEmployment.setCellValueFactory(new PropertyValueFactory<Employee, String>("date_employment"));

        TableColumn<Employee, Double> salary = new TableColumn("Salary");
        salary.setMinWidth(100);
        salary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));

        TableColumn<Employee, Integer> endTasks = new TableColumn("End tasks");
        endTasks.setMinWidth(100);
        endTasks.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("end_tasks"));


        //CREATE TASK BUTTON
        Button createTask = new Button("Create a task");
        createTask.setMaxWidth(750);
        createTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage createTask=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                createTask.setHeight(400);
                createTask.setWidth(700);
                createTask.setScene(scene);
                createTask.setTitle("Add task");
                createTask.show();

                Label email=new Label("Enter employee email");
                TextField emailField=new TextField();
                Label title=new Label("Enter task title");
                TextField titleField=new TextField();
                Label description=new Label("Enter task description");
                TextField descField=new TextField();
                Label deadline=new Label("Enter task deadline");
                DatePicker dp=new DatePicker();
                Button add=new Button("Add new task");

                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(emailField.getText().equals("") || titleField.getText().equals("") || descField.getText().equals("")){
                            Alert alert=new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Enter all fields!");
                            alert.showAndWait();
                        }
                        else{
                            try {
                                int id=0;
                                Date date= Date.valueOf(dp.getValue());
                                DbConnection db=new DbConnection();
                                db.myRs=db.myStmt.executeQuery("select id_employee from company.employee where email='"+emailField.getText()+"'");
                                while(db.myRs.next()){
                                    id=db.myRs.getInt(1);
                                }
                                db.preparedStatement=db.connection.prepareStatement(
                                        " insert into company.task (id_employee,title,description,deadline) values (?,?,?,?)"

                                );
                                db.preparedStatement.setInt(1,id);
                                db.preparedStatement.setString(2,titleField.getText());
                                db.preparedStatement.setString(3,descField.getText());
                                db.preparedStatement.setDate(4,date);


                                db.preparedStatement.executeUpdate();

                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Recorded successfully");
                                alert.showAndWait();


                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

                vBox.getChildren().setAll(email,emailField,title,titleField,description,descField,dp,add);


            }
        });




        //SEE ALL TASKS
        Button seeAllTasks=new Button("See all tasks");
        seeAllTasks.setMaxWidth(750);
        seeAllTasks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage createTask=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                createTask.setHeight(400);
                createTask.setWidth(700);
                createTask.setScene(scene);
                createTask.setTitle("All tasks");

                TableView<TasksTitles> table2 = new TableView<TasksTitles>();
                try {
                    table2.setItems(loadTaskTitles());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                TableColumn<TasksTitles, String> colFirstName2 = new TableColumn("Email");
                colFirstName2.setMinWidth(150);
                colFirstName2.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<TasksTitles, String> email2 = new TableColumn("First name");
                email2.setMinWidth(150);
                email2.setCellValueFactory(new PropertyValueFactory<>("first_name"));

                TableColumn<TasksTitles, String> title = new TableColumn("Title");
                title.setMinWidth(150);
                title.setCellValueFactory(new PropertyValueFactory<>("title"));

                TableColumn<TasksTitles, Date> deadline = new TableColumn("deadline");
                deadline.setMinWidth(150);
                deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

                try {
                    table.setItems(loadData());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                table2.getColumns().addAll(colFirstName2,email2,title,deadline);
                vBox.getChildren().setAll(table2);

                createTask.show();

            }
        });




        ///DELETE TASK BUTTON
        Button deleteTask=new Button("Delete task");
        deleteTask.setMaxWidth(750);
        deleteTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage deleteTask=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                deleteTask.setHeight(400);
                deleteTask.setWidth(700);
                deleteTask.setScene(scene);
                deleteTask.setTitle("Delete task");
                Label enterTask=new Label("Enter task to delete");
                TextField taskDelete=new TextField();
                Button delete=new Button("Delete task");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(taskDelete.getText().equals("")){
                            Alert alert=new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Enter task name");
                            alert.showAndWait();
                        }
                        else{
                            DbConnection db= null;
                            try {
                                db = new DbConnection();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                db.myStmt.executeUpdate("delete from company.task where title='"+taskDelete.getText()+"'");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }


                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Task deleted");
                            alert.showAndWait();
                            taskDelete.setText("");
                        }
                    }
                });


                vBox.getChildren().setAll(enterTask,taskDelete,delete);

                deleteTask.show();
            }
        });



        ///EDIT TASK BUTTON
        Button editTask=new Button("Edit task");
        editTask.setMaxWidth(750);
        editTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage editTask=new Stage();
                VBox vBox=new VBox();
                Scene scene=new Scene(vBox);
                editTask.setHeight(400);
                editTask.setWidth(700);
                editTask.setScene(scene);
                editTask.setTitle("Edit task");

                Label edit=new Label("Enter task to edit");
                TextField editTf=new TextField();
                Label editDesc=new Label("Enter new description");
                TextField editDescTf=new TextField();
                Label deadline=new Label("Enter new deadline");
                DatePicker dp=new DatePicker();
                Button save=new Button("Save changes");
                save.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(editTf.getText().equals("") || editDescTf.getText().equals("")){
                            Alert alert=new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Enter task name");
                            alert.showAndWait();
                        }
                        else{
                            DbConnection db= null;
                            try {
                                db = new DbConnection();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                db.myStmt.executeUpdate("update company.task set description='"+editDescTf.getText()+"',deadline='"+dp.getValue()+"' where title='"+editTf.getText()+"'");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }


                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Task changed");
                            alert.showAndWait();
                            editTf.setText("");
                        }
                    }
                });
                vBox.getChildren().setAll(edit,editTf,editDesc,editDescTf,deadline,dp,save);
                editTask.show();

            }
        });

        table.getColumns().addAll(colId,colFirstName,lastName,email,dataEmployment,salary,endTasks);
        VBox paneMain = new VBox();
        paneMain.setSpacing(10);
        paneMain.setPadding(new Insets(10, 10, 10, 10));
        paneMain.getChildren().addAll(lblHeading, table,addEmployee,editEmployee,deleteEmployee,addTaskToEmployee,createTask,seeAllTasks,deleteTask,editTask,topEmployees);
        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Employees");
        primaryStage.show();
    }
    public ObservableList<Employee> loadData() throws SQLException {

        ObservableList<Employee> data = FXCollections.observableArrayList();

        DbConnection db=new DbConnection();
        db.myRs=db.myStmt.executeQuery("select * from company.employee");
        System.out.println("uspesno");
        while(db.myRs.next()){

            data.add(new Employee(db.myRs.getInt(1),db.myRs.getString(2),db.myRs.getString(3),db.myRs.getString(4),db.myRs.getString(5),db.myRs.getDouble(6),db.myRs.getInt(7),db.myRs.getInt(8)));
        }
        return data;
    }
    public ObservableList<Task> loadTaskTitle() throws SQLException {

        ObservableList<Task> data = FXCollections.observableArrayList();
        DbConnection db=new DbConnection();
        db.myRs=db.myStmt.executeQuery("select distinct title from company.task");
        System.out.println("uspesno");
        while(db.myRs.next()){

            data.add(new Task(db.myRs.getString(1)));
            System.out.println(db.myRs.getString(1));
        }
        return data;
    }

    public ObservableList<TasksTitles> loadTaskTitles() throws SQLException {

        ObservableList<TasksTitles> data = FXCollections.observableArrayList();

        DbConnection db=new DbConnection();
        db.myRs=db.myStmt.executeQuery("SELECT distinct first_name,email,title,deadline from company.employee inner join company.task on employee.id_task=task.id_task");
        System.out.println("uspesno");
        while(db.myRs.next()){

            data.add(new TasksTitles(db.myRs.getString(1),db.myRs.getString(2),db.myRs.getString(3),db.myRs.getDate(4)));
        }
        return data;
    }

}
