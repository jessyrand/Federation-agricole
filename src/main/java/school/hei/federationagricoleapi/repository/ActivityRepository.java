package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {
    private final Connection connection;

    public boolean activityExists(String activityId) {
        String sql = "select 1 from activity where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           preparedStatement.setString(1, activityId);
           try(ResultSet resultSet = preparedStatement.executeQuery()){
               return resultSet.next();
           }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
