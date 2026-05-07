package school.hei.federationagricoleapi.mapper;

import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.CollectivityActivity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CollectivityActivityMapper {

    public CollectivityActivity map(ResultSet rs) throws SQLException {

        CollectivityActivity a = new CollectivityActivity();

        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setLabel(rs.getString("label"));
        a.setActivityType(rs.getString("activity_type"));

        Date d = rs.getDate("executive_date");
        if (d != null) a.setExecutiveDate(d.toLocalDate());

        a.setWeekOrdinal((Integer) rs.getObject("week_ordinal"));
        a.setDayOfWeek(rs.getString("day_of_week"));

        return a;
    }
}