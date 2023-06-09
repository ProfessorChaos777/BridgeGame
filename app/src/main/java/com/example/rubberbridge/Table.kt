package com.example.rubberbridge

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

//import com.example.rubberbridge.databinding.TableBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */



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
    fun tryGetResultsFromFile(view: View): String {
        val letDirectory = File(context?.getFilesDir(), "Rubber")
        print(context?.getFilesDir().toString())
        var success = true
        if(!letDirectory.exists())
            success = letDirectory.mkdirs()

        val sd2 = File(letDirectory,"Results.txt")

        var result : List<String> = emptyList()

        if (!sd2.exists()) {
            success = sd2.createNewFile()
            binding.errorTextView.setText(getString(R.string.open_file_error))
        }
        if(success) {
            try {
                result = sd2.readLines()
            } catch (e: Exception) {
                // handle the exception
                success = false
                binding.errorTextView.setText(getString(R.string.create_directory_error))
            }
        }

        return result.get(0)
    }
    fun colorGameZones(robber:Robber)
    {
        if(robber.table.zoneTeam1)
            binding.layoutColumnTopLeft.setBackgroundColor(Color.RED)
        else
            binding.layoutColumnTopLeft.setBackgroundColor(Color.GREEN)

        if(robber.table.zoneTeam2)
            binding.layoutColumnTopRight.setBackgroundColor(Color.RED)
        else
            binding.layoutColumnTopRight.setBackgroundColor(Color.GREEN)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.revertLastGame.setOnClickListener {

            var success = true


           /* val letDirectory = File(context?.getFilesDir(), "Rubber")
            if(!letDirectory.exists())
                success = letDirectory.mkdirs()

            val sd2 = File(letDirectory,"Results_file.txt")

            if (!sd2.exists()) {
                success = sd2.createNewFile()
            }
            if(success) {
                try {
                    var lines =  Vector<String>();
                    val robber:Robber=Robber()



                    sd2.readLines().forEach {
                        if(it.length > 3)
                            lines.addElement(it)
                    }

                    if(lines.size > 0) {
                        lines.removeLast();

                        var first:Boolean = true

                        for (line in lines) {

                            if(first) {
                                sd2.writeText(line)
                                sd2.appendText("\n")
                                first = false
                            }
                            else {
                                sd2.appendText(line)

                                val words = line.split("\\s".toRegex()).toTypedArray()

                                val level: Int = words.get(0).toInt()
                                val suit: Int = words.get(1).toInt()
                                val result: Int = words.get(2).toInt()
                                val team: Int = words.get(3).toInt()

                                robber.addGame(Game(team, result, Contract(level, suit,0)))
                            }
                        }*/

                        val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
                        val datab= Database(db)
                        datab.deleteLastRecord()

                        val robber:Robber=datab.getRobber()

                        binding.columnBottomLeft.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam1).toString())
                        binding.columnBottomRight.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam2).toString())

                        binding.columnTopLeft.setText((robber.table.partPointsTeam1).toString() + getString(R.string.of_100))
                        binding.columnTopRight.setText((robber.table.partPointsTeam2).toString() + getString(R.string.of_100))

                        colorGameZones(robber)
              /*      }
                } catch (e: Exception) {
                    // handle the exception
                    success = false
                    //binding.errorTextView.setText(getString(R.string.open_file_or_read_error))
                }
            }*/
        }

        binding.buttonApproveContract.setOnClickListener {
            findNavController().navigate(R.id.action_Table_to_ResultOfDeal)
        }

     /*   val letDirectory = File(context?.getFilesDir(), "Rubber")
        var success = true
        if(!letDirectory.exists())
            success = letDirectory.mkdirs()

        val sd2 = File(letDirectory,"Results_file.txt")

        if (!sd2.exists()) {
            success = sd2.createNewFile()
        }
        if(success) {
            try {
                var first:Boolean = true

                val robber:Robber=Robber()

                sd2.readLines().forEach {

                    val str:String = it
                    if(str.length > 4) {
                        val words = it.split("\\s".toRegex()).toTypedArray()

                        if (first) {
                            binding.firstPair.setText(words.get(0) + "/" + words.get(1))
                            binding.secondPair.setText(words.get(2) + "/" + words.get(3))
                            first = false
                        } else {

                            val level: Int = words.get(0).toInt()
                            val suit: Int = words.get(1).toInt()
                            val result: Int = words.get(2).toInt()
                            val team: Int = words.get(3).toInt()

                            robber.addGame(Game(team, result, Contract(level, suit, 0)))
                        }
                    }
                }*/

                val db = context?.openOrCreateDatabase("app.db", AppCompatActivity.MODE_PRIVATE, null)
                val datab= Database(db)

                val robber:Robber=datab.getRobber()


                binding.columnTopRight.setText("texts")

                binding.columnBottomLeft.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam1).toString())
                binding.columnBottomRight.setText(getString(R.string.summ_count) + (robber.table.allPointsTeam2).toString())

                binding.columnTopLeft.setText((robber.table.partPointsTeam1).toString() + getString(R.string.of_100))
                binding.columnTopRight.setText((robber.table.partPointsTeam2).toString() + getString(R.string.of_100))

                colorGameZones(robber)

          /*  } catch (e: Exception) {
                // handle the exception
                success = false
                //binding.errorText.setText(getString(R.string.create_directory_error))
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
