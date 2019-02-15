package com.hesc.urp.exclusiveGateWay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class ZhcsV3Test {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentExclusiveGateWay() {
	InputStream inbpmn =this.getClass().getResourceAsStream("zhcs_event.bpmn");
	InputStream inpng =this.getClass().getResourceAsStream("zhcs_event.png");
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             .name("排他网关测试")
		             .addInputStream("zhcs_event.bpmn", inbpmn)
		              .addInputStream("zhcs_event.png", inpng)
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="zhcs_event";
		Map<String,Object> varibale=new HashMap<String,Object>();
		varibale.put("lian", EventConstants.EVENTSTATE_WGLA);
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey, varibale);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		//  我们也可以在流程实例启动时添加一些流程变量，因为第一个用户任务的表达式需要这些变量。
		System.out.println("流程实例id："+pi.getId()); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId()); 
	}
	
	
	/**查询当前人的个人任务*/
	@Test
	public void  findMyPersonTask() {
		  String assignee="网格员";
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
		String taskid="137506";
		//完成任务的同时,设置流程流程变量，使用流程变量用来指定完成后，下一个连心，对应sequenceFlow.bpmn中的${message=="不重要"}
		Map<String,Object> varibale=new HashMap<String,Object>();
		varibale.put("wgflow", EventConstants.EVENTSTATE_WGBJ);
		varibale.put("reporter", "1");
		processEngine.getTaskService()
		             .complete(taskid, varibale);
		
		System.out.println("请上报人确认");
		
	}
	
	/**完成我的任务*/
	@Test
	public void  completeReporterTask() {
		String taskid="140006";
		//完成任务的同时,设置流程流程变量，使用流程变量用来指定完成后，下一个连心，对应sequenceFlow.bpmn中的${message=="不重要"}
		Map<String,Object> varibale=new HashMap<String,Object>();
		varibale.put("confirm", EventConstants.EVENTSTATE_SBRQRK);
		processEngine.getTaskService()
		             .complete(taskid, varibale);
		
		System.out.println("上报人确认完毕！上报成功！");
		
	}
	
	@Test
	public void listLastVersion() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
		.createProcessDefinitionQuery()
		.orderByProcessDefinitionVersion().asc()//升序
		.list();
		//定义有序map，相同的key,添加map值后，后面的会覆盖前面的值
		Map<String,ProcessDefinition> map=new LinkedHashMap<String,ProcessDefinition>();
		//遍历相同的key，替换最新的值
		for(ProcessDefinition pd:list){
			map.put(pd.getKey(), pd);
		}
		
		List<ProcessDefinition> linkedList=new LinkedList<ProcessDefinition>(map.values());
		for(ProcessDefinition pd:linkedList){
			System.out.println(pd.getId());
			System.out.println(pd.getName());
			System.out.println(pd.getVersion());
		}
	}

}


