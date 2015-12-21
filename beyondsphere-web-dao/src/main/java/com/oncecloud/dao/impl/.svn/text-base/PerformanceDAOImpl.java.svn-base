package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.PerformanceDAO;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.model.performance.Cpu;
import com.beyondsphere.model.performance.Memory;
import com.beyondsphere.model.performance.Pif;
import com.beyondsphere.model.performance.Vbd;
import com.beyondsphere.model.performance.Vif;

@Component("PerformanceDAO")
public class PerformanceDAOImpl implements PerformanceDAO {
	
	@Resource
	private SessionHelper sessionHelper;

	@SuppressWarnings("unchecked")
	public List<Long> getAllCPUTime430() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Cpu";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCPUTime4Oneday() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Cpu1d";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCPUTime4Onemonth() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Cpu1m";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCPUTime4Sixhours() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Cpu6h";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCPUTime4Twoweeks() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Cpu2w";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllMemoryTime430() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Memory";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllMemoryTime4Oneday() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Memory1d";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllMemoryTime4Onemonth() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Memory1m";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllMemoryTime4Sixhours() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Memory6h";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllMemoryTime4Twoweeks() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Memory2w";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllPifTime430() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Pif";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllPifTime4Oneday() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Pif1d";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllPifTime4Onemonth() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Pif1m";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllPifTime4Sixhours() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Pif6h";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllPifTime4Twoweeks() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Pif2w";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVbdTime430() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vbd";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVbdTime4Oneday() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vbd1d";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVbdTime4Onemonth() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vbd1m";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVbdTime4Sixhours() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vbd6h";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVbdTime4Twoweeks() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vbd2w";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVifTime430() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vif";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVifTime4Oneday() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vif1d";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVifTime4Onemonth() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vif1m";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVifTime4Sixhours() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vif6h";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllVifTime4Twoweeks() {
		List<Long> timeList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select distinct time from Vif2w";
			Query query = session.createQuery(queryString);
			timeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	public List<Cpu> getCpuList(String uuid) {
		List<Cpu> cpuList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Cpu30min where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			cpuList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuList;
	}

	@SuppressWarnings("unchecked")
	public List<Cpu> getCpuList4Oneday(String uuid) {
		List<Cpu> cpuList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Cpu1d where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			cpuList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuList;
	}

	@SuppressWarnings("unchecked")
	public List<Cpu> getCpuList4Onemonth(String uuid) {
		List<Cpu> cpuList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Cpu1m where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			cpuList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuList;
	}

	@SuppressWarnings("unchecked")
	public List<Cpu> getCpuList4Sixhours(String uuid) {
		List<Cpu> cpuList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Cpu6h where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			cpuList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuList;
	}

	@SuppressWarnings("unchecked")
	public List<Cpu> getCpuList4Twoweeks(String uuid) {
		List<Cpu> cpuList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Cpu2w where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			cpuList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuList;
	}

	@SuppressWarnings("unchecked")
	public List<Memory> getMemoryList(String uuid) {
		List<Memory> memoryList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory30min where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			memoryList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Memory> getMemoryList4Oneday(String uuid) {
		List<Memory> memoryList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory1d where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			memoryList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Memory> getMemoryList4Onemonth(String uuid) {
		List<Memory> memoryList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory1m where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			memoryList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Memory> getMemoryList4Sixhours(String uuid) {
		List<Memory> memoryList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory6h where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			memoryList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Memory> getMemoryList4Twoweeks(String uuid) {
		List<Memory> memoryList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory2w where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			query.setMaxResults(100);
			memoryList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Pif> getPifList(String uuid) {
		List<Pif> pifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Pif30min where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			pifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pifList;
	}

	@SuppressWarnings("unchecked")
	public List<Pif> getPifList4Oneday(String uuid) {
		List<Pif> pifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Pif1d where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			pifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pifList;
	}

	@SuppressWarnings("unchecked")
	public List<Pif> getPifList4Onemonth(String uuid) {
		List<Pif> pifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Pif1m where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			pifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pifList;
	}

	@SuppressWarnings("unchecked")
	public List<Pif> getPifList4Sixhours(String uuid) {
		List<Pif> pifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Pif6h where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			pifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pifList;
	}

	@SuppressWarnings("unchecked")
	public List<Pif> getPifList4Twoweeks(String uuid) {
		List<Pif> pifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Pif2w where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			pifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pifList;
	}

	@SuppressWarnings("unchecked")
	public List<Vbd> getVbdList(String uuid) {
		List<Vbd> vbdList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vbd30min where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vbdList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vbdList;
	}

	@SuppressWarnings("unchecked")
	public List<Vbd> getVbdList4Oneday(String uuid) {
		List<Vbd> vbdList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vbd1d where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vbdList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vbdList;
	}

	@SuppressWarnings("unchecked")
	public List<Vbd> getVbdList4Onemonth(String uuid) {
		List<Vbd> vbdList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vbd1m where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vbdList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vbdList;
	}

	@SuppressWarnings("unchecked")
	public List<Vbd> getVbdList4Sixhours(String uuid) {

		List<Vbd> vbdList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vbd6h where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vbdList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vbdList;
	}

	@SuppressWarnings("unchecked")
	public List<Vbd> getVbdList4Twoweeks(String uuid) {
		List<Vbd> vbdList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vbd2w where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vbdList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vbdList;
	}

	@SuppressWarnings("unchecked")
	public List<Vif> getVifList(String uuid) {
		List<Vif> vifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif30min where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vifList;
	}

	@SuppressWarnings("unchecked")
	public List<Vif> getVifList4Oneday(String uuid) {
		List<Vif> vifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif1d where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vifList;
	}

	@SuppressWarnings("unchecked")
	public List<Vif> getVifList4Onemonth(String uuid) {
		List<Vif> vifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif1m where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vifList;
	}

	@SuppressWarnings("unchecked")
	public List<Vif> getVifList4Sixhours(String uuid) {
		List<Vif> vifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif6h where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vifList;
	}

	@SuppressWarnings("unchecked")
	public List<Vif> getVifList4Twoweeks(String uuid) {
		List<Vif> vifList = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif2w where uuid=:id order by time";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			vifList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vifList;
	}
	
	@SuppressWarnings("unchecked")
	public Vif getCurrentVif(String uuid) {
		Vif vif = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Vif30min where uuid=:id order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			List<Vif> listVif = (List<Vif>)query.list();
			Date date = new Date();
			if (listVif.size() != 0) {
				if (Math.abs(date.getTime()-listVif.get(0).getTime())<360*60000) {
					vif = listVif.get(0);
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vif;
	}
	
	@SuppressWarnings("unchecked")
	public double getCurrentCpuUsage(String uuid) {
		double cpuUsage = -1;
		Session session = null;
		Date date = new Date();
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "select time,avg(usage) from Cpu30min where uuid=:id group by time order by time desc";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			List<Object> listCpuUsage = (List<Object>)query.list();
			if (listCpuUsage.size() != 0) {
				Object[] obj = (Object[])listCpuUsage.get(0);
				if (Math.abs(date.getTime()-Long.valueOf(obj[0].toString()))<360*60000) {
					cpuUsage = Double.valueOf(obj[1].toString());
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return cpuUsage;
	}
	
	@SuppressWarnings("unchecked")
	public Memory getCurrentMemory(String uuid) {
		Memory memory = null;
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			String queryString = "from Memory30min where uuid=:id order by time  ";
			Query query = session.createQuery(queryString);
			query.setString("id", uuid);
			List<Memory> memoryList = (List<Memory>)query.list();
			Date date = new Date();
			if (memoryList.size() != 0) {
				if (Math.abs(date.getTime()-memoryList.get(0).getTime())<360*60000) {
					memory = memoryList.get(0);
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return memory;
	}
	
}
