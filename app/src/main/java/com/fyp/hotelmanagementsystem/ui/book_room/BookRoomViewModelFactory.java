package com.fyp.hotelmanagementsystem.ui.book_room;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class BookRoomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BookRoomListener listener;
    private AppDatabase database;
    private Executor executor;

    public BookRoomViewModelFactory(BookRoomListener listener, AppDatabase database, Executor executor) {
        this.listener = listener;
        this.database = database;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookRoomViewModel(listener, database, executor);
    }
}
