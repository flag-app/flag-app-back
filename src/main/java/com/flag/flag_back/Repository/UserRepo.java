package com.flag.flag_back.Repository;

import com.flag.flag_back.Dto.UserDto;
import com.flag.flag_back.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepo {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }
//
//    public Member findOne(Long id) {
//        return em.find(Member.class, id);
//    }
//
//    public List<Member> findAll() {
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//    }

    public List<User> findByName(String name) {
        return em.createQuery("select m from User m where m.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public UserDto findOne(Long id) {
        return em.find(UserDto.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

}
