package com.instagram.downloader.Views.Activities;


import static com.instagram.downloader.Utils.DirectoryUtils.shareImage;
import static com.instagram.downloader.Utils.DirectoryUtils.shareVideo;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.instagram.downloader.Adapters.PreviewAdapter;
import com.instagram.downloader.R;
import com.instagram.downloader.Utils.DirectoryUtils;
import com.instagram.downloader.Utils.StatusColorState;
import com.instagram.downloader.Utils.Utils;
import com.instagram.downloader.databinding.ActivityPreviewBinding;

import java.io.File;
import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity {
    ActivityPreviewBinding binding;
    private PreviewActivity previewActivity;
    private ArrayList<File> fileArrayList;
    private int Position = 0;
    PreviewAdapter showImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_preview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.preview));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        previewActivity = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileArrayList= (ArrayList<File>) getIntent().getSerializableExtra(getString(R.string.imagedatafile));
            Position = getIntent().getIntExtra(getString(R.string.position),0);
        }
        initViews();

        DirectoryUtils.statusColorChanger(this , android.R.color.white, StatusColorState.ColorOther);
        Drawable homeIcon = ContextCompat.getDrawable(this, R.drawable.ic_round_arrow_back_menu);
        homeIcon.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN);
        binding.toolbar.setNavigationIcon(homeIcon);
    }

    public void initViews(){
        showImagesAdapter=new PreviewAdapter(this, fileArrayList, PreviewActivity.this);

        this.binding.viewPager.setAdapter(showImagesAdapter);
        this.binding.viewPager.setCurrentItem(Position);

        this.binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Position=arg0;
                System.out.println(getString(R.string.current_position)+Position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });

    }

    public void initDeleteDialog() {
        final BottomSheetDialog dialogSortBy = new BottomSheetDialog(PreviewActivity.this, R.style.SheetDialog);
        dialogSortBy.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSortBy.setContentView(R.layout.dialog_delete);
        dialogSortBy.show();
        TextView textViewDelete = dialogSortBy.findViewById(R.id.textViewDelete);
        textViewDelete.setOnClickListener(view -> {
            boolean delete = fileArrayList.get(Position).delete();
            if (delete){
                fileArrayList.remove(Position);
                showImagesAdapter.notifyDataSetChanged();
                Utils.setToast(previewActivity,getResources().getString(R.string.file_deleted));
                if (fileArrayList.size()==0){
                    onBackPressed();
                }
            }

            dialogSortBy.dismiss();

        });

        TextView textViewCancel = dialogSortBy.findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(view -> dialogSortBy.dismiss());
    }

    @Override
    protected void onResume() {
        super.onResume();
        previewActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.action_delete) {
            initDeleteDialog();
            return true;
        } else if (itemId == R.id.action_share) {
            shareView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareView() {
        if (fileArrayList.get(Position).getName().contains(getString(R.string.mp4))){
            Log.d("SSSSS", "onClick: "+fileArrayList.get(Position)+"");
            shareVideo(previewActivity,fileArrayList.get(Position).getPath());
        }else {
            shareImage(previewActivity,fileArrayList.get(Position).getPath());
        }
    }

}
