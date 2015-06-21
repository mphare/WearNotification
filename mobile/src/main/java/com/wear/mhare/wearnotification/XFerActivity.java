package com.wear.mhare.wearnotification;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Date;

public class XFerActivity extends Activity implements GoogleApiClient.ConnectionCallbacks
{
  GoogleApiClient mApiClient;
  public static final String START_ACTIVITY    = "/start_activity";
  public static final String WEAR_MESSAGE_PATH = "/message";
  private ArrayAdapter<String> mAdapter;
  private Button               mSendButton;
  private EditText             mEditText;
  private ListView             mListView;

  /**
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_xfer);

    init();
    initGoogleApiClient();

  }

  /**
   *
   */
  private void initGoogleApiClient()
  {
    mApiClient = new GoogleApiClient.Builder(this)
        .addApi(Wearable.API)
        .build();

    mApiClient.connect();
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

  private void init()
  {
    mSendButton = (Button) findViewById(R.id.btn_send);
    mEditText = (EditText) findViewById(R.id.input);
    mListView = (ListView) findViewById(R.id.list_view);

    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    mListView.setAdapter(mAdapter);

    mSendButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        String text = mEditText.getText().toString();
        if (!TextUtils.isEmpty(text))
        {
          mAdapter.add(text);
          mAdapter.notifyDataSetChanged();
//          sendMessage(WEAR_MESSAGE_PATH, text);

          Date myDate = new Date();
          ItemDataModel itemEntry = new ItemDataModel("My Name", "My Type", myDate);
          sendMessageItem(WEAR_MESSAGE_PATH, itemEntry);
        }
      }
    });

  }

  /**
   * @param path
   * @param text
   */
  private void sendMessage(final String path, final String text)
  {
    new Thread(new Runnable()
    {
      @Override public void run()
      {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();

        for (Node node : nodes.getNodes())
        {
          MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), path, text
              .getBytes()).await();
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

  private void sendMessageItem(final String path, final ItemDataModel itemDataModel)
  {

  }

  /**
   * @param bundle
   */
  @Override
  public void onConnected(Bundle bundle)
  {

    sendMessage(START_ACTIVITY, "Hello Message");
  }

  @Override
  public void onConnectionSuspended(int i)
  {

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_xfer, menu);
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
