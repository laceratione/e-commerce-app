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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.databinding.FragmentProfileBinding
import com.example.effectivemobiletest.presentation.ui.signin.SignInActivity
import java.io.FileNotFoundException

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val sharedViewModel: ProfileViewModel by activityViewModels() {
        ProfileViewModelFactory(requireActivity().application)
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
                    showMsg("Файл не найден")
                }
            } else {
                showMsg("Фото не выбрано")
            }
//            sharedViewModel._action.value = Action.Default
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            sharedViewModel.logout()
            navigateToSignIn()
        }
        binding.tvChangePhoto.setOnClickListener {
            loadImage()
        }

        sharedViewModel.uriPhoto.observe(requireActivity(), {
            binding.photoProfile.setImageURI(Uri.parse(it))
        })
    }

    private fun navigateToSignIn(){
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
        Log.d("myLogs", "MainActivity is FINISHED")
    }

    private fun loadImage(){
        Log.d("myLogs", "ACTION CHANGE_PHOTO")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        launcher.launch(intent)
    }

    private fun showMsg(text: String){
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

}