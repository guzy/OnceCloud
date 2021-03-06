package com.github.dockerjava.api;

import java.io.*;

import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.api.model.Identifier;

/**
 * @author langzi
 * Docker API的入口
 */
public interface DockerClient extends Closeable {

	/**
	 * @return
	 * @throws DockerException
	 * 验证链接Docker服务的输入信息
	 */
	public AuthConfig authConfig() throws DockerException;

	/**
	 * Authenticate with the server, useful for checking authentication.
	 */
	public AuthCmd authCmd();

	/**
	 * @return
	 * 获取信息操作命令的主体
	 */
	public InfoCmd infoCmd();

	/**
	 * @return
	 * 获取ping命令的操作主体
	 */
	public PingCmd pingCmd();

	/**
	 * @return
	 * 获取版本信息的操作主体
	 */
	public VersionCmd versionCmd();

	/**
	 * * IMAGE API *
	 */

	/**
	 * @param repository imageName
	 * @return
	 * 从仓库中获取镜像
	 */
	public PullImageCmd pullImageCmd(String repository);

	public PushImageCmd pushImageCmd(String name);

    public PushImageCmd pushImageCmd(Identifier identifier);

	public CreateImageCmd createImageCmd(String repository,
			InputStream imageStream);

	public SearchImagesCmd searchImagesCmd(String term);

	public RemoveImageCmd removeImageCmd(String imageId);

	public ListImagesCmd listImagesCmd();

	public InspectImageCmd inspectImageCmd(String imageId);
    
    public SaveImageCmd saveImageCmd(String name);

	/**
	 * * CONTAINER API *
	 */

	public ListContainersCmd listContainersCmd();

	public CreateContainerCmd createContainerCmd(String image);

	/**
	 * Creates a new {@link StartContainerCmd} for the container with the
	 * given ID.
	 * The command can then be further customized by using builder
	 * methods on it like {@link StartContainerCmd#withDns(String...)}.
	 * <p>
	 * <b>If you customize the command, any existing configuration of the
	 * target container will get reset to its default before applying the
	 * new configuration. To preserve the existing configuration, use an 
	 * unconfigured {@link StartContainerCmd}.</b> 
	 * <p>
	 * This command corresponds to the <code>/containers/{id}/start</code>
	 * endpoint of the Docker Remote API.
	 */
	public StartContainerCmd startContainerCmd(String containerId);

    public ExecCreateCmd execCreateCmd(String containerId);

	public InspectContainerCmd inspectContainerCmd(String containerId);

	public RemoveContainerCmd removeContainerCmd(String containerId);

	public WaitContainerCmd waitContainerCmd(String containerId);

	public AttachContainerCmd attachContainerCmd(String containerId);

    public ExecStartCmd execStartCmd(String containerId);

    public InspectExecCmd inspectExecCmd(String execId);

	public LogContainerCmd logContainerCmd(String containerId);

	public CopyFileFromContainerCmd copyFileFromContainerCmd(
			String containerId, String resource);

	public ContainerDiffCmd containerDiffCmd(String containerId);

	public StopContainerCmd stopContainerCmd(String containerId);

	public KillContainerCmd killContainerCmd(String containerId);

	public RestartContainerCmd restartContainerCmd(String containerId);

	public CommitCmd commitCmd(String containerId);

    public BuildImageCmd buildImageCmd();

	public BuildImageCmd buildImageCmd(File dockerFileOrFolder);

	public BuildImageCmd buildImageCmd(InputStream tarInputStream);

	public TopContainerCmd topContainerCmd(String containerId);

	public TagImageCmd tagImageCmd(String imageId, String repository, String tag);
	
	public PauseContainerCmd pauseContainerCmd(String containerId);
	
	public UnpauseContainerCmd unpauseContainerCmd(String containerId);

	public EventsCmd eventsCmd(EventCallback eventCallback);

	public StatsCmd statsCmd(StatsCallback statsCallback);

	@Override
	public void close() throws IOException;

}
