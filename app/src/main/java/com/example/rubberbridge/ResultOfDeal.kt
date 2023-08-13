package com.example.rubberbridge

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
var suit:Int = -1;

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

        var level:Int
        var result_level:Int
        var player:Int
        var double:Int

        level = view.findViewById<NumberPicker>(R.id.edit_level).value
        result_level = view.findViewById<NumberPicker>(R.id.edit_result_level).value
        player = view.findViewById<NumberPicker>(R.id.edit_player).value
        double = view.findViewById<NumberPicker>(R.id.edit_double).value

        datab.insertData(level,suit,result_level,player,double)

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

           // val adb = AlertDialog.Builder(context)



           if (  binding.editSuit.length() == 0 ) {
               binding.errorText.setText(getString(R.string.suit_was_not_entered))
           } else {
               if(trySaveResultsToFile(view))
                    findNavController().navigate(R.id.action_to_Table)
           }
        }

        binding.buttonClubs.setOnClickListener {
            binding.editSuit.setText(getString(R.string.clubs))
            suit = 0;
        }

        binding.buttonDiamonds.setOnClickListener {
            binding.editSuit.setText(getString(R.string.diamonds))
            suit = 1;
        }

        binding.buttonHearts.setOnClickListener {
            binding.editSuit.setText(getString(R.string.hearts))
            suit = 2;
        }

        binding.buttonSpades.setOnClickListener {
            binding.editSuit.setText(getString(R.string.spades))
            suit = 3;
        }

        binding.buttonNotrump.setOnClickListener {
            binding.editSuit.setText(getString(R.string.no_trumps))
            suit = 4;
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