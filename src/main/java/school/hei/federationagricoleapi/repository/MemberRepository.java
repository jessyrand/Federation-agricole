package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.ENUM.Gender;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.ENUM.MemberOccupation;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MemberRepository {
    private Connection connection;

    public Optional<Member> findById(String id) {
        String sql = """
                select id, first_name, last_name, birth_date,
                    gender, address, profession, phone_number, email, occupation, created_at
                from members
                where id = ?;
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, UUID.fromString(id));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getString("id"));
                    member.setFirstName(rs.getString("first_name"));
                    member.setLastName(rs.getString("last_name"));
                    member.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    member.setGender(Gender.valueOf(rs.getString("gender")));
                    member.setAddress(rs.getString("address"));
                    member.setProfession(rs.getString("profession"));
                    member.setPhoneNumber(rs.getString("phone_number"));
                    member.setEmail(rs.getString("email"));
                    member.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));


                    Timestamp timestamp = rs.getTimestamp("created_at");
                    member.setCreatedAt(timestamp == null ? Instant.now() : timestamp.toInstant());


                    return Optional.of(member);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsAnyMember() {
        String sql = """
        select id
        from members
        limit 1
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Member> saveAll(List<Member> members) {
        String sql = """
            insert into members (
                first_name, last_name, birth_date,
                gender, address, profession, phone_number,
                email, occupation, created_at
            )
            values (?, ?, ?, ?::gender_enum, ?, ?, ?, ?, ?::member_occupation_enum, ?)
            returning id
        """;

        List<Member> savedMembers = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (Member member : members) {

                pstmt.setString(1, member.getFirstName());
                pstmt.setString(2, member.getLastName());
                pstmt.setDate(3, Date.valueOf(member.getBirthDate()));
                pstmt.setString(4, member.getGender().name());
                pstmt.setString(5, member.getAddress());
                pstmt.setString(6, member.getProfession());
                pstmt.setString(7, member.getPhoneNumber());
                pstmt.setString(8, member.getEmail());
                pstmt.setString(9, member.getOccupation().name());
                pstmt.setTimestamp(10, Timestamp.from(Instant.now()));

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        member.setId(rs.getString("id"));

                        insertMemberCollectivity(member.getId(), member.getCollectivity().getId());
                        insertMemberReferees(member.getId(), member.getReferees());

                        savedMembers.add(member);
                    }
                }
            }

            return savedMembers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertMemberCollectivity(String memberId, String collectivityId) throws SQLException {
        String sql = """
            insert into member_collectivity (member_id, collectivity_id)
            values (?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, UUID.fromString(memberId));
            pstmt.setObject(2, UUID.fromString(collectivityId));
            pstmt.executeUpdate();
        }
    }

    private void insertMemberReferees(String memberId, List<Member> referees) throws SQLException {
        if (referees == null || referees.isEmpty()) return;

        String sql = """
            insert into member_referees (member_id, referee_id)
            values (?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Member referee : referees) {
                pstmt.setObject(1, UUID.fromString(memberId));
                pstmt.setObject(2, UUID.fromString(referee.getId()));
                pstmt.executeUpdate();
            }
        }
    }
}