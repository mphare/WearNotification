package com.wear.mhare.wearnotification;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class WearActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks
{

  private static final String WEAR_MESSAGE_PATH = "/message";

  GoogleApiClient mApiClient;
  private TextView             mTextView;
  private ArrayAdapter<String> mAdapter;

  private ListView mListView;

  /**
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mListView = (ListView) findViewById(R.id.list);
    mAdapter = new ArrayAdapter<String>(this, R.layout.list_item);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    initGoogleApiClient();

  }

  /**
   *
   */
  private void initGoogleApiClient()
  {
    mApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(this).build();

    if (mApiClient != null && !(mApiClient.isConnected() || mApiClient.isConnecting()))
    { mApiClient.connect(); }
  }

  /**
   *
   */
  @Override
  protected void onResume()
  {
    super.onResume();
    if (mApiClient != null && !(mApiClient.isConnected() || mApiClient.isConnecting()))
    { mApiClient.connect(); }

  }

  /**
   *
   */
  @Override
  protected void onStart()
  {
    super.onStart();
  }

  /**
   * @param messageEvent
   */
  @Override
  public void onMessageReceived(final MessageEvent messageEvent)
  {
    runOnUiThread(new Runnable()
    {
      @Override
      public void run()
      {
        if (messageEvent.getPath().equalsIgnoreCase(WEAR_MESSAGE_PATH))
        {
          mAdapter.add(new String(messageEvent.getData()));
          mAdapter.notifyDataSetChanged();

        }
      }
    });
  }

  /**
   * @param bundle
   */
  @Override
  public void onConnected(Bundle bundle)
  {
    Wearable.MessageApi.addListener(mApiClient, this);
  }

  /**
   *
   */
  @Override
  protected void onStop()
  {
    if (mApiClient != null)
    {
      Wearable.MessageApi.removeListener(mApiClient, this);
      if (mApiClient.isConnected())
      {
        mApiClient.disconnect();
      }
    }
    super.onStop();
  }

  /**
   *
   */
  @Override
  protected void onDestroy()
  {
    if (mApiClient != null)
    {
      mApiClient.unregisterConnectionCallbacks(this);
    }
    super.onDestroy();
  }

  /**
   * @param i
   */
  @Override public void onConnectionSuspended(int i)
  {

  }

}
