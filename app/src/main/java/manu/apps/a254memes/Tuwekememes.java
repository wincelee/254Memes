package manu.apps.a254memes;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class Tuwekememes extends Fragment {

    private static final int CHOOSE_IMAGE=1;

    private Button chooseImage, btnUploadImage, memeZaMafans;
    private ImageView memePreview;
    private EditText memeDescription;
    private ProgressBar uploadProgress;

    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    public Tuwekememes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuwekememes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        chooseImage = view.findViewById(R.id.chooseImage);
        memeDescription = view.findViewById(R.id.memeDescription);
        memePreview = view.findViewById(R.id.memePreview);
        uploadProgress = view.findViewById(R.id.uploadProgress);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress())
                {
                    Toast.makeText(getContext(), "Meme yako inaendea mafans", Toast.LENGTH_SHORT).show();
                }else
                {
                    uploadImage();
                }
            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChoose();
            }
        });

        memePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChoose();
            }
        });

    }

    private void showFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData()!=null)
        {
            imgUrl = data.getData();

            Glide.with(this).load(imgUrl).into(memePreview);
        }
    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        if (imgUrl!=null)
        {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Upload upload = new Upload(memeDescription.getText().toString().trim(),uri.toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);


                                    Toast.makeText(getContext(), "Meme imefikia mafans", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Walikua wanalamba lolo", Toast.LENGTH_SHORT).show();
                                    memeDescription.setText("");

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int)progress);
                        }
                    });
        }else
        {
            Toast.makeText(getContext(), "iza maze, hujachagulia mafans meme", Toast.LENGTH_SHORT).show();
        }
    }
}
