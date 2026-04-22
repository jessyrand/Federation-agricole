package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.Gender;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

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
            pstmt.setString(1, id);
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

                    String created_at = rs.getString("created_at");
                    member.setCreatedAt(created_at == null ? Instant.now() : Instant.parse(created_at));

                    return Optional.of(member);
                }
                return Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
