package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_OK = 1;
    private static final int RESULT_LOAD_IMAGE = 2;

    ImageView photo_image;
    ImageButton camera, upload, add;

    EditText heading, description;

    public static ArrayList<NotesData> list = new ArrayList<>();

    ListView photo_list;
    Context context;

    ArrayList<Bitmap> photo_array = new ArrayList<>(10);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        photo_image = view.findViewById(R.id.photo);
        camera = view.findViewById(R.id.camera_button);
        upload = view.findViewById(R.id.file_upload_button);
        add = view.findViewById(R.id.add_button);

        heading = view.findViewById(R.id.title_heading);
        description = view.findViewById(R.id.description);

        photo_list = view.findViewById(R.id.photo_list);

        TextInputLayout title_l = view.findViewById(R.id.filled_title);
        TextInputLayout desc_l = view.findViewById(R.id.filled_description);


        ConstraintLayout main = view.findViewById(R.id.main_layout);
        LottieAnimationView animationView = view.findViewById(R.id.animationView);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String header_text = heading.getText().toString();
                String description_text = description.getText().toString();
                int flag = 0;

                if (header_text.equals("")) {
                    Toast.makeText(getActivity(), "Enter a title", Toast.LENGTH_SHORT).show();
                    flag = 1;
                    title_l.setError("Set a title");

                } else {
                    title_l.setErrorEnabled(false);
                }

                if (description_text.equals("")) {
                    Toast.makeText(getActivity(), "Enter the description", Toast.LENGTH_SHORT).show();
                    desc_l.setError("Provide a description");
                    flag = 1;

                } else {
                    desc_l.setErrorEnabled(false);
                }


                if (flag == 0) {

                    list.add(new NotesData(header_text, description_text, (int) photo_image.getAlpha()));

                    main.setVisibility(View.GONE);
                    animationView.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            main.setVisibility(View.VISIBLE);
                            animationView.setVisibility(View.GONE);

                            heading.setText("");
                            description.setText("");
                        }
                    }, 2000);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", "picturePath1");

        if (requestCode == CAMERA_REQUEST) {
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            if (photo_array.size() == 10) {
                Toast.makeText(getActivity(), "Maximum number of photos reached!", Toast.LENGTH_SHORT).show();
            } else {
                photo_array.add(photo);
                photo_image.setImageBitmap(photo);

//                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, photo_array);
//                photo_list.setAdapter(arrayAdapter);
            }
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Log.d("TAG", "picturePath2");

            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
            Uri selectedImage = data.getData();

            Log.d("TAG", "picturePath3");

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = requireActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Toast.makeText(getActivity(), picturePath, Toast.LENGTH_SHORT).show();
            cursor.close();


            Log.d("TAG", picturePath);
            photo_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}