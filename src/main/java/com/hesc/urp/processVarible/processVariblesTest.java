package com.hesc.urp.processVarible;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class processVariblesTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentProcessVaribles() {
		InputStream inbpmn =this.getClass().getResourceAsStream("/diagrams/processVaribles.bpmn");
		InputStream inpng =this.getClass().getResourceAsStream("/diagrams/processVaribles.bpmn");
	    Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
		             .createDeployment()//创建一个部署对象
		             .name("变量定义")
		             .addInputStream("processVaribles.bpmn", inbpmn)
		             .addInputStream("processVaribles.png", inpng)
		             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	 
	/**启动流程实例*/
	@Test
	public void startProcessInstance() {
		String processDefinitionKey="ProcessInstanceProcessVarible";
		ProcessInstance pi= processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动key启动流程实例，key对应helloword.bpmn文件id的属性值
		System.out.println("流程实例id："+pi.getId() ); 
		System.out.println("流程实例定义id："+pi.getProcessDefinitionId() ); 
	}
	
	
	/**设置流程变量*/
	@Test
	public void setVariables() {
		/**与任务（正在执行）*/
		String taskid="47504";
		/**使用基本数据类型*/
		TaskService taskservice=processEngine.getTaskService();
		//taskservice.setVariable(taskid , "请假天数", 8);
		//taskservice.setVariable(taskid , "请假日期", new Date());
		//taskservice.setVariable(taskid , "请假原因","回家探亲，一起吃饭，顺便洗个澡" );
		/**使用javabean类型*/
		/**javabean 必须实现序列化，要求属性不能发生变化
		 * 假如要 添加
		 * private static final long serialVersionUID = 6176994645930130425L;
		 * 实现序列化
		 * */
		Person  person =new Person();
		person.setId("10");
		person.setName("翠花");
		taskservice.setVariable(taskid, "人员信息", person);
		System.out.println("输出成功");
		
	}
	
	
	/**获取流程变量*/
	@Test
	public void getVariables() { 
		/**与任务（正在执行）*/
		String taskid="47504";
		TaskService taskservice=processEngine.getTaskService();
		/**使用基本数据类型
		Integer day=(Integer) taskservice.getVariable(taskid , "请假天数");
		Date date=(Date) taskservice.getVariable(taskid , "请假日期");
		String renson=(String) taskservice.getVariable(taskid , "请假原因");
		System.out.println("请假天数："+day);
		System.out.println("请假日期："+date);
		System.out.println("请假原因："+renson);*/
		
		Person p=(Person) taskservice.getVariable(taskid, "人员信息");
		System.out.println("id:"+p.getId()+"### name:"+p.getName());
		
	}
	/**模拟设置和和获取流程变量的场景*/
	
	@Test
	public void setAndGetVariables() {
		RuntimeService runtimeService	=processEngine.getRuntimeService();
		TaskService taskservice=processEngine.getTaskService();
	}
	
	/**完成我的任务*/
	@Test
	public void  completeMyPersonTask() {
		String taskid="60002";
		processEngine.getTaskService()
		             .complete(taskid);
		System.out.println("完成任务id："+taskid);
		
	}
	/**查询流程变量的历史表*/
	@Test
	public void  findHistoryVaribale(){
	List<HistoricVariableInstance>	list=processEngine.getHistoryService()
		             .createHistoricVariableInstanceQuery()
		             .variableName("请假天数")
		             .list();
	 if(list!=null &&list.size()>0){
		 for (HistoricVariableInstance hvi : list) {
			 System.out.println("1:"+hvi.getProcessInstanceId());
		}
	 }          
	}
}
