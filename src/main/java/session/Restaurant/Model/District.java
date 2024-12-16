package session.Restaurant.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class District implements RowMapper<District> {
    private String id;
    private String name;

    public District(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public District() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public District mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new District(rs.getString("district_id"), rs.getString("district_name"));
    }


}