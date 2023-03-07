package com.example.effectivemobiletest.presentation.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

class ProfileFragment: Fragment() {
    private val sharedViewModel: ProfileViewModel by activityViewModels() {ProfileViewModelFactory(requireActivity().application)}

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

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try{
                    val data: Intent? = result.data
                    val uri: Uri? =  data?.getData()
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                    binding.photoProfile.setImageBitmap(bitmap)

                } catch (e: FileNotFoundException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "File not found", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(requireContext(), "You haven't selected photo",Toast.LENGTH_LONG).show();
            }
        }

        sharedViewModel.action.observe(requireActivity(), {
            when(it){
                Action.NavigateToSignIn -> {
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                Action.ChangePhoto -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("image/*")
                    launcher.launch(intent)
                }
            }
        })

        return binding.root
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}