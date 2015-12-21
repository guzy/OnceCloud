package com.beyondsphere.helper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public final class SessionHelper {
	private SessionFactory mainSessionFactory;
	private SessionFactory performanceSessionFactory;
	private SessionFactory platformSessionFactory;

	private SessionFactory getMainSessionFactory() {
		return mainSessionFactory;
	}

	private void setMainSessionFactory(SessionFactory mainSessionFactory) {
		this.mainSessionFactory = mainSessionFactory;
	}

	private SessionFactory getPerformanceSessionFactory() {
		return performanceSessionFactory;
	}

	private void setPerformanceSessionFactory(
			SessionFactory performanceSessionFactory) {
		this.performanceSessionFactory = performanceSessionFactory;
	}

	public SessionFactory getPlatformSessionFactory() {
		return platformSessionFactory;
	}

	public void setPlatformSessionFactory(SessionFactory platformSessionFactory) {
		this.platformSessionFactory = platformSessionFactory;
	}

	public SessionHelper() {
		try {
			this.configureMainDatabase();
			this.configurePerformanceDatabase();
			this.configurePlatformDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configurePlatformDatabase() {
		Configuration platformConfiguration = new Configuration()
				.configure("com/beyondsphere/config/hibernate-platform.cfg.xml");
		ServiceRegistry platformServiceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(platformConfiguration.getProperties()).build();
		this.setPlatformSessionFactory(platformConfiguration
				.buildSessionFactory(platformServiceRegistry));
	}

	private void configureMainDatabase() {
		Configuration mainConfiguration = new Configuration()
				.configure("com/beyondsphere/config/hibernate-main.cfg.xml");
		ServiceRegistry mainServiceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(mainConfiguration.getProperties()).build();
		this.setMainSessionFactory(mainConfiguration
				.buildSessionFactory(mainServiceRegistry));
	}

	private void configurePerformanceDatabase() {
		Configuration performanceConfiguration = new Configuration()
				.configure("com/beyondsphere/config/hibernate-performance.cfg.xml");
		ServiceRegistry performanceServiceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(performanceConfiguration.getProperties())
				.build();
		this.setPerformanceSessionFactory(performanceConfiguration
				.buildSessionFactory(performanceServiceRegistry));
	}

	public Session getMainSession() {
		return this.getMainSessionFactory().getCurrentSession();
	}

	public Session getPerformaceSession() {
		return this.getPerformanceSessionFactory().getCurrentSession();
	}

	public Session getPlatformSession() {
		return this.getPlatformSessionFactory().getCurrentSession();
	}
}
