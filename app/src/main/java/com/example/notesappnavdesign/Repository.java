package com.example.notesappnavdesign;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private TaskDao taskDao;
    private TasksDatabase database;
    private LiveData<List<ItemTask>> allTask;

    public Repository(Application application){
        database = TasksDatabase.getInstance(application);
        taskDao = database.getTaskDao();
        allTask = taskDao.getAllTask();
    }

    public void insertList(ItemTask itemTask){
        new InsertItemTaskAsync(taskDao).execute(itemTask);
    }

    public void updateList(ItemTask itemTask){
        new UpdateItemTaskAsync(taskDao).execute(itemTask);
    }

    public void deleteList(ItemTask itemTask){
        new DeleteItemTaskAsync(taskDao).execute(itemTask);
    }

    public LiveData<List<ItemTask>> getAllTask(){
        return allTask;
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
    private static class DeleteItemTaskAsync extends AsyncTask<ItemTask, Void, Void> {
        private TaskDao taskDao;

        private DeleteItemTaskAsync(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(ItemTask... itemTask) {
            taskDao.deleteTask(itemTask[0]);
            return null;
        }
    }
}
