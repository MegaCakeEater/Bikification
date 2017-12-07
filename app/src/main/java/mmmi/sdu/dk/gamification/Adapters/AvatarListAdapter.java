package mmmi.sdu.dk.gamification.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import mmmi.sdu.dk.gamification.R;
import mmmi.sdu.dk.gamification.model.User;
import mmmi.sdu.dk.gamification.utils.DatabaseFacade;

public class AvatarListAdapter implements ListAdapter {
      private final List<AvatarItem> items;
      private final Context context;
      private final LayoutInflater inflater;

      public AvatarListAdapter(List<AvatarItem> items, Context context) {
            this.items = items;
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }

      @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
            final AvatarHolder holder;
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
                              holder.btn_buy.setText(buyEquipAvatar(items.get(position), position));
                        }
                  });
                  holder.btn_buy.setText("Get it!");
                  if (item.isOwned()) {
                        holder.btn_buy.setText("Use as avatar");
                  }
                  if (DatabaseFacade.getInstance().getUser().getCurrentAvatar() == position) {
                        holder.btn_buy.setText("Currently using");
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

      private String buyEquipAvatar(AvatarItem item, int position) {
            User user = DatabaseFacade.getInstance().getUser();
            String btnText = "Get it!";
            if (!item.isOwned()) {
                  //if coin is sufficient and that the user didn't get it yet
                  if (user.getCoins() >= item.getPrice() && !item.isOwned()) {

                        //Take off his coin
                        user.setCoins(user.getCoins() - item.getPrice());
                        user.getAvatarsOwned().add(position);
                        user.setCurrentAvatar(position);
                        Toast.makeText(context, "Congratulations! You buy the avatar", Toast.LENGTH_LONG).show();
                        items.get(position).setOwned(true);
                        DatabaseFacade.getInstance().updateUser(user);
                        btnText = "Use as avatar";
                  } else {
                        Toast.makeText(context, "You must have " + (item.getPrice() - user.getCoins()) + " coins more", Toast.LENGTH_LONG).show();
                  }
            } else {
                  //If the user have already an avatar
                  user.setCurrentAvatar(position);
                  Toast.makeText(context, "Congratulations! You put this avatar in profile", Toast.LENGTH_LONG).show();
                  DatabaseFacade.getInstance().updateUser(user);
                  btnText = "Currently Using";
            }
            return btnText;
      }

      private void loadImageFromUrl(String url, ImageView imageView) {
            Picasso.with(context).load(url).resize(100, 100).into(imageView);
      }


      static class AvatarHolder {
            TextView tv_name;
            TextView tv_price;
            ImageView iv_avatar;
            Button btn_buy;
      }
}
