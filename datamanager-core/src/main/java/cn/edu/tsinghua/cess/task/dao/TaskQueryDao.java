package cn.edu.tsinghua.cess.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;

/**
 * Created by kurt on 2014/9/22.
 */
public interface TaskQueryDao {

    public List<SubTaskListEntry> querySubTaskList(@Param("taskId") Integer taskId);

    public SubTask querySubTaskResult(@Param("subTaskId") Integer subTaskId);

}
