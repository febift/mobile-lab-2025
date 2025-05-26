package com.example.instagram;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class PostFragment extends Fragment {
    private static final int PICK_IMG_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView photoToPost;
    private EditText captionInput;
    private Button btnSubmit;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        photoToPost = view.findViewById(R.id.photoToPost);
        captionInput = view.findViewById(R.id.caption_input);
        btnSubmit = view.findViewById(R.id.btn_submit);

        photoToPost.setOnClickListener(v -> openImageChooser());
        btnSubmit.setOnClickListener(v -> submitPost());

        captionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            photoToPost.setImageURI(selectedImageUri);
            validateInputs();
        }
    }

    private void validateInputs() {
        btnSubmit.setEnabled(!captionInput.getText().toString().isEmpty() && selectedImageUri != null);
    }


    private void submitPost() {
        FeedProfile newPost = new FeedProfile(
                selectedImageUri.toString(),
                captionInput.getText().toString(),
                 "300", "250","150"

        );
        DataDummy.feedProfiles.add(0, newPost);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).notifyProfileAdapter();
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }
}