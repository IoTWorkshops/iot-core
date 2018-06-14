package gr.iot.iot.components.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created on 11/11/16.
 * Implementation of PagingAndSortingRepository using JdbcTemplate
 */

@Component
public class JdbcTemplateRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplateRepository.class);

    private static final String _countPrefix = "SELECT COUNT(*) FROM (";
    private static final String _countSuffix = ") AS T";

    private int pageSize = 20;

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlQueryService sqlQueryService;

    @PostConstruct
    private void init() {
        this.pageSize = env.getProperty("spring.pageable.size", Integer.class);
    }


    public long count(String queryKey, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        query = _countPrefix + query + _countSuffix;
        return jdbcTemplate.queryForObject(query, Long.class, args);
    }


    public <T> T findOne(String queryKey, Class<T> returnType, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.queryForObject(query, returnType, args);
    }

    public <T> List<T> findAll(String queryKey, Class<T> returnType, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(returnType), args);
    }

    public <T> List<T> findAll(String queryKey, Class<T> returnType) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(returnType));
    }

    public <T> Page<T> findAll(Pageable pageable, String queryKey, Class<T> returnType, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);

        if (pageable.getSort() != null) {
            for (Sort.Order o : pageable.getSort()) {
                query += " ORDER BY " + o.getProperty() + " " + o.getDirection().toString() + " ";
            }
        }

        query += " OFFSET " + pageable.getPageNumber() * this.pageSize + " LIMIT " + this.pageSize + " ";

        long count = count(queryKey, args);

        return new JdbcPage<>(pageable, (int) Math.ceil((double) count / this.pageSize), (int) count,
            jdbcTemplate.queryForList(query, returnType, args)
        );
    }

    public Page<Map<String, Object>> findAll(Pageable pageable, String queryKey, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);

        if (pageable.getSort() != null) {
            for (Sort.Order o : pageable.getSort()) {
                query += " ORDER BY " + o.getProperty() + " " + o.getDirection().toString() + " ";
            }
        }

        query += " OFFSET " + pageable.getPageNumber() * this.pageSize + " LIMIT " + this.pageSize + " ";

        long count = count(queryKey, args);

        return new JdbcPage<>(pageable, (int) Math.ceil((double) count / this.pageSize), (int) count,
            jdbcTemplate.queryForList(query, args)
        );
    }

    public <T> List<T> query(String queryKey, RowMapper<T> rowMapper, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.query(query, rowMapper, args);
    }

    public <T> List<T> query(String queryKey, Class<T> returnType, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(returnType), args);
    }

    public List<Map<String, Object>> query(String queryKey, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.queryForList(query, args);
    }

    public void execute(String queryKey) {
        String query = this.sqlQueryService.getQuery(queryKey);
        jdbcTemplate.execute(query);
    }

    public int update(String queryKey) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.update(query);
    }

    public int update(String queryKey, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.update(query, args);
    }

    public Map<String, Object> queryForMap(String queryKey, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        return jdbcTemplate.queryForMap(query, args);
    }


    public <T> T queryForObject(String queryKey, RowMapper<T> rowMapper, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);
        try {
            return jdbcTemplate.queryForObject(query, rowMapper, args);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public <T> T queryForObject(String queryKey, Class<T> returnType, Object... args) {
        String query = this.sqlQueryService.getQuery(queryKey);

        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(returnType), args);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
