package com.beyondsphere.dao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.dao.StatisticsDAO;
import com.oncecloud.entity.OCStatistics;
import com.oncecloud.model.StatisticsType;
import com.oncecloud.util.Utilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class StatisticsDAOImplTest {

	private StatisticsDAO statisticsDAO;

	public StatisticsDAO getStatisticsDAO() {
		return statisticsDAO;
	}

	@Autowired
	public void setStatisticsDAO(StatisticsDAO statisticsDAO) {
		this.statisticsDAO = statisticsDAO;
	}

	@Test
	@Ignore
	public void test() {
		try {
			Date date = Utilities.Timestamp2Date((new Date()).getTime() - 1000
					* 60 * 60 * 24);
			List<OCStatistics> list = this.getStatisticsDAO().getListByUserId(
					2, date, StatisticsType.INSTANCE);
			System.out.println(list.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
