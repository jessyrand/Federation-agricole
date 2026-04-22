package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CollectivityRepository {
    private Connection connection;
    private MemberRepository memberRepository;

    public List<Collectivity> getAllCollectivities() {
        String sql = """
                select id, location, president_id, vice_president_id, treasurer_id, secretary_id
                from collectivities
                """;
        List<Collectivity> collectivities = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(rs.getString("id"));
                collectivity.setLocation(rs.getString("location"));

                collectivity.setPresident(memberRepository.findById(rs.getString("president_id")).orElse(null));
                collectivity.setVicePresident(memberRepository.findById(rs.getString("vice_president_id")).orElse(null));
                collectivity.setTreasurer(memberRepository.findById(rs.getString("treasurer_id")).orElse(null));
                collectivity.setSecretary(memberRepository.findById(rs.getString("secretary_id")).orElse(null));
                collectivities.add(collectivity);
            }
            return collectivities;
        }
        catch (SQLException e) {
           throw  new RuntimeException(e);
        }
    }

    public String save (CreateCollectivityDTO collectivity) {
        String sql = """
                   insert into collectivities (location, president_id, vice_president_id, treasurer_id, secretary_id)
                   values (?, ?, ?, ?, ?)
                   returning id
                """;
        String id;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivity.getLocation());
            pstmt.setString(2, collectivity.getPresident_id());
            pstmt.setString(3, collectivity.getVicePresident_id());
            pstmt.setString(4, collectivity.getTreasurer_id());
            pstmt.setString(5, collectivity.getSecretary_id());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getString("id");
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
