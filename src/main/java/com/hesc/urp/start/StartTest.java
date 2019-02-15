package com.hesc.urp.start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentSTart() {
	InputStream inbpmn =this.getClass().getResourceAsStream("start.bpmn");
	InputStream inpng =this.getClass().getResourceAsStream("start.png");
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             .name("开始活动")
		             .addInputStream("start.bpmn", inbpmn)
		              .addInputStream("start.png", inpng)
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="start";
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		System.out.println("流程实例id："+pi.getId() ); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId() ); 
		
		/**判断流程是否结束，查询正在执行的执行对象表*/
		ProcessInstance rpi=processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(pi.getId())
				.singleResult();
			if(rpi==null){
			System.out.println("流程已经结束");
			 HistoricProcessInstance hpi=	processEngine.getHistoryService()
                     .createHistoricProcessInstanceQuery()
                     .processInstanceId(pi.getId())
                     .singleResult();

                   System.out.println("sssss:"+hpi.getProcessDefinitionId());	
			}else{
			System.out.println("流程没有结束");
			}
	}
	
	
	
	
}
