package com.sks.sksschool.service;

import com.sks.sksschool.repository.ContactRepository;
import com.sks.sksschool.constants.SKSSchoolConstants;
import com.sks.sksschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
/* Apart from Singleton and Prototype scope, we have above three scopes as well.
* @RequestScope- Here for each request one new bean will be created and the constructor will be called
* @SessionScope - Only one bean for Session based. If you clear the cache/open in diff browser, then new Session
* @ApplicationScope - Only one bean for every user per Application based. If the server restarts, then new*/

//@Getter @Setter
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactService(){
        System.out.println("Contact Service has been initialized");
    }

    public boolean sendContactMsgToDB(Contact contact){

        boolean isSaved=false;

        contact.setStatus(SKSSchoolConstants.OPEN);
        /*This below is not required now as we added Auditing in MyBaseEntity class*/
        /*contact.setCreatedBy(SKSSchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());*/

        /*This save method is from JPA. It will insert the record in db. Here we didn't write any SQL.
        * internally the ListCrudRepository which we extends in ContactRepository do this*/
        Contact savedContact=contactRepository.save(contact);

        if(savedContact.getContactId()>0){
            isSaved=true;
        }

        return isSaved;

    }


    public List<Contact> fetchMessagesByStatusOpen() {

        return contactRepository.findByStatus(SKSSchoolConstants.OPEN);
    }

    public boolean closeMessageById(int id, String loggedInName) {

        boolean isChanged=false;

        Optional<Contact> contactById = contactRepository.findById(id);
        contactById.ifPresent(contact ->{
            contact.setUpdatedBy(loggedInName);
            /*This below is not required now as we added Auditing in MyBaseEntity class*/
            /*contact.setUpdatedAt(LocalDateTime.now());*/
            contact.setStatus(SKSSchoolConstants.CLOSE);
        });
        Contact updatedContact=contactRepository.save(contactById.get());
        if(updatedContact.getContactId()>0){
            isChanged=true;
        }
        return isChanged;
    }
}
