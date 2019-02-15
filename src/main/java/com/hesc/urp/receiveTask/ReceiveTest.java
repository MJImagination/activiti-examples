package com.hesc.urp.receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentReceive() {
	InputStream inbpmn =this.getClass().getResourceAsStream("receiveTask.bpmn");
	InputStream inpng =this.getClass().getResourceAsStream("receiveTask.png");
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             .name("接收活动任务")
		             .addInputStream("receiveTask.bpmn", inbpmn)
		              .addInputStream("receiveTask.png", inpng)
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="receviceTask";
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		System.out.println("流程实例id："+pi.getId() ); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId() ); 
		/**查询执行对象*/
	 Execution execution= processEngine.getRuntimeService()
		             .createExecutionQuery()
		             .processInstanceId(pi.getId())
		             .activityId("receivetask1")
		             .singleResult();
		
		/**使用流程变量设置当前销售额，用来传递业务参数*/
		processEngine.getRuntimeService()
		             .setVariable(execution.getId(), "汇总当日销售额", 21000);
		
		/**向后执行一步，如果流程处于等待状态，使流程继续进行*/
		processEngine.getRuntimeService()
		             .signal(execution.getId());
		
		
		/**查询执行对象*/
	 Execution execution2= processEngine.getRuntimeService()
		             .createExecutionQuery()
		             .processInstanceId(pi.getId())
		             .activityId("receivetask2")
		             .singleResult();
	 
		/**从流程变量中获取汇总当日销售额*/
	Integer value=	(Integer) processEngine.getRuntimeService()
		             .getVariable(execution2.getId(), "汇总当日销售额");
	
	System.out.println("发送金额："+value);
	/**向后执行一步，如果流程处于等待状态，使流程继续进行*/
	processEngine.getRuntimeService()
	             .signal(execution2.getId());  
	}
}
