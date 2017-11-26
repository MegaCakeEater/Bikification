package mmmi.sdu.dk.gamification.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import mmmi.sdu.dk.gamification.R;

public class AvatarListAdapter implements ListAdapter {
      private final List<AvatarItem> items;
      private final Context context;
      private final LayoutInflater inflater;
      private DatabaseReference mDatabase;

      public AvatarListAdapter(List<AvatarItem> items, Context context) {
            this.items = items;
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }

      @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
            AvatarHolder holder;
            if (convertView == null) {
                  holder = new AvatarHolder();
                  convertView = inflater.inflate(R.layout.listitem_avatar, parent, false);
                  holder.btn_buy = (Button) convertView.findViewById(R.id.btn_buy);
                  holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
                  holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                  holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

                  convertView.setTag(holder);
            } else {
                  holder = (AvatarHolder) convertView.getTag();
            }

            AvatarItem item = items.get(position);

            if (item != null) {
                  holder.tv_name.setText("Name:\t\t" + item.getName());
                  holder.tv_price.setText("Price:\t\t" + item.getPrice());
                  loadImageFromUrl(item.getImageUrl(), holder.iv_avatar);
                  holder.btn_buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                              buyEquipAvatar(items.get(position), position);
                        }
                  });
                  if (item.isOwned()) {
                        holder.btn_buy.setText("Use as avatar");
                  }
            }

            return convertView;
      }

      @Override
      public boolean areAllItemsEnabled() {
            return false;
      }

      @Override
      public boolean isEnabled(int position) {
            return false;
      }

      @Override
      public void registerDataSetObserver(DataSetObserver observer) {

      }

      @Override
      public void unregisterDataSetObserver(DataSetObserver observer) {

      }

      @Override
      public int getCount() {
            return items.size();
      }

      @Override
      public Object getItem(int position) {
            return items.get(position);
      }

      @Override
      public long getItemId(int position) {
            return position;
      }

      @Override
      public boolean hasStableIds() {
            return true;
      }

      @Override
      public int getItemViewType(int position) {
            return 1;
      }

      @Override
      public int getViewTypeCount() {
            return 1;
      }

      @Override
      public boolean isEmpty() {
            return items.isEmpty();
      }

      private void buyEquipAvatar(AvatarItem item, int position) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = userId.getUid();

            if (!item.isOwned()) {
                  final int[] coins = new int[1];
                  mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                              coins[0] = (int) dataSnapshot.child(uid).child("avatar").child("coins").getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                  });
                  //if coin is sufficient and that the user didn't get it yet
                  if (coins[0] >= item.getPrice() && !item.isOwned()) {

                        //Take off his coin
                        int newCoin = coins[0] - item.getPrice();
                        String newC = Integer.toString(newCoin);
                        mDatabase.child(uid).child("avatar").child("coins").setValue(newC);

                        //Add avatar
                        mDatabase.child(uid).child("avatar").child("avatar" + position).child("have").setValue("true");
                        Toast.makeText(context, "Congratulations! You buy the avatar", Toast.LENGTH_LONG).show();
                        items.get(position).setOwned(true);
                  } else {
                        Toast.makeText(context, "You must have " + (item.getPrice() - coins[0]) + " coins more", Toast.LENGTH_LONG).show();
                  }
            } else {
                  //If the user have already an avatar
                  mDatabase.child(uid).child("avatar").child("currentAvatar").setValue(item.getImageUrl());
                  Toast.makeText(context, "Congratulations! You put this avatar in profile", Toast.LENGTH_LONG).show();

            }

      }

      private void loadImageFromUrl(String url, ImageView imageView) {
            Picasso.with(context).load(url).resize(100,100).into(imageView, new com.squareup.picasso.Callback() {

                  @Override
                  public void onSuccess() {
                  }

                  @Override
                  public void onError() {
                  }
            });
      }


      static class AvatarHolder {
            TextView tv_name;
            TextView tv_price;
            ImageView iv_avatar;
            Button btn_buy;
      }
}
