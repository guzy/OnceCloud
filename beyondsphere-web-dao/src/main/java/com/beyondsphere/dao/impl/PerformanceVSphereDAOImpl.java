package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.PerformanceVSphereDAO;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.OCVM;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.model.performance.Cpu1d;
import com.beyondsphere.model.performance.Cpu1m;
import com.beyondsphere.model.performance.Cpu2w;
import com.beyondsphere.model.performance.Cpu30min;
import com.beyondsphere.model.performance.Cpu6h;
import com.beyondsphere.model.performance.Memory1d;
import com.beyondsphere.model.performance.Memory1m;
import com.beyondsphere.model.performance.Memory2w;
import com.beyondsphere.model.performance.Memory30min;
import com.beyondsphere.model.performance.Memory6h;
@Component("PerformanceVSphereDAO")
public class PerformanceVSphereDAOImpl implements PerformanceVSphereDAO {
	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private HashHelper hashHelper;
	@Override
	public void addPerformanceData(Cpu6h cpu6h) {
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
//			session.save(cpu6h);
			String sql="insert into cpu_6h( `usage` , t, id, cpu_id) values("+cpu6h.getUsage()+", "+cpu6h.getTime()+", '"+cpu6h.getUuid()+"',"+cpu6h.getCpuId()+")";
			Query query =session.createSQLQuery(sql);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}
	public void addPerformanceData(Memory6h memory6h) {
		Session session = null;
		try {
			session = sessionHelper.getPerformaceSession();
			session.beginTransaction();
			session.save(memory6h);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}
	//所有主机
			@SuppressWarnings("unchecked")
			public List<OCHost> getAllHost(String hosttype) {
				List<OCHost> list = null;
				Session session = null;
				try {
					session = sessionHelper.getMainSession();
					session.beginTransaction();
					String queryString = "from OCHost where hostStatus = 1 and hostType =:hosttype";
					Query query = session.createQuery(queryString);
					query.setString("hosttype", hosttype);
					list = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				return changePwdOfHost(list);
			}
			private List<OCHost> changePwdOfHost(List<OCHost> hosts){
				if (hosts.size() > 0) {
					for(OCHost ocHost:hosts){
						//解码服务器密码
						ocHost.setHostPwd(hashHelper.hashDIY(ocHost.getHostPwd()));
					}
				}
				return hosts;
			}

			@Override
			public void addPerformanceData(Cpu1d cpu1d) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
//					session.save(cpu1d);
					String sql = "insert into cpu_1d( `usage` , t, id, cpu_id) values("+cpu1d.getUsage()+", "+cpu1d.getTime()+", '"+cpu1d.getUuid()+"',"+cpu1d.getCpuId()+")";
					Query query =session.createSQLQuery(sql);
					query.executeUpdate();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
			}

			@Override
			public void addPerformanceData(Cpu2w cpu2w) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
//					session.save(cpu2w);
					String sql ="insert into cpu_2w( `usage` , t, id, cpu_id) values("+cpu2w.getUsage()+", "+cpu2w.getTime()+", '"+cpu2w.getUuid()+"',"+cpu2w.getCpuId()+")";
					Query query =session.createSQLQuery(sql);
					query.executeUpdate();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
			}

			@Override
			public void addPerformanceData(Cpu1m cpu1m) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
//					session.save(cpu1m);
					String sql = "insert into cpu_1m( `usage` , t, id, cpu_id) values("+cpu1m.getUsage()+", "+cpu1m.getTime()+", '"+cpu1m.getUuid()+"',"+cpu1m.getCpuId()+")";
					Query query =session.createSQLQuery(sql);
					query.executeUpdate();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
			}


			@Override
			public void addPerformanceData(Memory1m memory1d) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					session.save(memory1d);
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
			}

			@Override
			public void addPerformanceData(Memory2w memory2w) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					session.save(memory2w);
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				
			}

			@Override
			public void addPerformanceData(Memory1d memory1m) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					session.save(memory1m);
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<Double> getAllCPU30MinsByTimes(long times,String vmuuid) {
				List<Double> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select usage from Cpu30min where time >:times and uuid=:uuid ";
					Query query = session.createQuery(queryString);
					query.setLong("times", times);
					query.setString("uuid", vmuuid);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				return timeList;
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxCPUTime4OneDay(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Cpu1d where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxCPUTime4TwoWeeks(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Cpu2w where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxCPUTime4OneMonth(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Cpu1m where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<Memory30min> getAllMem30MinsByTimes(long times,String uuid) {
				List<Memory30min> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "from Memory30min where time >:times and uuid=:uuid ";
					Query query = session.createQuery(queryString);
					query.setLong("times", times);
					query.setString("uuid", uuid);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				return timeList;
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxMem4OneDay(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Memory1d where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxMem4TwoWeeks(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Memory2w where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public long getMaxMem4OneMonth(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Memory1m where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}
			@Override
			public void addPerformanceData(Cpu30min cpu30min) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
//					session.save(cpu1m);
					String sql = "insert into cpu_30min( `usage` , t, id, cpu_id) values("+cpu30min.getUsage()+", "+cpu30min.getTime()+", '"+cpu30min.getUuid()+"',"+cpu30min.getCpuId()+")";
					Query query =session.createSQLQuery(sql);
					query.executeUpdate();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}				
			}
			@Override
			public void addPerformanceData(Memory30min memory30min) {
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					session.save(memory30min);
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
			}
			@SuppressWarnings("unchecked")
			@Override
			public long getMaxCPUTime4SixHours(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Cpu6h where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
				
			}
			@SuppressWarnings("unchecked")
			@Override
			public long getMaxMem4SixHours(String id) {
				List<Long> timeList = null;
				Session session = null;
				try {
					session = sessionHelper.getPerformaceSession();
					session.beginTransaction();
					String queryString = "select max(time) from Memory6h where uuid=:uuid";
					Query query = session.createQuery(queryString);
					query.setString("uuid", id);
					timeList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				if(timeList.size()>0){
					return timeList.get(0)==null?0:timeList.get(0);
				}else{
					return 0;
				}
			}
			
			// 根据主机查询虚拟机
			@SuppressWarnings("unchecked")
			public List<OCVM> getVMsByHost(String hostuuid) {
				List<OCVM> vmList = null;
				Session session = null;
				try {
					session = sessionHelper.getMainSession();
					session.beginTransaction();
					String queryString = "from OCVM where vmHostUuid = :hostuuid and vmStatus = 1 order by vmCreateDate desc";
					Query query = session.createQuery(queryString);
					query.setString("hostuuid", hostuuid);
					vmList = query.list();
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (session.isOpen()) {
						session.getTransaction().rollback();
					}
				}
				return vmList;
			}
	}
