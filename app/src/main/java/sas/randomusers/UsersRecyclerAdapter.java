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
import java.util.Comparator;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {
    private final ArrayList<User> originalUsers;
    private ArrayList<User> sortedUsers;
    private ArrayList<User> users;

    public UsersRecyclerAdapter(ArrayList<User> originalUsers) {
        this.originalUsers = new ArrayList<>(originalUsers);
        this.sortedUsers = new ArrayList<>(originalUsers);
        this.users = new ArrayList<>(originalUsers);
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
        holder.userDeleteButton.setOnClickListener((view) -> {
            System.out.println(position + " POSITION");
            System.out.println(holder.getAdapterPosition() + " HOLDER POSITION");
            this.users.remove(holder.getAdapterPosition());
            this.sortedUsers.remove(holder.getAdapterPosition());
            this.notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void sortUsersByCountry() {
        this.sortedUsers.sort(Comparator.comparing(User::getCountry));
        this.users.clear();
        this.users.addAll(this.sortedUsers);
        this.notifyDataSetChanged();
    }

    public void resetUsers() {
        this.users.clear();
        this.users.addAll(this.originalUsers);
        this.sortedUsers.clear();
        this.sortedUsers.addAll(this.originalUsers);
        this.notifyDataSetChanged();
    }

    public void sortUsersByFirstName() {
        this.sortedUsers.sort(Comparator.comparing(User::getFirstName));
        this.users.clear();
        this.users.addAll(this.sortedUsers);
        this.notifyDataSetChanged();
    }

    public void sortUsersByLastName() {
        this.sortedUsers.sort(Comparator.comparing(User::getLastName));
        this.users.clear();
        this.users.addAll(this.sortedUsers);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userThumbnail;
        private final TextView userFirstName;
        private final TextView userLastName;
        private final TextView userCountry;
        private final Button userDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userThumbnail = itemView.findViewById(R.id.user_thumbnail);
            this.userFirstName = itemView.findViewById(R.id.user_first_name);
            this.userLastName = itemView.findViewById(R.id.user_last_name);
            this.userCountry = itemView.findViewById(R.id.user_country);
            this.userDeleteButton = itemView.findViewById(R.id.user_delete);
           // this.setUserDeleteButton();
        }

        public void setUser(User user) {
            Picasso.get().load(user.getPicture().getThumbnail()).into(this.userThumbnail);
            this.userFirstName.setText(user.getName().getFirst());
            this.userLastName.setText(user.getName().getLast());
            this.userCountry.setText(user.getLocation().getCountry());
        }

    /*    private void setUserDeleteButton() {
            this.userDeleteButton.setOnClickListener((view) -> {
                users.remove(this.getAdapterPosition());
            });
        }*/
    }
}