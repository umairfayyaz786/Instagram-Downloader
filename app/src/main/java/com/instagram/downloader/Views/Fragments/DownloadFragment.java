package com.instagram.downloader.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.instagram.downloader.Adapters.FileAdapter;
import com.instagram.downloader.Interface.FileClickInterface;
import com.instagram.downloader.Views.Activities.PreviewActivity;
import com.instagram.downloader.Utils.DirectoryUtils;
import com.instagram.downloader.databinding.FragmentDownloadBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class DownloadFragment extends Fragment implements FileClickInterface {
    private FileAdapter fileAdapter;
    private ArrayList<File> fileArrayList;
    FragmentDownloadBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDownloadBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            getAllFiles();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        getAllFiles();
    }

    private void getAllFiles() {
        fileArrayList = new ArrayList<>();
        File[] listFiles = DirectoryUtils.DIRECTORY_FOLDER.listFiles();
        if (listFiles != null) {
            fileAdapter = new FileAdapter(requireActivity(), fileArrayList, DownloadFragment.this);
            binding.recyclerViewFile.setAdapter(fileAdapter);
            this.fileArrayList.addAll(Arrays.asList(listFiles));
            if (listFiles.length > 0) {
                binding.linearLayoutPlaceHolder.setVisibility(View.GONE);
                binding.recyclerViewFile.setVisibility(View.VISIBLE);
                return;
            }

            binding.recyclerViewFile.setVisibility(View.GONE);
            binding.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
            return;
        }
        binding.recyclerViewFile.setVisibility(View.GONE);
        binding.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void getPosition(int position, File file) {
        Intent inNext = new Intent(requireActivity(), PreviewActivity.class);
        inNext.putExtra("ImageDataFile", fileArrayList);
        inNext.putExtra("Position", position);
        requireActivity().startActivity(inNext);
    }
}
