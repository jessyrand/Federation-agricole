package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CollectivityRepository {
    private Connection connection;
    private MemberRepository memberRepository;

    public List<Collectivity> getAllCollectivities() {
        String sql = """
                select id, number, name, location, president_id, vice_president_id, treasurer_id, secretary_id
                """;
        List<Collectivity> collectivities = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Collectivity collectivity = saveCollectivityInfo(rs);
                collectivities.add(collectivity);
            }
            return collectivities;
        }
        catch (SQLException e) {
           throw  new RuntimeException(e);
        }
    }

    public Optional<Collectivity> findById(String id) {
        String sql = """
                select id, location, president_id, vice_president_id, treasurer_id, secretary_id
                from collectivities
                where id = ?
                """;
        
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(saveCollectivityInfo(rs));
                }
            }
            return Optional.empty();
        }
        catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public List<Collectivity> save (List<CreateCollectivityDTO> collectivities) {
        String sql = """
                   insert into collectivities (location, president_id, vice_president_id, treasurer_id, secretary_id)
                   values (?, ?, ?, ?, ?)
                   returning id, location, president_id, vice_president_id, treasurer_id, secretary_id
                """;
        List<Collectivity> collectivitiesList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (CreateCollectivityDTO collectivity : collectivities) {
                pstmt.setString(1, collectivity.getLocation());
                pstmt.setString(2, collectivity.getStructure().getPresident_id());
                pstmt.setString(3, collectivity.getStructure().getVicePresident_id());
                pstmt.setString(4, collectivity.getStructure().getTreasurer_id());
                pstmt.setString(5, collectivity.getStructure().getSecretary_id());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                       Collectivity coll = saveCollectivityInfo(rs);
                       coll.setMembers(addMemberToCollectivity(collectivity, coll.getId()));

                       collectivitiesList.add(coll);
                    }
                }
            }
            System.out.println(collectivitiesList);
            return collectivitiesList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> addMemberToCollectivity(CreateCollectivityDTO collectivity, String collectivity_id) {
        String sql = """
                insert into member_collectivity (member_id, collectivity_id)
                VALUES (?, ?)
                """;

        List<Member> members = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (String id : collectivity.getMember_id()) {
                    pstmt.setString(1, id);
                    pstmt.setString(2, collectivity_id);
                    pstmt.executeUpdate();
                    memberRepository.findById(id).ifPresent(members::add);
                }
           return members;
        }
        catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    private Collectivity saveCollectivityInfo(ResultSet rs) throws SQLException {
        Collectivity collectivity = new Collectivity();
        collectivity.setId(rs.getString("id"));
        collectivity.setNumber(rs.getString("number"));
        collectivity.setName(rs.getString("name"));
        collectivity.setLocation(rs.getString("location"));

        collectivity.setPresident(memberRepository.findById(rs.getString("president_id")).orElse(null));
        collectivity.setVicePresident(memberRepository.findById(rs.getString("vice_president_id")).orElse(null));
        collectivity.setTreasurer(memberRepository.findById(rs.getString("treasurer_id")).orElse(null));
        collectivity.setSecretary(memberRepository.findById(rs.getString("secretary_id")).orElse(null));
        collectivity.setMembers(getMembersOfCollectivity(rs.getString("id")));
        return collectivity;
    }

    private List<Member> getMembersOfCollectivity(String collectivityId) {
        String sql = """
            SELECT member_id FROM member_collectivity WHERE collectivity_id = ?
            """;
        List<Member> members = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivityId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    memberRepository.findById(rs.getString("member_id")).ifPresent(members::add);
                }
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByName(String name) {
        String sql = "select 1 from collectivities where name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity updateIdentification(String id, String number, String name) {
        String sql = """
        update collectivities
        set number = ?, name = ?
        where id = ?
        returning id, number, name, location, president_id, vice_president_id, treasurer_id, secretary_id
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return saveCollectivityInfo(rs);
                }
                else {
                    throw new RuntimeException("Update failed");
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
