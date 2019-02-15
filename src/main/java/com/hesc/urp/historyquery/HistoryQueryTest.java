package com.hesc.urp.historyquery;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class HistoryQueryTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
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
	
	/**查询历史活动*/
	@Test
	public void  findHistoryactiviti() {
		String processInstanceId="20001";
	List<HistoricActivityInstance>	list=processEngine.getHistoryService()
		              .createHistoricActivityInstanceQuery()
		              .processInstanceId(processInstanceId)
		              .orderByHistoricActivityInstanceStartTime().asc()
		              .list();
	if(list!=null &&list.size()>0){
		for (HistoricActivityInstance hai : list) {
			System.out.println(hai.getId()+"     "+hai.getProcessInstanceId()+"        "+hai.getStartTime());
			System.out.println("#########################");
		}
	 }
	}
	
	/**查询历史任务*/
	@Test
	public void  findHistory() {
		String processInstanceId="20001";
	List<HistoricTaskInstance> list=	processEngine.getHistoryService()
			                             .createHistoricTaskInstanceQuery()
		                                 .processInstanceId(processInstanceId)
		                                 .orderByHistoricTaskInstanceStartTime().asc()
		                                 .list();
	 if(list!=null &&list.size()>0){
		 for (HistoricTaskInstance ht : list) {
			 System.out.println(ht.getId()+"     "+ht.getProcessInstanceId()+"        "+ht.getStartTime()+"   "+ht.getName());
				System.out.println("#########################");;	
		}
	 }
	}
	
	
	/**查询流程变量的历史表*/
	@Test
	public void  findHistoryVaribale(){
	String processInstanceId="32501";
	List<HistoricVariableInstance>	list=processEngine.getHistoryService()
		             .createHistoricVariableInstanceQuery()
		             .processInstanceId(processInstanceId)
		             .list();
	 if(list!=null &&list.size()>0){
		 for (HistoricVariableInstance hvi : list) {
			 System.out.println(hvi.getProcessInstanceId()+"   "+hvi.getVariableTypeName()+"   "+hvi.getVariableName());
			 System.out.println("##############################");
		}
	 }          
	}
}
