package com.sks.sksschool.repository;

import com.sks.sksschool.model.Holiday;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidayRepository extends ListCrudRepository<Holiday,String> {

    /*This below code is not required now as we have started using Spring data JPA and hence changing
     * above class to interface and extending diff interface which will provide CRUD operation. So
     * no need to write SQL for now*/

    /*@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Holiday> getHolidaysByType(){

        String sql="SELECT * FROM HOLIDAY";
        *//*List<Holiday> holidayList=jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,type);
            }
        },new HolidayRowMapper());*//*

        var rowMapper=BeanPropertyRowMapper.newInstance(Holiday.class);

        List<Holiday> holidayList = jdbcTemplate.query(sql, rowMapper);
        return holidayList;
    }*/
}
