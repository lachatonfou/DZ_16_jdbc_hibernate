package jdbc.utils;

import jdbc.models.Animal;
import lombok.SneakyThrows;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String insertAnimal = "INSERT INTO public.animal (id, \"name\", age, \"type\", sex, place) VALUES(?, ?, ?, ?, ?, ?);\n";
    private static final String deleteAnimal = "DELETE FROM public.animal WHERE id = ?";
    private static final String getCountAnimalRow = "SELECT count(*) from animal";

    public static List<Animal> getAnimalData(String query) {
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int type = resultSet.getInt("type");
                int sex = resultSet.getInt("sex");
                int place = resultSet.getInt("place");

                animals.add(new Animal(id, name, age, type, sex, place));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return animals;
    }

    @SneakyThrows
    public static void insertAnimalData(Animal animal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertAnimal);
        preparedStatement.setInt(1, animal.getId());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setInt(3, animal.getAge());
        preparedStatement.setInt(4, animal.getType());
        preparedStatement.setInt(5, animal.getSex());
        preparedStatement.setInt(6, animal.getPlace());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void deleteAnimalById(int id) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteAnimal);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static int getAnimalCountRow() {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getCountAnimalRow);
        resultSet.next();
        return resultSet.getInt("count(*)");
    }
}
