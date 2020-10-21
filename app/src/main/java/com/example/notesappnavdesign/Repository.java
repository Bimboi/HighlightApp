package com.example.notesappnavdesign;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private ListDao listDao;
    private ListDatabase database;
    private LiveData<List<ItemList>> allList;

    public Repository(Application application){
        database = ListDatabase.getInstance(application);
        listDao = database.getListDao();
        allList = listDao.getAllList();
    }

    public void insertList(ItemList itemList){
        new InsertItemListAsync(listDao).execute(itemList);
    }

    public void updateList(ItemList itemList){
        new UpdateItemListAsync(listDao).execute(itemList);
    }

    public void deleteList(ItemList itemList){
        new DeleteItemListAsync(listDao).execute(itemList);
    }

    public LiveData<List<ItemList>> getAllList(){
        return allList;
    }
    private static class InsertItemListAsync extends AsyncTask<ItemList, Void, Void>{
        private ListDao listDao;

        private InsertItemListAsync(ListDao listDao){
            this.listDao = listDao;
        }
        @Override
        protected Void doInBackground(ItemList... itemList) {
            listDao.insertList(itemList[0]);
            return null;
        }
    }
    private static class UpdateItemListAsync extends AsyncTask<ItemList, Void, Void>{
        private ListDao listDao;

        private UpdateItemListAsync(ListDao listDao){
            this.listDao = listDao;
        }
        @Override
        protected Void doInBackground(ItemList... itemList) {
            listDao.updateList(itemList[0]);
            return null;
        }
    }
    private static class DeleteItemListAsync extends AsyncTask<ItemList, Void, Void> {
        private ListDao listDao;

        private DeleteItemListAsync(ListDao listDao) {
            this.listDao = listDao;
        }

        @Override
        protected Void doInBackground(ItemList... itemList) {
            listDao.deleteList(itemList[0]);
            return null;
        }
    }
}
