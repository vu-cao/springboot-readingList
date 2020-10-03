package cao.vu.readinglist.dao;

import cao.vu.readinglist.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderDao extends JpaRepository<Reader, String> {
}
