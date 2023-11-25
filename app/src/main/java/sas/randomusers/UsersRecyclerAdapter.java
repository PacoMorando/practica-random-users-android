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
import java.util.HashMap;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {
    private final ArrayList<User> originalUsers;
    private final ArrayList<User> sortedUsers;
    private final ArrayList<User> unsortedUsers;
    private ArrayList<User> usersToShow;
    private final HashMap<String, Boolean> sortState;
    private CharSequence inputFilter = "";

    public UsersRecyclerAdapter(ArrayList<User> originalUsers) {
        this.originalUsers = new ArrayList<>(originalUsers);
        this.sortedUsers = new ArrayList<>(originalUsers);
        this.unsortedUsers = new ArrayList<>(originalUsers);
        this.usersToShow = new ArrayList<>(originalUsers);
        this.sortState = new HashMap<>();
        this.setUnsortedSortState();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setUser(this.usersToShow.get(position));
    }

    @Override
    public int getItemCount() {
        return usersToShow.size();
    }

    private void setUsersToShow() {
        if (Boolean.TRUE.equals(this.sortState.get("isSorted"))) {
            this.usersToShow = this.getUsersFiltered(this.sortedUsers);
        } else {
            this.setUnsortedSortState();
            this.usersToShow = this.getUsersFiltered(this.unsortedUsers);
        }
        this.notifyDataSetChanged();
    }

    public void sortUsersByCountry() {
        if (Boolean.FALSE.equals(this.sortState.get("byCountry"))) {
            this.sortedUsers.sort(Comparator.comparing(User::getCountry));
            this.setUnsortedSortState();
            this.sortState.put("byCountry", true);
            this.sortState.put("isSorted", true);
            this.setUsersToShow();
        } else {
            this.setUnsortedSortState();
            this.setUsersToShow();
        }
    }

    public void sortUsersByFirstName() {
        if (Boolean.FALSE.equals(this.sortState.get("byFirstName"))) {
            this.sortedUsers.sort(Comparator.comparing(User::getFirstName));
            this.setUnsortedSortState();
            this.sortState.put("byFirstName", true);
            this.sortState.put("isSorted", true);
            this.setUsersToShow();
        } else {
            this.setUnsortedSortState();
            this.setUsersToShow();
        }
    }

    public void sortUsersByLastName() {
        if (Boolean.FALSE.equals(this.sortState.get("byLastName"))) {
            this.sortedUsers.sort(Comparator.comparing(User::getLastName));
            this.setUnsortedSortState();
            this.sortState.put("byLastName", true);
            this.sortState.put("isSorted", true);
            this.setUsersToShow();
        } else {
            this.setUnsortedSortState();
            this.setUsersToShow();
        }
    }

    public void resetUsers() {
        this.unsortedUsers.clear();
        this.unsortedUsers.addAll(this.originalUsers);
        this.sortedUsers.clear();
        this.sortedUsers.addAll(this.originalUsers);
        this.setUnsortedSortState();
        this.setUsersToShow();
    }

    private void deleteUser(User user, int position) {
        if (!(position < 0)) { //TODO HAY UN ERROR AL ELEMINAR USUARIOS MIENTRAS ESTA FILTRADO
            this.unsortedUsers.remove(user);
            this.sortedUsers.remove(user);
            notifyItemRemoved(position);
        }
    }

    public boolean getSortByCountryState() {
        return Boolean.TRUE.equals(this.sortState.get("byCountry"));
    }

    private ArrayList<User> getUsersFiltered(ArrayList<User> users) {
        ArrayList<User> filteredUsers = new ArrayList<>(users);
        filteredUsers.removeIf(user -> !user.getCountry().toLowerCase().contains(this.inputFilter));
        return filteredUsers;
    }

    private void setUnsortedSortState() {
        this.sortState.put("isSorted", false);
        this.sortState.put("byCountry", false);
        this.sortState.put("byFirstName", false);
        this.sortState.put("byLastName", false);
    }

    public void setInputFilter(CharSequence inputFilter){
        this.inputFilter = inputFilter;
        this.setUsersToShow();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
        }

        public void setUser(User user) {
            Picasso.get().load(user.getPicture().getThumbnail()).into(this.userThumbnail);
            this.userFirstName.setText(user.getName().getFirst());
            this.userLastName.setText(user.getName().getLast());
            this.userCountry.setText(user.getLocation().getCountry());
            this.userDeleteButton.setOnClickListener((view) -> deleteUser(user, getAdapterPosition()));
        }
    }
}