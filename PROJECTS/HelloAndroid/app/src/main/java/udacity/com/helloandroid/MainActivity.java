package udacity.com.helloandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startBrowser(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.udacity.com"));
        startActivity(intent);
    }

    public void startCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:650-555-5555"));
        startActivity(intent);
    }
}
