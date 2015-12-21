package com.beyondsphere.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.User;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.model.UpFileModel;
import com.beyondsphere.tools.SSH;

@Component
public class UpXenMessageWebSocketHandler implements WebSocketHandler {
	
	private String location = "/tmp";
	
	@Resource
    private HostDAO hostDAO;
	
	@Resource
	private LogRecord logRecord;
	private static List<WebSocketSession> currentUsers;

	private static List<WebSocketSession> getCurrentUsers() {
		return currentUsers;
	}

	private static void setCurrentUsers(List<WebSocketSession> currentUsers) {
		UpXenMessageWebSocketHandler.currentUsers = currentUsers;
	}

	static {
		UpXenMessageWebSocketHandler
				.setCurrentUsers(new ArrayList<WebSocketSession>());
	}

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		UpXenMessageWebSocketHandler.getCurrentUsers().add(session);
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
	private void handleTextMessage(WebSocketSession session, TextMessage message) {
		// TODO Auto-generated method stub
		//System.out.print("handleTextMessage");
		/// 判断是否要上传文件，如果是那么，记录要上传的文件的基本信息。
		/// 约定fileStartName 开始上传
		/// 约定fileEndName 结束上传，上传完成
		/// TODO : 这个时间要做的是保存到数据库。
		try {
			
			if(message.getPayload().contains("fileStartName")){
				UpFileModel filemodel = new ObjectMapper().readValue(message.getPayload(), UpFileModel.class);

				filemodel.setFileSrc("./"+filemodel.getFileStartName());
				session.getAttributes().put("filemodel", filemodel);
				deleteFile(filemodel.getFileSrc());
				session.sendMessage(new TextMessage(new ObjectMapper()
				.writeValueAsString(new UpFileMessage("OK"))));
			}
			else if(message.getPayload().contains("fileEndName")){
				UpFileModel filemodel = (UpFileModel) session.getAttributes().get("filemodel");
				UpFileModel filemodel2 = new ObjectMapper().readValue(message.getPayload(), UpFileModel.class);
				filemodel.setFileEndName(filemodel2.getFileEndName());
				filemodel.setHostList(filemodel2.getHostList());
				session.getAttributes().put("filemodel", filemodel);
				session.sendMessage(new TextMessage(new ObjectMapper()
				.writeValueAsString(new UpFileMessage("UPOVER"))));
			}
			else if(message.getPayload().contains("UpdateXend")){
				UpFileModel filemodel = (UpFileModel) session.getAttributes().get("filemodel");
				User user = (User) session.getAttributes().get("user");
				String[] hostUuids = filemodel.getHostList().split(",");
				String result ="TRUE";
				for(String hostUuid : hostUuids)
				{
					if(!updateXen(filemodel,hostUuid,user.getUserId())){
						result ="FALSE";
						break;
					}
				}
				session.sendMessage(new TextMessage(new ObjectMapper()
				.writeValueAsString(new UpFileMessage(result))));
			}
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}


	//处理二进制内容
	private void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) {
		UpFileModel filemodel = (UpFileModel) session.getAttributes().get("filemodel");
		if(filemodel!=null){
			// TODO: 将二进制流写入文件
			try {
				if(saveFileFromBytes(message.getPayload(), filemodel.getFileSrc())){
					session.sendMessage(new TextMessage(new ObjectMapper()
					.writeValueAsString(new UpFileMessage("OK"))));
				}
				else{
					session.sendMessage(new TextMessage(new ObjectMapper()
					.writeValueAsString(new UpFileMessage("FALSE"))));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		UpXenMessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		UpXenMessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	public void sendMessageToUsers(Message message) {
		for (WebSocketSession user : UpXenMessageWebSocketHandler.getCurrentUsers()) {
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

	public void sendMessageToUser(int userId, Message message) {
		for (WebSocketSession user : UpXenMessageWebSocketHandler.getCurrentUsers()) {
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
	  private static boolean saveFileFromBytes(ByteBuffer byteBuffer, String outputFile) {  
        FileOutputStream fstream = null;  
        File file = null;  
        try {  
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
      
    @SuppressWarnings("deprecation")
	private boolean updateXen(UpFileModel filemodel,String hostUuid, int userId){
    	  Date startTime = new Date();
    	  OCHost host =hostDAO.getHost(hostUuid);
    	  boolean isok =false;
    	  if(host!=null) {
    		  SSH ssh=new SSH(host.getHostIP(),"root",host.getHostPwd());
    		  if(ssh.connect()){
    			  if(ssh.SCPFile(filemodel.getFileSrc(), location)){
    				  try {
						ssh.execute("cd " + location + " && /bin/tar xvf " + filemodel.getFileStartName());
						ssh.execute("cd " + location+ " ; nohup bash update.sh svn.tar.gz > /dev/null 2>&1");
						isok =true;
    				  } catch (Exception e) {
						e.printStackTrace();
    				  }
    			  }
    			  ssh.close();
    		  }
    	  }
    	  Date endTime = new Date();
    	  JSONArray infoArray = logRecord.createLoginfos(LogRole.HOST, host.getHostName());
    	  if (isok) {
  			logRecord.addSuccessLog(userId, LogObject.STORAGE, 
  					LogAction.UPDATE, 
  					infoArray.toString(), startTime, endTime);
  		} else {
  			logRecord.addFailedLog(userId, LogObject.STORAGE, 
  					LogAction.UPDATE, 
  					infoArray.toString(), startTime, endTime);
  		}
    	  return isok;
      }
}