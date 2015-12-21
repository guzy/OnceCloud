package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.QAService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class QAServiceTest {
	@Autowired
	private QAService qaService;
	
	@Test
	public final void testGetQuestionList() {
		try {
			//test the method qaService.getQuestionList(userId, page, limit, search)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testCloseQuestion() {
		try {
			//admin can close the question from user
			//test the method qaService.closeQuestion(userId, qaId)
			//params userId is user's id not admin's id
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testCreateQuestion() {
		try {
			//
			//test the method qaService.createQuestion(userId, qaTitle, qaContent)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testReply() {
		try {
			//
			//qaService.reply(userId, qaTid, qaContent)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetQuestionDetail() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetReplyList() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
