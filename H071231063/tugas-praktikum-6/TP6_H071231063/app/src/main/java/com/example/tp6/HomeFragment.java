package com.example.tp6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp6.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserAdapter userAdapter;
    private int currentPage = 1;
    private boolean isLoading = false;
    private AppDatabase db;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "user_db").allowMainThreadQueries().build();
        userAdapter = new UserAdapter(new ArrayList<>());
        binding.userRecycler.setAdapter(userAdapter);
        userAdapter.setOnUserClickListener(user -> {
            int characterId = user.getId();
            DetailFragment detailFragment = DetailFragment.newInstance(characterId);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.refresh.setOnClickListener(v -> {
            loadUsers(currentPage);
        });

        loadUsers(currentPage);

        userAdapter.setOnLoadMoreClickListener(() -> {
            if (!isLoading) {
                currentPage++;
                loadUsers(currentPage);
            }
        });

        return binding.getRoot();
    }

    private void loadUsers(int page) {
        AppDatabase db = AppDatabase.getDatabase(requireContext());
        UserDao userDao = db.userDao();

        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            List<UserEntity> localUsers = userDao.getAllUsers();
            if (!localUsers.isEmpty()) {
                List<User> offlineUsers = UserMapper.fromEntityList(localUsers);
                userAdapter.clearUsers();
                userAdapter.addMoreUsers(offlineUsers);
                userAdapter.setShowLoadMore(false);
                userAdapter.setShowLoadMore(true);
                showError(false, null);
            } else {
                showError(true, "Tidak ada koneksi dan tidak ada data lokal.");
            }
            return;
        }

        showError(false, null);
        isLoading = true;
        binding.pb.setVisibility(View.VISIBLE);

        ApiService apiService = ApiConfig.getClient().create(ApiService.class);
        apiService.getCharacterData(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                isLoading = false;
                binding.pb.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<User> newUsers = response.body().getResults();
                    if (page == 1) userAdapter.clearUsers();
                    userAdapter.addMoreUsers(newUsers);
                    userAdapter.setShowLoadMore(!newUsers.isEmpty());

                    // Simpan ke Room
                    List<UserEntity> entities = UserMapper.toEntityList(newUsers);
                    userDao.insertAll(entities);
                } else {
                    showError(true, "Gagal memuat data.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;
                binding.pb.setVisibility(View.GONE);
                showError(true, "Gagal memuat data: " + t.getMessage());
            }
        });
    }



    private void showError(boolean show, String message) {
        binding.errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.userRecycler.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.pesan.setText(message != null ? message : "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}