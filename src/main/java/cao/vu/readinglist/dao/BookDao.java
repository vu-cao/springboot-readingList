package cao.vu.readinglist.dao;

import java.util.List;

import cao.vu.readinglist.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import cao.vu.readinglist.model.Book;


public interface BookDao extends JpaRepository<Book, Long>
{
	List<Book> findByReader(final Reader reader);
}
