package sas.randomusers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {
    private ArrayList<User> users;

    public UsersRecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setUser(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userThumbnail;
        private final TextView userFirstName;
        private final TextView userLastName;
        private final TextView userCountry;
        private Button userDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userThumbnail = itemView.findViewById(R.id.user_thumbnail);
            this.userFirstName = itemView.findViewById(R.id.user_first_name);
            this.userLastName = itemView.findViewById(R.id.user_last_name);
            this.userCountry = itemView.findViewById(R.id.user_country);
            this.userDeleteButton = itemView.findViewById(R.id.user_delete);
        }

        public void setUser(User user) {
            Picasso.get().load(user.getPicture().getThumbnail()).into(this.userThumbnail);
            this.userFirstName.setText(user.getName().getFirst());
            this.userLastName.setText(user.getName().getLast());
            this.userCountry.setText(user.getLocation().getCountry());
        }
    }
}