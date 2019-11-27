package View;

import Database.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewRequestsOffController {

    public void initialize() throws SQLException {
        fillTable();
        fillOutSelectedData();
    }

    @FXML
    Button doneButton;

    @FXML
    Button approveButton;

    @FXML
    Button denyButton;

    @FXML
    TableView requestsOffTableView;

    @FXML
    TextField employeeTextField;

    @FXML
    TextField startDateTextField;

    @FXML
    TextField endDateTextField;

    @FXML
    public void handleCloseButtonAction()
    {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    DBConnection dbConnection = new DBConnection();

    public void fillTable() throws SQLException {
        Connection connection = dbConnection.getConnection();
        ObservableList<ObservableList> requestOffInfo = FXCollections.observableArrayList();
        String sqlData = "SELECT Employee_ID, StartDate, EndDate from RequestedTimeOff";
        System.out.println(sqlData);
        ResultSet resultSet = connection.createStatement().executeQuery(sqlData);
        System.out.println(resultSet);

        for (int i = 0; i<resultSet.getMetaData().getColumnCount(); i++){
            final int j = i;
            TableColumn column = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
            column.setCellValueFactory
                    (new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            requestsOffTableView.getColumns().addAll(column);
        }

        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                row.add(resultSet.getString(i));
            }
            requestOffInfo.add(row);
        }
        requestsOffTableView.setItems(requestOffInfo);
    }

    public void fillOutSelectedData(){
        requestsOffTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            Object selectedItems = requestsOffTableView.getSelectionModel().getSelectedItems().get(0);
            String employeeId = selectedItems.toString().split(",")[0].substring(1);
            employeeTextField.setText(employeeId);
            String startDate = selectedItems.toString().split(",")[1].substring(1);
            startDateTextField.setText(startDate);
            String endDate = selectedItems.toString().split(",")[2].substring(1);
            endDateTextField.setText(endDate.substring(0,endDate.length()-1));
        });
    }

    public void removeRowFromTable(){
        Object selectedItems = requestsOffTableView.getSelectionModel().getSelectedItems().get(0);
        requestsOffTableView.getItems().remove(selectedItems);
    }

    @FXML
    public void deny() throws SQLException {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String deleteRow = "DELETE FROM RequestedTimeOff WHERE Employee_ID = '" + employeeTextField.getText()
                + "' AND StartDate = '" + startDateTextField.getText() + "' AND EndDate = '"
                + endDateTextField.getText() + "';";
        int resultSet = statement.executeUpdate(deleteRow);
        removeRowFromTable();
    }

    @FXML
    public void approve() throws SQLException {
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        String updateeRow = "UPDATE RequestedTimeOff SET Approval = '1' WHERE Employee_ID = '" + employeeTextField.getText()
                + "' AND StartDate = '" + startDateTextField.getText() + "' AND EndDate = '" + endDateTextField.getText() + "';";
        int resultSet = statement.executeUpdate(updateeRow);

    }

}
