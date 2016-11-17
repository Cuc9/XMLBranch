package loggers;

import beans.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by arpi on 16.11.2016.
 * Logs Events to database
 *
 * NOT FINISHED!!!!!!!!!
 */
public class DBLogger implements IEventLogger{
    JdbcTemplate jdbcTemplate;

    public void logEvent(Event event) {
        jdbcTemplate.update("INSERT INTO t_event (id, msg) VALUES (?,?)",
                event.getId(), event.toString());
        int count = jdbcTemplate.queryForObject("SELECT cont(*) FROM t_event", Integer.class);
        String msg = jdbcTemplate.queryForObject("SELECT msg FROM t_event where id=?", new Object[]{1},String.class);
    }

    /*public void readObjectFromDB() {
        final Event event = jdbcTemplate.queryForObject("SELECT msg FROM t_event where id=?", new Object[]{1},
                new RowMapper<Event>() {
                    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Integer id = rs.getDate("id");
                        Date date = rs.getDate(date);
                        String msg = rs.getString("msg");
return null;
                        *//*Event ev = new Event(id, date);
                        ev.setMsg(msg);
                        return event;*//*
                    }
                })
    }*/
}
