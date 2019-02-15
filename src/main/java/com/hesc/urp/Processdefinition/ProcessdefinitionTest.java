package com.hesc.urp.Processdefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessdefinitionTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**部署流程定义*/
	@Test
	public void deploymentProcessdefinition_classpath() {
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             .name("Hellowrod入门程序")
		             .addClasspathResource("diagrams/HelloWord.bpmn")
		             .addClasspathResource("diagrams/HelloWord.png")
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	
	  /**部署流程定义*/
		@Test
		public void deploymentProcessdefinition_zip() {
			InputStream in =this.getClass().getClassLoader().getResourceAsStream("diagrams/HelloWord.zip ");
			ZipInputStream  zipInputStream =  new ZipInputStream(in);
		Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
			             .createDeployment()//创建一个部署对象
			             .name("流程定义")
			             .addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
			             .deploy();
		System.out.println("部署id:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	   }

	  /**查询流程定义*/
		@Test
		public void findProcessdefinition() {
		List<ProcessDefinition>	 list=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
			             .createProcessDefinitionQuery()//创建一个流程定义的查询
			             /**指定查询条件*/
			             //.deploymentId(arg0)//使用部署对象id查询
			             //.processDefinitionId(arg0)//使用流程定义id查询
			             //.processDefinitionKey(arg0)//使用流程定义key查询
			             //.processDefinitionNameLike(arg0)//使用流程定义的名称模糊查询
			             /**排序*/
			             .orderByProcessDefinitionVersion().asc()//版本的升序排列
			             //.orderByProcessDefinitionName().desc()
			             /**返回结果集*/
			             .list();//返回一个集合列表，封装流程定义
			             //.singleResult();//返回一个结果集
			             //.count();//返回结果数量
			             //.listPage(arg0, arg1);//分页查询
		if(list!=null&&list.size()>0){
			for (ProcessDefinition pd : list) {
				System.out.println("流程定义id:"+pd.getId());
				System.out.println("部署对象id："+pd.getDeploymentId());
				System.out.println("######################");
			}
		 }
		}
		
		/**删除流程定义*/
		@Test
		public void deteleProcessdefinition() {
			//使用部署id 完成删除
			 String deploymentId="";
			 /**
			  * 不带联级的删除
			  *  只能删除没有启动的流程.如果流程启动就会跑出异常
			  * 
			  * */
			// processEngine.getRepositoryService().deleteDeployment(deploymentId);
			 /**
			  * 级联删除
			  *  不管流程程序是否启动，都可以删除
			  *  
			  * */
			 processEngine.getRepositoryService().deleteDeployment(deploymentId,true);
			 System.out.println("删除成功");
		}
		
		/**查询流程图
		 * @throws IOException */
		@Test
		public void viewPic() throws IOException {
			/**
			 将生成的图片放到文件夹下*/
			String deploymentid="20001";
			//获取图片资源名称
		List<String> list=	processEngine.getRepositoryService()
			                             .getDeploymentResourceNames(deploymentid);
	    String resourceName="";
		if(list!=null&list.size()>0){
			for (String name : list) {
				if(name.indexOf(".png")>=0){
					resourceName=name;
				}
			}
		}	
		
		//获取图片的输入流
		InputStream in=	processEngine.getRepositoryService()
			             .getResourceAsStream(deploymentid, resourceName);
		
		//将图片生成到D盘的目录下
		File file=new File("D:/"+resourceName);
		 //将输入流的图片写到d盘下
		FileUtils.copyInputStreamToFile(in, file);
		}	
		
		
		/**附加功能：查询最新版本的流程定义*/
		@Test
		public void findLastVersionProcessDefinition()  {
		List<ProcessDefinition>	 list=processEngine.getRepositoryService()
			             .createProcessDefinitionQuery()
			             .orderByProcessDefinitionVersion().asc()
			             .list();
		/**
		 * Map<String,ProcessDefinition>
		 * map的 key:流程定义的key
		 * map的 value:流程定义的对象
		 * map特点： 当map集合key值相同的情况下，后一个值替换前一个值
		 * */
		Map<String,ProcessDefinition> map=new LinkedHashMap<String,ProcessDefinition>();
		if(list!=null&list.size()>0){
			for (ProcessDefinition pd : list) {
				map.put(pd.getKey(), pd);
			}
		}
		List<ProcessDefinition> pdList=new ArrayList<ProcessDefinition>(map.values());
		if(pdList!=null&&pdList.size()>0){
			for (ProcessDefinition pd : pdList) {
				System.out.println("流程定义id:"+pd.getId());
				System.out.println("部署对象id："+pd.getDeploymentId());
				System.out.println("######################");
			}
		}
	  }
		
		/**附加功能：删除流程定义（删除key相同的所有不同版本的流程的定义）*/
		@Test
		public void deleteProcessDefinitionByKey()  {
			String processDefinitionKey="Helloword";
			//先使用流程定义的key 查询流程定义，所有的版本
			List<ProcessDefinition>	 list=processEngine.getRepositoryService()
		             .createProcessDefinitionQuery()
		             .processDefinitionKey(processDefinitionKey)
		             .list();
			//遍历，获取灭个流程定义的部署id
			if(list!=null&list.size()>0){
				for (ProcessDefinition pd : list) {
					String deploymentId =pd.getDeploymentId();
					processEngine.getRepositoryService()
					             .deleteDeployment(deploymentId, true);
				}
			}
		}	
		
}
