package kr.ac.phdljr.springbootsecurity.repository;

import kr.ac.phdljr.springbootsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
