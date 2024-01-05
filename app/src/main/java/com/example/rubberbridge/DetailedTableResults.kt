package com.example.rubberbridge

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rubberbridge.databinding.FragmentDetailedTableResultsBinding
import java.lang.Math.abs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultOfDeal.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailedTableResults : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDetailedTableResultsBinding? = null

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
        _binding = FragmentDetailedTableResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

   override fun onResume() {

       super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)

        val query = db?.rawQuery("SELECT * FROM games;", null)
        var allPointsTeam1 = 0;

        val games = mutableListOf("list");

        val layout = view.findViewById<TableLayout>(R.id.detailedTable) as LinearLayout

        while (query?.moveToNext() == true) {
            allPointsTeam1++;

            val level: Int = query.getInt(1)
            val suit: Int = query.getInt(2)
            val result: Int = query.getInt(3)
            val team: Int = query.getInt(4)
            val dbl: Int = query.getInt(5)

            //--< Row >--
            //val row: /*@@lvyrxx@@*/android.widget.TableRow? = android.widget.TableRow()
            val row = TableRow(this.context)
            //val tableRow = TableRow(this)
            //< dayNr >
            //val lblDayNr: /*@@xucasf@@*/TextView? = TextView()
            val tv_dynamic = TextView(this.context)
//уровень масть (под контрой) уровень - результат
            tv_dynamic.setText("K" + team.toString() + " " + level.toString() + " " + translateSuit(suit) + translateDouble(dbl) + " - " + translateResult(6 + level - result)) //*cast integer to string
            if(6 + level - result <= 0)
                tv_dynamic.setBackgroundColor(Color.GREEN)
            else
                tv_dynamic.setBackgroundColor(Color.RED)
            val size:Float
            size = 17.0.toFloat()
            tv_dynamic.setTextSize(size)
            row.addView(tv_dynamic)

            layout.addView(row)
        }

    }

    fun translateSuit(suit: Int): String {
        if(suit == 0)
            return getString(R.string.clubs);
        else if(suit == 1)
            return getString(R.string.diamonds);
        else if(suit == 2)
            return getString(R.string.hearts);
        else if(suit == 3)
            return getString(R.string.spades);
        else if(suit == 4)
            return getString(R.string.no_trumps);

        return "";
    }

    fun translateDouble(contra: Int): String {
        if(contra == 1)
            return " " + getString(R.string.contra);
        else if(contra == 2)
            return " " + getString(R.string.redouble);

        return "";
    }

    fun translateResult(result: Int): String {
        if(result == 0)
            return "ровно";
        else if(result < 0)
            return (abs(result)).toString() + " овертрика";
        else if(result > 0)
            return "сели без " + (abs(result)).toString();
        return "";
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