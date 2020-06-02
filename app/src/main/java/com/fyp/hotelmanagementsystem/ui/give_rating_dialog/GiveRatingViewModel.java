package com.fyp.hotelmanagementsystem.ui.give_rating_dialog;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Rating;

import java.util.concurrent.Executor;

public class GiveRatingViewModel extends ViewModel {

    private AppDatabase database;
    private Executor executor;

    public GiveRatingViewModel(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public void saveRating(Rating rating) {
        executor.execute(()-> database.ratingDAO().insert(rating));
    }
}
