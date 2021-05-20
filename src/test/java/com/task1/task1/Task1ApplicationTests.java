package com.task1.task1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.task1.task1.entity.Book;
import com.task1.task1.service.BookServiceImpl;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { Task1Application.class })
class Task1ApplicationTests {

	@Autowired
	BookServiceImpl bookService;

	@Test
	public void testToGetAllBooks() {
		Book book = bookService.getAllAvailaBooks().get(0);
		Assert.assertEquals("bk101", book.getId());
		bookService.getAllAvailaBooks();

	}

}
