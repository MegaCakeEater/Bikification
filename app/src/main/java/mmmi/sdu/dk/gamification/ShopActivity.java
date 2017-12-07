package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import mmmi.sdu.dk.gamification.Adapters.AvatarItem;
import mmmi.sdu.dk.gamification.Adapters.AvatarListAdapter;
import mmmi.sdu.dk.gamification.model.Avatar;
import mmmi.sdu.dk.gamification.utils.DatabaseFacade;

public class ShopActivity extends Activity {

    TextView coin;
    ListView lv_avatars;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Typeface disney = Typeface.createFromAsset(getAssets(), "fonts/waltographUI.ttf");
        TextView tx = (TextView) findViewById(R.id.shopTxt);
        tx.setTypeface(disney);

        coin = (TextView) findViewById(R.id.coinTxt);
        coin.setText("" + DatabaseFacade.getInstance().getUser().getCoins());
        //Redirection home
        findViewById(R.id.homeButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectHome();
            }
        });
        lv_avatars = (ListView) findViewById(R.id.lv_avatars);
        lv_avatars.setAdapter(new AvatarListAdapter(getAvatars(), getApplicationContext()));
    }


    private void redirectHome() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    private ArrayList<AvatarItem> getAvatars() {
        ArrayList<AvatarItem> items = new ArrayList();
        for (Avatar avatar : DatabaseFacade.getInstance().getAvatars()) {
            items.add(new AvatarItem(avatar, getOwned(avatar.getId())));
        }
        return items;
    }

    private boolean getOwned(int id) {
        return DatabaseFacade.getInstance().getUser().getAvatarsOwned().contains(id);

    }
}