package loggers;

import beans.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by arpi on 16.11.2016.
 * Logs Events to database
 *
 * NOT FINISHED!!!!!!!!!
 */
public class DBLogger implements IEventLogger{
    JdbcTemplate jdbcTemplate;

    public DBLogger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logEvent(Event event) {
        jdbcTemplate.update("INSERT INTO t_event (id, msg) VALUES (?,?)",
                event.getId(), event.toString());
        /*int count = jdbcTemplate.queryForObject("SELECT cont(*) FROM t_event", Integer.class);
        String msg = jdbcTemplate.queryForObject("SELECT msg FROM t_event where id=?", new Object[]{1},String.class);*/
    }

    public void readObjectFromDB() {
        final Event event = jdbcTemplate.queryForObject("SELECT msg FROM t_event where id=?", new Object[]{1},
                new RowMapper<Event>() {
                    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Integer id = rs.getInt("id");
                        Date date = rs.getDate("date");
                        String msg = rs.getString("msg");

                        Event event = new Event(date, DateFormat.getDateInstance());
                        event.setMsg(msg);
                        return event;
                    }
                });
    }

    /*public void readMultiplyObjectFromDB() {
        List<Event> events = jdbcTemplate.query("SELECT * FROM t_event",
                new RowMapper<Event>(){
                    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Integer id = rs.getInt("id");
                        Date date = rs.getDate("date");
                        String msg = rs.getString("msg");

                        Event event = new Event(date, DateFormat.getDateInstance());
                        event.setMsg(msg);
                        return event;
                    }
                });
    }*/
}
