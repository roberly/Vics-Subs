package Main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Employee.
 **/

public class Employee
{
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;

    /**
     * Default constructor.
     */
    public Employee()
    {
        this(null, null, "0");
    }

    public Employee(String firstName, String lastName, String id)
    {
        this.id = new SimpleIntegerProperty(Integer.valueOf(id));
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public IntegerProperty IdProperty()
    {
        return id;
    }

    public int getId()
    {
        return id.get();
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String firstName)
    {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty()
    {
        return lastName;
    }

}
