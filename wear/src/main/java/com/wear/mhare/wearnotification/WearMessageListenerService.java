package com.wear.mhare.wearnotification;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;

/**
 * Created by mphare on 6/18/2015.
 */
public class WearMessageListenerService
{

  private static final String START_ACTIVITY = "/start_activity";

  @Override
  public void onMessageReceived(MessageEvent messageEvent)
  {
    if (messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY))
    {
      Intent intent new Intent(this, WearActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    } else
    {
      super.onMessageReceived(messageEvent);
    }
  }
}
