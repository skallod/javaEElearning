package com.bookstore.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bookstore.domain.Book;
import com.bookstore.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookResource {

	private static final Logger log = LoggerFactory.getLogger(BookResource.class);

	public static final String PNG = ".png";

	@Value("${book.image.path}")
	private String bookImagePath;
	
	private BookService bookService;

	@Autowired
	public BookResource(BookService bookService){
		this.bookService = bookService;
	}
	
	@RequestMapping (value="/add", method=RequestMethod.POST)
	public Book addBookPost(@RequestBody Book book) {
		return bookService.save(book);
	}

	@RequestMapping (value="/update", method=RequestMethod.POST)
	public Book updateBookPost(@RequestBody Book book) {
		return bookService.save(book);
	}

	@RequestMapping (value="/delete", method=RequestMethod.POST)
	public ResponseEntity deleteBookPost(@RequestBody String bookId) {
		bookService.removeOne(Long.parseLong(bookId));
		String fileName = bookId+ PNG;
		try {
			deleteBookImage(fileName);
		} catch (IOException e) {
			log.error("delete image",e);
			return new ResponseEntity("Delete img failed!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity("Remove success",HttpStatus.OK);
	}

    @RequestMapping(value="/add/image", method=RequestMethod.POST)
	public ResponseEntity upload(
			@RequestParam("id") Long id,
			HttpServletResponse response, HttpServletRequest request
			){
		try {
			//Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			byte[] bytes = multipartFile.getBytes();
			if(bytes.length==0){
				throw new IllegalArgumentException("upload bytes is empty");
			}
			String fileName = id+ PNG;
			deleteBookImage(fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream
					(new File(bookImagePath+fileName)));
			stream.write(bytes);
			stream.close();
			return new ResponseEntity("Upload Success!", HttpStatus.OK);
		} catch (Exception e) {
			log.error("upload img",e);
			return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
		}
	}

	private void deleteBookImage(String fileName) throws IOException {
		Path path = Paths.get(bookImagePath + fileName);
		if(Files.exists(path)) {
			Files.delete(path);
		}
	}

	@RequestMapping (value="/list", method=RequestMethod.GET)
	public List<Book> getBookList() {
		return bookService.findAll();
	}

	@RequestMapping (value="/{id}", method=RequestMethod.GET)
	public Book getBook(@PathVariable("id") Long id) {
		return bookService.findOne(id);
	}
}
