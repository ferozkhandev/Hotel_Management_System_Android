package com.fyp.hotelmanagementsystem.ui.give_rating_to_user;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Rating;

import java.util.concurrent.Executor;

public class GiveRatingToUserViewModel extends ViewModel {

    private AppDatabase database;
    private Executor executor;

    public GiveRatingToUserViewModel(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public void saveRating(Rating rating) {
        executor.execute(()-> database.ratingDAO().insert(rating));
    }

}
