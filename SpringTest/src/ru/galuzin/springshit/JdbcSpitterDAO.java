package ru.galuzin.springshit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by User on 16.01.2016.
 */
@Component
public class JdbcSpitterDAO implements SpitterDao {

    @Autowired
    private SimpleJdbcTemplate jdbcTemplate;
    private String string;

    private static final String SQL_INSERT_SPITTER =
            "insert into spitter ( id, username, password, fullname) values ( :id , :username , :password , :fullname)";

    private static final String SQL_UPDATE_SPITTER =
            "update spitter set username = ?, password = ?, fullname = ?"
                    + "where id = ?";

    private static final String SQL_SELECT_SPITTER =
            "select id, username, fullname from spitter where id = ?";


    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void addSpitter(Spitter spitter) {
        Long uuid = UUID.randomUUID().getLeastSignificantBits();
        Map<String,Object> map = new HashMap<>();
        map.put("id", uuid);
        map.put("username", spitter.getUsername());
        map.put("password", spitter.getPassword());
        map.put("fullname", spitter.getFullName());
        jdbcTemplate.update(SQL_INSERT_SPITTER,
                map
        );
        spitter.setId(uuid);

    }

    @Override
    public void saveSpitter(Spitter spitter) {
        jdbcTemplate.update(SQL_UPDATE_SPITTER,
                spitter.getUsername(),
                spitter.getPassword(),
                spitter.getFullName(),
                spitter.getId());

    }

    @Override
    public Spitter getSpitterById(long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_SPITTER,
                new ParameterizedRowMapper<Spitter>() {
                    @Override
                    public Spitter mapRow(ResultSet resultSet, int i) throws SQLException {
                        Spitter spitter = new Spitter();
                        spitter.setId(resultSet.getLong(1));
                        spitter.setUsername(resultSet.getString(2));
                        spitter.setPassword(resultSet.getString(3));
                        spitter.setFullName(resultSet.getString(4));
                        return spitter;
                    }
                }, id);
    }

    @Override
    public List<Spittle> getRecentSpittle() {
        return null;
    }

    @Override
    public void saveSpittle(Spittle spittle) {

    }

    @Override
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        return null;
    }

    @Override
    public Spitter getSpitterByUsername(String username) {
        return null;
    }

    @Override
    public void deleteSpittle(long id) {

    }

    @Override
    public Spittle getSpittleById(long id) {
        return null;
    }

    @Override
    public List<Spitter> findAllSpitters() {
        return null;
    }

}