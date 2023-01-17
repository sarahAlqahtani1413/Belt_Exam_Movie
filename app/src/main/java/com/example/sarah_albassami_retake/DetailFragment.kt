package com.example.sarah_albassami_retake

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sarah_albassami_retake.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentDetailBinding.inflate(layoutInflater)
        binding.apply {
            tvDetailTitle.text = activity?.intent?.getStringExtra("title")
            tvDetailPlatform.text = activity?.intent?.getStringExtra("platform")
            tvDetailGenre.text = activity?.intent?.getStringExtra("genre")
            activity?.intent?.getStringExtra("short_description")
                ?.let { tvDetailShortDescription.loadData(it, "text/html", "UTF-8") }
            Glide.with(requireContext()).load(activity?.intent?.getStringExtra("image")).into(ivDetailShow)
        }
        return binding.root
    }
}