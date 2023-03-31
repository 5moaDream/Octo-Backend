package com.example.activityservice;

import com.example.activityservice.dao.GuestBookDao;
import com.example.activityservice.repository.GuestBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
class ActivityServiceApplicationTests {

	@Autowired
	GuestBookRepository guestBookRepository;

	@BeforeEach
	void generateData(){
		guestBookRepository.save(
				new GuestBookDao(1L, "asd@123.com", "zxc@123.com"
						, "안녕~", LocalDateTime.now(), false));
		guestBookRepository.save(
				new GuestBookDao(2L, "asd@123.com", "zzzz@123.com"
						, "안녕1~", LocalDateTime.now(), false));
		guestBookRepository.save(
				new GuestBookDao(3L, "asd@123.com", "222s@123.com"
						, "안녕2~", LocalDateTime.now(), false));
		guestBookRepository.save(
				new GuestBookDao(4L, "asd@123.com", "zhmr@123.com"
						, "안녕3~", LocalDateTime.now(), false));
		guestBookRepository.save(
				new GuestBookDao(5L, "asd@123.com", "chfg@123.com"
						, "안녕4~", LocalDateTime.now(), false));
	}

	@Test
	@Transactional
	void updateTest(){
		System.out.println(guestBookRepository.findAllByUserEmailAndReadFalse("asd@123.com"));
		System.out.println(guestBookRepository.updateRead(1L));
		System.out.println(guestBookRepository.findAll());
		System.out.println(guestBookRepository.updateAllRead("asd@123.com"));
		System.out.println(guestBookRepository.findAllByUserEmailAndReadFalse("asd@123.com"));
		System.out.println(guestBookRepository.findAll());
	}
}
