package com.example.effectivemobiletest.presentation.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.FragmentProfileBinding
import com.example.effectivemobiletest.presentation.ui.signin.Action
import com.example.effectivemobiletest.presentation.ui.signin.SignInActivity
import java.io.FileNotFoundException


class ProfileFragment : Fragment() {
    private val sharedViewModel: ProfileViewModel by activityViewModels() {
        ProfileViewModelFactory(
            requireActivity().application
        )
    }

    //проводник
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val uri: Uri? = result.data?.getData()
                    sharedViewModel.loadImageFromGallery(uri.toString())
                    sharedViewModel.setUserPhotoJob(uri.toString())
                    Log.d("myLogs", "URI  set: ${uri.toString()}")
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "File not found", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(),"You haven't selected photo", Toast.LENGTH_LONG).show();
            }
            sharedViewModel._action.value = Action.Default
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.apply {
            profileViewModel = sharedViewModel
        }
        binding.setLifecycleOwner(this)

        binding.tvChangePhoto.setOnClickListener {
            loadImage()
        }

        sharedViewModel._action.observe(requireActivity(), {
            when (it) {
                Action.NavigateToSignIn -> {
                    val intent = Intent(requireContext(), SignInActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                    Log.d("myLogs", "MainACtivity is FINISHED")
                }
                Action.Default -> {}
            }
        })

        sharedViewModel.uriPhoto.observe(requireActivity(), {
            binding.photoProfile.setImageURI(Uri.parse(it))
        })


        return binding.root
    }

    private fun loadImage(){
        Log.d("myLogs", "ACTION CHANGEPHOTO")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        launcher.launch(intent)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}