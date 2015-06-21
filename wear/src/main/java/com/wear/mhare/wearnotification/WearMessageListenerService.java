package com.wear.mhare.wearnotification;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by mphare on 6/18/2015.
 */
public class WearMessageListenerService extends WearableListenerService
{

  private static final String START_ACTIVITY    = "/start_activity";
  private static final String WEAR_MESSAGE_PATH = "/message";

  @Override
  public void onMessageReceived(MessageEvent messageEvent)
  {
    if (messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY))
    {
      Intent intent = new Intent(this, WearActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    } else if (messageEvent.getPath().equalsIgnoreCase(WEAR_MESSAGE_PATH))
    {
    } else
    {
      super.onMessageReceived(messageEvent);
    }
  }
}
