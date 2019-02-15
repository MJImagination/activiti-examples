package com.hesc.urp.processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ProcessInstanceTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
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
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="Helloword";
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		System.out.println("流程实例id："+pi.getId() ); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId() ); 
	}
	
	
	/**查询当前人的个人任务*/
	@Test
	public void  findMyPersonTask() {
		  String assignee="王五";
		  List<Task> list=  processEngine.getTaskService()//与正在执行的任务管理的service
		                 .createTaskQuery()//创建任务查询对象
		                 /**查询条件*/
		                // .taskCandidateUser(arg0)组任务的办理人查询
		                 .taskAssignee(assignee)//制定个人任务查询，指定办理人
		                  /**排序*/
		                 
		                  /**结果集*/
		                 .list();
		  if(list!=null &&list.size()>0){
			 for (Task task : list) {
				System.out.println("任务id:"+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务创建时间："+task.getCreateTime());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("流程实例id："+task.getProcessInstanceId());
				System.out.println("执行对象Id："+task.getExecutionId());
				System.out.println("流程定义Id："+task.getProcessDefinitionId());
				System.out.println("###################################");
			}
		  }
	}
	
	/**完成我的任务*/
	@Test
	public void  completeMyPersonTask() {
		String taskid="25002";
		processEngine.getTaskService()
		             .complete(taskid);
		System.out.println("完成任务id："+taskid);
		
	}
	/**查询流程状态（判断流程正在执行，还是结束）*/
	@Test
	public void  isProcessEnd() {
    String processInstanceId="20001";
	ProcessInstance pi=processEngine.getRuntimeService()
									.createProcessInstanceQuery()
									.processInstanceId(processInstanceId)
									.singleResult();
		if(pi==null){
			System.out.println("流程已经结束");
		}else{
			System.out.println("流程没有结束");
		}
	}
	/**查询历史任务*/
	@Test
	public void  findHistory() {
		String taskAssignee="张三";
	List<HistoricTaskInstance> list=	processEngine.getHistoryService().createHistoricTaskInstanceQuery()
		                                 .taskAssignee(taskAssignee)
		                                 .list();
	 if(list!=null &&list.size()>0){
		 for (HistoricTaskInstance ht : list) {
		System.out.println("sssss:"+ht.getId());	
		}
	 }
	}
	
	/**查询历史流程实例*/
	@Test
	public void  findHistoryProcessInstance() {
		String processInstanceId="20001";
     HistoricProcessInstance hpi=	processEngine.getHistoryService()
			                             .createHistoricProcessInstanceQuery()
		                                 .processInstanceId(processInstanceId)
		                                 .singleResult();
	
		System.out.println("sssss:"+hpi.getProcessDefinitionId());	
	
	}
}
