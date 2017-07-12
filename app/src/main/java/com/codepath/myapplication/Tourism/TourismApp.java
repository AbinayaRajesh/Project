package com.codepath.myapplication.Tourism;

import android.app.Application;
import android.content.Context;

import com.codepath.myapplication.Event.EventBriteClient;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApp.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TourismApp extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();

		// FlowManager.init(new FlowConfig.Builder(this).build());
		// FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

		TourismApp.context = this;
	}

	public static TourismClient getTourismClient() {
		return (TourismClient) TourismClient.getInstance(TourismClient.class, TourismApp.context);
	}

//	public static FoodClient getFoodClient() {
//		return (FoodClient) FoodClient.getInstance(FoodClient.class, TourismApp.context);
//	}

	public static EventBriteClient getEventClient() {
		return (EventBriteClient) EventBriteClient.getInstance(EventBriteClient.class, TourismApp.context);
	}
}

