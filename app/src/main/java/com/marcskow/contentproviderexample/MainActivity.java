package com.marcskow.contentproviderexample;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

  private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 314;
  private ListView contactsListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    contactsListView = findViewById(R.id.contactsListView);

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_CONTACTS},
          PERMISSIONS_REQUEST_READ_CONTACTS);
    } else {
      getLoaderManager().initLoader(0, null, new CustomLoader());
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[],
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case PERMISSIONS_REQUEST_READ_CONTACTS: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getLoaderManager().initLoader(0, null, new CustomLoader());
        } else {
          Toast.makeText(this,
              "Unfortunatelly this app core functionality needs READ_CONTACTS permissions",
              Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  private class CustomLoader implements LoaderCallbacks<Cursor> {

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      // Return CursorLoader here with Contacts._ID, Contacts.DISPLAY_NAME data.
      // If you don't remember content URI of Contacts, check Android Developers page.
      return null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      // Create SimpleCursorAdapter here and set it on contactsListView
      contactsListView.setAdapter(null);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
      // Leave this line as setAdapter(null)
      contactsListView.setAdapter(null);
    }
  }
}