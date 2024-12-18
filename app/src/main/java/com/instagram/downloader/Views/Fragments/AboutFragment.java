package com.instagram.downloader.Views.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.instagram.downloader.Utils.Dialogs.RateDialog;
import com.instagram.downloader.R;
import com.instagram.downloader.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {


    FragmentAboutBinding binding;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAboutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.share.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "Instagram Downloader \n";
            shareMessage = shareMessage + "https://github.com/umairfayyaz786/Instagram-Downloader.git";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        });

        binding.rate.setOnClickListener(v -> new RateDialog(requireActivity(), false).show());

        binding.feedback.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + requireActivity().getString(R.string.email_feedback)));
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_SUBJECT, requireActivity().getString(R.string.app_name));
            startActivity(intent);


        });

        binding.privacyPolicy.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url)))));
//
//        binding.linearLayoutApps.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.developer_account_link)))));
//
//        binding.linearLayoutCode.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.source_code)))));
//
//        binding.linearLayoutPaid.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.paid_apk)))));

    }
}