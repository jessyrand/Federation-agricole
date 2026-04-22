package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.Collectivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CollectivityRepository {
    private Connection connection;

    public void save (Collectivity collectivity) {
        String sql = """
                   insert into collectivities (location, president_id, vice_president_id, treasurer_id, secretary_id)  
                   values (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivity.getLocation());
            pstmt.setString(2, collectivity.getStructure().getPresident().getId());
            pstmt.setString(3, collectivity.getStructure().getVicePresident().getId());
            pstmt.setString(4, collectivity.getStructure().getTreasurer().getId());
            pstmt.setString(5, collectivity.getStructure().getSecretary().getId());
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
