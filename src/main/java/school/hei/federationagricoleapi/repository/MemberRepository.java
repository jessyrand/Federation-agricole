package school.hei.federationagricoleapi.repository;

import org.springframework.stereotype.Repository;

import school.hei.federationagricoleapi.datasource.DataSource;
import school.hei.federationagricoleapi.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UUID insertMember(CreateMember m) {

        String sql = """
            INSERT INTO members (
                collectivity_id,
                first_name, last_name, birth_date, gender,
                address, profession, phone_number, email,
                occupation,
                registration_fee_paid,
                membership_dues_paid,
                created_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())
            RETURNING id
        """;

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, UUID.fromString(m.getCollectivityIdentifier()));
            ps.setString(2, m.getFirstName());
            ps.setString(3, m.getLastName());
            ps.setDate(4, m.getBirthDate() != null ? Date.valueOf(m.getBirthDate()) : null);
            ps.setString(5, m.getGender() != null ? m.getGender().name() : null);
            ps.setString(6, m.getAddress());
            ps.setString(7, m.getProfession());
            ps.setString(8, m.getPhoneNumber());
            ps.setString(9, m.getEmail());
            ps.setString(10, m.getOccupation() != null ? m.getOccupation().name() : null);
            ps.setBoolean(11, m.isRegistrationFeePaid());
            ps.setBoolean(12, m.isMembershipDuesPaid());

            ResultSet rs = ps.executeQuery();
            rs.next();

            return (UUID) rs.getObject("id");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertReferee(UUID memberId, UUID refereeId) {

        String sql = "INSERT INTO member_referees (member_id, referee_id) VALUES (?, ?)";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, memberId);
            ps.setObject(2, refereeId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsMember(UUID id) {

        String sql = "SELECT 1 FROM members WHERE id = ?";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Member findFullMember(UUID id, Collectivity collectivity) {

        String sql = """
            SELECT 
                first_name,
                last_name,
                birth_date,
                gender,
                address,
                profession,
                phone_number,
                email,
                occupation
            FROM members
            WHERE id = ?
        """;

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            Member member = map(rs);

            member.setCollectivity(collectivity);
            member.setReferees(findReferees(c, id));

            return member;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Member> findReferees(Connection c, UUID id) throws SQLException {

        String sql = """
            SELECT 
                m.first_name,
                m.last_name,
                m.birth_date,
                m.gender,
                m.address,
                m.profession,
                m.phone_number,
                m.email,
                m.occupation
            FROM members m
            JOIN member_referees r ON m.id = r.referee_id
            WHERE r.member_id = ?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();

            List<Member> list = new ArrayList<>();

            while (rs.next()) {
                list.add(map(rs));
            }

            return list;
        }
    }

    private Member map(ResultSet rs) throws SQLException {

        Member m = new Member();

        m.setFirstName(rs.getString("first_name"));
        m.setLastName(rs.getString("last_name"));

        if (rs.getDate("birth_date") != null) {
            m.setBirthDate(rs.getDate("birth_date").toLocalDate());
        }

        if (rs.getString("gender") != null) {
            m.setGender(Gender.valueOf(rs.getString("gender")));
        }

        m.setAddress(rs.getString("address"));
        m.setProfession(rs.getString("profession"));
        m.setPhoneNumber(rs.getString("phone_number"));
        m.setEmail(rs.getString("email"));

        if (rs.getString("occupation") != null) {
            m.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));
        }

        return m;
    }
}