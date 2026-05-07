package school.hei.federationagricoleapi.mapper;

import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CollectivityActivityMapper {

    public CollectivityActivity map(ResultSet rs) throws SQLException {

        CollectivityActivity a = new CollectivityActivity();

        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setLabel(rs.getString("label"));
        a.setActivityType(rs.getString("activity_type"));

        Date d = rs.getDate("executive_date");
        if (d != null) {
            a.setExecutiveDate(d.toLocalDate());
        }

        Object weekOrdinalObj = rs.getObject("week_ordinal");
        if (weekOrdinalObj != null) {
            a.setWeekOrdinal(((Number) weekOrdinalObj).intValue());
        }

        a.setDayOfWeek(rs.getString("day_of_week"));

        Array sqlArray = rs.getArray("member_occupation_concerned");

        if (sqlArray != null) {
            String[] values = (String[]) sqlArray.getArray();

            List<MemberOccupation> occupations = new ArrayList<>();

            for (String v : values) {
                occupations.add(MemberOccupation.valueOf(v));
            }

            a.setMemberOccupationConcerned(occupations);
        }

        return a;
    }
}