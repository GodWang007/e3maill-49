package cn.e3mall.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDfsTest {

	@Test
	public void upload() throws Exception {
		//创建一个配置文件,配置文件中包含tracker服务器的地址
		//读取配置文件信息
		ClientGlobal.init("D:/mysoftinstall/workspace/git-49/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient=new TrackerClient();
		//通过TrackerClient获得TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StorageClient对象,需要两个参数一个是trackerserver   一个是storageserver(可是为null)
		StorageClient storageClient=new StorageClient(trackerServer,null);
		//使用storageclient对象上传文件,返回文件的路径和文件名
		//参数1:本地文件 路径    参数2:文件的扩展名,不能包含"." 参数3:元数据  可以为null
		String[] strings = storageClient.upload_file("F:/aa.txt", "txt", null);
		for (String string : strings) {
			System.out.println(string);
		}
		
		
		
	}
	@Test
	public void testFastDFSClient() throws Exception{
		FastDFSClient fastDFSClient=new FastDFSClient("D:/mysoftinstall/workspace/git-49/e3-manager-web/src/main/resources/conf/client.conf");
		String strings = fastDFSClient.uploadFile("F:/aa.txt");
		System.out.println(strings);
		
	}

}
