package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.CollectivityTransaction;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.ENUM.Type_enum;
import school.hei.federationagricoleapi.entity.Member;

import java.sql.*;
import java.time.Instant;
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
                from collectivities
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
                select id, number, name, location, president_id, vice_president_id, treasurer_id, secretary_id
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
                   returning id, number, name, location, president_id, vice_president_id, treasurer_id, secretary_id
                """;
        List<Collectivity> collectivitiesList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (CreateCollectivityDTO collectivity : collectivities) {
                pstmt.setString(1, collectivity.getLocation());
                pstmt.setString(2, collectivity.getStructure().getPresident());
                pstmt.setString(3, collectivity.getStructure().getVicePresident());
                pstmt.setString(4, collectivity.getStructure().getTreasurer());
                pstmt.setString(5, collectivity.getStructure().getSecretary());

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

    public List<Member> addMemberToCollectivity(CreateCollectivityDTO collectivity, String idCollectivity) {
        String sql = """
                insert into member_collectivity (member_id, collectivity_id)
                VALUES (?, ?)
                """;

        List<Member> members = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (String id : collectivity.getMembers()) {
                    pstmt.setString(1, id);
                    pstmt.setString(2, idCollectivity);

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
        int number = rs.getInt("number");
        collectivity.setNumber(rs.wasNull() ? null : number);
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
            pstmt.setString( 1, collectivityId);
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

    public Collectivity updateIdentification(String id, Integer number, String name) {
        String sql = """
        update collectivities
        set number = ?, name = ?
        where id = ?
        returning id, number, name, location, president_id, vice_president_id, treasurer_id, secretary_id
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, number);
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

    @Repository
    @AllArgsConstructor
    public static class TransactionRepository {
        private AccountRepository accountRepository;
        private Connection connection;
       private MemberRepository memberRepository;

       public List<CollectivityTransaction> findByCollectivityIdAndDateBetween (
               String collectivityId, Instant from, Instant to) {
           String sql = """
                   select ct.id, ct.collectivity_id, ct.member_id, ct.type,
                       ct.amount, ct.payment_mode, ct.account_credited_id, ct.creation_date
                   from collectivity_transaction ct
                   where ct.collectivity_id = ?
                       and ct.creation_date >= ?
                       and ct.creation_date <= ?
                   order by ct.creation_date desc
                   """;
           List<CollectivityTransaction> transactions = new ArrayList<>();

           try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, collectivityId);
                pstmt.setTimestamp(2, Timestamp.from(from));
                pstmt.setTimestamp(3, Timestamp.from(to));

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        transactions.add(mapRow(rs));
                    }
                }
                return transactions;
           }
           catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }

       private CollectivityTransaction mapRow(ResultSet rs) throws SQLException {
           CollectivityTransaction ct = new CollectivityTransaction();

           ct.setId(rs.getString("id"));
           ct.setCollectivityId(rs.getString("collectivity_id"));
           ct.setType(Type_enum.valueOf(rs.getString("type")));
           ct.setPaymentMode(AccountType.valueOf(rs.getString("payment_mode")));

           Timestamp creationDate = rs.getTimestamp("creation_date");
           ct.setCreationDate(creationDate != null ? creationDate.toInstant() : Instant.now());

           String memberId = rs.getString("member_id");
           ct.setMemberDebited(memberRepository.findById(memberId).orElse(null));

           String accountCreditedId = rs.getString("account_credited_id");
           if (accountCreditedId != null) {
               ct.setAcountCredited(accountRepository.findAccountById(accountCreditedId));
           }

           return ct;
       }
    }
}
