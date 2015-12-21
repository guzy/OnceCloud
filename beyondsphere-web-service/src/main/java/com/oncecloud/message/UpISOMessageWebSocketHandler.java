package com.oncecloud.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.oncecloud.core.ISOCore;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.User;
import com.oncecloud.model.ISOModel;
import com.oncecloud.model.PagedList;
import com.oncecloud.model.UpFileModel;
import com.oncecloud.service.HostService;
import com.oncecloud.service.PoolService;
import com.oncecloud.tools.SSH;

@Component
public class UpISOMessageWebSocketHandler implements WebSocketHandler {
	
	@Resource
	private PoolService poolService;
    @Resource
    private HostService hostService;
    @Resource
    private ISOCore isoCore;
    @Resource
    private PoolDAO poolDAO;
	
	private static List<WebSocketSession> currentUsers;

	private static List<WebSocketSession> getCurrentUsers() {
		return currentUsers;
	}

	private static void setCurrentUsers(List<WebSocketSession> currentUsers) {
		UpISOMessageWebSocketHandler.currentUsers = currentUsers;
	}

	static {
		UpISOMessageWebSocketHandler
				.setCurrentUsers(new ArrayList<WebSocketSession>());
	}

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		UpISOMessageWebSocketHandler.getCurrentUsers().add(session);
		User user = (User) session.getAttributes().get("user");
		if (user != null) {
		}
	}

	
	/// cyh edit
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		if (message instanceof TextMessage) {
			handleTextMessage(session, (TextMessage) message);
		}
		else if (message instanceof BinaryMessage) {
			handleBinaryMessage(session, (BinaryMessage) message);
		}
		else {
			throw new IllegalStateException("Unexpected WebSocket message type: " + message);
		}
	}

	//处理字符串 
	@SuppressWarnings("deprecation")
	private void handleTextMessage(WebSocketSession session, TextMessage message) {
		// TODO Auto-generated method stub
		User user = (User) session.getAttributes().get("user");
		/// 判断是否要上传文件，如果是那么，记录要上传的文件的基本信息。
		/// 约定fileStartName 开始上传
		/// 约定fileEndName 结束上传，上传完成
		/// TODO : 这个时间要做的是保存到数据库。
		try {
			
			if(message.getPayload().contains("fileStartName"))
			{
				UpFileModel filemodel = new ObjectMapper().readValue(message.getPayload(), UpFileModel.class);
			
				PagedList<ISOModel> list = getISOListAll(user.getUserId());
				OCHost host = hostService.getMasterOfPool(filemodel.getPoolId());
				if(host==null)
				{
					session.sendMessage(new TextMessage(new ObjectMapper()
					.writeValueAsString(new UpFileMessage("NOTPOOL"))));
					return ;
				}
				if(list!=null && list.getList()!=null && list.getList().size()>0)
				{
					for(ISOModel model : list.getList())
					{
						if(model.getPoolId().equals(filemodel.getPoolId()))
						{
							if(model.getIsoName().equals(filemodel.getFileStartName()))
							{
								session.sendMessage(new TextMessage(new ObjectMapper()
								.writeValueAsString(new UpFileMessage("ISEXIT"))));
								return ;
							}
						}
					}
				}
				
				filemodel.setFileSrc("./"+filemodel.getFileStartName());
				session.getAttributes().put("filemodel", filemodel);
				deleteFile(filemodel.getFileSrc());
				session.sendMessage(new TextMessage(new ObjectMapper()
				.writeValueAsString(new UpFileMessage("OK"))));
			}
			else if(message.getPayload().contains("sendover"))
			{
				UpFileModel filemodel = (UpFileModel) session.getAttributes().get("filemodel");
				OCHost host = hostService.getMasterOfPool(filemodel.getPoolId());
				String result ="FALSE";
				if(host!=null)
				{
					SSH ssh=new SSH(host.getHostIP(),"root",host.getHostPwd());
					String remoteDir="";
					JSONArray arr = hostService.getSrOfHost(host.getHostUuid());
					if(arr.length()>0)
					{
						JSONObject obj =(JSONObject) arr.get(0);
						remoteDir = (String) obj.get("srdir")+"/iso";
					}
					if(ssh.connect())
					{
						if(ssh.SCPFile(filemodel.getFileSrc(), remoteDir))
						{
							result= "TRUE";
							deleteFile(filemodel.getFileSrc());
						}
						ssh.close();
					}
				}
				session.sendMessage(new TextMessage(new ObjectMapper()
				.writeValueAsString(new UpFileMessage(result))));
			}
			
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	//处理二进制内容
	private void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) {
		// TODO Auto-generated method stub
		//System.out.print("handleBinaryMessage");
		UpFileModel filemodel = (UpFileModel) session.getAttributes().get("filemodel");
		if(filemodel!=null)
		{
			//// TODO: 将二进制流写入文件
			//System.out.print(message.getPayload());
			try {
				if(saveFileFromBytes(message.getPayload(), filemodel.getFileSrc()))
				{
					session.sendMessage(new TextMessage(new ObjectMapper()
					.writeValueAsString(new UpFileMessage("OK"))));
				}
				else
				{
					session.sendMessage(new TextMessage(new ObjectMapper()
					.writeValueAsString(new UpFileMessage("FALSE"))));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		UpISOMessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		UpISOMessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	public void sendMessageToUsers(Message message) {
		for (WebSocketSession user : UpISOMessageWebSocketHandler.getCurrentUsers()) {
			try {
				if (user.isOpen()) {
					user.sendMessage(new TextMessage(new ObjectMapper()
							.writeValueAsString(message)));
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	public void sendMessageToUser(int userId, Message message) {
		for (WebSocketSession user : UpISOMessageWebSocketHandler.getCurrentUsers()) {
			User iterator = (User) user.getAttributes().get("user");
			if (iterator != null) {
				if (iterator.getUserId() == userId) {
					try {
						if (user.isOpen()) {
							user.sendMessage(new TextMessage(new ObjectMapper()
									.writeValueAsString(message)));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
     * 将二进制byte[]数组写入文件中
     * @param byteBuffer byte[]数组
     * @param outputFile 文件位置
     * @return 成功: true 失败: false
     */
      private static boolean saveFileFromBytes(ByteBuffer byteBuffer, String outputFile)  
      {  
        FileOutputStream fstream = null;  
        File file = null;  
        try  
        {  
        	//System.out.print(outputFile);
          file = new File(outputFile);
          if(!file.exists())
        	  file.createNewFile();
          fstream = new FileOutputStream(file, true);  
          fstream.write(byteBuffer.array());
        }  
        catch (Exception e)  
        {  
          e.printStackTrace();
          return false;
        }  
        finally  
        {  
          if (fstream != null)  
          {  
            try  
            {  
                fstream.close();  
            }  
            catch (IOException e1)  
            {  
              e1.printStackTrace();  
            }  
          }  
        }  
        return true;  
      }  
      
	private static boolean deleteFile(String outputFile)  
      {
    	  File file = null;  
          try  
          {  
            file = new File(outputFile);
            if(file.exists())
          	  file.delete();
            
            return true;
          }  
          catch (Exception e)  
          {  
            e.printStackTrace();
            return false;
          }  
      }
	
	private PagedList<ISOModel> getISOListAll(int userId) {
		PagedList<ISOModel> pagedList=null;
		try {
			List<ISOModel> list =new ArrayList<ISOModel>();
			List<OCPool> poollist = poolService.getPoolList();
			for(OCPool pool :poollist){
				if(pool.getPoolMaster()!=null){
					list = isoCore.getISOModelOfPool(list, pool);
				}
			}
			pagedList  = new  PagedList<ISOModel>(1,123456,"",list.size());
			pagedList.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagedList;
	}
      
}