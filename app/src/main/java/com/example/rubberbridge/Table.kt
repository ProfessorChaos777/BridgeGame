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

    companion object {
        var userApprovedEndOfGame:Boolean = false
    }
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

    fun endGame() {
        binding.revertLastGame.setText(R.string.type_players)
        userApprovedEndOfGame = true

        binding.columnBottomLeft.setText("")
        binding.columnBottomRight.setText("")

        binding.columnTopLeft.setText("")
        binding.columnTopRight.setText("")

        val db =
            context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
        val datab = Database(db)
        datab.clearGamesTable()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.revertLastGame.setOnClickListener {
            if(userApprovedEndOfGame || binding.revertLastGame.getText() == "Переход к вводу игроков")
            {
                userApprovedEndOfGame = false
                findNavController().navigate(R.id.action_Table_to_SetPlayers)
            }
            else {
                val db =
                    context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
                val datab = Database(db)
                datab.deleteLastRecord()

                val robber: Robber = datab.getRobber()

                binding.columnBottomLeft.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam[FIRST_TEAM]).toString())
                binding.columnBottomRight.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam[SECOND_TEAM]).toString())

                binding.columnTopLeft.setText(
                    (robber.table.partPointsTeam[FIRST_TEAM]).toString() + getString(
                        R.string.of_100
                    )
                )
                binding.columnTopRight.setText(
                    (robber.table.partPointsTeam[SECOND_TEAM]).toString() + getString(
                        R.string.of_100
                    )
                )

                colorGameZones(robber)

                binding.revertLastGame.setText(R.string.enter_result)
                binding.buttonApproveContract.setEnabled(true);
                userApprovedEndOfGame = false

                if( robber.table.allPointsTeam[FIRST_TEAM] == 0 && robber.table.allPointsTeam[SECOND_TEAM] == 0 &&
                    robber.table.partPointsTeam[FIRST_TEAM] == 0 && robber.table.partPointsTeam[SECOND_TEAM] == 0 )
                {
                    binding.revertLastGame.setText(R.string.type_players);
                }
            }
        }

        binding.buttonApproveContract.setOnClickListener {
            userApprovedEndOfGame = false
            findNavController().navigate(R.id.action_Table_to_ResultOfDeal)
            binding.buttonApproveContract.setEnabled(true);
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

        if(allPointsTeam1 == 0 && allPointsTeam2 == 0 &&
            partPointsTeam1 == 0 && partPointsTeam2 == 0 )
        {
            binding.revertLastGame.setText(R.string.type_players)
        }

        val pop_up_end_game = AlertDialog.Builder(context)
        if(robber.table.endGame){
            // заголовок
            pop_up_end_game.setTitle("Игра завершена");
            // сообщение
            pop_up_end_game.setMessage("Команда выиграла со счетом +"+abs(allPointsTeam1-allPointsTeam2) + "\n Начать новую игру?");
            pop_up_end_game.setPositiveButton("Да", DialogInterface.OnClickListener(){
                    dialog, which ->5
                endGame()
            })

            pop_up_end_game.setNegativeButton("Нет", DialogInterface.OnClickListener(){
                    dialog, which ->5
                binding.buttonApproveContract.setEnabled(false);
            })

            pop_up_end_game.create();
            pop_up_end_game.show();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
