package com.sks.sksschool.repository;

import com.sks.sksschool.model.Contact;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends ListCrudRepository<Contact,Integer> {

    List<Contact> findByStatus(String status);

    /*This below code is not required now as we have started using Spring data JPA and hence changing
    * above class to interface and extending diff interface which will provide CRUD operation. So
    * no need to write SQL for now*/

  /*  @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveContactIntoDb(Contact contact){

        String seqSql ="SELECT contact_id_seq.NEXTVAL FROM dual";

        Long seq = jdbcTemplate.queryForObject(seqSql, new Object[]{}, Long.class);

        String sql = "INSERT INTO CONTACT_MSG (CONTACT_ID,NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS" +
                ",CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?,?,?)";

        return jdbcTemplate.update(sql,seq,contact.getName(),contact.getMobileNum(),contact.getEmail(),
                contact.getSubject(),contact.getMessage(),contact.getStatus(),contact.getCreatedAt(),
                contact.getCreatedBy());

    }

    public List<Contact> fetchContactMessageByStatus(String status) {

        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
       List<Contact> results= jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,status);
            }
        },new ContactRowMapper());

       return results;
    }

    public int changeStatus(String id, String status, String loggedInName) {

        String sql="UPDATE CONTACT_MSG SET STATUS=?,UPDATED_BY=?,UPDATED_AT=? WHERE CONTACT_ID=?";

        int result=jdbcTemplate.update(sql, ps -> {
            ps.setString(1,status);
            ps.setString(2,loggedInName);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4,id);
        });
        return result;
    }*/
}
