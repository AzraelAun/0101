package com.example.menu


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.menu.databinding.FragmentPracticalBinding
import kotlinx.android.synthetic.main.fragment_practical.*


class Practical : Fragment() {

    lateinit var thisView : ImageView


    data class Game(
        val bin: Int,
        val garbage: List<Int>)

    private val game: MutableList<Game> = mutableListOf(
        Game(bin = R.id.bin1,
            garbage = listOf(R.drawable.bandaid, R.drawable.chemical, R.drawable.cigarette,R.drawable.hairgel,
                R.drawable.injection,R.drawable.lightbulb, R.drawable.medicalglove,R.drawable.nailpolish,
                R.drawable.paint,R.drawable.battery,R.drawable.insecticide,R.drawable.makeup,R.drawable.thermometer)),
        Game(bin = R.id.bin2,
            garbage = listOf(R.drawable.apple,R.drawable.bread,R.drawable.cake,R.drawable.chilli,R.drawable.chocolate,
                R.drawable.corn,R.drawable.crab,R.drawable.dryleaf,R.drawable.eggshell,R.drawable.fishbone,R.drawable.shrimp,
                R.drawable.strawberry,R.drawable.tomatosauce,R.drawable.vegetable,R.drawable.watermelon)),
        Game(bin = R.id.bin3,
            garbage = listOf(R.drawable.basketball,R.drawable.bathtub,R.drawable.broom,R.drawable.chopstick,R.drawable.flowerpot,
                R.drawable.peanut,R.drawable.rooftiles,R.drawable.toiletbowl,R.drawable.toiletpaper,R.drawable.seashell,R.drawable.sponge,
                R.drawable.woodencomb)),
        Game(bin = R.id.bin4,
            garbage = listOf(R.drawable.cap,R.drawable.cloths,R.drawable.lockpad,R.drawable.cup,R.drawable.glassbottle,R.drawable.handbag,
                R.drawable.milkbox,R.drawable.mirror,R.drawable.plasticbottle,R.drawable.plasticcomb,R.drawable.shoe,R.drawable.tin,
                R.drawable.toys,R.drawable.toothpaste,R.drawable.safetypin))
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPracticalBinding>(inflater,
            R.layout.fragment_practical,container,false)

        var count  = 0

        val image : List<Int> = listOf( R.drawable.chemical, R.drawable.cake,R.drawable.cigarette,R.drawable.hairgel,R.drawable.bathtub,
            R.drawable.injection, R.drawable.medicalglove,R.drawable.broom,R.drawable.nailpolish,R.drawable.thermometer,R.drawable.chopstick,
            R.drawable.battery,R.drawable.insecticide,R.drawable.makeup,R.drawable.apple,R.drawable.bread,R.drawable.chilli,R.drawable.chocolate,
            R.drawable.corn,R.drawable.crab,R.drawable.paint,R.drawable.dryleaf,R.drawable.eggshell,R.drawable.fishbone,R.drawable.shrimp,
            R.drawable.strawberry,R.drawable.tomatosauce,R.drawable.flowerpot, R.drawable.vegetable,R.drawable.watermelon,R.drawable.basketball,
            R.drawable.peanut,R.drawable.rooftiles,R.drawable.toiletbowl,R.drawable.handbag,R.drawable.toiletpaper,R.drawable.seashell,
            R.drawable.woodencomb,R.drawable.cap,R.drawable.bandaid,R.drawable.cloths,R.drawable.lockpad,R.drawable.cup,R.drawable.glassbottle,
            R.drawable.lightbulb,R.drawable.milkbox,R.drawable.mirror,R.drawable.plasticbottle,R.drawable.plasticcomb,R.drawable.shoe,R.drawable.tin,
            R.drawable.toys,R.drawable.toothpaste,R.drawable.safetypin,R.drawable.sponge)

        val dragableImage : List<ImageView> = listOf(binding.garbage1, binding.garbage2,
            binding.garbage3, binding.garbage4, binding.garbage5, binding.garbage6, binding.garbage7, binding.garbage8)

        for(items in dragableImage){
            items.setImageResource(image[java.util.Random().nextInt(49) + 1])
        }

        val dropTarget : List<View> = listOf(binding.bin1, binding.bin2,
            binding.bin3, binding.bin4)

        binding.timerText.visibility = View.INVISIBLE
        // Initialize a new instance of
        val builder = AlertDialog.Builder(binding.root.context)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.Start_game))

        // Display a message on alert dialog
        builder.setMessage(getString(R.string.ready))

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.YES)){dialog, which ->

            object : CountDownTimer(30000, 1000) {
                @SuppressLint("StringFormatInvalid")
                override fun onTick(millisUntilFinished: Long) {
                    val timeLeft = millisUntilFinished / 1000
                    binding.timerText.visibility = View.VISIBLE
                    timer_text.text = getString(R.string.timeLeft, timeLeft)

                    if(count == 8) {
                        cancel()
                        onFinish()
                    }
                }

                override fun onFinish() {
                    gameFinished(count)
                }
            }.start()

            val listener = View.OnTouchListener(function = { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                    view.y = motionEvent.rawY - view.height
                    view.x = motionEvent.rawX - view.width/2
                }
                true
            })

            for(dropItem in dropTarget) {
                dropItem.setOnDragListener{ v, event ->
                    for(garbage in game) {
                        var wrongBin = 0
                        var correctWrongBin = 0
                        for(item in garbage.garbage) {
                            when (event.action) {
                                DragEvent.ACTION_DROP -> {
                                    if (v.id == garbage.bin){
                                        if(thisView.drawable.constantState == resources.getDrawable(item,null).constantState) {
                                            val correct = MediaPlayer.create(activity, R.raw.correctsoundeffect)
                                            thisView.visibility = View.INVISIBLE
                                            correct.start()
                                            correctWrongBin += 1
                                            count++
                                        }
                                        else if (thisView.drawable.constantState != resources.getDrawable(item,null).constantState) {
                                           wrongBin++
                                        }
                                    }
                                }
                            }
                        }
                        if (wrongBin > 0 && correctWrongBin == 0){
                            val wrong = MediaPlayer.create(activity, R.raw.wrongsoundeffect)
                            wrong.start()
                        }

                    }
                    true
                }
            }


            for(item in dragableImage) {
                item.setOnTouchListener(listener)
                item.setOnTouchListener { v, arg1 ->

                    // TODO Auto-generated method stub
                    val data = ClipData.newPlainText("", "")
                    val shadow = View.DragShadowBuilder(item)
                    thisView = item
                    v.startDragAndDrop(data, shadow, null, 0)
                    false
                }
            }
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton(getString(R.string.No)){dialog,which ->
            Toast.makeText(activity,getString(R.string.agree),Toast.LENGTH_SHORT).show()
            val action = PracticalDirections.actionPracticalToMenu()
            NavHostFragment.findNavController(this).navigate(action)
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()

        return binding.root
    }

    private fun gameFinished(count : Int){
        Toast.makeText(activity, getString(R.string.finished), Toast.LENGTH_SHORT).show()

        if(count == 8) {
            val action = PracticalDirections.actionPracticalToCompletePractical()
            NavHostFragment.findNavController(this).navigate(action)
        }
        else if(count == 0 || count < 8){
            val action = PracticalDirections.actionPracticalToFailPractical()
            NavHostFragment.findNavController(this).navigate(action)
        }


    }

}
