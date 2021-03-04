package sample;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements Initializable {
    private Label label;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfPages;
    @FXML
    private TableView<Books> tvBooks;
    @FXML
    private TableColumn<Books, Integer> colId;
    @FXML
    private TableColumn<Books, String> colTitle;
    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private TableColumn<Books, Integer> colYear;
    @FXML
    private TableColumn<Books, Integer> colPages;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private void handleButtonAction (ActionEvent event){
        System.out.println("You clicked me!");
        label.setText("Hello World");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //TODO

    }
    public Connection getConnection(){
            Connection conn;
            try{
                conn = DriverManager.getConnection("jdbc:mysql://localhost:127.0.0.1/library","root","");
                return conn;
            }catch (Exception ex){
                System.out.println("Error: "+ex.getMessage());
                return null;
            }
    }
    public ObservableList<Books> getBookslist(){
        ObservableList<Books> booklist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM books";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Books books;
            while (rs.next()){
                books = new Books(rs.getInt("id"),rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));
                booklist.add(books);


            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return booklist;
    }

    public void showBooks(){
        ObservableList<Books> list = getBookslist();

        colId.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));
        tvBooks.setItems(list);
    }
}
