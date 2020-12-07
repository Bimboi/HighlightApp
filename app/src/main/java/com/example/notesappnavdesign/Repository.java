package com.example.notesappnavdesign;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private TaskDao taskDao;
    private LiveData<List<ItemTask>> allTask, allToday, allImportant, allOverdue;

    public Repository(Application application){
        TasksDatabase database = TasksDatabase.getInstance(application);
        taskDao = database.getTaskDao();
        allTask = taskDao.getAllTask();
        allToday = taskDao.getTodayTask();
        allImportant = taskDao.getImportantTask();
        allOverdue = taskDao.getOverdueTask();
    }

    public void insertTask(ItemTask itemTask){
        new InsertItemTaskAsync(taskDao).execute(itemTask);
    }

    public void updateTask(ItemTask itemTask){
        new UpdateItemTaskAsync(taskDao).execute(itemTask);
    }

    public void deleteTaskById(Integer id){
        new DeleteItemTaskByIdAsync(taskDao).execute(id);
    }

    public LiveData<List<ItemTask>> getAllTask(){
        return allTask;
    }

    public LiveData<List<ItemTask>> getAllToday(){
        return allToday;
    }

    public LiveData<List<ItemTask>> getAllImportant(){
        return allImportant;
    }

    public LiveData<List<ItemTask>> getAllOverdue(){
        return allOverdue;
    }

    private static class InsertItemTaskAsync extends AsyncTask<ItemTask, Void, Void>{
        private TaskDao taskDao;

        private InsertItemTaskAsync(TaskDao taskDao){
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(ItemTask... itemTask) {
            taskDao.insertTask(itemTask[0]);
            return null;
        }
    }

    private static class UpdateItemTaskAsync extends AsyncTask<ItemTask, Void, Void>{
        private TaskDao taskDao;

        private UpdateItemTaskAsync(TaskDao taskDao){
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(ItemTask... itemTask) {
            taskDao.updateTask(itemTask[0]);
            return null;
        }
    }

    private static class DeleteItemTaskByIdAsync extends AsyncTask<Integer, Void, Void> {
        private TaskDao taskDao;

        private DeleteItemTaskByIdAsync(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            taskDao.deleteById(params[0]);
            return null;
        }
    }

}
