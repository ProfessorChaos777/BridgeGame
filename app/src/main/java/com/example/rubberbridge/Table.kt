package com.example.rubberbridge
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database
import com.example.rubberbridge.databinding.FragmentTableBinding
import java.io.File
import java.util.*
import kotlin.math.abs

class Table : Fragment() {

    private var _binding: FragmentTableBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun colorGameZones(robber:Robber)
    {
        if(robber.table.zoneTeam[FIRST_TEAM])
            binding.layoutColumnTopLeft.setBackgroundColor(Color.RED)
        else
            binding.layoutColumnTopLeft.setBackgroundColor(Color.GREEN)

        if(robber.table.zoneTeam[SECOND_TEAM])
            binding.layoutColumnTopRight.setBackgroundColor(Color.RED)
        else
            binding.layoutColumnTopRight.setBackgroundColor(Color.GREEN)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.revertLastGame.setOnClickListener {

            val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
            val datab= Database(db)
            datab.deleteLastRecord()

            val robber:Robber=datab.getRobber()

            binding.columnBottomLeft.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam[FIRST_TEAM]).toString())
            binding.columnBottomRight.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam[SECOND_TEAM]).toString())

            binding.columnTopLeft.setText((robber.table.partPointsTeam[FIRST_TEAM]).toString() + getString(R.string.of_100))
            binding.columnTopRight.setText((robber.table.partPointsTeam[SECOND_TEAM]).toString() + getString(R.string.of_100))

            colorGameZones(robber)



        }

        binding.buttonApproveContract.setOnClickListener {
            findNavController().navigate(R.id.action_Table_to_ResultOfDeal)
        }

        val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
        val datab= Database(db)

        val robber:Robber=datab.getRobber()

        binding.columnTopRight.setText("texts")

        var allPointsTeam1 = robber.table.allPointsTeam[FIRST_TEAM]
        var allPointsTeam2 = robber.table.allPointsTeam[SECOND_TEAM]
        var partPointsTeam1 = robber.table.partPointsTeam[FIRST_TEAM]
        var partPointsTeam2 = robber.table.partPointsTeam[SECOND_TEAM]

        binding.columnBottomLeft.setText(getString(R.string.summ_count) + (allPointsTeam1).toString())
        binding.columnBottomRight.setText(getString(R.string.summ_count) + (allPointsTeam2).toString())

        binding.columnTopLeft.setText((partPointsTeam1).toString() + getString(R.string.of_100))
        binding.columnTopRight.setText((partPointsTeam2).toString() + getString(R.string.of_100))

        colorGameZones(robber)



        val adb = AlertDialog.Builder(context)
        if(robber.table.endGame){
            // заголовок
            adb.setTitle("Игра завершена");
            // сообщение
            adb.setMessage("Команда выиграла со счетом +"+abs(allPointsTeam1-allPointsTeam2));



            adb.setPositiveButton("Да", DialogInterface.OnClickListener(){ dialog, which ->5 })
            adb.setNegativeButton("Нет", DialogInterface.OnClickListener(){ dialog, which ->5 })

            adb.create();
            adb.show();

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
