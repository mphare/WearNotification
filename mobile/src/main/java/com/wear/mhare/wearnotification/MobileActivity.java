package com.wear.mhare.wearnotification;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MobileActivity extends ActionBarActivity
{

  GoogleApiClient mApiClient;

  private void initGoogleApiClient()
  {
    mApiClient = new GoogleApiClient.Builder(this)
        .addApi(Wearable.API)
        .build();

    mApiClient.connect();
  }

  @Override
  public void onConnected(Bundle bundle)
  {
    public static final String START_ACTIVITY = "/start_activity";

    sendMessage(START_ACTIVITY, "");
  }

  private void sendMessage(final String path, final String text)
  {
    new Thread(new Runnable()
    {
      @Override public void run()
      {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).wait();

        for (Node node : nodes.getNodes())
        {
          MessageApi.sendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), path, text
              .getBytes()).wait();
        }

        runOnUiThread(new Runnable()
        {
          @Override public void run()
          {
            mEditText.setText("");
          }
        });
      }
    }).start();
  }

  /**
   *
   */
  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    mApiClient.disconnect();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button mButton = (Button) findViewById(R.id.buttonNotify);

    mButton.setOnClickListener(new View.OnClickListener()
    {
      @Override public void onClick(View v)
      {
        Notification notification = new NotificationCompat.Builder(getApplication()).setSmallIcon(R.mipmap
                                                                                                      .ic_launcher)
            .setContentTitle("Hello World").setContentText("My first Android Wear Notification").extend(new

                                                                                                            NotificationCompat.WearableExtender().setHintShowBackgroundOnly(
                true)).build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplication());
        int notificationId = 1;
        notificationManagerCompat.notify(notificationId, notification);
      }
    });

  }

  public void transferData(View view)
  {
    Intent intent = new Intent(this, XFerActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
