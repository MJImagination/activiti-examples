package com.hesc.urp.group2;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@SuppressWarnings("serial")
public class Task implements TaskListener {
    /**用来指定任务的办理人*/
	@Override
	public void notify(DelegateTask delegateTask) {
		//指定个人入伍的办理人，也可以指定组任务的办理人
		//个人任务：通过类去查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee()方法指定任务的办理人
		//delegateTask.setAssignee("张小飞");
		//组任务
		delegateTask.addCandidateUser("武警");
		delegateTask.addCandidateUser("慕斯");
	}

}
