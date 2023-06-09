package com.example.rubberbridge

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database
import com.example.rubberbridge.databinding.FragmentResultOfDealBinding
import java.io.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultOfDeal.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultOfDeal : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentResultOfDealBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_result_of_deal, container, false)
        _binding = FragmentResultOfDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun trySaveResultsToFile(view: View): Boolean {
        val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
        val datab= Database(db)


        var a:Int
        var b:Int
        var c:Int
        var d:Int
        var e:Int

        a=view.findViewById<NumberPicker>(R.id.edit_level).value
        b=view.findViewById<TextView>(R.id.edit_suit).text.toString().toInt()
        c=view.findViewById<NumberPicker>(R.id.edit_result_level).value
        d=view.findViewById<NumberPicker>(R.id.edit_player).value
        e=view.findViewById<NumberPicker>(R.id.edit_double).value

        //datab.insertData(view.findViewById<NumberPicker>(R.id.edit_level).value,view.findViewById<TextView>(R.id.edit_suit).text.toString().toInt(),view.findViewById<NumberPicker>(R.id.edit_result_level).value,view.findViewById<NumberPicker>(R.id.edit_player).value)

        datab.insertData(a,b,c,d,e)

     /*   val letDirectory = File(context?.getFilesDir(), "Rubber")
        var success = true
        if(!letDirectory.exists())
            success = letDirectory.mkdirs()

        val sd2 = File(letDirectory,"Results_file.txt")

        if (!sd2.exists()) {
            success = sd2.createNewFile()
            binding.errorText.setText(getString(R.string.open_file_error))
        }
        if(success) {
            try {
                var result = view.findViewById<NumberPicker>(R.id.edit_level).value.toString() + " "
                result +=view.findViewById<TextView>(R.id.edit_suit).text.toString() + " "
                result +=view.findViewById<NumberPicker>(R.id.edit_result_level).value.toString() + " "
                result += view.findViewById<NumberPicker>(R.id.edit_player).value.toString() + " "


                sd2.appendText("\n")
                sd2.appendText(result)
            } catch (e: Exception) {
                // handle the exception
                success = false
                binding.errorText.setText(getString(R.string.create_directory_error))
            }
        }
        return success*/
        return true
    }
   override fun onResume() {

        super.onResume()


        var numberPicker: NumberPicker = requireView().findViewById(R.id.edit_level)
        numberPicker.setMaxValue(7);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // блокируем появление клавиатуры



         numberPicker = requireView().findViewById(R.id.edit_result_level)
        numberPicker.setMaxValue(13);
        numberPicker.setMinValue(0);
        numberPicker.setValue(7);
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // блокируем появление клавиатуры


        numberPicker = requireView().findViewById(R.id.edit_player)
        numberPicker.setMaxValue(2);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // блокируем появление клавиатуры



       numberPicker = requireView().findViewById(R.id.edit_double)
       numberPicker.setMaxValue(2);
       numberPicker.setMinValue(0);
       numberPicker.setValue(0);
       numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // блокируем появление клавиатуры


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonApproveContract.setOnClickListener {
           if (  binding.editSuit.length() == 0 ) {

               binding.errorText.setText(getString(R.string.not_enough_players))
           } else {
               if(trySaveResultsToFile(view))
                    findNavController().navigate(R.id.action_to_Table)
           }
        }

        binding.buttonClubs.setOnClickListener {
            binding.editSuit.setText("0")
            binding.errorText.setText("крести")
        }

        binding.buttonDiamonds.setOnClickListener {
            binding.editSuit.setText("1")
            binding.errorText.setText("буби")
        }

        binding.buttonHearts.setOnClickListener {
            binding.editSuit.setText("2")
            binding.errorText.setText("черви")
        }

        binding.buttonSpades.setOnClickListener {
            binding.editSuit.setText("3")
            binding.errorText.setText("пики")
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultOfDeal.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultOfDeal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}