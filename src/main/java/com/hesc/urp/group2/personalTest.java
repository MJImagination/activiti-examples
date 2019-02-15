package com.hesc.urp.group2;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class personalTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentpersonal() {
	InputStream inbpmn =this.getClass().getResourceAsStream("personal.bpmn");
	InputStream inpng =this.getClass().getResourceAsStream("personal.png");
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             //.name("组任务第一种")
		             .name("组任务第三种")
		             .addInputStream("personal.bpmn", inbpmn)
		              .addInputStream("personal.png", inpng)
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="personal";
		//Map<String,Object> varibale=new HashMap<String,Object>();
		//varibale.put("userIDs", "大大,小小,中中");
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		System.out.println("流程实例id："+pi.getId() ); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId() ); 
	}
	
	
	/**查询当前人的个人任务及组任务*/
	@Test
	public void  findMyPersonTask() {
		  String assignee="小B";
		  List<Task> list=  processEngine.getTaskService()//与正在执行的任务管理的service
		                 .createTaskQuery()//创建任务查询对象
		                 /**查询条件*/
		                 .taskCandidateUser(assignee)//组任务的办理人查询
		                 //.taskAssignee(assignee)//制定个人任务查询，指定办理人
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
		String taskid="237504";
		processEngine.getTaskService()
		             .complete(taskid);
		System.out.println("完成任务id："+taskid);
		
	}
	/**查询正在执行的任务办理人表*/
	@Test
	public void  findRunPersonTask() {
		String taskid="212504";
	List<IdentityLink>	list = processEngine.getTaskService()
		             .getIdentityLinksForTask(taskid);
	 if(list!=null &&list.size()>0){
		 for (IdentityLink identityLink : list) {
			System.out.println("ssssssssss:"+identityLink.getUserId());
		}
	 }
	}
	/**查询历史任务办理人表*/
	@Test
	public void  findHistoryPersonTask() {
		String processInstanceId="212501";
	    List<HistoricIdentityLink> list =	processEngine.getHistoryService().getHistoricIdentityLinksForProcessInstance(processInstanceId);
		
		 if(list!=null &&list.size()>0){
			 for (HistoricIdentityLink historicIdentityLink : list) {
				System.out.println("sdsddddddd:"+historicIdentityLink.getUserId());
			}
		 }		
	}
	/**拾取任务，将组任务分配给个人任务，指定任务的办理人字段*/
	@Test
	public void  claim() {
		String taskId="212504";
		String userId="大F";
		processEngine.getTaskService().claim(taskId, userId);
	}
	
	/**将个人任务回退到组任务，前提之前一定是个组任务*/
	@Test
	public void  setAssigee() {
		String taskId="212504";
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	/**向组任务中添加成员*/
	@Test
	public void  addAssigee() {
		String taskId="212504";
		String UserId="大H";
		processEngine.getTaskService().addCandidateUser(taskId, UserId);
	}
	/**向组任务中删除成员*/
	@Test
	public void delAssigee() {
		String taskId="212504";
		String UserId="小B";
		processEngine.getTaskService().deleteCandidateUser(taskId, UserId);
	}
}
