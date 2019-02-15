package com.hesc.urp.helloWord;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class HelloWord {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**部署流程定义*/
	@Test
	public void deploymentProcessdefinition() {
	Deployment deployment =	processEngine.getRepositoryService()//与流程定义和部署对象相关的service
							             .createDeployment()//创建一个部署对象
							             .name("Helloword入门程序")
							             .addClasspathResource("diagrams/HelloWord.bpmn")
							             .addClasspathResource("diagrams/HelloWord.png")
							             .deploy();
	System.out.println("部署id:"+deployment.getId());
	System.out.println("部署名称:"+deployment.getName());
   }
	/**启动流程定义*/
	@Test
	public void startProcessdefinition() {
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
						                 .taskAssignee(assignee)//制定个人任务查询，指定办理人
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
		String taskid="14";
		processEngine.getTaskService().complete(taskid);
		System.out.println("完成任务id："+taskid);
		
	}
	
	
	
	
	@Test
	public void turnTransition( ) throws Exception {  
		String taskId="2542";
		String activityId="usertask51";
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId); 
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
  
        // 执行转向任务  
    	processEngine.getTaskService().complete(taskId);  
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    	processEngine.getTaskService().complete(taskId);  
    }  
    
    /** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @return 节点流向集合 
     */  
	public List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }  
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
	public void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    } 
    
    /** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     * @param taskId 
     *            任务ID 
     * @param activityId 
     *            活动节点ID <br> 
     *            如果为null或""，则默认查询当前活动节点 <br> 
     *            如果为"end"，则查询结束节点 <br> 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("unused")
    public ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if ("".equals(activityId)||activityId==null) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = processDefinition.findActivity(activityId);  
  
        return activityImpl;  
    }  
  
    
    /** 
     * 根据任务ID获取流程定义 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl)processEngine.getRepositoryService())  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    } 
    
    /** 
     * 根据任务ID获得任务实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    public TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) 	processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  
}
