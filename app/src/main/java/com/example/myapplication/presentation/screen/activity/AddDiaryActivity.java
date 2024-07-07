package com.example.myapplication.presentation.screen.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.DiaryDao;
import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.data.repository.DiaryRepositoryImpl;
import com.example.myapplication.databinding.ActivityAddDiaryBinding;
import com.example.myapplication.presentation.screen.viewmodel.AddDiaryViewModel;
import com.example.myapplication.presentation.util.DateTimeUtil;
import com.example.myapplication.presentation.util.ImageUtil;
import com.example.myapplication.presentation.util.StringUtils;
import com.example.myapplication.presentation.util.UserPreferencesUtil;

import java.io.IOException;
import java.util.List;

public class AddDiaryActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityAddDiaryBinding binding;
    private String imagePath;
    private AddDiaryViewModel viewModel;
    private boolean isUpdateDiary = false;
    private String currentDate;
    private int currentId;
    private ActivityResultLauncher<String[]> requestPermissions = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (permissions.containsKey(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (permissions.get(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Permission granted, handle accordingly
                        selectedImage();
                    } else {
                        // Permission denied, handle accordingly
                        selectedImage();
                    }
                } else if (permissions.containsKey(Manifest.permission.READ_MEDIA_IMAGES) ||
                        permissions.containsKey(Manifest.permission.READ_MEDIA_VIDEO) ||
                        permissions.containsKey(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)) {
                    // Handle multiple media permissions accordingly
                    boolean mediaPermissionGranted = true;
                    for (String permission : permissions.keySet()) {
                        if (!permissions.get(permission)) {
                            mediaPermissionGranted = false;
                            break;
                        }
                    }
                    if (mediaPermissionGranted) {
                        selectedImage();
                    } else {
                        selectedImage();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddDiaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                DiaryDao diaryDao = AppDatabase.getDatabase(AddDiaryActivity.this).diaryDao();
                DiaryRepositoryImpl diaryRepository = new DiaryRepositoryImpl(diaryDao); // 实例化 UserRepositoryImpl
                return (T) new AddDiaryViewModel(diaryRepository);
            }
        }).get(AddDiaryViewModel.class);

        initViews();
    }

    private void initViews() {

        if (UserPreferencesUtil.getInstance(this).isUserLoggedIn()) {
            String types = UserPreferencesUtil.getInstance(this).getCurrentUser().types;
            List<String> strings = StringUtils.parseTypesToList(types);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerType.setAdapter(adapter);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("diaryModel")) {
            DiaryModel diaryModel = (DiaryModel) getIntent().getSerializableExtra("diaryModel");
            isUpdateDiary = true;
            // 使用传递的数据进行操作
            if (diaryModel != null) {
                binding.titleContent.setText(diaryModel.diaryName);
                binding.diaryInputContent.setText(diaryModel.diaryContent);
                currentId = diaryModel.id;
                currentDate = diaryModel.date;

                if (diaryModel.imagePath != null && !diaryModel.imagePath.isEmpty()) {
                    imagePath = diaryModel.imagePath;
                    binding.imageContent.setImageURI(Uri.parse(diaryModel.imagePath));
                    binding.imageContent.setVisibility(View.VISIBLE);
                }

                // 获取 classify 对应的 Spinner 选项位置
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) binding.spinnerType.getAdapter();
                if (adapter != null) {
                    int position = adapter.getPosition(diaryModel.classify);
                    if (position >= 0) {
                        binding.spinnerType.setSelection(position);
                    }
                }
            }
        }

        binding.saveDiaryButton.setOnClickListener(v -> saveDiary());
        binding.imageSelect.setOnClickListener(v -> requestPermissions());
    }

    private void selectedImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            });
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            });
        } else {
            requestPermissions.launch(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        }
    }

    private void saveDiary() {
        String titleContent = binding.titleContent.getText().toString();
        String diaryContent = binding.diaryContent.getEditText().getText().toString();

        if (titleContent.trim().isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }

        if (diaryContent.trim().isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        DiaryModel diaryModel = new DiaryModel();
        diaryModel.diaryName = titleContent;
        diaryModel.diaryContent = diaryContent;
        diaryModel.classify = binding.spinnerType.getSelectedItem().toString();
        diaryModel.date = currentDate != null ? currentDate : DateTimeUtil.getCurrentDateTime();
        diaryModel.imagePath = imagePath;
        diaryModel.userId = UserPreferencesUtil.getInstance(this).getCurrentUser().id;

        if (isUpdateDiary) {
            diaryModel.id = currentId;
            viewModel.updateDiary(diaryModel, this::handleUpdateResult);
        } else {
            viewModel.saveDiary(diaryModel, this::handleUpdateResult);
        }
    }

    private void handleUpdateResult(boolean success) {
        if (success) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imagePath = ImageUtil.saveImageToLocal(this, bitmap);
                Log.d("AddDiaryActivity", "imagepath = " + imageUri);
                if (imagePath != null) {
                    binding.imageContent.setImageURI(Uri.parse(imagePath));
                    binding.imageContent.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

}