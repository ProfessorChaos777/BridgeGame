package com.example.rubberbridge

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rubberbridge.databinding.FragmentSetPlayersBinding
import java.io.File



import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetPlayers : Fragment() {

    private var _binding: FragmentSetPlayersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetPlayersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //var text_length = binding.editTextPlayer1.length()
//            val adb = AlertDialog.Builder(context)
//
//            // заголовок
//            adb.setTitle("Игра завершена");
//            // сообщение
//            adb.setMessage("gffdgdfgfdfgf");
//
//
//
//            adb.setPositiveButton("Да",DialogInterface.OnClickListener(){dialog, which ->5 })
//            adb.setNegativeButton("Нет",DialogInterface.OnClickListener(){dialog, which ->5 })
//
//            adb.create();
//            adb.show();
       // }

            if( binding.editTextPlayer1.length() == 0 ||
                binding.editTextPlayer2.length() == 0 ||
                binding.editTextPlayer3.length() == 0 ||
                binding.editTextPlayer4.length() == 0 ) {

                binding.errorText.setText(getString(R.string.not_enough_players))
            }
            else {

                val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
               val datab= Database(db)

              datab.insertTeam(1,binding.editTextPlayer1.text.toString(),binding.editTextPlayer2.text.toString())
             datab.insertTeam(2,binding.editTextPlayer3.text.toString(),binding.editTextPlayer4.text.toString())



                val letDirectory = File(context?.getFilesDir(), "Rubber")
                var success = true
                if(!letDirectory.exists())
                    success = letDirectory.mkdirs()

                if(!success) {
                    binding.errorText.setText(getString(R.string.create_directory_error))
                }
                else {
                    val sd2 = File(letDirectory, "Results_file.txt")

                    if (!sd2.exists()) {
                        success = sd2.createNewFile()
                        binding.errorText.setText(getString(R.string.create_file_error))
                    }
                    if (success) {
                        try {
                            var result = binding.editTextPlayer1.text.toString() + " "
                            result += binding.editTextPlayer2.text.toString() + " "
                            result += binding.editTextPlayer3.text.toString() + " "
                            result += binding.editTextPlayer4.text.toString() + " "

                            sd2.writeText(result)
                        } catch (e: Exception) {
                            // handle the exception
                            success = false
                            binding.errorText.setText(getString(R.string.open_file_error))
                        }
                    }

                    findNavController().navigate(R.id.action_SetPlayers_to_Table)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}