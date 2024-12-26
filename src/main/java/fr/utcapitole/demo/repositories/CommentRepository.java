package fr.utcapitole.demo.repositories;

import fr.utcapitole.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Integer> {
    List<Comment> findByAnswerId(Integer answerId);
}
