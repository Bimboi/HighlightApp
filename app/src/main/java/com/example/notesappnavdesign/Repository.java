package com.example.notesappnavdesign;

import android.app.Application;
import android.content.ClipData;
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

    public void insertTask(ItemTask itemTask){
        new InsertItemTaskAsync(taskDao).execute(itemTask);
    }

    public void updateTask(ItemTask itemTask){
        new UpdateItemTaskAsync(taskDao).execute(itemTask);
    }

    public void deleteTask(ItemTask itemTask){
        new DeleteItemTaskAsync(taskDao).execute(itemTask);
    }

    public void deleteTaskById(Integer id){
        new DeleteItemTaskByIdAsync(taskDao).execute(id);
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
