package cao.vu.readinglist.controller;

import java.util.List;

import cao.vu.readinglist.util.AmazonProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cao.vu.readinglist.dao.BookDao;
import cao.vu.readinglist.model.Book;
import cao.vu.readinglist.model.Reader;


@Controller
@RequestMapping(value = "/reading")
public class ReadingListController
{
    private Logger LOGGER = LoggerFactory.getLogger(ReadingListController.class);
    @Autowired
	private BookDao bookDao;
    @Autowired
	private AmazonProperties amazonProperties;

	/*@Autowired
	public ReadingListController(BookDao bookDao)
	{
		this.bookDao = bookDao;
	}*/

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String readerBooks(final Reader reader, Model model)
	{
		final List<Book> books = bookDao.findByReader(reader);
		model.addAttribute("reader", reader);
		if (!CollectionUtils.isEmpty(books))
		{
			model.addAttribute("books", books);
			model.addAttribute("amazonID", amazonProperties.getAssociateId());
		}

		return "list";
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String addBook(final Reader reader, final Book book)
	{
		book.setReader(reader);
		bookDao.save(book);
		LOGGER.debug("Book is saved!");

		return "redirect:/reading/list";
	}
}
